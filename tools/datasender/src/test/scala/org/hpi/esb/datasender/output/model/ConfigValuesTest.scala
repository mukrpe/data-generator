package com.sketchbench.ingestion.datasender.output.model

import com.sketchbench.ingestion.commons.config.Configs.BenchmarkConfig
import com.sketchbench.ingestion.commons.config.QueryConfig
import com.sketchbench.ingestion.datasender.config.{Config, DataReaderConfig, KafkaProducerConfig}
import com.sketchbench.ingestion.datasender.output.model.ConfigValues._
import org.scalatest.FunSpec
import org.scalatest.mockito.MockitoSugar

class ConfigValuesTest extends FunSpec with MockitoSugar {

  val batchSize = "1000"
  val bufferMemorySize = "1000"
  val lingerTime = "0"
  val readInRam = "true"
  val sendingInterval = "10"
  val sendingIntervalTimeUnit = "SECONDS"
  val scaleFactor = "1"
  val kafkaBootstrapServers = "kafdrop:9092"
  val zookeeperServers = "zookeeper:2181"

  val exampleConfigValues = ConfigValues(batchSize, bufferMemorySize, lingerTime, readInRam,
    sendingInterval, sendingIntervalTimeUnit)

  describe("toList") {
    it("should return a list representation of the config values") {
      val configValuesList = exampleConfigValues.toList
      val expectedList = List(batchSize, bufferMemorySize, lingerTime, readInRam,
        sendingInterval, sendingIntervalTimeUnit)

      assert(configValuesList == expectedList)
    }
  }

  describe("fromMap") {
    it("should return a ConfigValues object") {
      val valueMap = Map(
        BATCH_SIZE -> batchSize,
        BUFFER_MEMORY_SIZE -> bufferMemorySize,
        LINGER_TIME -> lingerTime,
        READ_IN_RAM -> readInRam,
        SENDING_INTERVAL -> sendingInterval,
        SENDING_INTERVAL_TIMEUNIT -> sendingIntervalTimeUnit
      )
      val configValuesFromMap = new ConfigValues(valueMap)

      assert(configValuesFromMap == exampleConfigValues)
    }
  }

  describe("get") {
    it("should return the most important config values") {
      val queryConfigs = List(QueryConfig("mockName", 1))

      val kafkaProducerConfig = KafkaProducerConfig(
        keySerializerClass = Some("keySerializerClass"),
        valueSerializerClass = Some("valueSerializerClass"),
        acks = Some("0"),
        batchSize = Some(batchSize.toInt),
        bufferMemorySize = bufferMemorySize.toLong,
        lingerTime = lingerTime.toInt
      )

      val dataReaderConfig = DataReaderConfig(
        dataInputPath = List[String]("/path"),
        readInRam = readInRam.toBoolean
      )
      val config: Config = Config(dataReaderConfig, kafkaProducerConfig)

      val benchmarkConfig = BenchmarkConfig(
        topicPrefix = "SketchBench",
        benchmarkRun = 0,
        queryConfigs = queryConfigs,
        sendingInterval = sendingInterval.toInt,
        sendingIntervalTimeUnit = sendingIntervalTimeUnit,
        kafkaBootstrapServers = kafkaBootstrapServers,
        zookeeperServers = zookeeperServers
      )

      val configValues = ConfigValues.get(config, benchmarkConfig)

      assert(configValues == exampleConfigValues)
    }
  }

  describe("header") {
    val expectedHeader = List(BATCH_SIZE, BUFFER_MEMORY_SIZE, LINGER_TIME, READ_IN_RAM,
      SENDING_INTERVAL, SENDING_INTERVAL_TIMEUNIT)
    assert(ConfigValues.header == expectedHeader)
  }
}
