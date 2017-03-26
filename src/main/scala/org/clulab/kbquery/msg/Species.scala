package org.clulab.kbquery.msg

import Species._

/** Represent a single species name. */
case class Species (
  val name: String
) extends Message


/** A set of species name strings. */
case class SpeciesNames (
  val species: List[String]                 // convey the set as a List for convenience
) extends Message


/** Object holding constants and convenience methods related to species manipulation. */
object Species {

  /** Constant which represents the lack of a species in a KB entry. */
  val NoSpeciesValue: String = ""

  /** Default value for human species string. */
  val Human: String = "human"

  /** A set of label strings for humans, found in KBs. */
  val HumanLabels: Set[String] = Set[String]("homo sapiens", "human")

  /** Default value for human species NS:ID string (NCBI Taxonomy). */
  val HumanNsId: String = "taxonomy:9606"

  /** A set of species strings for humans, found in the knowledge bases. */
  val HumanSpecies = Set[String]("homo sapiens", "human")


  /** Tell whether the given sequence of namespace/ID strings contains a human nsId. */
  def containsHumanNsId (nsIdSeq: Seq[String]): Boolean = nsIdSeq.exists(hasHumanNsId(_))

  /** Tell whether the given sequence of species strings contain a human species label. */
  def containsHumanSpecies (speciesSeq: Seq[String]): Boolean = speciesSeq.exists(isHumanSpecies(_))

  /** Tell whether the given namespace/ID string is an ID for humans or not. */
  def hasHumanNsId (nsId: String): Boolean = HumanNsId == nsId

  /** Tell whether the given species string is label for humans or not. */
  def isHumanSpecies (species: String): Boolean =
    if (HumanLabels.contains(species.toLowerCase)) true else false

  /** Tell whether the given species string is a member of the given set of species. */
  def isMemberOf (species: String, speciesSet: Set[String]): Boolean =
    if (speciesSet.contains(species.toLowerCase)) true else false

  /** Tell whether the given string is the special no-species constant or not. */
  def isNoSpeciesValue (species:String): Boolean = (species == NoSpeciesValue)

}
