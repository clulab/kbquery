package org.clulab.kbquery.dao

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import org.clulab.kbquery.msg._

/**
  * A Slick table definition for the KB Namespaces table.
  *   Written by: Tom Hicks. 5/6/2017.
  *   Last Modified: Initial creation.
  */
class Namespaces (tag: Tag) extends Table[NamespaceType] (tag, "LABELS") {

  // NB: Any field changes, additions, or deletions must also be updated in the package types!
  def uid: Rep[Int]          = column[Int]("uid", O.PrimaryKey)
  def namespace: Rep[String] = column[String]("namespace")

  // every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[NamespaceType] = (uid, namespace)
}


/**
  * Companion object which represents the actual database table.
  */
object Namespaces extends TableQuery(new Namespaces(_)) {

  /** Convert a source table row of the correct shape into a KBNamespace. */
  def toKBNamespace (row: NamespaceType): KBNamespace = KBNamespace(row._1, row._2)

  /** Convert a sequence of table rows into a KBNamespaces object. */
  def toKBNamespaces (rows: Seq[NamespaceType]): KBNamespaces = {
    KBNamespaces(rows.map(row => toKBNamespace(row)).toList)
  }

}
