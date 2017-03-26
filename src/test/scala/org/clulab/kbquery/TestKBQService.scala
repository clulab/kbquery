package org.clulab.kbquery

import scala.concurrent.duration._
import scala.concurrent.Await
import org.scalatest.{ FlatSpec, BeforeAndAfterAll }
import org.scalatest.MustMatchers
import com.typesafe.config.{ Config, ConfigValueFactory, ConfigFactory }

import akka.event.Logging
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.ScalatestRouteTest
// import MediaTypes._

// import org.json4s.{ Formats, DefaultFormats, jackson, native }

import org.clulab.kbquery._
import org.clulab.kbquery.msg._

class TestKBQService extends FlatSpec
    with MustMatchers
    with ScalatestRouteTest
    with BeforeAndAfterAll
    with KBQService
{
  override implicit val executionContext = system.dispatcher
  override val logger = Logging(system, getClass)

  val config = ConfigFactory.load()
  val route = makeRoutes(config)            // create the service route to test

  // override def beforeAll { }
  // override def afterAll { }

  "KBQServer" should "return correct JSON version string" in {
    Get("/version") ~> route ~> check {
      status must equal(StatusCodes.OK)
      val resp = responseAs[Map[String,String]]
      (resp.get("version")) must equal (Some("0.7.0"))
    }
  }

  //   Get(s"/tweets/$id") ~> route ~> check {
  //     status must equal(StatusCodes.OK)
  //     responseAs[TweetEntity] must equal(tweetEntity)
  //   }
  // }

}
