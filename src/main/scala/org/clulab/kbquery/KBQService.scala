package org.clulab.kbquery

import java.io.File

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._
import com.typesafe.config.Config

import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{ Formats, DefaultFormats, jackson, native }

import akka.actor.ActorSystem
import akka.event.{ LoggingAdapter, Logging }
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer

/**
  * Trait to provide an Akka HTTP service using Json4s support for marshalling.
  *   Written by: Tom Hicks from code by Gus Hahn-Powell. 3/24/2016.
  *   Last Modified: Minor cleanup: remove unused code.
  */
trait KBQService extends Json4sSupport {

  implicit val serialization = jackson.Serialization // or native.Serialization
  implicit val formats = DefaultFormats

  implicit val system: ActorSystem
  implicit def executionContext: ExecutionContextExecutor
  implicit val materializer: Materializer

  val logger: LoggingAdapter

  def in [U] (duration: FiniteDuration)(body: => U): Unit =
    system.scheduler.scheduleOnce(duration)(body)

  /** Create and return the route for this app, using the given configuration, if needed. */
  def makeRoute (config: Config): Route = {
    val appVersion = config.getString("app.version")

    val routes = {
      logRequestResult("kbquery-microservice") {    // wrap contained paths in logger
        get {                               // GETS
          pathPrefix("kblu") {
            path("byText") {                        // by text string
              parameters("text") { text =>
                logger.info(s"GET kblu/byText -> ${text}")
                complete(KBLookup.lookup(text))
              }
            } ~
            path("byNsId") {                        // by NS/ID string
              parameters("nsId") { nsId =>
                logger.info(s"GET kblu/byNsId -> ${nsId}")
                complete(KBLookup.lookupNsId(nsId))
              }
            } ~
            path("byNsAndId") {                     // by namespace and ID
              parameters("ns", "id") { (ns, id) =>
                logger.info(s"GET kblu/byNsAndId -> ${ns}, ${id}")
                complete(KBLookup.lookupNsAndId(ns, id))
              }
            } ~
            path("synonyms") {                      // synonym texts for NS/ID string
              parameters("nsId") { nsId =>
                logger.info(s"GET kblu/synonyms -> ${nsId}")
                complete(KBLookup.synonyms(nsId))
              }
            }
          } ~
          path("") {                                // index page
            getFromResource("static/index.html")
          } ~
          path("version") {                         // show version
            complete( ("version" -> appVersion) )
          }
        } ~
        post {                              // POSTS
          pathPrefix("kblu") {
            path("byText") {                        // by text string
              entity(as[msg.TextMessage]) { msg =>
                logger.info(s"POST kblu/byText -> ${msg}")
                complete(KBLookup.lookup(msg.text))
              }
            } ~
            path("byNsId") {                        // by NS/ID string
              entity(as[msg.NsId]) { msg =>
                logger.info(s"POST kblu/byNsId -> ${msg}")
                complete(KBLookup.lookupNsId(msg.nsId))
              }
            } ~
            path("byNsAndId") {                     // by namespace and ID
              entity(as[msg.NsAndId]) { msg =>
                logger.info(s"POST kblu/byNsAndId -> ${msg}")
                complete(KBLookup.lookupNsAndId(msg.ns, msg.id))
              }
            } ~
            path("synonyms") {                      // synonym texts for NS/ID string
              entity(as[msg.NsId]) { msg =>
                logger.info(s"POST kblu/synonyms -> ${msg}")
                complete(KBLookup.synonyms(msg.nsId))
              }
            }
          } ~
          path("shutdown") {                        // shut down the server
            // complete request and then shut down the server in 1 second
            complete {
              in (1.second) {
                system.terminate()
              }
              "Stopping kbquery..."
            }
          }
        }  // post
      }  // log wrapper
    }  // routes

    routes                                  // return constructed routes
  }

}
