package org.clulab.kbquery.load

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.config._

import slick.jdbc.MySQLProfile.api._

import org.clulab.kbquery.dao._
import org.clulab.kbquery.msg._

/**
  * Singleton app to load data into the KBQuery DB.
  *   Written by: Tom Hicks. 3/28/2017.
  *   Last Modified: Switch to MySQL. Add drop tables method.
  */
object KBLoader extends App {

  /** The Database: configure and open it. */
  val theDB = Database.forConfig("db.kbqdb")

  val argsList = args.toList                // save any command line arguments

  /** Read the sources table configuration from the configuration file. */
  val sourcesConfiguration: List[SourceType] = {
    val srcMetaInfo = config.getList("db.sources.metaInfo")
    srcMetaInfo.iterator().asScala.map { srcLine =>
      val src = srcLine.asInstanceOf[ConfigObject].toConfig
      val id = src.getInt("id")
      val namespace = src.getString("ns")
      val filename = src.getString("filename")
      val label = src.getString("label")
      (id, namespace, filename, label)
    }.toList
  }

  /** Drop the tables if they exist. */
  val dropTables = DBIO.seq (
    sqlu"SET FOREIGN_KEY_CHECKS = 0",
    sqlu"DROP TABLE IF EXISTS TKEYS",
    sqlu"DROP TABLE IF EXISTS ENTRIES",
    sqlu"DROP TABLE IF EXISTS SOURCES"
  )

  /** Create the DB tables from the schema. */
  def createTables: DBIO[Unit] = {
    DBIO.seq ( (Sources.schema ++ Entries.schema).create )
  }

  /** Load the Sources table with the information extracted from the configuration file. */
  def loadSources: DBIO[Unit] = {
    DBIO.seq ( (Sources ++= sourcesConfiguration) )
  }

  /** Use the Sources configuration to find and load the configured KB files. */
  def loadFiles: Unit = {
    val sources:List[KBSource] = sourcesConfiguration.map(row => Sources.toKBSource(row))
    sources.foreach { src => KBFileLoader.loadFile(src) }
  }

  /** Add the given batch of entries to the current KB. Called repeatedly as a co-routine
     by the file loader. */
  def loadBatch (batch: Seq[EntryType]): Unit = {
    Await.result(theDB.run(DBIO.seq((Entries ++= batch))), Duration.Inf)
  }

  /** Execute SQL command to cleanly shutdown the DB. Only useful for embedded DBs. */
  def shutdown: DBIO[Int] = sqlu"""shutdown"""

  //
  // MAIN: Run the actions sequentially to execute the loading steps.
  //
  Await.result(theDB.run(createTables), Duration.Inf)
  Await.result(theDB.run(loadSources), Duration.Inf)
  loadFiles                                 // the major work: load all KB data files
  KBFileLoader.shutdown                     // close down the file loader
  theDB.close                               // close down DB and exit
}
