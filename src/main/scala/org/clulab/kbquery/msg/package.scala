package org.clulab.kbquery

package object msg {

  /** The default namespace string for KBs. */
  val DefaultNamespace: String = "uaz"

  /** The string used to separate a namespace and an ID. */
  val NamespaceIdSeparator: String = ":"

  /** Return a formatted string containing this entry's namespace and ID. */
  def makeNamespaceId (namespace:String, id:String): String =
    s"${namespace.trim.toLowerCase}${NamespaceIdSeparator}${id.trim}"


  /** Trait implemented by all model class which are used as messages. */
  trait Message

}
