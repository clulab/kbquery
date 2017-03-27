package org.clulab.kbquery.msg

/** Represent a single NS/ID string. */
case class NsId (
  val nsId: String                          // in the form 'namespace:id'
) extends Message

/** Represent namespace and ID strings separately. */
case class NsAndId (
  val ns: String,
  val id: String
) extends Message

/** A Set of NS/ID strings. */
case class NsIds (
  val nsIds: List[NsId]                     // convey the set as a List for convenience
) extends Message
