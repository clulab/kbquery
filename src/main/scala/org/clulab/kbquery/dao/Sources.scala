package org.clulab.kbquery.dao

import slick.jdbc.HsqldbProfile.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}

import org.clulab.kbquery.msg._

/**
  * A Slick table definition for the KB source table; holding meta info on the source of the KBs.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Add source label field.
  */
class Sources (tag: Tag) extends Table[SourceType] (tag, "SRCS") {

  // NB: Any field changes, additions, or deletions must also be updated in the package types!
  def srcId: Rep[Int]          = column[Int]("src_id", O.PrimaryKey)
  def srcName: Rep[String]     = column[String]("src_name")
  def srcFilename: Rep[String] = column[String]("src_filename")
  def srcLabel: Rep[String]    = column[String]("src_label")

  // every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[SourceType] = (srcId, srcName, srcFilename, srcLabel)

}


/**
  * Companion object which represents the actual database table.
  */
object Sources extends TableQuery(new Sources(_)) {

  /** Convert a source table row of the correct shape into a KBSource. */
  def toKBSource (row: SourceType): KBSource = KBSource(row._1, row._2, row._3, row._4)

  /** Convert a sequence of table rows into a KBSources object. */
  def toKBSources (rows: Seq[SourceType]): KBSources = {
    KBSources(rows.map(row => toKBSource(row)).toList)
  }

}
