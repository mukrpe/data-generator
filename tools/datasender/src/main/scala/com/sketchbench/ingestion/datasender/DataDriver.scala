package com.sketchbench.ingestion.datasender

import com.sketchbench.ingestion.commons.config.Configs
import com.sketchbench.ingestion.commons.util.Logging
import com.sketchbench.ingestion.datasender.config._
import com.sketchbench.ingestion.datasender.output.writers.DatasenderRunResultWriter

import scala.io.Source

class DataDriver extends Logging {

  private val config = ConfigHandler.config
  private val dataProducer = createDataProducer
  private val resultHandler = new DatasenderRunResultWriter(config, Configs.benchmarkConfig, dataProducer.getKafkaProducer)

  def run(): Unit = {
    dataProducer.execute()
  }

  def createDataProducer: DataProducer = {
    val sendingInterval = Configs.benchmarkConfig.sendingInterval
    val sendingIntervalTimeUnit = Configs.benchmarkConfig.getSendingIntervalTimeUnit
    val duration = Configs.benchmarkConfig.duration
    val durationTimeUnit = Configs.benchmarkConfig.getDurationTimeUnit

    new DataProducer(resultHandler, config.dataReaderConfig, Configs.benchmarkConfig.sourceTopics,
      sendingInterval, sendingIntervalTimeUnit, duration, durationTimeUnit)
  }
}
