package org.clulab.kbquery.load

import scala.collection.JavaConverters._
// import scala.collection.mutable.ListBuffer
import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging

import scalikejdbc._
import scalikejdbc.config._

import org.clulab.kbquery.msg._

/**
  * Singleton app to load data into the KBQuery DB.
  *   Written by: Tom Hicks. 3/28/2017.
  *   Last Modified: Continue switch to ScalikeJDBC: load all tables.
  */
object KBLoader extends App with LazyLogging {

  val argsList = args.toList                // save any command line arguments

  // implicit session provider makes Scalike interface much easier to use
  implicit val session = AutoSession

  // Initialize JDBC driver & connection pool
  Class.forName(config.getString("db.kbqdb.driver"))
  ConnectionPool.singleton(
    config.getString("db.kbqdb.url"),
    config.getString("db.kbqdb.user"),
    config.getString("db.kbqdb.password")
  )
  // DBs.setup('kbqdb)                         // Use with configured connection pool

  /** Read the sources table configuration from the configuration file. */
  val sourcesConfiguration: KBSources = {
    val srcMetaInfo = config.getList("app.sources.metaInfo")
    val sources = srcMetaInfo.iterator().asScala.map { srcLine =>
      val src = srcLine.asInstanceOf[ConfigObject].toConfig
      val id = src.getInt("id")
      val namespace = src.getString("ns")
      val filename = src.getString("filename")
      val label = src.getString("label")
      KBSource(id, namespace, filename, label)
    }.toList
    KBSources(sources)
  }

  /** Drop the tables if they exist. */
  def dropTables: Unit = {
    sql"SET FOREIGN_KEY_CHECKS = 0".execute.apply()
    sql"DROP TABLE IF EXISTS `TKEYS`".execute.apply()
    sql"DROP TABLE IF EXISTS `ENTRIES`".execute.apply()
    sql"DROP TABLE IF EXISTS `SOURCES`".execute.apply()
    sql"SET FOREIGN_KEY_CHECKS = 1".execute.apply()
  }

  /** Create the DB tables. */
  def createTables: Unit = {
    sql"SET FOREIGN_KEY_CHECKS = 0".execute.apply()

    sql"""
CREATE TABLE `SOURCES` (
  `uid` int(11) NOT NULL,
  `namespace` text NOT NULL,
  `filename` text NOT NULL,
  `label` text NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    """.execute.apply()

    sql"""
CREATE TABLE `ENTRIES` (
  `uid` INT NOT NULL AUTO_INCREMENT,
  `text` text NOT NULL,
  `namespace` text NOT NULL,
  `id` text  NOT NULL,
  `label` text  NOT NULL,
  `is_gene_name` TINYINT(1) NOT NULL,
  `is_short_name` TINYINT(1) NOT NULL,
  `species` text NOT NULL,
  `priority` INT NOT NULL,
  `source_ndx` INT NOT NULL,
  PRIMARY KEY (uid),
  KEY `SRC_FK`(`source_ndx`),
  CONSTRAINT SRC_FK FOREIGN KEY(`source_ndx`) REFERENCES `SOURCES`(`uid`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    """.execute.apply()

    sql"SET FOREIGN_KEY_CHECKS = 1".execute.apply()
  }

  /** Load the Sources table with the information extracted from the configuration file. */
  def loadSources (implicit session:DBSession = AutoSession): Unit = {
    sourcesConfiguration.sources.foreach { src =>
      sql"""insert into Sources values (
              ${src.id}, ${src.namespace}, ${src.filename}, ${src.label})""".update.apply()
    }
  }

  /** Use the Sources configuration to find and load the configured KB files. */
  def loadFiles: Unit = {
    sourcesConfiguration.sources.foreach { src => KBFileLoader.loadFile(src) }
  }


  /** Add the given batch of entries to the current KB. */
  def loadBatch (data: Seq[KBEntry]): Unit = {
    val batchData: Seq[Seq[Any]] = data.map(_.toSeq)
    sql"insert into Entries values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)".batch(batchData: _*).apply()
  }

  /** Execute SQL command to cleanly shutdown the DB. Only useful for embedded DBs. */
  // def shutdown: Unit = { sql"shutdown".execute.apply() }

  //
  // MAIN: Run the actions sequentially to execute the loading steps.
  //
  dropTables
  createTables
  loadSources                               // load the KB meta info table from config
  loadFiles                                 // the major work: load all KB data files
  // shutdown                               // close down/cleanup the loader
  if (Verbose)
    logger.info("Finished loading all configured KB files.")
}
