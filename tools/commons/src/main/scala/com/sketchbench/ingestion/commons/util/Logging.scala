package com.sketchbench.ingestion.commons.util

import org.apache.log4j.{Level, Logger}

trait Logging {
  var logger: Logger = Logger.getLogger("SketchBenchLogger")
}

object Logging {

  def setToInfo() {
    Logger.getRootLogger.setLevel(Level.INFO)
  }

  def setToDebug() {
    Logger.getRootLogger.setLevel(Level.DEBUG)
  }
}
