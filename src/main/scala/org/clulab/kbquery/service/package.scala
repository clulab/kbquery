package org.clulab.kbquery

import scala.util.matching.Regex

package object service {

  /** for keeping track of information related to the server **/
  case class Description(version: String)

  case class RichRegex(override val regex: String) extends Regex(regex) {
    def matches(s: String) = this.pattern.matcher(s).matches
  }

  implicit def toRichRegex(regex: Regex): RichRegex = RichRegex(regex.toString)

  // def mkDescription: Description = Description(projectVersion)

}
