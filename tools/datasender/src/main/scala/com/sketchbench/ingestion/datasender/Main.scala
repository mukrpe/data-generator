package com.sketchbench.ingestion.datasender

import com.sketchbench.ingestion.commons.util.Logging
import com.sketchbench.ingestion.datasender.config.ConfigHandler
import com.sketchbench.ingestion.datasender.db.ImportData

object Main extends Logging {

  def main(args: Array[String]): Unit = {
    logger.info("args: " + args.mkString(" "))
    logger.info("Starting Datasender...")
    if (args.length > 0 && args(0).contains("import")) {
      new ImportData().importErpData
    } else {
      setLogLevel(ConfigHandler.config.verbose)
      new DataDriver().run()
    }
  }

  def setLogLevel(verbose: Boolean): Unit = {
    if (verbose) {
      Logging.setToDebug
      logger.info("DEBUG/VERBOSE mode switched on")
    }
  }
}
