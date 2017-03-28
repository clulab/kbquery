package org.clulab.kbquery

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.config._

import slick.jdbc.HsqldbProfile.api._

import org.clulab.kbquery.msg._
import org.clulab.kbquery.Entries._
import org.clulab.kbquery.Sources._

/**
  * Singleton class implementing the database management backend for this app.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Add and echo test data using futures.
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


  def dummyInit: Unit = {
    val setup: DBIO[Unit] = DBIO.seq (
      // create the tables from the DDL
      (Sources.schema ++ Entries.schema).create,

      // create/insert some dummy data:
      Sources ++= Seq (
        (0, "UNK", ""),
        (1, "Uniprot", "uniprot-proteins.tsv.gz"),
        (2, "PFAM", "PFAM-families.tsv.gz"),
        (3, "PubChem", "PubChem.tsv.gz")
      ),

      Entries ++= Seq (
        ("AKT1", "uniprot", "P31749", "Gene_or_gene_product", true, false, "Human", 0, 1),
        ("AKT1", "uniprot", "P31749", "Gene_or_gene_product", true, false, "Homo sapiens", 0, 1),
        ("PKB", "uniprot", "P31749", "Gene_or_gene_product", true, true, "Human", 0, 1),
        ("PKB", "uniprot", "P31749", "Gene_or_gene_product", true, true, "Homo sapiens", 0, 1),
        ("PKB alpha", "uniprot", "P31749", "Gene_or_gene_product", false, true, "Human", 0, 1),
        ("PKB alpha", "uniprot", "P31749", "Gene_or_gene_product", false, true, "Homo sapiens", 0, 1),
        ("RAC", "uniprot", "P31749", "Gene_or_gene_product", true, false, "Human", 0, 1),
        ("RAC", "uniprot", "P31749", "Gene_or_gene_product", true, false, "Homo sapiens", 0, 1),
        ("RAC-PK-alpha", "uniprot", "P31749", "Gene_or_gene_product", false, true, "Homo sapiens", 0, 1),
        ("RAC-PK-alpha", "uniprot", "P31749", "Gene_or_gene_product", false, true, "Human", 0, 1)
      )
    )

    val setupFuture: Future[Unit] = theDB.run(setup)
    Await.result(setupFuture, Duration.Inf)

    val action = DBIO.seq {
      Entries.result.map(ent => println(s"ENTITY=${ent}"))
    }
    Await.result(theDB.run(action), Duration.Inf)
  }

  // Now create and initialize the DB as a test
  dummyInit
}
