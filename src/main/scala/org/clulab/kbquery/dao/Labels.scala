package org.clulab.kbquery.dao

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import org.clulab.kbquery.msg._

/**
  * A Slick table definition for the KB Labels table: holding info on the entity types/categories.
  *   Written by: Tom Hicks. 5/3/2017.
  *   Last Modified: Initial creation.
  */
class Labels (tag: Tag) extends Table[LabelType] (tag, "LABELS") {

  // NB: Any field changes, additions, or deletions must also be updated in the package types!
  def uid: Rep[Int]          = column[Int]("uid", O.PrimaryKey)
  def label: Rep[String]     = column[String]("label")

  // every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[LabelType] = (uid, label)
}


/**
  * Companion object which represents the actual database table.
  */
object Labels extends TableQuery(new Labels(_)) {

  /** Convert a source table row of the correct shape into a KBLabel. */
  def toKBLabel (row: LabelType): KBLabel = KBLabel(row._1, row._2)

  /** Convert a sequence of table rows into a KBLabels object. */
  def toKBLabels (rows: Seq[LabelType]): KBLabels = {
    KBLabels(rows.map(row => toKBLabel(row)).toList)
  }

}
