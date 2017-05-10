package org.clulab.kbquery.load

import scala.collection.mutable.ListBuffer
import com.typesafe.scalalogging.LazyLogging

import scalikejdbc._
import scalikejdbc.config._

import org.clulab.kbquery.msg._

/**
  * Class which accumulates a limited, configurable number of entries before
  * periodically writing them all to the database as a batch.
  *   Written by: Tom Hicks. 4/4/2017.
  *   Last Modified: Major refactoring to classes.
  */
class EntryBatcher (

  /** The parent loader for which this class is providing batching services. */
  kbLoader: KBLoader

) extends LazyLogging {

  private val batch = new ListBuffer[KBEntry] // buffer to hold the current batch of entries
  private var fileTotal: Int = 0            // count of items from the current input file
  private var total: Int = 0                // total count of all entries processed

  /** Tell whether batching is being used or not. */
  def batchEnabled: Boolean = (BatchSize > 0)

  /** Add the given sequence of entries to the current batch waiting to be written to the DB. */
  def addBatch (entries: Seq[KBEntry]): Int = {
    val numEntries = entries.size           // remember number given in this round
    batch ++= entries                       // add given entries to current batch
    fileTotal += numEntries                 // increment count for the current input file
    total += numEntries                     // increment total items count
    // if batch exceeds batch size OR batch not enabled
    if (!batchEnabled || (batch.size > BatchSize)) {
      kbLoader.writeBatch(batch.toSeq)      // then write out the batch
      batch.clear                           // and reset the batch to empty
    }
    return numEntries                       // return number of entries processed
  }

  /** Flush any remaining entries to the database and return total count of items written. */
  def flushBatch: Int = {
    kbLoader.writeBatch(batch.toSeq)        // write remaining items in the batch
    batch.clear                             // and reset the batch to empty
    return total                            // return total items written
  }

  /** Reset the current input file record count and return the value before reset. */
  def resetFileCount: Int = {
    val cnt = fileTotal                     // save the file count
    fileTotal = 0                           // reset the counter
    return cnt                              // return the count before reset
  }

}
