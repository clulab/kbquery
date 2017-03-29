package org.clulab.kbquery

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.config._

import slick.jdbc.HsqldbProfile.api._

import org.clulab.kbquery.msg._
import org.clulab.kbquery.dao.{Entries, Sources}

/**
  * Singleton class implementing the database management backend for this app.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Update for new dao package.
  */
object DBManager {

  val theDB = Database.forConfig("db.kbqdb")

  /** Close down the database and cleanup before an exit. */
  def close: Unit = theDB.close

  /** Return the (possibly empty) set of KB entries for the given namespace and ID string. */
  def byNsAndId (ns:String, id:String): KBEntries = {
    return KBEntries(List(                // DUMMY DATA: IMPLEMENT LATER
      KBEntry("textA", ns, s"${id}-A", "Gene_or_gene_product"),
      KBEntry("textB", ns, s"${id}-B", "Gene_or_gene_product"),
      KBEntry("textC", ns, s"${id}-C", "Family")
    ))
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
    val data = theDB.run(Entries.result.map(_.map(row => Entries.toKBEntry(row)).toList))
    val kbeList = Await.result(data, Duration.Inf)
    return KBEntries(kbeList)
  }

  /** Return all source information entries from the KB. */
  def dumpSources: KBSources = {
    val data = theDB.run(Sources.result.map(_.map(row => Sources.toKBSource(row)).toList))
    val srcList = Await.result(data, Duration.Inf)
    return KBSources(srcList)
  }

}
