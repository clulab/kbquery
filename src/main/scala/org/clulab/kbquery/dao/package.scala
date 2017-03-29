package org.clulab.kbquery

// import slick.jdbc.HsqldbProfile.api._

package object dao {

  /** Type declaration for a row in the Slick-based Entries table. */
  type EntryType = Tuple9[String, String, String, String, Boolean, Boolean, String, Int, Int]
  // type EntryRepType = Rep[EntryType]

  /** Type declaration for a row in the Slick-based Sources table. */
  type SourceType = Tuple3[Int, String, String]
  // type SourceRepType = Rep[SourceType]

}
