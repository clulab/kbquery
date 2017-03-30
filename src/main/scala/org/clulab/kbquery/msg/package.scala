package org.clulab.kbquery

package object msg {

  /** The default namespace string for KB entries. */
  val DefaultNamespace: String = "uaz"

  /** The default priority level for entries in the KB.
      Priority levels: 0 = normal, higher numbers = higher priority. */
  val DefaultPriority: Int = 0

  /** The priority level for entries in an override KB.
      Priority levels: 0 = normal, higher numbers = higher priority. */
  val OverridePriority: Int = 1

  /** The string used to separate a namespace and an ID. */
  val NamespaceIdSeparator: String = ":"

  /** Indicate that there is no implicit default namespace for override or mixed-label KBs. */
  val NoDefaultNamespace: String = ""

  /** Indicate that there is no implicit default label for override or mixed-label KBs. */
  val NoImplicitLabel: String = ""

  /** The default data source indicator for entries in the KB. */
  val UnknownSource: Int = 0

  /** Concatenate and return a valid NS/ID from the given separate parts. */
  def makeNamespaceId (namespace: String, id: String): String =
    s"${namespace.trim.toLowerCase}${NamespaceIdSeparator}${id.trim}"

  /** Return a (possibly empty) list containing the namespace and ID from the given NS/ID string. */
  def parseNamespaceId (nsId: String): List[String] = {
    val parts = nsId.split(NamespaceIdSeparator).toList
    if (parts.size == 2) parts else List[String]()
  }


  /** Trait implemented by all model class which are used as messages. */
  trait Message

}
