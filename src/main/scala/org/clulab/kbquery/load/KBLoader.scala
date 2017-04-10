package org.clulab.kbquery.load

import scala.collection.JavaConverters._
import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging

import scalikejdbc._
import scalikejdbc.config._

import org.clulab.kbquery.msg._

/**
  * Singleton app to load data into the KBQuery DB.
  *   Written by: Tom Hicks. 3/28/2017.
  *   Last Modified: Add index to and fill keys table.
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
    val srcMetaInfo = config.getList("app.loader.sources")
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
    checksOFF                               // turn off slow DB validation
    sql"DROP TABLE IF EXISTS `TKEYS`".execute.apply()
    sql"DROP TABLE IF EXISTS `ENTRIES`".execute.apply()
    sql"DROP TABLE IF EXISTS `SOURCES`".execute.apply()
    commitChanges                           // commit the table drop
  }

  /** Turn off DB checks during inserts to prevent massive slowdown. */
  def checksOFF: Unit = {
    sql"SET FOREIGN_KEY_CHECKS=0".execute.apply()
    sql"SET UNIQUE_CHECKS=0".execute.apply()
    sql"SET AUTOCOMMIT=0".execute.apply()
  }

  /** Turn off DB checks during inserts to prevent massive slowdown. */
  def checksON: Unit = {
    sql"SET FOREIGN_KEY_CHECKS=1".execute.apply()
    sql"SET UNIQUE_CHECKS=1".execute.apply()
    sql"SET AUTOCOMMIT=1".execute.apply()
  }

  /** Commit any previous changes while autocommit was off. */
  def commitChanges: Unit = {
    sql"COMMIT".execute.apply()             // commit any previous changes
  }

  /** Commit any previous changes while autocommit was off, restore normal DB validation. */
  def commitChangesAndRestoreChecks: Unit = {
    commitChanges                           // commit any previous changes
    checksON                                // and restore normal checking
  }

  /** Create the DB tables. */
  def createTables: Unit = {
    checksOFF                               // turn off slow DB validation

    sql"""
CREATE TABLE `SOURCES` (
  `uid` int(11) NOT NULL,
  `namespace` varchar(20) NOT NULL,
  `filename` varchar(60) NOT NULL,
  `label` varchar(40) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    """.execute.apply()

    sql"""
CREATE TABLE `ENTRIES` (
  `uid` INT NOT NULL AUTO_INCREMENT,
  `text` varchar(80) NOT NULL,
  `namespace` varchar(40) NOT NULL,
  `id` varchar(80)  NOT NULL,
  `label` varchar(40)  NOT NULL,
  `is_gene_name` TINYINT(1) NOT NULL,
  `is_short_name` TINYINT(1) NOT NULL,
  `species` varchar(256) NOT NULL,
  `priority` INT NOT NULL,
  `source_ndx` INT NOT NULL,
  PRIMARY KEY (uid),
  INDEX `text_ndx` (`text`),
  INDEX `namespace_ndx` (`namespace`),
  INDEX `id_ndx` (`id`),
  INDEX `label_ndx` (`label`),
  KEY `SRC_FK`(`source_ndx`),
  CONSTRAINT SRC_FK FOREIGN KEY(`source_ndx`) REFERENCES `SOURCES`(`uid`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    """.execute.apply()

    sql"""
CREATE TABLE `TKEYS` (
  `text` varchar(80) NOT NULL,
  `entry_ndx` INT NOT NULL,
  INDEX `tkey_ndx` (`text`),
  KEY `ENT_FK`(`entry_ndx`),
  CONSTRAINT ENT_FK FOREIGN KEY(`entry_ndx`) REFERENCES `ENTRIES`(`uid`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    """.execute.apply()

    commitChanges                           // commit table creations
  }


  /** Load the Sources table with the information extracted from the configuration file. */
  def loadSources (implicit session:DBSession = AutoSession): Unit = {
    checksOFF                               // turn off slow DB validation
    sourcesConfiguration.sources.foreach { src =>
      sql"""insert into Sources values (
              ${src.id}, ${src.namespace}, ${src.filename}, ${src.label})""".update.apply()
    }
    commitChanges                           // commit insertions
  }


  /** Use the Sources configuration to find and load the configured KB files. */
  def loadFiles: Int = {
    var total = 0
    checksOFF                               // turn off slow DB validation
    sourcesConfiguration.sources.foreach { kbInfo =>
      EntryBatcher.resetFileCount
      val kbTypeCode = KBFileLoader.loadFile(kbInfo)
      val fileCnt = EntryBatcher.resetFileCount
      total = EntryBatcher.flushBatch
      commitChanges                         // commit insertions for last KB
      if (Verbose) {
        val filename = kbInfo.filename
        val kbType = if (kbTypeCode == MultiLabel) "multi-source" else "single-source"
        logger.info(
          s"Finished loading ${fileCnt} entries from ${kbType} KB file '$filename'. Total loaded: $total")
      }
    }
    commitChangesAndRestoreChecks           // commit changes and restore DB validation
    total                                   // return count of total entries loaded
  }

  /** Load the given sequence of entries to the database. This may happen
    * immediately or entries may be batched for later writing, determined by
    * the batch size configuration parameter.
    */
  def loadEntries (entries: Seq[KBEntry]): Unit = EntryBatcher.addBatch(entries)


  /** Stream over the entries, transform each entry.text into one or more keys and
      then store the keys into the Keys table. */
  def fillKeysTable: Unit = {
    checksOFF                               // turn off slow DB validation
    // The following SQL will work IFF the key transform you want is IDENTITY:
    // sql"insert into TKEYS (entry_ndx, text) select uid, text from ENTRIES".execute.apply()
    sql"select uid, text from ENTRIES".foreach { rs =>
      val uid = rs.int("uid")
      val text = rs.string("text")
      KBFileLoader.generateKeys(text).foreach { tkey =>
        sql"""insert into TKEYS values(${tkey}, ${uid})""".execute.apply()
      }
    }
    commitChangesAndRestoreChecks           // commit changes and restore DB validation
  }

  /** Execute SQL command to cleanly shutdown the DB. Only useful for embedded DBs. */
  // def shutdown: Unit = { sql"shutdown".execute.apply() }

  /** Called by the batching system to immediately write the given batch of
      entries to the database. */
  def writeBatch (entries: Seq[KBEntry]): Int = {
    val batchData: Seq[Seq[Any]] = entries.map(_.toSeq)
    checksOFF                               // turn off slow DB validation
    sql"insert into Entries values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)".batch(batchData: _*).apply()
    commitChanges                           // commit insertions
    return entries.size
  }


  //
  // MAIN: Run the actions sequentially to execute the loading steps.
  //
  dropTables
  createTables
  loadSources                               // load the KB meta info table from config
  var entCnt = 0
  entCnt = loadFiles                        // the major work: load all KB data files
  fillKeysTable
  // shutdown                               // close down/cleanup the loader
  if (Verbose)
    logger.info(s"Finished loading all configured KB files. Total entities loaded: $entCnt")
}
