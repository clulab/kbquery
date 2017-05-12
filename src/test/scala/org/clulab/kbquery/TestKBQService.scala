package org.clulab.kbquery

import scala.concurrent.ExecutionContextExecutor

import com.typesafe.config.{ Config, ConfigFactory }

import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{ Formats, DefaultFormats, jackson, native }

import akka.event.Logging
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.ScalatestRouteTest

import org.scalatest.{ Matchers, WordSpec }

import org.clulab.kbquery._
import org.clulab.kbquery.msg._

/**
  * Unit tests of the KBQ service class.
  *   Written by: Tom Hicks. 3/26/2017.
  *   Last Modified: Update for major refactoring.
  */
class TestKBQService extends WordSpec
    with Matchers
    with ScalatestRouteTest
    with Json4sSupport
{
  val config = ConfigFactory.load()
  val version = config.getString("app.version")

  implicit val serialization = jackson.Serialization // or native.Serialization
  implicit val formats = DefaultFormats

  implicit val executionContext = system.dispatcher
  val logger = Logging(system, getClass)

  // currently configured label for Gene_or_gene_product
  val GGP_LABEL = "Gene_or_gene_product"

  val dbManager = new DBManager(config)
  val kbLookup = new KBLookup(dbManager)
  val kbqService = new KBQService(config, kbLookup)
  val route = kbqService.makeRoute(config)  // create the service route to test

  "The class under test" should {

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
        val resp = responseAs[Entities]
        (resp.entities) should not be (empty)
        (resp.entities.size) should be (2)
        val entity = resp.entities(0)
        (entity.text) should equal ("ZZZ4")
        (entity.namespace) should equal ("uniprot")
        (entity.id) should equal ("P36037")
        (entity.label) should equal (GGP_LABEL)
        (entity.isGeneName) should be (false)
        (entity.isShortName) should be (false)
      }
    }

    "lookup by nsId" in {
      Get("/kblu/byNsId?nsId=uniprot:Q15942") ~> route ~> check {
        status should equal(StatusCodes.OK)
        val resp = responseAs[Entities]
        (resp.entities) should not be (empty)
        (resp.entities.size) should be (12)
        val entity = resp.entities(0)
        ((List[String](entity.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
        (entity.namespace) should equal ("uniprot")
        (entity.id) should equal ("Q15942")
        (entity.label) should equal (GGP_LABEL)
        (entity.isGeneName) should be (false)
        (entity.isShortName) should be (false)
      }
    }

    "lookup by namespace and ID" in {
      Get("/kblu/byNsAndId?ns=uniprot&id=Q15942") ~> route ~> check {
        status should equal(StatusCodes.OK)
        val resp = responseAs[Entities]
        (resp.entities) should not be (empty)
        (resp.entities.size) should be (12)
        val entity = resp.entities(0)
        ((List[String](entity.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
        (entity.namespace) should equal ("uniprot")
        (entity.id) should equal ("Q15942")
        (entity.label) should equal (GGP_LABEL)
        (entity.isGeneName) should be (false)
        (entity.isShortName) should be (false)
      }
    }

    "lookup by ID only" in {
      Get("/kblu/byId?id=Q15942") ~> route ~> check {
        status should equal(StatusCodes.OK)
        val resp = responseAs[Entities]
        (resp.entities) should not be (empty)
        (resp.entities.size) should be (12)
        val entity = resp.entities(0)
        ((List[String](entity.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
        (entity.namespace) should equal ("uniprot")
        (entity.id) should equal ("Q15942")
        (entity.label) should equal (GGP_LABEL)
        (entity.isGeneName) should be (false)
        (entity.isShortName) should be (false)
      }
    }

    "lookup synonyms by nsId" in {
      Get("/kblu/synonyms?nsId=uniprot:Q13131") ~> route ~> check {
        status should equal(StatusCodes.OK)
        val resp = responseAs[Synonyms]
        (resp.synonyms) should not be (empty)
        (resp.synonyms.size) should be (22)
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
          val resp = responseAs[Entities]
          (resp.entities) should not be (empty)
          (resp.entities.size) should be (2)
          val entity = resp.entities(0)
          (entity.text) should equal ("ZZZ4")
          (entity.namespace) should equal ("uniprot")
          (entity.id) should equal ("P36037")
          (entity.label) should equal (GGP_LABEL)
          (entity.isGeneName) should be (false)
          (entity.isShortName) should be (false)
        }
    }

    "POST lookup by nsId" in {
      Post("/kblu/byNsId",
        HttpEntity(ContentTypes.`application/json`,
          """{ "nsId": "uniprot:Q15942" }""")) ~> route ~> check
      {
        status should equal(StatusCodes.OK)
        val resp = responseAs[Entities]
        (resp.entities) should not be (empty)
        (resp.entities.size) should be (12)
        val entity = resp.entities(0)
        ((List[String](entity.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
        (entity.namespace) should equal ("uniprot")
        (entity.id) should equal ("Q15942")
        (entity.label) should equal (GGP_LABEL)
        (entity.isGeneName) should be (false)
        (entity.isShortName) should be (false)
      }
    }

    "POST lookup by namespace and ID" in {
      Post("/kblu/byNsAndId",
        HttpEntity(ContentTypes.`application/json`,
          """{ "ns": "uniprot", "id": "Q15942" }""")) ~> route ~> check
      {
        status should equal(StatusCodes.OK)
        val resp = responseAs[Entities]
        (resp.entities) should not be (empty)
        (resp.entities.size) should be (12)
        val entity = resp.entities(0)
        ((List[String](entity.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
        (entity.namespace) should equal ("uniprot")
        (entity.id) should equal ("Q15942")
        (entity.label) should equal (GGP_LABEL)
        (entity.isGeneName) should be (false)
        (entity.isShortName) should be (false)
      }
    }

    "POST lookup by ID only" in {
      Post("/kblu/byId",
        HttpEntity(ContentTypes.`application/json`,
          """{ "id": "Q15942" }""")) ~> route ~> check
      {
        status should equal(StatusCodes.OK)
        val resp = responseAs[Entities]
        (resp.entities) should not be (empty)
        (resp.entities.size) should be (12)
        val entity = resp.entities(0)
        ((List[String](entity.text)) should contain oneOf ("ZYX", "Zyxin", "Zyxin-2"))
        (entity.namespace) should equal ("uniprot")
        (entity.id) should equal ("Q15942")
        (entity.label) should equal (GGP_LABEL)
        (entity.isGeneName) should be (false)
        (entity.isShortName) should be (false)
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
        (resp.synonyms.size) should be (22)
        (resp.synonyms) should contain ("AMPKa1")
        (resp.synonyms) should contain ("AMPK-a1")
        (resp.synonyms) should contain ("AMPK-alpha1")
        (resp.synonyms) should contain ("AMPK alpha-1")
      }
    }
  }

}
