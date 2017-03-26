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

import org.clulab.kbquery._
import org.clulab.kbquery.msg._

/**
  * Unit tests of the KBQ service class.
  *   Written by: Tom Hicks. 3/26/2017.
  *   Last Modified: Add test for synonyms.
  */
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

  it should "lookup by text" in {
    Get("/kblu/byText?text=AKT1") ~> route ~> check {
      status must equal(StatusCodes.OK)
      val resp = responseAs[KBEntries]
      (resp.entries) must not be (empty)
      val entry = resp.entries(0)
      (entry.text) must equal ("AKT1")
      (entry.namespace) must equal ("uniprot")
      (entry.id) must equal ("P31749")
      (entry.label) must equal ("Gene_or_gene_product")
      (entry.isGeneName) must be (false)
      (entry.isShortName) must be (false)
    }
  }

  it should "lookup by nsId" in {
    Get("/kblu/byNsId?nsId=myNamespace:XYZ") ~> route ~> check {
      status must equal(StatusCodes.OK)
      val resp = responseAs[KBEntries]
      (resp.entries) must not be (empty)
      (resp.entries.size) must be (3)
      val entry = resp.entries(0)
      (entry.text) must equal ("textA")
      (entry.namespace) must equal ("myNamespace")
      (entry.id) must equal ("XYZ-A")
      (entry.label) must equal ("Gene_or_gene_product")
      (entry.isGeneName) must be (false)
      (entry.isShortName) must be (false)
    }
  }

  it should "lookup by namespace and ID" in {
    Get("/kblu/byNsAndId?ns=NEW_NS&id=QQQ") ~> route ~> check {
      status must equal(StatusCodes.OK)
      val resp = responseAs[KBEntries]
      (resp.entries) must not be (empty)
      (resp.entries.size) must be (3)
      val entry = resp.entries(0)
      (entry.text) must equal ("textD")
      (entry.namespace) must equal ("NEW_NS")
      (entry.id) must equal ("QQQ-D")
      (entry.label) must equal ("Simple_chemical")
      (entry.isGeneName) must be (false)
      (entry.isShortName) must be (false)
    }
  }

  it should "lookup synonyms by nsId" in {
    Get("/kblu/synonyms?nsId=uniprot:Q13131") ~> route ~> check {
      status must equal(StatusCodes.OK)
      val resp = responseAs[Synonyms]
      (resp.synonyms) must not be (empty)
      (resp.synonyms.size) must be (4)
      (resp.synonyms) must contain ("AMPKa1")
      (resp.synonyms) must contain ("AMPK-a1")
      (resp.synonyms) must contain ("AMPK-alpha1")
      (resp.synonyms) must contain ("AMPK alpha-1")
    }
  }

}
