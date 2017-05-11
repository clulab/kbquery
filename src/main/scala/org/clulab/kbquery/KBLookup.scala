package org.clulab.kbquery

import com.typesafe.scalalogging.LazyLogging

import org.clulab.kbquery.msg._
import org.clulab.kbquery.KBKeyTransforms._

/**
  * Singleton class implementing knowledge base lookup and manipulation methods.
  *   Written by: Tom Hicks. 3/25/2017.
  *   Last Modified: Update for entity results.
  */
class KBLookup (

  /** Class incorporating the specifics of a particular DB implementation. */
  dbManager: DBManager

) extends LazyLogging {

  /** Constant denoting an empty set of entities. */
  val NoEntities: List[Entity] = List.empty[Entity]

  /** Constant denoting an empty set of strings. */
  val NoTexts: List[String] = List.empty[String]


  // Facade pass-thru calls:
  //
  def countEntries: Map[String, Int] = dbManager.countEntries
  def countKeys: Map[String, Int] = dbManager.countKeys
  def countLabels: Map[String, Int] = dbManager.countLabels
  def countNamespaces: Map[String, Int] = dbManager.countNamespaces
  def countSources: Map[String, Int] = dbManager.countSources

  def dumpEntries: KBEntries = dbManager.dumpEntries
  def dumpKeys: KBKeys = dbManager.dumpKeys
  def dumpLabels: KBLabels = dbManager.dumpLabels
  def dumpNamespaces: KBNamespaces = dbManager.dumpNamespaces
  def dumpSources: KBSources = dbManager.dumpSources


  /** Return the (possibly empty) set of entities for the given ID string, in any namespace. */
  def lookupId (id:String): Entities = {
    return dbManager.byId(id)
  }

  /** Return the (possibly empty) set of entities for the given NS/ID string. */
  def lookupNsId (nsId: String): Entities = {
    val parts = parseNamespaceId(nsId)      // divide NS and ID
    if (parts.nonEmpty) {                   // if not empty there are 2 parts
      val ns = parts(0)
      val id = parts(1)
      return dbManager.byNsAndId(ns, id)
    }
    else Entities(NoEntities)               // else empty result set
  }

  /** Return the (possibly empty) set of entities for the given namespace and ID string. */
  def lookupNsAndId (ns:String, id:String): Entities = {
    return dbManager.byNsAndId(ns, id)
  }

 //  /** Try lookups for all given NS/IDs until one succeeds or all fail. */
 //  def lookupNsIds (nsIds: Set[String]): Resolutions = newResolutions(lookupEntities(nsIds))

 //  /** Return the set of species for the entities mapped by the given NS/ID key. */
 //  def speciesForNsId (nsId:String): SpeciesNameSet =
 //    nsidMap.getOrElse(nsId.trim, NoEntities).map(_.species).filter(_ != NoSpeciesValue).toSet

  /** Return the (possibly empty) set of all entities with a text key string
      that loosely matches the given text string. */
  def lookup (text: String): Entities = {
    val textSet = applyAllTransforms(DefaultKeyTransforms, text).toSet
    if (textSet.nonEmpty)
      dbManager.byTextSet(textSet)
    else
      Entities(NoEntities)                  // else empty result set
  }

  /** Return the (possibly empty) set of all entities exactly matching the given text string. */
  def lookupText (text: String): Entities = {
    return dbManager.byText(text)
  }

 //  /** Find the set of entities, for the given text string, which match the given
 //      single species. Returns resolutions for matching entities or None. */
 //  def lookupByASpecies (text:String, species:String): Resolutions =
 //    newResolutions(search(text, Some((kbe:Entity) => kbe.species == species)))

 //  /** Finds the set of entities, for the given text string, which contains a species
 //      in the given set of species. Returns resolutions for matching entities or None. */
 // def lookupBySpecies (text:String, speciesSet:SpeciesNameSet): Resolutions =
 //   newResolutions(search(text, Some((kbe:Entity) => isMemberOf(kbe.species, speciesSet))))

 //  /** Finds the set of entities, for the given text string, which have humans as the species.
 //      Returns resolutions for matching entities or None. */
 //  def lookupHuman (text:String): Resolutions =
 //    newResolutions(search(text, Some((kbe:Entity) => isHumanSpecies(kbe.species))))

 //  /** Find the set of entities, for the given text string, which do not contain a species.
 //      Returns resolutions for matching entities or None. */
 //  def lookupNoSpecies (text:String): Resolutions =
 //    newResolutions(search(text, Some((kbe:Entity) => kbe.hasNoSpecies)))

  /** Return the (possibly empty) set of textual synonyms for the given NS/ID string. */
  def synonyms (nsId: String): Synonyms = {
    val parts = parseNamespaceId(nsId)      // divide NS and ID
    if (parts.nonEmpty) {                   // if not empty there are 2 parts
      val ns = parts(0)
      val id = parts(1)
      return dbManager.synonyms(ns, id)
    }
    else Synonyms(NoTexts)                  // else empty result set
  }

}
