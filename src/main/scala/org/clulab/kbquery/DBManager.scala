package org.clulab.kbquery

import com.typesafe.config._

import slick.jdbc.HsqldbProfile._
import slick.jdbc.HsqldbProfile.api._

import org.clulab.kbquery.msg._

/**
  * Singleton class implementing the database management backend for this app.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Initial creation.
  */
object DBManager {

  val theDB = Database.forConfig("db.kbqdb")

  /** Close down the database and cleanup before an exit. */
  def close: Unit = theDB.close

  def byNsAndId (ns:String, id:String): KBEntries = {
    return KBEntries(List(                // DUMMY DATA: IMPLEMENT LATER
      KBEntry("textA", ns, s"${id}-A", "Gene_or_gene_product"),
      KBEntry("textB", ns, s"${id}-B", "Gene_or_gene_product"),
      KBEntry("textC", ns, s"${id}-C", "Family")
    ))
  }

  def byText (text:String): KBEntries = {
    return KBEntries(List(                // DUMMY DATA: IMPLEMENT LATER
      KBEntry("AKT1", "uniprot", "P31749", "Gene_or_gene_product")
    ))
  }

  def synonyms (ns:String, id:String): Synonyms = {
    return Synonyms(List(                 // DUMMY DATA: IMPLEMENT LATER
      "AMPKa1", "AMPK-a1", "AMPK-alpha1", "AMPK alpha-1"
    ))
  }

}
