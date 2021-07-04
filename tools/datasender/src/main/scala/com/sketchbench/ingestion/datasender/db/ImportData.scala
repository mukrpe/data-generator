package com.sketchbench.ingestion.datasender.db

import com.sketchbench.ingestion.commons.util.Logging

class ImportData extends Logging {

  def importErpData(): Unit = {

    new PostgresDB().importData()

  }

}