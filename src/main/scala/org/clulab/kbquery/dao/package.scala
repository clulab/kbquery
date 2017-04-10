package org.clulab.kbquery

package object dao {

  /** Type declaration for a row in the Slick-based Entries table. */
  type EntryType = Tuple10[Int, String, String, String, String, Boolean, Boolean, String, Int, Int]
  // type EntryRepType = Rep[EntryType]

  /** Type declaration for a row in the Slick-based Sources table. */
  type SourceType = Tuple4[Int, String, String, String]
  // type SourceRepType = Rep[SourceType]

  /** Type declaration for a row in the Slick-based Keys table. */
  type KeyType = Tuple2[String, Int]
  // type KeyRepType = Rep[KeyType]

}
