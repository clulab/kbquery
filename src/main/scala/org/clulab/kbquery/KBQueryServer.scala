package org.clulab.kbquery

import scala.annotation.tailrec
import com.typesafe.config.{ Config, ConfigValueFactory, ConfigFactory }

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

/**
  * App to    
  *   Written by: Tom Hicks from code by Gus Hahn-Powell. 3/24/2016.
  *   Last Modified: Refactor configuration. Add class docs.
  */
object KBQueryServer extends App with KBQService {

  // Support methods

  @throws(classOf[Exception])
  @tailrec
  def buildArgMap (defaults: Map[String, String], cmdArgs: List[String]): Map[String, String] = {
    cmdArgs match {
      case Nil => defaults                  // no args: return given map
      case "--port" :: port :: tail =>      // handle port
        buildArgMap(defaults ++ Map("port" -> port), tail)
      case "-p" :: port :: tail =>
        buildArgMap(defaults ++ Map("port" -> port), tail)
      case "--host" :: host :: tail =>      // handle host
        buildArgMap(defaults ++ Map("host" -> host), tail)
      case unknown :: tail =>
        throw new Exception(s"""Unknown option "$unknown"""")
    }
  }

  def initializeResources: Unit = {
    // val _ = do long initialization here at startup
  }


  // read default configuration from config file
  private val defaultConfig = ConfigFactory.load()
  private val defaultPort = defaultConfig.getString("akka.http.server.port")
  private val defaultHostName = defaultConfig.getString("akka.http.server.host")

  // merge configuration with any arguments from the command line
  private val defaults = Map (
    "port" -> defaultPort,
    "host" -> defaultHostName
  )

  val argMap = buildArgMap(defaults, args.toList)

  val port: Int = argMap("port").toInt
  val host: String = argMap("host")

  // create final config from loaded configuration updated with decoded command line values
  val config = defaultConfig
    .withValue(defaultHostName, ConfigValueFactory.fromAnyRef(host))
    .withValue(defaultPort, ConfigValueFactory.fromAnyRef(port))

  // initialize any resources the program needs
  initializeResources

  override implicit val system: ActorSystem = ActorSystem("akka", config)
  override implicit val executionContext = system.dispatcher
  override implicit val materializer: ActorMaterializer = ActorMaterializer()

  override val logger = Logging(system, getClass)

  val routes = makeRoutes(config)
  val bindingFuture =  Http().bindAndHandle(handler = routes, interface = host, port = port)

  logger.info(s"Server online at http://$host:$port")
}
