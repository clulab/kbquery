package org.clulab.kbquery

import com.typesafe.config._

package object load {

  // read configuration and values from the configuration file
  val config = ConfigFactory.load()
  val KBDirPath = config.getString("app.sources.KBDirPath")
  val BatchSize = config.getInt("app.sources.batchSize")
  val MaxFieldSize = config.getInt("app.sources.maxFieldSize")
  val Verbose = config.getBoolean("app.verbose")

}
