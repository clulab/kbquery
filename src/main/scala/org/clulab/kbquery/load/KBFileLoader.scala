package org.clulab.kbquery.load

import java.io._
import java.util.zip.GZIPInputStream

import scala.io.Source
import com.typesafe.scalalogging.LazyLogging

import org.clulab.kbquery.KBKeyTransforms._
import org.clulab.kbquery.dao._
import org.clulab.kbquery.msg._
import org.clulab.kbquery.msg.Species._

/**
  * Methods and utilities for reading and parsing KB files.
  *   Written by Tom Hicks. 3/29/2017.
  *   Last Modified: Update for keys table.
  */
object KBFileLoader extends LazyLogging {

  /** Map a label string to an abbreviated form. */
  val shortLabel = Map[String, String] (
    ("BioProcess" -> "BP"),
    ("Family" -> "F"),
    ("Gene_or_gene_product" -> "G"),
    ("Cellular_component" -> "CC"),
    ("CellLine" -> "CL"),
    ("CellType" -> "CT"),
    ("Organ" -> "O"),
    ("Simple_chemical" -> "SC"),
    ("Species" -> "SP"),
    ("TissueType" -> "TT")
  )

  /** Load the KB specified by the given KB source information. */
  def loadFile (kbInfo: KBSource): String = {
    if (kbInfo.label == NoImplicitLabel) {  // if not a single source KB
      loadMultiSourceKB(kbInfo)             // process a multi-source KB
      return MultiLabel                     // signal which type of KB was read
    }
    else {
      loadUniSourceKB(kbInfo)               // process a single-source KB
      return UniLabel                       // signal which type of KB was read
    }
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
        KBLoader.loadEntries(Seq(entryFromMultiFields(kbInfo, fields)))
      }
      source.get.close
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
        KBLoader.loadEntries(Seq(entryFromUniFields(kbInfo, fields)))
      }
      source.get.close
    }
  }

  /** Process fields from a single multi-source input record to create zero or more entries.
    *   1st column (0) is the text string,
    *   2nd column (1) is the ID string,
    *   3rd column (2) is the Species string (optional content),
    *   4th column (3) is the Namespace string (required),
    *   5th column (4) is the Label string (optional: may be missing if implicit for entire KB)
    */
  private def entryFromMultiFields (kbInfo: KBSource, fields: Seq[String]): KBEntry = {
    val text = fields(0)
    val id = fields(1)
    val species = if (fields(2) != Species.NoSpeciesValue) fields(2) else Species.Human
    val namespace = fields(3)
    val label = shortLabel.getOrElse(if (fields.size > 4) fields(4) else kbInfo.label, "X")
    KBEntry(0, text, namespace, id, label, false, false, species, OverridePriority, kbInfo.id)
  }

  /** Extract fields to create zero or more entries from a single uni-source input record.
    *   1st column (0) is the text string,
    *   2nd column (1) is the ID string,
    *   3rd column (2) is the Species string (optional content),
    *   4th column (3) is the Namespace string (optional: use if present, else use source info)
    *   5th column (4) is the Label string (ignored: KB has one label type).
    */
  private def entryFromUniFields (kbInfo: KBSource, fields: Seq[String]): KBEntry = {
    val text = fields(0)
    val id = fields(1)
    val species = if (fields.size > 2) fields(2) else NoSpeciesValue
    val namespace = if ((fields.size > 3) && fields(3).nonEmpty) fields(3) else kbInfo.namespace
    val label = shortLabel.getOrElse(kbInfo.label, "X")
    KBEntry(0, text, namespace, id, label, false, false, species, DefaultPriority, kbInfo.id)
  }

  /** Generate a set of key strings from the text field of the given KB entry object. */
  def generateKeys (text: String): Set[String] = {
    applyAllTransforms(DefaultKeyTransforms, text).toSet
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
      logger.error(s"Unable to find or read from KB file '$inFile'. Skipping.")
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
    val text = fields(0)
    if (text.isEmpty || (text.size > MaxFieldSize)) {
      if (ShowTruncated)
        logger.warn(s"Text field must be non-empty or less than $MaxFieldSize characters: '$text'")
      return false
    }
    return fields(1).nonEmpty && fields(3).nonEmpty
  }

  /** Check for required fields in one row of a standard, uni-source input file. */
  private def validateUniFields (fields:Seq[String]): Boolean = {
    val text = fields(0)
    if (text.isEmpty || (text.size > MaxFieldSize)) {
      if (ShowTruncated)
        logger.warn(s"Text field must be non-empty or less than $MaxFieldSize characters: '$text'")
      return false
    }
    return (fields.size >= 2) && fields(1).nonEmpty
  }

}
