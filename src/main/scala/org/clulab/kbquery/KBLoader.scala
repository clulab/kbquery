package org.clulab.kbquery

import scala.collection.JavaConverters._
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.config._

import slick.jdbc.HsqldbProfile.api._

import org.clulab.kbquery.dao._
import org.clulab.kbquery.msg._

/**
  * Singleton app to load data into the KBQuery DB.
  *   Written by: Tom Hicks. 3/28/2017.
  *   Last Modified: Stub out file loading in batches. Replace load plan.
  */
object KBLoader extends App {

  // read configuration from config file
  private val config = ConfigFactory.load()

  /** The Database: configure and open it. */
  val theDB = Database.forConfig("db.kbqdb")

  /** Read the sources table configuration from the configuration file. */
  val sourcesConfiguration: List[SourceType] = {
    val srcMetaInfo = config.getList("db.sources.metaInfo")
    srcMetaInfo.iterator().asScala.map { srcLine =>
      val src = srcLine.asInstanceOf[ConfigObject].toConfig
      val srcId = src.getInt("id")
      val srcName = src.getString("name")
      val srcFilename = src.getString("filename")
      val srcLabel = src.getString("label")
      (srcId, srcName, srcFilename, srcLabel)
    }.toList
  }

  /** Create the DB tables from the schema. */
  def createTables: DBIO[Unit] = {
    DBIO.seq ( (Sources.schema ++ Entries.schema).create )
  }

  /** Load the Sources table with the information extracted from the configuration file. */
  def loadSources: DBIO[Unit] = {
    DBIO.seq ( (Sources ++= sourcesConfiguration) )
  }

  /** Use the Sources configuration to find and load the configured KB files. */
  def loadFiles: Unit = {
    val sources:List[KBSource] = sourcesConfiguration.map(row => Sources.toKBSource(row))
    sources.foreach { src => FileLoader.loadFile(src) }
  }

  /** Add the given batch of entries to the current KB. Called repeatedly as a co-routine
     by the file loader. */
  def loadBatch (batch: Seq[EntryType]): Unit = {
    Await.result(theDB.run(DBIO.seq((Entries ++= batch))), Duration.Inf)
  }


  /** Load a small Entries table with test data. */
  def loadTestEntries: DBIO[Unit] = {
    DBIO.seq (

      Entries ++= Seq (                   // Proteins from NER
        ("AKT1", "uniprot", "P31749", "Gene_or_gene_product", true, false, "Human", 1, 4),
        ("AKT1", "uniprot", "P31749", "Gene_or_gene_product", true, false, "Homo sapiens", 1, 4),
        ("PKB", "uniprot", "P31749", "Gene_or_gene_product", true, true, "Human", 1, 4),
        ("PKB", "uniprot", "P31749", "Gene_or_gene_product", true, true, "Homo sapiens", 1, 4),
        ("PKB alpha", "uniprot", "P31749", "Gene_or_gene_product", false, true, "Human", 1, 4),
        ("PKB alpha", "uniprot", "P31749", "Gene_or_gene_product", false, true, "Homo sapiens", 1, 4),
        ("RAC", "uniprot", "P31749", "Gene_or_gene_product", true, false, "Human", 1, 4),
        ("RAC", "uniprot", "P31749", "Gene_or_gene_product", true, false, "Homo sapiens", 1, 4),
        ("RAC-PK-alpha", "uniprot", "P31749", "Gene_or_gene_product", false, true, "Homo sapiens", 1, 4),
        ("RAC-PK-alpha", "uniprot", "P31749", "Gene_or_gene_product", false, true, "Human", 1, 4)
      ),

      Entries ++= Seq (                   // Proteins from Uniprot
        ("AKT1", "uniprot", "P0C550", "Gene_or_gene_product", true, false, "Oryza sativa", 0, 1),
        ("AKT1", "uniprot", "P0C550", "Gene_or_gene_product", true, false, "Rice", 0, 1),
        ("AKT1", "uniprot", "P31749", "Gene_or_gene_product", true, false, "Homo sapiens", 0, 1),
        ("AKT1", "uniprot", "P31749", "Gene_or_gene_product", true, false, "Human", 0, 1),
        ("Akt1", "uniprot", "P31750", "Gene_or_gene_product", true, false, "Mouse", 0, 1),
        ("Akt1", "uniprot", "P31750", "Gene_or_gene_product", true, false, "Mus musculus", 0, 1),
        ("Akt1", "uniprot", "P47196", "Gene_or_gene_product", true, false, "Rat", 0, 1),
        ("Akt1", "uniprot", "P47196", "Gene_or_gene_product", true, false, "Rattus norvegicus", 0, 1),
        ("AKT1", "uniprot", "Q01314", "Gene_or_gene_product", true, false, "Bos taurus", 0, 1),
        ("AKT1", "uniprot", "Q01314", "Gene_or_gene_product", true, false, "Bovine", 0, 1),
        ("AKT1", "uniprot", "Q0JKV1", "Gene_or_gene_product", true, false, "Oryza sativa", 0, 1),
        ("AKT1", "uniprot", "Q0JKV1", "Gene_or_gene_product", true, false, "Rice", 0, 1),
        ("akt-1", "uniprot", "Q17941", "Gene_or_gene_product", true, false, "Caenorhabditis elegans", 0, 1),
        ("AKT1", "uniprot", "Q38998", "Gene_or_gene_product", true, false, "Arabidopsis thaliana", 0, 1),
        ("AKT1", "uniprot", "Q38998", "Gene_or_gene_product", true, false, "Mouse-ear cress", 0, 1),
        ("Akt1", "uniprot", "Q8INB9", "Gene_or_gene_product", true, false, "Drosophila melanogaster", 0, 1),
        ("Akt1", "uniprot", "Q8INB9", "Gene_or_gene_product", true, false, "Fruit fly", 0, 1),
        ("akt1", "uniprot", "Q98TY9", "Gene_or_gene_product", true, false, "African clawed frog", 0, 1),
        ("akt1", "uniprot", "Q98TY9", "Gene_or_gene_product", true, false, "Xenopus laevis", 0, 1)
      ),

      Entries ++= Seq (                   // Chemicals from NER
        ("EHT 1864",  "pubchem", "9938202", "Simple_chemical", false, false, "", 1, 4),
        ("EHT1864",   "pubchem", "9938202", "Simple_chemical", false, false, "", 1, 4),
        ("estradiol", "pubchem", "5757",    "Simple_chemical", false, false, "", 1, 4),
        ("estrone",   "pubchem", "5870",    "Simple_chemical", false, false, "", 1, 4),
        ("Ethyl Methanesulfonate", "pubchem", "6113", "Simple_chemical", false, false, "", 1, 4),
        ("GDP",       "pubchem", "8977",    "Simple_chemical", false, false, "", 1, 4),
        ("GSH",       "pubchem", "124886",  "Simple_chemical", false, false, "", 1, 4),
        ("GTP",       "pubchem", "6830",    "Simple_chemical", false, false, "", 1, 4),
        ("ROS",       "chebi",   "CHEBI:26523", "Simple_chemical", false, false, "", 1, 4),
        ("ROSh2o2",   "pubchem", "784",      "Simple_chemical", false, false, "", 1, 4),
        ("ROSox",     "pubchem", "977",      "Simple_chemical", false, false, "", 1, 4),
        ("ROSsox",    "pubchem", "5359597",  "Simple_chemical", false, false, "", 1, 4)
      ),

      Entries ++= Seq (                   // Chemicals from PubChem
        ("Estradiol",    "pubchem", "53477783", "Simple_chemical", false, false, "", 0, 3),
        ("Estroclim",    "pubchem", "5757", "Simple_chemical", false, false, "", 0, 3),
        ("Estroclim 50", "pubchem", "5757", "Simple_chemical", false, false, "", 0, 3),
        ("Estrogel",     "pubchem", "53477783", "Simple_chemical", false, false, "", 0, 3),
        ("Estrogel",     "pubchem", "5757", "Simple_chemical", false, false, "", 0, 3),
        ("Estrogel HBF", "pubchem", "5757", "Simple_chemical", false, false, "", 0, 3),
        ("Estrogen",     "pubchem", "5991", "Simple_chemical", false, false, "", 0, 3),
        ("Estrogin",     "pubchem", "222757", "Simple_chemical", false, false, "", 0, 3),
        ("Estrone",      "pubchem", "3001028", "Simple_chemical", false, false, "", 0, 3)
      ),

      Entries ++= Seq (                   // Families from NER
        ("COX4",  "pfam", "PF02936", "Family", false, false, "", 1, 4),
        ("COX6A", "pfam", "PF02046", "Family", false, false, "", 1, 4),
        ("COX6B", "pfam", "PF02297", "Family", false, false, "", 1, 4),
        ("COX7A", "pfam", "PF02238", "Family", false, false, "", 1, 4),
        ("COX7B", "pfam", "PF05392", "Family", false, false, "", 1, 4),
        ("COX8",  "pfam", "PF02285", "Family", false, false, "", 1, 4),
        ("CRISP", "pfam", "PF08562", "Family", false, false, "", 1, 4)
      ),

      Entries ++= Seq (                   // Families from PFAM
        ("COX1",  "pfam", "PF00115", "Family", false, false, "", 0, 2),
        ("COX14", "pfam", "PF14880", "Family", false, false, "", 0, 2),
        ("COX16", "pfam", "PF14138", "Family", false, false, "", 0, 2),
        ("COX17", "pfam", "PF05051", "Family", false, false, "", 0, 2),
        ("COX2",  "pfam", "PF00116", "Family", false, false, "", 0, 2),
        ("COX3",  "pfam", "PF00510", "Family", false, false, "", 0, 2),
        ("COX4",  "pfam", "PF02936", "Family", false, false, "", 0, 2),
        ("COX5A", "pfam", "PF02284", "Family", false, false, "", 0, 2),
        ("COX5B", "pfam", "PF01215", "Family", false, false, "", 0, 2),
        ("COX6A", "pfam", "PF02046", "Family", false, false, "", 0, 2),
        ("COX6B", "pfam", "PF02297", "Family", false, false, "", 0, 2),
        ("COX6C", "pfam", "PF02937", "Family", false, false, "", 0, 2),
        ("COX7B", "pfam", "PF05392", "Family", false, false, "", 0, 2),
        ("COX7C", "pfam", "PF02935", "Family", false, false, "", 0, 2),
        ("COX7a", "pfam", "PF02238", "Family", false, false, "", 0, 2),
        ("COX8",  "pfam", "PF02285", "Family", false, false, "", 0, 2)
      )
    )  // setup
  }

  /** Execute SQL command to cleanly shutdown the DB. */
  def shutdown: DBIO[Int] = sqlu"""shutdown"""

  //
  // MAIN: Run the actions sequentially to execute the loading steps.
  //
  Await.result(theDB.run(createTables), Duration.Inf)
  Await.result(theDB.run(loadSources), Duration.Inf)
  loadFiles
  // Await.result(theDB.run(loadTestEntries), Duration.Inf)
  Await.result(theDB.run(shutdown), Duration.Inf)
  theDB.close                               // close down DB and exit
}
