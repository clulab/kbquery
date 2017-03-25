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
// import akka.http.javadsl.model._
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer

trait KBQService extends Json4sSupport {

  implicit val serialization = jackson.Serialization // or native.Serialization
  implicit val formats = DefaultFormats

  implicit val system: ActorSystem
  implicit def executionContext: ExecutionContextExecutor
  implicit val materializer: Materializer

  val logger: LoggingAdapter

  def in [U] (duration: FiniteDuration)(body: => U): Unit =
    system.scheduler.scheduleOnce(duration)(body)

  // def apiRequest(request: HttpRequest): Future[HttpResponse] =
  //   Source.single(request).via(apiConnectionFlow).runWith(Sink.head)

  def makeRoutes (config: Config): Route = {
    val appVersion = config.getString("app.version")

    val routes = {
      logRequestResult("kbquery-microservice") { // wrap contained paths in logger
        get {
          path("") {                               // index page
            getFromResource("static/index.html")
          } ~
          path("version") {                        // show version
            val html =
              <html>
                <body>
                  <h2><code>kbquery</code> version {appVersion}</h2>
                </body>
              </html>
            complete(html)
          }  // REMOVE WHEN UNCOMMENTING NEXT
          // } ~
          // path("kblu" / "byNsId") {
          //   entity(as[api.NsIdMessage]) { msg =>
          //     logger.info(s"GET kblu/byNsId -> ${msg.nsId}")
          //     val kbes = KBLookup.lookupNsId(msg.nsId)
          //     val json = ConverterUtils.toJson(kbes)
          //     complete(json)
          //   }
          // }
        } ~
        post {
          path("shutdown") {                     // shut down the server
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
