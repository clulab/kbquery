package org.clulab.kbquery

import scala.annotation.tailrec
import com.typesafe.config.{ Config, ConfigValueFactory, ConfigFactory }
import com.typesafe.scalalogging.LazyLogging

import org.clulab.kbquery.load.KBLoader

/**
  * Application to load knowledge bases into an SQL database and query them using Akka HTTP.
  *   Written by: Tom Hicks. 5/9/2017.
  *   Last Modified: Refactor loader and server into classes selected by top-level app.
  */
object KBQuery extends App with LazyLogging {

  // save any command line arguments
  val argsList = args.toList

  // load application configuration from the configuration file
  val config = ConfigFactory.load()

  // which application does the user want to run
  private val whichApp = argsList.find(arg => arg.toLowerCase == "loader").getOrElse("server")

  // instantiate the chosen application
  val theApp: SubApplication = if (whichApp == "loader") new KBLoader(argsList, config)
                               else new KBQueryServer(argsList, config)

  theApp.start                              // start the chosen application
}


/** Trait defining minimal interface that child applications must implement. */
trait SubApplication {
  def start: Unit
}
