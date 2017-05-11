package org.clulab.kbquery.msg

import Species._

/**
  * DTO class for the top-level biological entity object returned from database queries.
  *   Written by: Tom Hicks. 5/11/2017.
  *   Last Modified: Initial creation.
  */
case class Entity (

  /** Unique primary key for this entity. */
  val uid: Int,

  /** Text for this entity, as found in the external KB. */
  val text: String,

  /** The external namespace for this entity (e.g., go, uniprot). */
  val namespace: String = DefaultNamespace,

  /** The reference ID, relative to the namespace for this entity (e.g., GO:0033110, P12345). */
  val id: String,

  /** The entity type label, as given by the Reach NER. */
  val label: String,

  /** Does this entity represent a Gene name or Gene synonym. */
  val isGeneName: Boolean = false,

  /** Does this entity represent a short name or alternative short name. */
  val isShortName: Boolean = false,

  /** The species associated with this entity, if any. Empty string represents no species. */
  val species: String = NoSpeciesValue,

  /** The search priority level: 0 = normal, higher numbers = higher priority. */
  val priority: Int = DefaultPriority,

  /** The filename of the source of the entity. */
  val sourceFilename: String = DefaultSourceFilename

) extends Message

/** A set of knowledge base entities, mostly a result of querying the KB. */
case class Entities (
  val entities: List[Entity]
) extends Message
