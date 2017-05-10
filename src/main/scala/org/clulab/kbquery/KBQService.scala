package org.clulab.kbquery

import java.io.File

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

import com.typesafe.config.Config

import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{ Formats, DefaultFormats, jackson, native }

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

/**
  * Trait to provide an Akka HTTP service using Json4s support for marshalling.
  *   Written by: Tom Hicks from code by Gus Hahn-Powell. 3/24/2016.
  *   Last Modified: Major refactoring to classes.
  */
class KBQService (

  /** Application configuration overridden with command line arguments. */
  configuration: Config,

  /** The class which implements the database queries. */
  kbLookup: KBLookup

) extends SubApplication with Json4sSupport {

  implicit val serialization = jackson.Serialization // or native.Serialization
  implicit val formats = DefaultFormats

  // setup Akka system
  implicit val system: ActorSystem = ActorSystem("kbquery", configuration)
  implicit val executionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val logger = Logging(system, getClass)

  /** The main method for this application. Start the server to serve up the database.*/
  def start: Unit = {
    val route = makeRoute(configuration)
    val host = configuration.getString("host")
    val port = configuration.getInt("port")
    val bindingFuture =  Http().bindAndHandle(handler = route, interface = host, port = port)
    logger.info(s"Server online at http://$host:$port")
  }

  /** Convenience method to execute a task after a fixed duration of time. */
  def in [U] (duration: FiniteDuration)(body: => U): Unit =
    system.scheduler.scheduleOnce(duration)(body)

  /** Create and return the route for this app, using the given configuration, if needed. */
  def makeRoute (config: Config): Route = {
    val appVersion = config.getString("app.version")

    val routesGet = {
      logRequestResult("kbquery-microservice") {    // wrap contained paths in logger
        get {                               // GETS
          pathPrefix("kblu") {
            path("lookup") {                        // by transformed text string(s)
              parameters("text") { text =>
                logger.info(s"GET kblu/lookup -> ${text}")
                complete(kbLookup.lookup(text))
              }
            } ~
            path("byText") {                        // by exact matching of text string
              parameters("text") { text =>
                logger.info(s"GET kblu/byText -> ${text}")
                complete(kbLookup.lookupText(text))
              }
            } ~
            path("byId") {                          // by ID only
              parameters("id") { (id) =>
                logger.info(s"GET kblu/byId -> ${id}")
                 complete(kbLookup.lookupId(id))
              }
            } ~
            path("byNsId") {                        // by NS/ID string
              parameters("nsId") { nsId =>
                logger.info(s"GET kblu/byNsId -> ${nsId}")
                complete(kbLookup.lookupNsId(nsId))
              }
            } ~
            path("byNsAndId") {                     // by namespace and ID
              parameters("ns", "id") { (ns, id) =>
                logger.info(s"GET kblu/byNsAndId -> ${ns}, ${id}")
                complete(kbLookup.lookupNsAndId(ns, id))
              }
            } ~
            path("synonyms") {                      // synonym texts for NS/ID string
              parameters("nsId") { nsId =>
                logger.info(s"GET kblu/synonyms -> ${nsId}")
                complete(kbLookup.synonyms(nsId))
              }
            }
          } ~
          path("") {                                // index page
            getFromResource("static/index.html")
          } ~
          pathPrefix("static") {                    // SHOULD WORK BUT DOES NOT
            getFromResourceDirectory("/static")
          } ~
          path("application.css") {                 // application stylesheet
            getFromResource("static/application.css")
          } ~
          path("CLU-notext-trans_68x68.png") {      // image
            getFromResource("images/CLU-notext-trans_68x68.png")
          } ~
          path("countEntries") {                    // count the KB entry records
            logger.info(s"GET countEntries")
            complete( kbLookup.countEntries )
          } ~
          path("countKeys") {                       // count the KB key records
            logger.info(s"GET countKeys")
            complete( kbLookup.countKeys )
          } ~
          path("countSources") {                    // count the KB source records
            logger.info(s"GET countSources")
            complete( kbLookup.countSources )
          } ~
          path("dumpSources") {                     // dump the KB source meta information
            logger.info(s"GET dumpSources")
            complete( kbLookup.dumpSources )
          } ~
          path("version") {                         // show version
            logger.info(s"GET version")
            complete( ("version" -> appVersion) )
          }
        }
      }
    }  // routesGet

    val routesPost = {
      logRequestResult("kbquery-microservice") {    // wrap contained paths in logger
        post {                              // POSTS
          pathPrefix("kblu") {
            path("lookup") {                        // by transformed text string(s)
              entity(as[msg.TextMessage]) { msg =>
                logger.info(s"POST kblu/lookup -> ${msg}")
                complete(kbLookup.lookup(msg.text))
              }
            } ~
            path("byText") {                        // by exact matching of text string
              entity(as[msg.TextMessage]) { msg =>
                logger.info(s"POST kblu/byText -> ${msg}")
                complete(kbLookup.lookupText(msg.text))
              }
            } ~
            path("byId") {                          // by ID only
              entity(as[msg.Id]) { msg =>
                logger.info(s"POST kblu/byId -> ${msg}")
                complete(kbLookup.lookupId(msg.id))
              }
            } ~
            path("byNsId") {                        // by NS/ID string
              entity(as[msg.NsId]) { msg =>
                logger.info(s"POST kblu/byNsId -> ${msg}")
                complete(kbLookup.lookupNsId(msg.nsId))
              }
            } ~
            path("byNsAndId") {                     // by namespace and ID
              entity(as[msg.NsAndId]) { msg =>
                logger.info(s"POST kblu/byNsAndId -> ${msg}")
                complete(kbLookup.lookupNsAndId(msg.ns, msg.id))
              }
            } ~
            path("synonyms") {                      // synonym texts for NS/ID string
              entity(as[msg.NsId]) { msg =>
                logger.info(s"POST kblu/synonyms -> ${msg}")
                complete(kbLookup.synonyms(msg.nsId))
              }
            }
          } ~
          path("countEntries") {                    // count the KB entry records
            logger.info(s"POST countEntries")
            complete( kbLookup.countEntries )
          } ~
          path("countKeys") {                       // count the KB key records
            logger.info(s"POST countKeys")
            complete( kbLookup.countKeys )
          } ~
          path("countSources") {                    // count the KB source records
            logger.info(s"POST countSources")
            complete( kbLookup.countSources )
          } ~
          path("dumpEntries") {                     // dump the KB entry records
            logger.info(s"POST dumpEntries")
            complete( kbLookup.dumpEntries )
          } ~
          path("dumpKeys") {                        // dump the KB key records
            logger.info(s"POST dumpKeys")
            complete( kbLookup.dumpKeys )
          } ~
          path("dumpSources") {                     // dump the KB source meta information
            logger.info(s"POST dumpSources")
            complete( kbLookup.dumpSources )
          } ~
          path("version") {                         // show version
            logger.info(s"POST version")
            complete( ("version" -> appVersion) )
          } ~
          path("shutdown") {                        // shut down the server
            logger.info(s"POST shutdown")
            // complete request and then shut down the server in 1 second
            complete {
              in (1.second) {
                system.terminate()
              }
              "Stopping kbquery..."
            }
          }
        }  // post
      }
    }  // routesPost

    routesGet ~ routesPost                  // return concatenated routes
  }

}
