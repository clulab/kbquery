package org.clulab.kbquery.dao

import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}

import org.clulab.kbquery.msg._

/**
  * A Slick table definition for the KB entries table.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Update text queries to use joins on Keys/Entries tables.
  */
class Entries (tag: Tag) extends Table[EntryType](tag, "ENTRIES") {

  // NB: Any field changes, additions, or deletions must also be updated in the package types!
  def uid: Rep[Int]             = column[Int]("uid", O.PrimaryKey, O.AutoInc)
  def text: Rep[String]         = column[String]("text")
  def namespace: Rep[String]    = column[String]("namespace")
  def id: Rep[String]           = column[String]("id")
  def label: Rep[String]        = column[String]("label")
  def isGeneName: Rep[Boolean]  = column[Boolean]("is_gene_name")
  def isShortName: Rep[Boolean] = column[Boolean]("is_short_name")
  def species: Rep[String]      = column[String]("species")
  def priority: Rep[Int]        = column[Int]("priority")
  def sourceNdx: Rep[Int]       = column[Int]("source_ndx")

  // every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[EntryType] =
    (uid, text, namespace, id, label, isGeneName, isShortName, species, priority, sourceNdx)

  // a reified foreign key relation that can be navigated to create a join
  def source: ForeignKeyQuery[Sources, SourceType] =
    foreignKey("SRC_FK", sourceNdx, TableQuery[Sources])(_.uid)
}


/**
  * Companion object which represents the actual database table.
  */
object Entries extends TableQuery(new Entries(_)) {

  /** Convert the given KBEntry to an entries table row with the correct shape. */
  def toEntryType (kbe: KBEntry): EntryType = {
    (0, kbe.text, kbe.namespace, kbe.id, kbe.label, kbe.isGeneName,
      kbe.isShortName, kbe.species, kbe.priority, kbe.sourceNdx)
  }

  /** Convert an entries table row of the correct shape into a KBEntry. */
  def toKBEntry (row: EntryType): KBEntry = {
    KBEntry(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10)
  }

  /** Convert a sequence of table rows into a KBEntries object. */
  def toKBEntries (rows: Seq[EntryType]): KBEntries = {
    KBEntries(rows.map(row => toKBEntry(row)).toList)
  }

  /** Convert a sequence of synonym strings into a Synonyms object. */
  def toSynonyms (syns: Seq[String]): Synonyms = Synonyms(syns.toSet.toList)


  /** Query to find records by ID string alone. */
  def findById (id:String): Query[Entries, EntryType, Seq] = {
    Entries.filter(kbe => (kbe.id === id))
  }

  /** Query to find records by namespace and ID strings. */
  def findByNsAndId (ns:String, id:String): Query[Entries, EntryType, Seq] = {
    Entries.filter(kbe => (kbe.namespace === ns) && (kbe.id === id))
  }

  /** Query to find records exactly matching the given text string. */
  def findByText (text:String): Query[Entries, EntryType, Seq] = {
    for {
      k <- Keys if (k.text === text)
      e <- k.entry
    } yield e
  }

  /** Query to find records with text in the given set of text strings. */
  def findByTextSet (textSet: Set[String]): Query[Entries, EntryType, Seq] = {
    for {
      k <- Keys if (k.text inSet textSet)
      e <- k.entry
    } yield e
  }

  /** Query to find text field synonyms by namespace and ID strings. */
  def findSynonyms (ns:String, id:String): Query[Rep[String], String, Seq] = {
    Entries.filter(kbe => (kbe.namespace === ns) && (kbe.id === id)).map(_.text)
  }


  /** Query to find a namespace by label and ID strings. */
  def findNsByLabelAndId (label:String, id:String): Query[Rep[String], String, Seq] = {
    Entries.filter(kbe => (kbe.label === label) && (kbe.id === id)).map(_.namespace)
  }

}
