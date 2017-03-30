package org.clulab.kbquery

import java.io._
import java.util.zip.GZIPInputStream

import scala.collection.mutable.ListBuffer
import scala.io.Source
import com.typesafe.config._

import org.clulab.kbquery.dao._
import org.clulab.kbquery.msg._

/**
  * Methods and utilities for reading and parsing KB files.
  *   Written by Tom Hicks. 3/29/2017.
  *   Last Modified: Initial port from Reach.
  */
object FileLoader {

  // read configuration from config file
  private val config = ConfigFactory.load()
  private val kbPath = config.getString("db.sources.kbPath")
  private val batchSize = config.getInt("db.sources.batchSize")

  /** File Path to the directory which holds the entity knowledge bases. */
  val KBDirFilePath = "src/main/resources/org/clulab/reach/kb"

  /** Resource Path to the directory which holds the entity knowledge bases. */
  val KBDirResourcePath = "/org/clulab/reach/kb"


  def loadFile (src: KBSource): Unit = {
    System.err.println(s"(loadFile): src=${src}") // REMOVE LATER
    System.err.println(s"(loadFile): kbPath=${kbPath}, batch=${batchSize}") // REMOVE LATER
    var batch = new ListBuffer[EntryType]
    for (n <- 1 to 4004) {
      val ent:EntryType = (s"text$n", "ns", s"PQ$n", src.srcLabel, false, false, "", 0, src.srcId)
      batch += ent
      if ((n % batchSize) == 0) {
        KBLoader.loadBatch(batch.toSeq)
        batch = new ListBuffer[EntryType]
      }
    }
    KBLoader.loadBatch(batch.toSeq)
  }

  /** Return a file for the given filename in the knowledge bases directory. */
  def makeFileInKBDir (filename:String): File = {
    return new File(KBDirFilePath + File.separator + filename)
  }

  /** Return a resource path string for the given filename in the knowledge bases directory. */
  def makePathInKBDir (filename:String): String = {
    return KBDirResourcePath + File.separator + filename
  }

  /** Return a file for the given filename in the current user working directory. */
  def makeFileInUserDir (filename:String): File = {
    return new File(makePathInUserDir(filename))
  }

  /** Return a path string for the given filename in the current user working directory. */
  def makePathInUserDir (filename:String): String = {
    return System.getProperty("user.dir") + File.separator + filename
  }

  /** Return a Scala Source object created from the given resource path string. If the
    * resource path ends with ".gz" the source is created around a gzip input stream. */
  def sourceFromResource (resourcePath:String): Source = {
    val inStream = this.getClass.getResourceAsStream(resourcePath)
    if (resourcePath.endsWith(".gz"))
      Source.fromInputStream(new GZIPInputStream(new BufferedInputStream(inStream)), "utf8")
    else
      Source.fromInputStream(inStream, "utf8")
  }

  /** Convert a single row string from a TSV file to a sequence of string fields. */
  def tsvRowToFields (row:String): Seq[String] = {
    return row.split("\t").map(_.trim)
  }

}
