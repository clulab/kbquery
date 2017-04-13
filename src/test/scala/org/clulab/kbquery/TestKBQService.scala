package org.clulab.kbquery

import com.typesafe.config.{ Config, ConfigValueFactory, ConfigFactory }

import scala.concurrent.duration._
import scala.concurrent.Await

import akka.event.Logging
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

import org.scalatest.{ Matchers, WordSpec }
import akka.http.scaladsl.testkit.ScalatestRouteTest

import org.clulab.kbquery._
import org.clulab.kbquery.msg._

/**
  * Unit tests of the KBQ service class.
  *   Written by: Tom Hicks. 3/26/2017.
  *   Last Modified: Add tests for byID methods.
  */
class TestKBQService extends WordSpec
    with Matchers
    with ScalatestRouteTest
    with KBQService
{
  override implicit val executionContext = system.dispatcher
  override val logger = Logging(system, getClass)

  val config = ConfigFactory.load()
  val version = config.getString("app.version")

  val route = makeRoute(config)             // create the service route to test

  "The service" should {

    "return correct JSON version string" in {
      Get("/version") ~> route ~> check {
        status should equal(StatusCodes.OK)
        val resp = responseAs[Map[String,String]]
        (resp.get("version")) should equal (Some(version))
      }
    }

    // GETs

    "lookup by text" in {
      Get("/kblu/byText?text=ZZZ4") ~> route ~> check {
        status should equal(StatusCodes.OK)
        val resp = responseAs[KBEntries]
        (resp.entries) should not be (empty)
          (resp.entries.size) should be (2)
        val entry = resp.entries(0)
          (entry.text) should equal ("ZZZ4")
        (entry.namespace) should equal ("uniprot")
          (entry.id) should equal ("P36037")
        (entry.label) should equal ("G")
          (entry.isGeneName) should be (false)
        (entry.isShortName) should be (false)
      }
    }

    "lookup by nsId" in {
      Get("/kblu/byNsId?nsId=uniprot:Q15942") ~> route ~> check {
        status should equal(StatusCodes.OK)
        val resp = responseAs[KBEntries]
        (resp.entries) should not be (empty)
          (resp.entries.size) should be (6)
        val entry = resp.entries(0)
          ((List[String](entry.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
        (entry.namespace) should equal ("uniprot")
          (entry.id) should equal ("Q15942")
        (entry.label) should equal ("G")
        (entry.isGeneName) should be (false)
          (entry.isShortName) should be (false)
      }
    }

    "lookup by namespace and ID" in {
      Get("/kblu/byNsAndId?ns=uniprot&id=Q15942") ~> route ~> check {
        status should equal(StatusCodes.OK)
        val resp = responseAs[KBEntries]
        (resp.entries) should not be (empty)
          (resp.entries.size) should be (6)
        val entry = resp.entries(0)
          ((List[String](entry.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
        (entry.namespace) should equal ("uniprot")
          (entry.id) should equal ("Q15942")
        (entry.label) should equal ("G")
          (entry.isGeneName) should be (false)
        (entry.isShortName) should be (false)
      }
    }

    "lookup by ID only" in {
      Get("/kblu/byId?id=Q15942") ~> route ~> check {
        status should equal(StatusCodes.OK)
        val resp = responseAs[KBEntries]
        (resp.entries) should not be (empty)
          (resp.entries.size) should be (6)
        val entry = resp.entries(0)
          ((List[String](entry.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
        (entry.namespace) should equal ("uniprot")
          (entry.id) should equal ("Q15942")
        (entry.label) should equal ("G")
          (entry.isGeneName) should be (false)
        (entry.isShortName) should be (false)
      }
    }

    "lookup synonyms by nsId" in {
      Get("/kblu/synonyms?nsId=uniprot:Q13131") ~> route ~> check {
        status should equal(StatusCodes.OK)
        val resp = responseAs[Synonyms]
        (resp.synonyms) should not be (empty)
          (resp.synonyms.size) should be (12)
        (resp.synonyms) should contain ("AMPKa1")
          (resp.synonyms) should contain ("AMPK-a1")
        (resp.synonyms) should contain ("AMPK-alpha1")
          (resp.synonyms) should contain ("AMPK alpha-1")
      }
    }


    // POSTs

    "POST lookup by text" in {
      Post("/kblu/byText",
        HttpEntity(ContentTypes.`application/json`, """{ "text": "ZZZ4" }""")) ~> route ~> check
        {
          status should equal(StatusCodes.OK)
          val resp = responseAs[KBEntries]
          (resp.entries) should not be (empty)
            (resp.entries.size) should be (2)
          val entry = resp.entries(0)
            (entry.text) should equal ("ZZZ4")
          (entry.namespace) should equal ("uniprot")
            (entry.id) should equal ("P36037")
          (entry.label) should equal ("G")
            (entry.isGeneName) should be (false)
          (entry.isShortName) should be (false)
        }
    }

    "POST lookup by nsId" in {
      Post("/kblu/byNsId",
        HttpEntity(ContentTypes.`application/json`, """{ "nsId": "uniprot:Q15942" }""")) ~> route ~> check
      {
        status should equal(StatusCodes.OK)
        val resp = responseAs[KBEntries]
          (resp.entries) should not be (empty)
        (resp.entries.size) should be (6)
        val entry = resp.entries(0)
        ((List[String](entry.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
          (entry.namespace) should equal ("uniprot")
        (entry.id) should equal ("Q15942")
          (entry.label) should equal ("G")
        (entry.isGeneName) should be (false)
          (entry.isShortName) should be (false)
      }
    }

    "POST lookup by namespace and ID" in {
      Post("/kblu/byNsAndId",
        HttpEntity(ContentTypes.`application/json`,
          """{ "ns": "uniprot", "id": "Q15942" }""")) ~> route ~> check
      {
        status should equal(StatusCodes.OK)
        val resp = responseAs[KBEntries]
          (resp.entries) should not be (empty)
        (resp.entries.size) should be (6)
        val entry = resp.entries(0)
        ((List[String](entry.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
          (entry.namespace) should equal ("uniprot")
        (entry.id) should equal ("Q15942")
          (entry.label) should equal ("G")
        (entry.isGeneName) should be (false)
          (entry.isShortName) should be (false)
      }
    }

    "POST lookup by ID only" in {
      Post("/kblu/byId",
        HttpEntity(ContentTypes.`application/json`,
          """{ "id": "Q15942" }""")) ~> route ~> check
      {
        status should equal(StatusCodes.OK)
        val resp = responseAs[KBEntries]
          (resp.entries) should not be (empty)
        (resp.entries.size) should be (6)
        val entry = resp.entries(0)
        ((List[String](entry.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
          (entry.namespace) should equal ("uniprot")
        (entry.id) should equal ("Q15942")
          (entry.label) should equal ("G")
        (entry.isGeneName) should be (false)
          (entry.isShortName) should be (false)
      }
    }

    "POST lookup synonyms by nsId" in {
      Post("/kblu/synonyms",
        HttpEntity(ContentTypes.`application/json`,
          """{ "nsId": "uniprot:Q13131" }""")) ~> route ~> check
      {
        status should equal(StatusCodes.OK)
        val resp = responseAs[Synonyms]
          (resp.synonyms) should not be (empty)
        (resp.synonyms.size) should be (12)
          (resp.synonyms) should contain ("AMPKa1")
        (resp.synonyms) should contain ("AMPK-a1")
          (resp.synonyms) should contain ("AMPK-alpha1")
        (resp.synonyms) should contain ("AMPK alpha-1")
      }
    }
  }

}
