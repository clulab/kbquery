package org.clulab.kbquery

import com.typesafe.config._

package object load {

  // read configuration and values from the configuration file
  val config = ConfigFactory.load()
  val KBDirPath = config.getString("db.sources.KBDirPath")
  val BatchSize = config.getInt("db.sources.batchSize")

}
