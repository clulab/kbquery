package org.clulab.kbquery

/**
  * Package object for the message package of the KB Query application.
  *   Written by: Tom Hicks. 3/25/2017.
  *   Last Modified: Redo the parse namespace/ID logic.
  */
package object msg {

  /** The default namespace string for KB entries. */
  val DefaultNamespace: String = "uaz"

  /** The default priority level for entries in the KB.
      Priority levels: 0 = normal, higher numbers = higher priority. */
  val DefaultPriority: Int = 0

  /** The default source filename string for KB entries. */
  val DefaultSourceFilename: String = ""

  /** The default configuration list of key transforms, for KB entries. */
  val DefaultTransformsList: List[String] = List.empty[String]

  /** The priority level for entries in an override KB.
      Priority levels: 0 = normal, higher numbers = higher priority. */
  val OverridePriority: Int = 1

  /** The string used to separate a namespace and an ID. */
  val NamespaceIdSeparator: String = ":"

  /** Indicate that there is no implicit default namespace for override or mixed-label KBs. */
  val NoDefaultNamespace: String = ""

  /** Indicate that there is no implicit default label for override or mixed-label KBs. */
  val NoImplicitLabel: String = ""

  /** The default entity label UID for entries in the KB. */
  val UnknownLabel: Int = 0

  /** The default namespace UID for entries in the KB. */
  val UnknownNamespace: Int = 0

  /** The default data source indicator UID for entries in the KB. */
  val UnknownSource: Int = 0

  /** Concatenate and return a valid NS/ID from the given separate parts. */
  def makeNamespaceId (namespace: String, id: String): String =
    s"${namespace.trim.toLowerCase}${NamespaceIdSeparator}${id.trim}"

  /** Return a (possibly empty) list containing the namespace and ID from the given NS/ID string. */
  def parseNamespaceId (nsId: String): List[String] = {
    val parts = nsId.split(NamespaceIdSeparator).toList
    val len = parts.size
    if (len == 2) parts
    else if (len > 2) List(parts(0), parts.drop(1).mkString(":"))
    else List[String]()
  }


  /** Trait implemented by all model class which are used as messages. */
  trait Message

}
