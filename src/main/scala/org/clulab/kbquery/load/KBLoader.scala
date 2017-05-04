package org.clulab.kbquery.load

import scala.collection.JavaConverters._
import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging

import scalikejdbc._
import scalikejdbc.config._

import org.clulab.kbquery.KBKeyTransforms._
import org.clulab.kbquery.dao._
import org.clulab.kbquery.msg._

/**
  * Singleton app to load data into the KBQuery DB.
  *   Written by: Tom Hicks. 3/28/2017.
  *   Last Modified: Update for label table.
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

  /** Read the labels table configuration from the configuration file. */
  val labelsConfiguration: KBLabels = {
    val lblMetaInfo = config.getList("app.loader.labels")
    val labels = lblMetaInfo.iterator().asScala.map { lblLine =>
      val lbl = lblLine.asInstanceOf[ConfigObject].toConfig
      val id = lbl.getInt("id")
      val label = lbl.getString("label")
      KBLabel(id, label)
    }.toList
    KBLabels(labels)
  }

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
    sql"DROP TABLE IF EXISTS `LABELS`".execute.apply()
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
CREATE TABLE `LABELS` (
  `uid` int(11) NOT NULL,
  `label` varchar(40) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    """.execute.apply()

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
  `is_gene_name` TINYINT(1) NOT NULL,
  `is_short_name` TINYINT(1) NOT NULL,
  `species` varchar(256) NOT NULL,
  `priority` INT NOT NULL,
  `label_ndx` INT  NOT NULL,
  `source_ndx` INT NOT NULL,
  PRIMARY KEY (uid),
  INDEX `text_ndx` (`text`),
  INDEX `namespace_ndx` (`namespace`),
  INDEX `id_ndx` (`id`),
  KEY `LBL_FK`(`label_ndx`),
  CONSTRAINT LBL_FK FOREIGN KEY(`label_ndx`) REFERENCES `LABELS`(`uid`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
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


  /** Load the Labels table with the information extracted from the configuration file. */
  def loadLabels (implicit session:DBSession = AutoSession): Unit = {
    checksOFF                               // turn off slow DB validation
    labelsConfiguration.labels.foreach { lbl =>
      sql"""insert into Labels values (${lbl.id}, ${lbl.label})""".update.apply()
    }
    commitChanges                           // commit insertions
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
    if (Verbose)  logger.info("Filling Keys table....")
    checksOFF                               // turn off slow DB validation
    sql"select uid, text from ENTRIES".fetchSize(BatchSize).foreach { rs =>
      KeyBatcher.addBatch(generateKeyRows(rs.int("uid"), rs.string("text")))
    }
    KeyBatcher.flushBatch
    commitChangesAndRestoreChecks           // commit changes and restore DB validation
  }

  /** Return a sequence of key row objects created from the given UID and text string. */
  def generateKeyRows (uid: Int, text: String): Seq[KBKey] = {
    val textSet = applyAllTransforms(DefaultKeyTransforms, text).toSet.toSeq
    textSet.map { tkey => KBKey(tkey, uid) }
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

  /** Called by the batching system to immediately write the given batch of
      keys to the database. */
  def writeKeyBatch (keyRows: Seq[KBKey]): Int = {
    val batchData: Seq[Seq[Any]] = keyRows.map(_.toSeq)
    checksOFF                               // turn off slow DB validation
    sql"insert into TKEYS values(?, ?)".batch(batchData: _*).apply()
    commitChanges                           // commit insertions
    return keyRows.size
  }


  //
  // MAIN: Run the actions sequentially to execute the loading steps.
  //
  dropTables
  createTables
  loadLabels                                // load the KB labels table from config
  loadSources                               // load the KB meta info table from config
  var entCnt = 0
  entCnt = loadFiles                        // the major work: load all KB data files
  fillKeysTable
  // shutdown                               // close down/cleanup the loader
  if (Verbose)
    logger.info(s"Finished loading all configured KB files. Total entities loaded: $entCnt")
}
