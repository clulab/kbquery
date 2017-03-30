package org.clulab.kbquery.load

import java.io._
import java.util.zip.GZIPInputStream

import scala.collection.mutable.ListBuffer
import scala.io.Source
import com.typesafe.config._

import org.clulab.kbquery.dao._
import org.clulab.kbquery.msg._
import org.clulab.kbquery.msg.Species._

/**
  * Methods and utilities for reading and parsing KB files.
  *   Written by Tom Hicks. 3/29/2017.
  *   Last Modified: Continue building: file paths.
  */
object FileLoader {

  /** Load the KB specified by the given KB source information. */
  def loadFile (kbsrc: KBSource): Unit = {
    if (kbsrc.label == NoImplicitLabel)     // if not a single source KB
      loadMultiSourceKB(kbsrc)
    else
      loadUniSourceKB(kbsrc)
  }

  /**
    * Load this KB from the given 4-5 column, tab-separated-value (TSV) text file.
    *   1st column (0) is the text string,
    *   2nd column (1) is the ID string,
    *   3rd column (2) is the Species string (optional content),
    *   4th column (3) is the Namespace string (required),
    *   5th column (4) is the Label string (optional: may be missing if implicit for entire KB)
    * If filename argument is null or the empty string, skip file loading.
    */
  private def loadMultiSourceKB (src: KBSource) = {
    val filename = src.filename
    if ((filename != null) && !filename.trim.isEmpty) { // skip loading if filename missing
      val kbPath = makePathInKBDir(filename)
      // TODO: Check for file existance: skip if not LATER
      val source = sourceFromFile(kbPath)
      source.getLines.map(tsvRowToFields(_)).filter(validateMultiFields(_)).foreach { fields =>
        processMultiFields(src, fields)
      }
      source.close()
    }
  }

  /**
    * Load this KB from the given 2-5 column, tab-separated-value (TSV) text file.
    *   1st column (0) is the text string,
    *   2nd column (1) is the ID string,
    *   3rd column (2) is the Species string (optional content),
    *   4th column (3) is the Namespace string (ignored: KB has one namespace),
    *   5th column (4) is the Label string (ignored: KB has one label type).
    * If filename argument is null or the empty string, skip file loading.
    */
  private def loadUniSourceKB (src: KBSource) = {
    val filename = src.filename
    if ((filename != null) && !filename.trim.isEmpty) { // skip loading if filename missing
      val kbPath = makePathInKBDir(filename)
      // TODO: Check for file existance: skip if not LATER
      val source = sourceFromFile(kbPath)
      source.getLines.map(tsvRowToFields(_)).filter(validateUniFields(_)).foreach { fields =>
        processUniFields(src, fields)
      }
      source.close()
    }
  }

  /** Return a resource path string for the given filename in the knowledge bases directory. */
  def makePathInKBDir (filename:String): String = {
    return KBDirPath + File.separator + filename
  }

  /** Extract fields for multi-source input file and process them as needed. */
  private def processMultiFields (src: KBSource, fields: Seq[String]): Unit = {
    val text = fields(0)
    val id = fields(1)
    val species = if (fields(2) != Species.NoSpeciesValue) fields(2) else Species.Human
    val namespace = fields(3)
    val label = if (fields.size > 4) fields(5) else src.label
    val kbe = KBEntry(text, namespace, id, label, false, false, species, OverridePriority, src.id)
    // TODO: save the new record LATER
  }

  /** Extract fields for uni-source input file and process them as needed. */
  private def processUniFields (src: KBSource, fields: Seq[String]): Unit = {
    val text = fields(0)
    val id = fields(1)
    val species = if (fields.size > 2) fields(2) else NoSpeciesValue
    val namespace = src.namespace
    val label = src.label
    val kbe = KBEntry(text, namespace, id, label, false, false, species, DefaultPriority, src.id)
    // TODO: save the new record LATER
  }

  /** Return a Scala Source object created from the given resource path string. If the
    * resource path ends with ".gz" the source is created around a gzip input stream. */
  def sourceFromFile (filePath:String): Source = {
    val inStream = new FileInputStream(filePath)
    if (filePath.endsWith(".gz"))
      Source.fromInputStream(new GZIPInputStream(new BufferedInputStream(inStream)), "utf8")
    else
      Source.fromInputStream(inStream, "utf8")
  }

  /** Convert a single row string from a TSV file to a sequence of string fields. */
  def tsvRowToFields (row:String): Seq[String] = {
    return row.split("\t").map(_.trim)
  }

  /** Check for required fields in one row of the multi-source input file. */
  private def validateMultiFields (fields:Seq[String]): Boolean = {
    // LATER: REQUIRE the Label field (field 5)??
    if (fields.size < 4) return false       // sanity check
    return fields(0).nonEmpty && fields(1).nonEmpty && fields(3).nonEmpty
  }

  /** Check for required fields in one row of a standard, uni-source input file. */
  private def validateUniFields (fields:Seq[String]): Boolean = {
    (fields.size >= 2) && fields(0).nonEmpty && fields(1).nonEmpty
  }

}

// var batch = new ListBuffer[EntryType]
// for (n <- 1 to 4004) {
//   val ent:EntryType = (s"text$n", "ns", s"PQ$n", src.label, false, false, "", 0, src.id)
//   batch += ent
//   if ((n % batchSize) == 0) {
//     KBLoader.loadBatch(batch.toSeq)
//     batch = new ListBuffer[EntryType]
//   }
// }
// KBLoader.loadBatch(batch.toSeq)
