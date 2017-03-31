package org.clulab.kbquery.load

import scala.collection.mutable.ListBuffer

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

import akka.actor.{ ActorRef, ActorSystem, Props, Actor }
import akka.actor.Actor

import org.clulab.kbquery.dao._
import org.clulab.kbquery.msg._
import BatchMessages._

/**
  * Akka Actor to batch up entries and write them to the DB.
  *   Written by Tom Hicks. 3/30/2017.
  *   Last Modified: Initial creation.
  */
class EntryBatcher (batchSize:Int) extends Actor {
  var batch = new ListBuffer[EntryType]     // buffer to hold the current batch
  var count: Int = 0                        // count of items in the current batch

  def receive = {

    case BatchAnEntry(entry) =>
      batch += entry                        // add entry to batch
      count += 1                            // increment current batch count
      if (count > batchSize) {              // if batch exceeds size
        KBLoader.loadBatch(batch.toSeq)     // then write out the batch
        batch.clear                         // and reset the batch to empty
        count = 0                           // and reset the batch count
      }
      sender ! EntryBatched(count)          // reply to sender with current count

    case BatchClose =>
      KBLoader.loadBatch(batch.toSeq)       // write remaining items in batch
      sender ! EntryBatched(count)          // reply to sender with current count

    case _ =>

  }
}


/** Object which defines messages used by the EntryBatcher actor. */
object BatchMessages {

  sealed trait BatchMessage
  case class BatchAnEntry (anEntry: EntryType) extends BatchMessage
  case object BatchClose extends BatchMessage

  sealed trait BatchResponse
  case class EntryBatched (batchCount:Int) extends BatchResponse

}
