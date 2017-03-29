package org.clulab.kbquery.msg

/** A set of synonymous text strings. */
case class Synonyms (
  val synonyms: List[String]                // convey the set as a List for convenience
) extends Message


/** A single text string message. */
case class TextMessage (
  val text: String
) extends Message
