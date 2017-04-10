package org.clulab.kbquery

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.config._

import slick.jdbc.MySQLProfile.api._

import org.clulab.kbquery.msg._
import org.clulab.kbquery.dao._

/**
  * Singleton class implementing the database management backend for this app.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Update for keys table.
  */
object DBManager {

  /** The Slick database manipulated by this manager class. */
  val theDB = Database.forConfig("db.kbqdb")

  /** Close down the database and cleanup before an exit. */
  def close: Unit = theDB.close

  /** Run the given query and return matching KB entries. */
  def queryToKBEntries (query: Query[Entries, EntryType, Seq]): KBEntries = {
    val data = theDB.run(query.result.map(rows => Entries.toKBEntries(rows)))
    return Await.result(data, Duration.Inf)
  }

  /** Return the (possibly empty) set of KB entries for the given ID string, in any namespace. */
  def byId (id:String): KBEntries = {
    queryToKBEntries(Entries.findById(id))
  }

  /** Return the (possibly empty) set of KB entries for the given namespace and ID string. */
  def byNsAndId (ns:String, id:String): KBEntries = {
    queryToKBEntries(Entries.findByNsAndId(ns, id))
  }

  /** Return the (possibly empty) set of all KB entries exactly matching the given text string. */
  def byText (text:String): KBEntries = {
    queryToKBEntries(Entries.findByText(text))
  }

  /** Return the (possibly empty) set of all KB entries for the given text string. */
  def byTextSet (textSet: Set[String]): KBEntries = {
    queryToKBEntries(Entries.findByTextSet(textSet))
  }

  /** Return the (possibly empty) set of textual synonyms for the given NS/ID string. */
  def synonyms (ns:String, id:String): Synonyms = {
    val query = Entries.findSynonyms(ns, id)
    val data = theDB.run(query.result.map(rows => Entries.toSynonyms(rows)))
    return Await.result(data, Duration.Inf)
  }

  /** Count all records in the Entries table. */
  def countEntries: Map[String, Int] = {
    val count = theDB.run(Entries.length.result.map(c => Map[String, Int]("count" -> c)))
    return Await.result(count, Duration.Inf)
  }

  /** Count all records in the Keys table. */
  def countKeys: Map[String, Int] = {
    val count = theDB.run(Keys.length.result.map(c => Map[String, Int]("count" -> c)))
    return Await.result(count, Duration.Inf)
  }

  /** Count all records in the Sources table. */
  def countSources: Map[String, Int] = {
    val count = theDB.run(Sources.length.result.map(c => Map[String, Int]("count" -> c)))
    return Await.result(count, Duration.Inf)
  }

  /** Return all bioentity entries from the KB. WARNING: HUGE!.*/
  def dumpEntries: KBEntries = {
    val data = theDB.run(Entries.result.map(rows => Entries.toKBEntries(rows)))
    return Await.result(data, Duration.Inf)
  }

  /** Return all permuted text keys from the KB. WARNING: HUGE!.*/
  def dumpKeys: KBKeys = {
    val data = theDB.run(Keys.result.map(rows => Keys.toKBKeys(rows)))
    return Await.result(data, Duration.Inf)
  }

  /** Return all source information entries from the KB. */
  def dumpSources: KBSources = {
    val data = theDB.run(Sources.result.map(rows => Sources.toKBSources(rows)))
    return Await.result(data, Duration.Inf)
  }

}
