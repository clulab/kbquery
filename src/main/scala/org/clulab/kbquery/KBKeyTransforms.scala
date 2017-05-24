package org.clulab.kbquery

/**
  * Methods for transforming text strings into potential keys for lookup in KBs.
  *   Written by: Tom Hicks. 10/22/2015.
  *   Last Modified: Add map for names to basic transforms.
  */
trait KBKeyTransforms {
  import org.clulab.kbquery.KBKeyTransforms._

  /** A key transform which implements a canonicalization function for Strings. */
  def canonicalKT (text:String): KeyCandidates = toKeyCandidates(canonicalKey(text))

  /** A key transform which implements an Identity function for Strings. */
  def identityKT (text:String): KeyCandidates = toKeyCandidates(text)

  /** A key transform which implements a minimal canonicalization function for Strings. */
  def lowercaseKT (text:String): KeyCandidates = toKeyCandidates(text.toLowerCase)

  /** Map a name string to an associated key transform function. */
  val nameToKeyTransformMap: Map[String, KeyTransformFn] = Map (
    ("identity" -> identityKT),
    ("canonical" -> canonicalKT),
    ("lowercase" -> lowercaseKT)
  )


  /** Canonicalize the given text string into a key for both storage and lookup. */
  def canonicalKey (text:String): String =
    text.trim.toLowerCase.filterNot(KeyCharactersToRemove)

  /** Apply the given transform function to the given text, return any non-empty result strings. */
  def applyTransform (transformFn: KeyTransformFn, text: String): KeyCandidates =
    toKeyCandidates(transformFn.apply(text))

  /** Apply the given transform function to the given texts, return any non-empty result strings. */
  def applyTransform (transformFn: KeyTransformFn, texts: Seq[String]): KeyCandidates =
    toKeyCandidates(texts.flatMap(transformFn.apply(_)))

  /** Apply the given key transforms to the given string, returning a (possibly empty)
      sequence of potential key strings. */
  def applyAllTransforms (transformFns: KeyTransforms, text: String): KeyCandidates =
    toKeyCandidates(transformFns.flatMap(_.apply(text)))


  /** Try to remove all of the suffixes in the given set from the given text. */
  def stripAllSuffixes (suffixes:Seq[String], text:String): String = {
    var done:Boolean = false
    var lastText = text.trim                // prepare for first round
    var modText = text.trim                 // remember text before stripping
    while (!done) {
      suffixes.foreach { suffix =>          // try all suffixes
        modText = modText.stripSuffix(suffix).trim
      }
      if (modText == lastText)              // if no suffixes were stripped in last round
        done = true                         // done: exit the loop
      else                                  // else try another round of stripping
        lastText = modText                  // update result from last round
    }
    modText                                 // return new or unchanged string
  }

  /** Try to remove all of the suffixes in the given set from the given text. */
  def stripAllSuffixesKT (suffixes:Seq[String], text:String): KeyCandidates =
    toKeyCandidates(stripAllSuffixes(suffixes, text))

  /** Transform the given string into (a possibly empty) key candidates. */
  def toKeyCandidates (text:String): KeyCandidates =
    if (text.trim.nonEmpty) Seq(text.trim) else NoCandidates

  /** Transform the given sequence of strings into (a possibly empty) key candidates. */
  def toKeyCandidates (candidates: Seq[String]): KeyCandidates =
    candidates.map(_.trim).filter(_.nonEmpty)
}


/** Trait Companion Object allows Mixin OR Import pattern. */
object KBKeyTransforms extends KBKeyTransforms {

  /** Type alias for a (possibly empty) sequence of key transform results. */
  type KeyCandidates = Seq[String]
  val NoCandidates = Seq.empty[String]

  /** Type alias for functions which take a text string and return a (possibly empty)
      list of potential key strings. */
  type KeyTransformFn = (String) => KeyCandidates
  type KeyTransforms = Seq[KeyTransformFn]
  val NoTransforms = Seq.empty[KeyTransformFn]

  /** The set of characters to remove from the text to create a lookup key. */
  val KeyCharactersToRemove = " '/-".toSet

  /** Default list of text transforms to use with each KB. */
  val DefaultKeyTransforms = Seq( identityKT _, canonicalKT _ )

}
