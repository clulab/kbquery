package org.clulab.kbquery

import com.typesafe.config._

package object load {

  // read configuration and values from the configuration file
  val config = ConfigFactory.load()
  val kbPath = config.getString("db.sources.kbPath")
  val batchSize = config.getInt("db.sources.batchSize")

}