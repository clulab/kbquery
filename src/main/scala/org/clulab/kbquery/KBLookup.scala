package org.clulab.kbquery

import org.clulab.kbquery.msg._

/**
  * Singleton class implementing knowledge base lookup and manipulation methods.
  *   Written by: Tom Hicks. 3/25/2017.
  *   Last Modified: Initial creation.
  */
object KBLookup {

  /** Constant denoting an empty set of KB entries. */
  val NoEntries: List[KBEntry] = List.empty[KBEntry]


  /** Return the (possibly empty) set of KB entries for the given NS/ID string. */
  def lookupNsId (nsId: String): KBEntries = {
    val parts = parseNamespaceId(nsId)      // divide NS and ID
    if (parts.nonEmpty) {                   // if not empty there are 2 parts
      val ns = parts(0)
      val id = parts(1)
      return KBEntries(List(                // DUMMY DATA: IMPLEMENT LATER
        KBEntry("textA", ns, s"${id}-A", "Protein"),
        KBEntry("textB", ns, s"${id}-B", "Protein"),
        KBEntry("textC", ns, s"${id}-C", "Family")
      ))
    }
    else KBEntries(NoEntries)               // else empty result set
  }

 //  /** Return resolutions for the set of KB entries for the given namespace and ID strings. */
 //  def lookupNsId (namespace:String, id:String): Resolutions =
 //    lookupNsId(makeNamespaceId(namespace, id)) // trimming handled in makeNamespaceId

 //  /** Try lookups for all given NS/IDs until one succeeds or all fail. */
 //  def lookupNsIds (nsIds: Set[String]): Resolutions = newResolutions(lookupEntries(nsIds))

 //  /** Return the set of species for the entries mapped by the given NS/ID key. */
 //  def speciesForNsId (nsId:String): SpeciesNameSet =
 //    nsidMap.getOrElse(nsId.trim, NoEntries).map(_.species).filter(_ != NoSpeciesValue).toSet


 //  /** Return resolutions for the set of all KB entries for the given text string. */
 //  def lookup (text:String): Resolutions = newResolutions(search(text, None))

 //  /** Find the set of KB entries, for the given text string, which match the given
 //      single species. Returns resolutions for matching entries or None. */
 //  def lookupByASpecies (text:String, species:String): Resolutions =
 //    newResolutions(search(text, Some((kbe:KBEntry) => kbe.species == species)))

 //  /** Finds the set of KB entries, for the given text string, which contains a species
 //      in the given set of species. Returns resolutions for matching entries or None. */
 // def lookupBySpecies (text:String, speciesSet:SpeciesNameSet): Resolutions =
 //   newResolutions(search(text, Some((kbe:KBEntry) => isMemberOf(kbe.species, speciesSet))))

 //  /** Finds the set of KB entries, for the given text string, which have humans as the species.
 //      Returns resolutions for matching entries or None. */
 //  def lookupHuman (text:String): Resolutions =
 //    newResolutions(search(text, Some((kbe:KBEntry) => isHumanSpecies(kbe.species))))

 //  /** Find the set of KB entries, for the given text string, which do not contain a species.
 //      Returns resolutions for matching entries or None. */
 //  def lookupNoSpecies (text:String): Resolutions =
 //    newResolutions(search(text, Some((kbe:KBEntry) => kbe.hasNoSpecies)))

}
