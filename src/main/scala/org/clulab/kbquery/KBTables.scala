package org.clulab.kbquery

import slick.jdbc.HsqldbProfile.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}

/** A table holding all the KB entries. */
class Entries (tag: Tag)
  extends Table[(String, String, String, String, Boolean, Boolean, String, Int, Int)](tag, "KBE")
{
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
  def * : ProvenShape[(String, String, String, String, Boolean, Boolean, String, Int, Int)] =
    (text, namespace, id, label, isGeneName, isShortName, species, priority, sourceIndex)

  // a reified foreign key relation that can be navigated to create a join
  def source: ForeignKeyQuery[Sources, (Int, String, String)] =
    foreignKey("SRC_FK", sourceIndex, TableQuery[Sources])(_.srcId)
}

/** Companion object which represents the actual database table. */
object Entries extends TableQuery(new Entries(_)) { }


/** A table holding meta information about the source of the KBs. */
class Sources (tag: Tag) extends Table[(Int, String, String)] (tag, "SRCS")
{
  def srcId: Rep[Int]          = column[Int]("src_id", O.PrimaryKey, O.AutoInc)
  def srcName: Rep[String]     = column[String]("src_name")
  def srcFilename: Rep[String] = column[String]("src_filename")

  // every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[(Int, String, String)] = (srcId, srcName, srcFilename)
}

/** Companion object which represents the actual database table. */
object Sources extends TableQuery(new Sources(_)) { }
