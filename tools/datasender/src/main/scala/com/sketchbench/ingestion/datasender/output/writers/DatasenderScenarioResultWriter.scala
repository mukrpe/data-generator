package com.sketchbench.ingestion.datasender.output.writers

import com.sketchbench.ingestion.commons.output.model.ScenarioResult
import com.sketchbench.ingestion.commons.output.writers.ResultWriter
import com.sketchbench.ingestion.datasender.config.ConfigHandler
import com.sketchbench.ingestion.datasender.output.model.{DatasenderResultRow, DatasenderScenarioResult}

class DatasenderScenarioResultWriter(inputFilesPrefix: String, resultsPath: String,
                                     outputFileName: String)
  extends ResultWriter(inputFilesPrefix, resultsPath, outputFileName) {


  override def getFinalResult(seriesResultMaps: List[Map[String, String]]): ScenarioResult = {
    val datasenderResultRows = seriesResultMaps.map(new DatasenderResultRow(_))
    new DatasenderScenarioResult(datasenderResultRows)
  }
}

object DatasenderScenarioResultWriter {
  def main(args: Array[String]): Unit = {
    val inputFilesPrefix: String = "Series_Result"
    val resultsPath: String = ConfigHandler.resultsPath
    val outputFileName = "Scenario_Result.csv"

    val merger = new DatasenderScenarioResultWriter(
      inputFilesPrefix = inputFilesPrefix,
      resultsPath = resultsPath,
      outputFileName = outputFileName)

    merger.execute()
  }
}
