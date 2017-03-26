package org.clulab.kbquery.msg

/** A set of text strings. */
case class Synonyms (
  val synonyms: List[String]                // convey the set as a List for convenience
) extends Message
