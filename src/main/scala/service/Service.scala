package service

import java.io.File

import akka.actor.ActorSystem
import akka.event.{LoggingAdapter, Logging}
import akka.http.javadsl.model.MediaTypes
// import akka.http.scaladsl.marshalling.xml.ScalaXmlSupport._
import akka.http.scaladsl.model.{HttpResponse, HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import com.typesafe.config.Config
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{Formats, DefaultFormats, jackson, native}
import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

trait Service extends Json4sSupport {

  implicit val serialization = jackson.Serialization // or native.Serialization
  implicit val formats = DefaultFormats

  implicit val system: ActorSystem

  implicit def executionContext: ExecutionContextExecutor

  implicit val materializer: Materializer

  def config: Config

  val logger: LoggingAdapter

  def in[U](duration: FiniteDuration)(body: => U): Unit =
    system.scheduler.scheduleOnce(duration)(body)

//  def apiRequest(request: HttpRequest): Future[HttpResponse] = Source.single(request).via(apiConnectionFlow).runWith(Sink.head)

  val routes = {

    logRequestResult("kbquery-microservice") { // wrap contained paths in logger

      path("") {                               // index page
        getFromResource("static/index.html")
      } ~
      path("version") {                        // show version
        get {
          val html =
            <html>
              <body>
                <h1><code>kbquery</code> version {utils.projectVersion}</h1>
              </body>
            </html>
          complete(html)
        }
      } ~
      post {
        // shuts down the server
        path("shutdown") {
          complete {
            // complete request and then shut down the server in 1 second
            in(1.second) {
              system.terminate()
            }
            "Stopping kbquery..."
          }
        }
      }

    }  // log wrapper
  }  // routes

}
