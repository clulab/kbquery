package org.clulab.kbquery

import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging

import scalikejdbc._
import scalikejdbc.config._

import org.clulab.kbquery.msg._

/**
  * Singleton class implementing the database management backend for this app.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Update method to convert result set to KBSource object.
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

  /** The syntax to create entity results, without any constraints or binding parameters. */
  val entityJoin: SQLSyntax = sqls"""
SELECT tk.text, e.uid, e.text, ns.namespace, e.id, lbl.label,
       e.is_gene_name, e.is_short_name, e.species, e.priority, s.filename
FROM Tkeys tk
JOIN Entries e on tk.entry_ndx = e.uid
JOIN Labels lbl on e.label_ndx = lbl.uid
JOIN Namespaces ns on e.ns_ndx = ns.uid
JOIN Sources s on e.source_ndx = s.uid
"""

  /** The syntax to query synonyms by NS/ID, without any constraints or binding parameters. */
  val synonymJoin: SQLSyntax = sqls"""
SELECT tk.text, ns.namespace, e.id
FROM Tkeys tk
JOIN Entries e on tk.entry_ndx = e.uid
JOIN Namespaces ns on e.ns_ndx = ns.uid
"""

  /** Close down the database and cleanup before an exit. */
  def close: Unit = DBs.closeAll()

  /** Return the (possibly empty) set of entities for the given ID string, in any namespace. */
  def byId (id:String): Entities =
    Entities(sql"${entityJoin} WHERE e.id = ${id}".map(rs => toEntity(rs)).list.apply())

  /** Return the (possibly empty) set of entities for the given namespace and ID string. */
  def byNsAndId (ns:String, id:String): Entities = {
    val nsl = ns.toLowerCase
    Entities(sql"${entityJoin} WHERE e.id = ${id} and ns.namespace = ${nsl}".map(rs => toEntity(rs)).list.apply())
  }

  /** Return the (possibly empty) set of all entities exactly matching the given text string. */
  def byText (text:String): Entities =
    Entities(sql"${entityJoin} WHERE tk.text = ${text}".map(rs => toEntity(rs)).list.apply())

  /** Return the (possibly empty) set of all entities for the given text string. */
  def byTextSet (textSet: Set[String]): Entities =
    Entities(sql"${entityJoin} WHERE tk.text in (${textSet})".map(rs => toEntity(rs)).list.apply())

  /** Return the (possibly empty) set of textual synonyms for the given NS/ID string. */
  def synonyms (ns:String, id:String): Synonyms = {
    val nsl = ns.toLowerCase
    val synList = sql"${synonymJoin} WHERE e.id = ${id} and ns.namespace = ${nsl}".map(rs => toSynonym(rs)).list.apply()
    Synonyms(synList.toSet.toList)
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
    KBEntries(sql"select * from Entries".map(rs => toKBEntry(rs)).list.apply())

  /** Return all permuted text keys from the KB. WARNING: HUGE!.*/
  def dumpKeys: KBKeys =
    KBKeys(sql"select * from Tkeys".map(rs => toKBKey(rs)).list.apply())

  /** Return all entity labels from the KB. */
  def dumpLabels: KBLabels =
    KBLabels(sql"select * from Labels".map(rs => toKBLabel(rs)).list.apply())

  /** Return all entity labels from the KB. */
  def dumpNamespaces: KBNamespaces =
    KBNamespaces(sql"select * from Namespaces".map(rs => toKBNamespace(rs)).list.apply())

  /** Return all source information entries from the KB. */
  def dumpSources: KBSources =
    KBSources(sql"select * from Sources".map(rs => toKBSource(rs)).list.apply())


  /** Return a fully expanded entity object converted from the given database result set. */
  private def toEntity (rs: WrappedResultSet): Entity = {
    Entity(
      rs.int("uid"), rs.string("text"), rs.string("namespace"),
      rs.string("id"), rs.string("label"),
      rs.boolean("is_gene_name"), rs.boolean("is_short_name"),
      rs.string("species"), rs.int("priority"), rs.string("filename")
    )
  }

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
  private def toKBSource (rs: WrappedResultSet): KBSource = {
    val tList = rs.string("transforms").trim
    if (tList.nonEmpty)
      KBSource(rs.int("uid"), rs.string("namespace"), rs.string("filename"),
               rs.string("label"), tList.split(",").toList)
    else
      KBSource(rs.int("uid"), rs.string("namespace"), rs.string("filename"), rs.string("label"))
  }

  /** Return a synonym string extracted from the given database result set. */
  private def toSynonym (rs: WrappedResultSet): String = rs.string("text")

}
