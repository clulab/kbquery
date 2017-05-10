package org.clulab.kbquery.dao

import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}

import org.clulab.kbquery.msg._

/**
  * A Slick table definition for the KB entries table.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Update for namespace table.
  */
class Entries (tag: Tag) extends Table[EntryType](tag, "ENTRIES") {

  // NB: Any field changes, additions, or deletions must also be updated in the package types!
  def uid: Rep[Int]             = column[Int]("uid", O.PrimaryKey, O.AutoInc)
  def text: Rep[String]         = column[String]("text")
  def id: Rep[String]           = column[String]("id")
  def isGeneName: Rep[Boolean]  = column[Boolean]("is_gene_name")
  def isShortName: Rep[Boolean] = column[Boolean]("is_short_name")
  def species: Rep[String]      = column[String]("species")
  def priority: Rep[Int]        = column[Int]("priority")
  def labelNdx: Rep[Int]        = column[Int]("label_ndx")
  def nsNdx: Rep[Int]           = column[Int]("ns_ndx")
  def sourceNdx: Rep[Int]       = column[Int]("source_ndx")

  // every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[EntryType] =
    (uid, text, id, isGeneName, isShortName, species, priority, labelNdx, nsNdx, sourceNdx)

  // a reified foreign key relation that can be navigated to create a join
  def label: ForeignKeyQuery[Labels, LabelType] =
    foreignKey("LBL_FK", labelNdx, TableQuery[Labels])(_.uid)

  // a reified foreign key relation that can be navigated to create a join
  def namespace: ForeignKeyQuery[Namespaces, NamespaceType] =
    foreignKey("NS_FK", nsNdx, TableQuery[Namespaces])(_.uid)

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
    (0, kbe.text, kbe.id, kbe.isGeneName, kbe.isShortName, kbe.species,
      kbe.priority, kbe.labelNdx, kbe.nsNdx, kbe.sourceNdx)
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
  def findByNsAndId (nsNdx:Int, id:String): Query[Entries, EntryType, Seq] = {
    Entries.filter(kbe => (kbe.nsNdx === nsNdx) && (kbe.id === id))
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
  def findSynonyms (nsNdx:Int, id:String): Query[Rep[String], String, Seq] = {
    Entries.filter(kbe => (kbe.nsNdx === nsNdx) && (kbe.id === id)).map(_.text)
  }

}
