package org.clulab.kbquery.dao

import slick.jdbc.HsqldbProfile.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}

import org.clulab.kbquery.msg._

/**
  * A Slick table definition for the KB entries table.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Add method to find by text set.
  */
class Entries (tag: Tag) extends Table[EntryType](tag, "KBE") {

  // NB: Any field changes, additions, or deletions must also be updated in the package types!
  def text: Rep[String]         = column[String]("text")
  def namespace: Rep[String]    = column[String]("namespace")
  def id: Rep[String]           = column[String]("id")
  def label: Rep[String]        = column[String]("label")
  def isGeneName: Rep[Boolean]  = column[Boolean]("is_gene_name")
  def isShortName: Rep[Boolean] = column[Boolean]("is_short_name")
  def species: Rep[String]      = column[String]("species")
  def priority: Rep[Int]        = column[Int]("priority")
  def sourceIndex: Rep[Int]     = column[Int]("source")

  // every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[EntryType] =
    (text, namespace, id, label, isGeneName, isShortName, species, priority, sourceIndex)

  // a reified foreign key relation that can be navigated to create a join
  def source: ForeignKeyQuery[Sources, SourceType] =
    foreignKey("SRC_FK", sourceIndex, TableQuery[Sources])(_.srcId)

}


/**
  * Companion object which represents the actual database table.
  */
object Entries extends TableQuery(new Entries(_)) {

  /** Query to find records by namespace and ID strings. */
  def findByNsAndId (ns:String, id:String): Query[Entries, EntryType, Seq] = {
    Entries.filter(kbe => (kbe.namespace === ns) && (kbe.id === id))
  }

  /** Query to find records exactly matching the given text string. */
  def findByText (text:String): Query[Entries, EntryType, Seq] = {
    Entries.filter(kbe => kbe.text === text)
  }

  /** Query to find records with text in the given set of text strings. */
  def findByTextSet (textSet: Set[String]): Query[Entries, EntryType, Seq] = {
    Entries.filter(kbe => kbe.text inSet textSet)
  }

  /** Query to find text field synonyms by namespace and ID strings. */
  def findSynonyms (ns:String, id:String): Query[Rep[String], String, Seq] = {
    Entries.filter(kbe => (kbe.namespace === ns) && (kbe.id === id)).map(_.text)
  }


  /** Convert an entries table row of the correct shape into a KBEntry. */
  def toKBEntry (row: EntryType): KBEntry = {
    KBEntry(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9)
  }

  /** Convert a sequence of table rows into a KBEntries object. */
  def toKBEntries (rows: Seq[EntryType]): KBEntries = {
    KBEntries(rows.map(row => toKBEntry(row)).toList)
  }

  /** Convert a sequence of synonym strings into a Synonyms object. */
  def toSynonyms (syns: Seq[String]): Synonyms = Synonyms(syns.toSet.toList)

}
