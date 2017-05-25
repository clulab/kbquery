package org.clulab.kbquery

import scala.annotation.tailrec

import com.typesafe.config.{ Config, ConfigValueFactory, ConfigFactory }
import com.typesafe.scalalogging.LazyLogging

/**
  * Sub application to query knowledge bases via Akka HTTP service.
  *   Written by: Tom Hicks from code by Gus Hahn-Powell. 3/24/2016.
  *   Last Modified: Pass configuration to KBLookup ctor.
  */
class KBQueryServer (

  /** Application command line argument list. */
  argsList: List[String],

  /** Application configuration. */
  config: Config

) extends SubApplication with LazyLogging {

  // read default configuration values
  private val defaultPort = config.getString("akka.http.server.port")
  private val defaultHostName = config.getString("akka.http.server.host")
  private val defaults = Map (
    "port" -> defaultPort,
    "host" -> defaultHostName
  )

  // merge configuration defaults with any arguments from the command line
  private val argMap = buildArgMap(defaults, argsList)

  // create final config from loaded configuration updated with decoded command line values
  private val mixedConfig = config
    .withValue("host", ConfigValueFactory.fromAnyRef(argMap("host")))
    .withValue("port", ConfigValueFactory.fromAnyRef(argMap("port")))


  /** The main method for this application. Start the server to serve up the database.*/
  def start: Unit = {
    val dbManager = new DBManager(mixedConfig)
    val kbLookup = new KBLookup(mixedConfig, dbManager)
    val kbqService = new KBQService(mixedConfig, kbLookup)
    kbqService.start                        // start the server
  }


  @throws(classOf[Exception])
  @tailrec
  private final def buildArgMap (
    defaults: Map[String, String],
    cmdArgs: List[String]
  ): Map[String, String] = {
    cmdArgs match {
      case Nil => defaults                  // no args: return given map
      case "--port" :: port :: tail =>      // handle port
        buildArgMap(defaults ++ Map("port" -> port), tail)
      case "-p" :: port :: tail =>
        buildArgMap(defaults ++ Map("port" -> port), tail)
      case "--host" :: host :: tail =>      // handle host
        buildArgMap(defaults ++ Map("host" -> host), tail)
      case unknown :: tail =>               // ignore all other arguments
        buildArgMap(defaults, tail)
    }
  }

}
