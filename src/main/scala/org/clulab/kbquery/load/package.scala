package org.clulab.kbquery

import com.typesafe.config._

package object load {

  // read configuration and values from the configuration file
  val config = ConfigFactory.load()
  val BatchSize = config.getInt("app.loader.batchSize")
  val KBDirPath = config.getString("app.loader.KBDirPath")
  val MaxFieldSize = config.getInt("app.loader.maxFieldSize")
  val ShowTruncated = config.getBoolean("app.loader.showTruncated")
  val Verbose = config.getBoolean("app.verbose")

  val MultiLabel = "M"                      // constant indicating type of KB read
  val UniLabel = "U"                        // constant indicating type of KB read

}
