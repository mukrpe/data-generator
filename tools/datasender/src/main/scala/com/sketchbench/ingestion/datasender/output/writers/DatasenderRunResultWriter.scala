package com.sketchbench.ingestion.datasender.output.writers

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.kafka.clients.producer.KafkaProducer
import com.sketchbench.ingestion.commons.config.Configs
import com.sketchbench.ingestion.commons.config.Configs.BenchmarkConfig
import com.sketchbench.ingestion.commons.output.{CSVOutput, Tabulator}
import com.sketchbench.ingestion.commons.util.Logging
import com.sketchbench.ingestion.datasender.config._
import com.sketchbench.ingestion.datasender.metrics.MetricHandler
import com.sketchbench.ingestion.datasender.output.model.{ConfigValues, DatasenderResultRow, ResultValues}

class DatasenderRunResultWriter(config: Config, benchmarkConfig: BenchmarkConfig,
                                kafkaProducer: KafkaProducer[String, String]) extends Logging {

  val currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())

  def outputResults(topicOffsets: Map[String, Long], expectedRecordNumber: Int): Unit = {
    val metricHandler = new MetricHandler(kafkaProducer, topicOffsets, expectedRecordNumber)
    val metrics = metricHandler.fetchMetrics()

    val configValues = ConfigValues.get(ConfigHandler.config, Configs.benchmarkConfig)
    val resultValues = new ResultValues(metrics)

    val dataSenderResultRow = DatasenderResultRow(configValues, resultValues)

    val table = dataSenderResultRow.toTable()
    CSVOutput.write(table, ConfigHandler.resultsPath, ConfigHandler.resultFileName(currentTime))
    logger.info(Tabulator.format(table))
  }
}
