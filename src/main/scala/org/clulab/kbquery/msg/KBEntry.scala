package org.clulab.kbquery.msg

import Species._

/**
  * Main class representing a single knowledge base entry.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Add method to convert KBEntry to a sequence of its values.
  */
case class KBEntry (

  /** Unique primary key for this entry. */
  val uid: Int,

  /** Text for this entry, as found in the external KB. */
  val text: String,

  /** The external namespace for this entry (e.g., go, uniprot). */
  val namespace: String,

  /** The reference ID, relative to the namespace for this entry (e.g., GO:0033110, P12345). */
  val id: String,

  /** The entry type label, often inherent in the KB type. */
  val label: String,

  /** Does this entry represent a Gene name or Gene synonym. */
  val isGeneName: Boolean = false,

  /** Does this entry represent a short name or alternative short name. */
  val isShortName: Boolean = false,

  /** The species associated with this entry, if any. Empty string represents no species. */
  val species: String = NoSpeciesValue,

  /** The search priority level: 0 = normal, higher numbers = higher priority. */
  val priority: Int = DefaultPriority,

  /** A foreign key field which indicates the source of the entry within the KB. */
  val sourceNdx: Int = UnknownSource

) extends Message {

  /** Convert this KBEntry to a sequence of its member values. */
  def toSeq: Seq[Any] =
    Seq(uid, text, namespace, id, label, isGeneName, isShortName, species, priority, sourceNdx)
}


/** A set of knowledge base entries, mostly a result of querying the KB. */
case class KBEntries (
  val entries: List[KBEntry]                // convey the set as a List for convenience
) extends Message


/**
  * A class representing source information for a single KB.
  */
case class KBSource (

  /** A field which indicates the source of the entry within the KB. */
  val id: Int = UnknownSource,

  /** The implicit namespace for all entries in this KB. */
  val namespace: String = NoDefaultNamespace,

  /** The filename from which the KB was loaded. */
  val filename: String,

  /** The entity type label for every entry in the KB (empty for override or mixed-label KBs). */
  val label: String = NoImplicitLabel
)

/** A list of source information records. */
case class KBSources (
  val sources: List[KBSource]
) extends Message


/**
  * A class holding a transformed text string (key) representing a varient
  * lexical form for an entity in the Entries table.
  */
case class KBKey (

  /** A text string formed by perturbing the text field of the corresponding Entries text field. */
  val text: String,

  /** A foreign key field pointing to the main entry that this record is a key for. */
  val entryNdx: Int
)

/** A list of Key records. */
case class KBKeys (
  val sources: List[KBKey]
) extends Message
