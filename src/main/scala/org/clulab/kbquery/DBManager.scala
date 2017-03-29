package org.clulab.kbquery

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.config._

import slick.jdbc.HsqldbProfile.api._

import org.clulab.kbquery.msg._
import org.clulab.kbquery.dao._

/**
  * Singleton class implementing the database management backend for this app.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Add/use general query run method. Implement byNsAndId.
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


  /** Return the (possibly empty) set of KB entries for the given namespace and ID string. */
  def byNsAndId (ns:String, id:String): KBEntries = {
    val query = Entries.findByNsAndId(ns, id)
    queryToKBEntries(query)
  }

  /** Return the (possibly empty) set of all KB entries for the given text string. */
  def byText (text:String): KBEntries = {
    return KBEntries(List(                // DUMMY DATA: IMPLEMENT LATER
      KBEntry("AKT1", "uniprot", "P31749", "Gene_or_gene_product")
    ))
  }

  /** Return the (possibly empty) set of textual synonyms for the given NS/ID string. */
  def synonyms (ns:String, id:String): Synonyms = {
    return Synonyms(List(                 // DUMMY DATA: IMPLEMENT LATER
      "AMPKa1", "AMPK-a1", "AMPK-alpha1", "AMPK alpha-1"
    ))
  }

  /** Return all bioentity entries from the KB. */
  def dumpEntries: KBEntries = {
    val data = theDB.run(Entries.result.map(rows => Entries.toKBEntries(rows)))
    return Await.result(data, Duration.Inf)
  }

  /** Return all source information entries from the KB. */
  def dumpSources: KBSources = {
    val data = theDB.run(Sources.result.map(rows => Sources.toKBSources(rows)))
    return Await.result(data, Duration.Inf)
  }

}
