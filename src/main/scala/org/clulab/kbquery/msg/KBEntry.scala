package org.clulab.kbquery.msg

import Species._

/**
  * Main class representing a single knowledge base entry.
  *   Written by: Tom Hicks. 3/27/2017.
  *   Last Modified: Add source label field.
  */
case class KBEntry (

  /** Text for this entry, as found in the external KB. */
  val text: String,

  /** The external namespace for this entry (e.g., go, uniprot). */
  val namespace: String = DefaultNamespace,

  /** The reference ID, relative to the namespace for this entry (e.g., GO:0033110, P12345). */
  val id: String,

  /** The entry type label, as given by the Reach NER. */
  val label: String,

  /** Does this entry represent a Gene name or Gene synonym. */
  val isGeneName: Boolean = false,

  /** Does this entry represent a short name or alternative short name. */
  val isShortName: Boolean = false,

  /** The species associated with this entry, if any. Empty string represents no species. */
  val species: String = NoSpeciesValue,

  /** The search priority level: 0 = normal, higher numbers = higher priority. */
  val priority: Int = DefaultPriority,

  /** A field which indicates the source of the entry within the KB. */
  val sourceIndex: Int = UnknownSource

) extends Message


/** A set of knowledge base entries, mostly a result of querying the KB. */
case class KBEntries (
  val entries: List[KBEntry]                // convey the set as a List for convenience
) extends Message


/**
  * A class representing source information for a single KB.
  */
case class KBSource (

  /** A field which indicates the source of the entry within the KB. */
  val srcId: Int = UnknownSource,

  /** A name to uniquely identify the source KB. */
  val srcName: String,

  /** The filename from which the KB was loaded. */
  val srcFilename: String,

  /** The entity type label for every entry in the KB (no value in override or mixed type KBs). */
  val srcLabel: String
)

/** A list of all source information records. */
case class KBSources (
  val sources: List[KBSource]
) extends Message
