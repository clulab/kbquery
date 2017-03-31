package org.clulab.kbquery.load

import java.io._
import java.util.zip.GZIPInputStream

import scala.io.Source
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._
import scala.language.postfixOps

import akka.actor.{ ActorRef, ActorSystem, Props, Actor }
import akka.pattern.ask
import akka.util.Timeout

import org.clulab.kbquery.dao._
import org.clulab.kbquery.msg._
import org.clulab.kbquery.msg.Species._
import BatchMessages._

/**
  * Methods and utilities for reading and parsing KB files.
  *   Written by Tom Hicks. 3/29/2017.
  *   Last Modified: Allow multiple entry records per input record.
  */
object KBFileLoader {

  implicit val timeout:Timeout = Timeout(7.days) // only finite durations allowed

  /** An Akka actor which batches up entries, periodically writing them to the DB. */
  val system = ActorSystem("BatchUpEntries")
  val entryBatcher = system.actorOf(Props(classOf[EntryBatcher], BatchSize), "entryBatcher")

  /** Load the KB specified by the given KB source information. */
  def loadFile (kbInfo: KBSource): Unit = {
    if (kbInfo.label == NoImplicitLabel)     // if not a single source KB
      loadMultiSourceKB(kbInfo)
    else
      loadUniSourceKB(kbInfo)
  }

  /** Called by external code to perform any shutdown cleanup actions. */
  def shutdown: Unit = {
    system.terminate                        // shutdown Actor system
  }


  /** Use the given KB source information to load records from a multi-source KB file.
    * The KB file must be a 4-5 column, tab-separated-value (TSV) text file.
    * If filename argument is null or the empty string, skip file loading.
    */
  private def loadMultiSourceKB (kbInfo: KBSource): Unit = {
    val filename = kbInfo.filename
    val source: Option[Source] = sourceFromFilename(kbInfo.filename)
    if (source.isDefined) {
      source.get.getLines.map(tsvRowToFields(_)).filter(validateMultiFields(_)).foreach { fields =>
        entryFromMultiFields(kbInfo, fields).foreach { kbent =>
          Await.result(ask(entryBatcher, BatchAnEntry(kbent)), Duration.Inf)
        }
      }
      source.get.close
      Await.result(ask(entryBatcher, BatchClose), Duration.Inf)
    }
  }

  /** Use the given KB source information to load records from a single-source KB file.
    * The KB file must be a 2-5 column, tab-separated-value (TSV) text file.
    * If filename argument is null or the empty string, skip file loading.
    */
  private def loadUniSourceKB (kbInfo: KBSource): Unit = {
    val filename = kbInfo.filename
    val source: Option[Source] = sourceFromFilename(kbInfo.filename)
    if (source.isDefined) {
      source.get.getLines.map(tsvRowToFields(_)).filter(validateUniFields(_)).foreach { fields =>
        entryFromUniFields(kbInfo, fields).foreach { kbent =>
          Await.result(ask(entryBatcher, BatchAnEntry(kbent)), Duration.Inf)
        }
      }
      source.get.close
      Await.result(ask(entryBatcher, BatchClose), Duration.Inf)
    }
  }

  /** Process fields from a single multi-source input record to create zero or more entries.
    *   1st column (0) is the text string,
    *   2nd column (1) is the ID string,
    *   3rd column (2) is the Species string (optional content),
    *   4th column (3) is the Namespace string (required),
    *   5th column (4) is the Label string (optional: may be missing if implicit for entire KB)
    */
  private def entryFromMultiFields (kbInfo: KBSource, fields: Seq[String]): Seq[EntryType] = {
    val text = fields(0)
    val id = fields(1)
    val species = if (fields(2) != Species.NoSpeciesValue) fields(2) else Species.Human
    val namespace = fields(3)
    val label = if (fields.size > 4) fields(4) else kbInfo.label
    Seq(new EntryType(text, namespace, id, label, false, false, species, OverridePriority, kbInfo.id))
  }

  /** Extract fields to create zero or more entries from a single uni-source input record.
    *   1st column (0) is the text string,
    *   2nd column (1) is the ID string,
    *   3rd column (2) is the Species string (optional content),
    *   4th column (3) is the Namespace string (ignored: KB has one namespace),
    *   5th column (4) is the Label string (ignored: KB has one label type).
    */
  private def entryFromUniFields (kbInfo: KBSource, fields: Seq[String]): Seq[EntryType] = {
    val text = fields(0)
    val id = fields(1)
    val species = if (fields.size > 2) fields(2) else NoSpeciesValue
    val namespace = kbInfo.namespace
    val label = kbInfo.label
    Seq(new EntryType(text, namespace, id, label, false, false, species, DefaultPriority, kbInfo.id))
  }

  /** Return a Scala Source object created from the given filename string and
    * configured KB directory path. If the file path ends with ".gz", the source
    * is created around a gzip input stream.
    */
  private def sourceFromFilename (filename:String): Option[Source] = {
    if ((filename == null) || filename.trim.isEmpty)
      return None
    val inFile = new File(KBDirPath + File.separator + filename)
    if (!inFile.exists || !inFile.canRead) { // check for existing readable file
      System.err.println(s"Error: Unable to find or read from file '$filename'. Skipping.")
      return None
    }
    else {
      val inStream = new FileInputStream(inFile)
      if (filename.endsWith(".gz"))
        Some(Source.fromInputStream(new GZIPInputStream(new BufferedInputStream(inStream)), "utf8"))
      else
        Some(Source.fromInputStream(inStream, "utf8"))
    }
  }

  /** Convert a single row string from a TSV file to a sequence of string fields. */
  def tsvRowToFields (row:String): Seq[String] = {
    return row.split("\t").map(_.trim)
  }

  /** Check for required fields in one row of the multi-source input file. */
  private def validateMultiFields (fields:Seq[String]): Boolean = {
    if (fields.size < 4) return false       // sanity check
    return fields(0).nonEmpty && fields(1).nonEmpty && fields(3).nonEmpty
  }

  /** Check for required fields in one row of a standard, uni-source input file. */
  private def validateUniFields (fields:Seq[String]): Boolean = {
    (fields.size >= 2) && fields(0).nonEmpty && fields(1).nonEmpty
  }

}
