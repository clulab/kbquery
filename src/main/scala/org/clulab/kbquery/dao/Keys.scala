package org.clulab.kbquery.dao

import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}

import org.clulab.kbquery.msg._

/**
  * A Slick table definition for the KB Keys table; holding transformed keys for the entries
  * in the Entries table.
  *   Written by: Tom Hicks. 4/9/2017.
  *   Last Modified: Initial addition.
  */
class Keys (tag: Tag) extends Table[KeyType] (tag, "TKEYS") {

  // NB: Any field changes, additions, or deletions must also be updated in the package types!
  def text: Rep[String]   = column[String]("text")
  def entryNdx: Rep[Int] = column[Int]("entry_ndx")

  // every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[KeyType] = (text, entryNdx)

  // a reified foreign key relation that can be navigated to create a join
  def entry: ForeignKeyQuery[Entries, EntryType] =
    foreignKey("ENT_FK", entryNdx, TableQuery[Entries])(_.uid)
}


/**
  * Companion object which represents the actual database table.
  */
object Keys extends TableQuery(new Keys(_)) {

  /** Convert a Key table row of the correct shape into a KBKey. */
  def toKBKey (row: KeyType): KBKey = KBKey(row._1, row._2)

  /** Convert a sequence of table rows into a KBKeys object. */
  def toKBKeys (rows: Seq[KeyType]): KBKeys = {
    KBKeys(rows.map(row => toKBKey(row)).toList)
  }

}
