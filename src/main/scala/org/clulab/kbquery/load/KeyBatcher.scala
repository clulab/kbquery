package org.clulab.kbquery.load

import scala.collection.mutable.ListBuffer
import com.typesafe.scalalogging.LazyLogging

import scalikejdbc._
import scalikejdbc.config._

import org.clulab.kbquery.msg._

/**
  * Class which accumulates a limited, configurable number of key rows before
  * periodically writing them all to the database as a batch.
  *   Written by: Tom Hicks. 4/9/2017.
  *   Last Modified: Major refactoring to classes.
  */
class KeyBatcher (

  /** The parent loader for which this class is providing batching services. */
  kbLoader: KBLoader

) extends LazyLogging {

  private val batch = new ListBuffer[KBKey] // buffer to hold the current batch of entries

  /** Tell whether batching is being used or not. */
  def batchEnabled: Boolean = (BatchSize > 0)

  /** Add the given sequence of entries to the current batch waiting to be written to the DB. */
  def addBatch (entries: Seq[KBKey]): Unit = {
    batch ++= entries                       // add given entries to current batch
    // if batch exceeds batch size OR batch not enabled
    if (!batchEnabled || (batch.size > BatchSize)) {
      kbLoader.writeKeyBatch(batch.toSeq)   // then write out the batch
      batch.clear                           // and reset the batch to empty
    }
  }

  /** Flush any remaining entries to the database and return total count of items written. */
  def flushBatch: Unit = {
    kbLoader.writeKeyBatch(batch.toSeq)     // write remaining items in the batch
    batch.clear                             // and reset the batch to empty
  }

}
