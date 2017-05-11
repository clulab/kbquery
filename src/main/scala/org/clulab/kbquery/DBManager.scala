package org.clulab.kbquery

import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging

import scalikejdbc._
import scalikejdbc.config._

import org.clulab.kbquery.msg._

/**
  * Singleton class implementing the database management backend for this app.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Begin conversion to Scalike: counts and dumps working.
  */
class DBManager (

  /** Application configuration. */
  config: Config

) extends LazyLogging {

  // implicit session provider makes Scalike interface much easier to use
  implicit val session = ReadOnlyAutoSession

  // Initialize JDBC driver & connection pool
  Class.forName(config.getString("db.kbqdb.driver"))
  ConnectionPool.singleton(
    config.getString("db.kbqdb.url"),
    config.getString("db.kbqdb.user"),
    config.getString("db.kbqdb.password")
  )
  // DBs.setup('kbqdb)                         // Use with configured connection pool

  /** Close down the database and cleanup before an exit. */
  def close: Unit = DBs.closeAll()

  /** Run the given query and return matching KB entries. */
  // def queryToKBEntries (query: Query[Entries, EntryType, Seq]): KBEntries = {
  //   val data = theDB.run(query.result.map(rows => Entries.toKBEntries(rows)))
  //   return Await.result(data, Duration.Inf)
  // }

  /** Return the (possibly empty) set of KB entries for the given ID string, in any namespace. */
  def byId (id:String): KBEntries = {
    // queryToKBEntries(Entries.findById(id))
    KBEntries(List.empty[KBEntry])          // TODO: REPLACE LATER
  }

  /** Return the (possibly empty) set of KB entries for the given namespace and ID string. */
  def byNsAndId (ns:String, id:String): KBEntries = {
    // val nsNdx = namespaceToIndex.getOrElse(ns.toLowerCase, UnknownNamespace)
    // queryToKBEntries(Entries.findByNsAndId(nsNdx, id))
    KBEntries(List.empty[KBEntry])          // TODO: REPLACE LATER
  }

  /** Return the (possibly empty) set of all KB entries exactly matching the given text string. */
  def byText (text:String): KBEntries = {
    // queryToKBEntries(Entries.findByText(text))
    KBEntries(List.empty[KBEntry])          // TODO: REPLACE LATER
  }

  /** Return the (possibly empty) set of all KB entries for the given text string. */
  def byTextSet (textSet: Set[String]): KBEntries = {
    // queryToKBEntries(Entries.findByTextSet(textSet))
    KBEntries(List.empty[KBEntry])          // TODO: REPLACE LATER
  }

  /** Return the (possibly empty) set of textual synonyms for the given NS/ID string. */
  def synonyms (ns:String, id:String): Synonyms = {
    // val nsNdx = namespaceToIndex.getOrElse(ns.toLowerCase, UnknownNamespace)
    // val query = Entries.findSynonyms(nsNdx, id)
    // val data = theDB.run(query.result.map(rows => Entries.toSynonyms(rows)))
    Synonyms(List.empty[String])            // TODO: REPLACE LATER
  }


  /** Count all records in the Entries table. */
  def countEntries: Map[String, Int] = {
    val count: Option[Int] =
      sql"select count(*) as count from Entries".map(rs => rs.int("count")).single.apply()
    Map[String, Int]("count" -> count.getOrElse(0))
  }

  /** Count all records in the Keys table. */
  def countKeys: Map[String, Int] = {
    val count: Option[Int] =
      sql"select count(*) as count from TKeys".map(rs => rs.int("count")).single.apply()
    Map[String, Int]("count" -> count.getOrElse(0))
  }

  /** Count all records in the Labels table. */
  def countLabels: Map[String, Int] = {
    val count: Option[Int] =
      sql"select count(*) as count from Labels".map(rs => rs.int("count")).single.apply()
    Map[String, Int]("count" -> count.getOrElse(0))
  }

  /** Count all records in the Namespaces table. */
  def countNamespaces: Map[String, Int] = {
    val count: Option[Int] =
      sql"select count(*) as count from Namespaces".map(rs => rs.int("count")).single.apply()
    Map[String, Int]("count" -> count.getOrElse(0))
  }

  /** Count all records in the Sources table. */
  def countSources: Map[String, Int] = {
    val count: Option[Int] =
      sql"select count(*) as count from Sources".map(rs => rs.int("count")).single.apply()
    Map[String, Int]("count" -> count.getOrElse(0))
  }


  /** Return the raw entries from the KB. WARNING: HUGE!.*/
  def dumpEntries: KBEntries =
    KBEntries(sql"select * from ENTRIES".map(rs => toKBEntry(rs)).list.apply())

  /** Return all permuted text keys from the KB. WARNING: HUGE!.*/
  def dumpKeys: KBKeys =
    KBKeys(sql"select * from TKEYS".map(rs => toKBKey(rs)).list.apply())

  /** Return all entity labels from the KB. */
  def dumpLabels: KBLabels =
    KBLabels(sql"select * from LABELS".map(rs => toKBLabel(rs)).list.apply())

  /** Return all entity labels from the KB. */
  def dumpNamespaces: KBNamespaces =
    KBNamespaces(sql"select * from NAMESPACES".map(rs => toKBNamespace(rs)).list.apply())

  /** Return all source information entries from the KB. */
  def dumpSources: KBSources =
    KBSources(sql"select * from SOURCES".map(rs => toKBSource(rs)).list.apply())


  /** Return a raw entry object converted from the given database result set. */
  private def toKBEntry (rs: WrappedResultSet): KBEntry =
    KBEntry(rs.int("uid"), rs.string("text"), rs.string("id"),
      rs.boolean("is_gene_name"), rs.boolean("is_short_name"), rs.string("species"),
      rs.int("priority"), rs.int("label_ndx"), rs.int("ns_ndx"), rs.int("source_ndx") )

  /** Return a permuted text key object converted from the given database result set. */
  private def toKBKey (rs: WrappedResultSet): KBKey =
    KBKey(rs.string("text"), rs.int("entry_ndx"))

  /** Return a label object converted from the given database result set. */
  private def toKBLabel (rs: WrappedResultSet): KBLabel =
    KBLabel(rs.int("uid"), rs.string("label"))

  /** Return a namespace object converted from the given database result set. */
  private def toKBNamespace (rs: WrappedResultSet): KBNamespace =
    KBNamespace(rs.int("uid"), rs.string("namespace"))

  /** Return a source object converted from the given database result set. */
  private def toKBSource (rs: WrappedResultSet): KBSource =
    KBSource(rs.int("uid"), rs.string("namespace"), rs.string("filename"), rs.string("label"))

}
