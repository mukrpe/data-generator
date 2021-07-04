package com.sketchbench.ingestion.datasender.output.writers

import com.sketchbench.ingestion.commons.config.Configs
import com.sketchbench.ingestion.commons.output.model.SeriesResult
import com.sketchbench.ingestion.commons.output.writers.ResultWriter
import com.sketchbench.ingestion.commons.util.Logging
import com.sketchbench.ingestion.datasender.config.ConfigHandler
import com.sketchbench.ingestion.datasender.output.model.{DatasenderResultRow, DatasenderSeriesResult}


class DatasenderSeriesResultWriter(inputFilesPrefix: String, resultsPath: String,
                                   outputFileName: String)
  extends ResultWriter(inputFilesPrefix, resultsPath, outputFileName) {

  override def getFinalResult(runResultMaps: List[Map[String, String]]): SeriesResult = {
    val rows: List[DatasenderResultRow] = runResultMaps.map(new DatasenderResultRow(_))
    new DatasenderSeriesResult(rows)
  }
}

object DatasenderSeriesResultWriter extends Logging {
  def main(args: Array[String]): Unit = {
    val inputFilesPrefix = Configs.benchmarkConfig.topicPrefix
    val resultsPath: String = ConfigHandler.resultsPath
    val outputFileName = s"Series_Result_$inputFilesPrefix.csv"

    val seriesResultWriter = new DatasenderSeriesResultWriter(
      inputFilesPrefix = inputFilesPrefix,
      resultsPath = resultsPath,
      outputFileName = outputFileName)

    seriesResultWriter.execute()
  }
}
