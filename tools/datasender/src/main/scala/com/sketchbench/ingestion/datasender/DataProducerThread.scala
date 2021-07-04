package com.sketchbench.ingestion.datasender

import java.util.concurrent.TimeUnit

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import com.sketchbench.ingestion.commons.util.Logging

class DataProducerThread(dataProducer: DataProducer,
                         val dataReader: DataReader,
                         topic: String,
                         duration: Long,
                         durationTimeUnit: TimeUnit)
  extends Runnable with Logging {

  var numberOfRecords: Int = 0
  val startTime: Long = currentTime
  val endTime: Long = startTime + durationTimeUnit.toMillis(duration)

  private var topicMsgIdList: Map[String, Int] = Map[String, Int]()

  def getNextMessageId(topic: String): Int = {
    var id = 0
    if (topicMsgIdList.contains(topic)) {
      id = topicMsgIdList(topic)
      id += 1
    }
    topicMsgIdList += (topic -> id)
    id
  }

  def currentTime: Long = System.currentTimeMillis()

  def run() {
    val msg = dataReader.readRecord
    if (currentTime < endTime && msg.nonEmpty) {
      send(msg.get)
    } else {
      logger.info(s"Shut down after $durationTimeUnit: $duration.")
      dataProducer.shutDown()
    }
  }

  def send(message: String): Unit = {
    val msgArray = message.split("\\s")
    val msgTmp = "\"ts\":" + msgArray(0) + "\",\"index\":\"" + msgArray(1) + "\",\"mf01\":\"" + msgArray(2) + "\",\"mf02\":\"" + msgArray(3) + "\",\"mf03\":\"" + msgArray(4)+"\",\"pc13\":\"" + msgArray(5) + "\",\"pc14\":\"" + msgArray(6) + "\",\"pc15\":\"" + msgArray(7) + "\",\"pc25\":\"" + msgArray(8) + "\",\"pc26\":\"" + msgArray(9) + "\",\"pc27\":\"" + msgArray(10) + "\",\"res\":\"" + msgArray(11)+"\""
    sendToKafka(topic, msgTmp)
  }

  def sendToKafka(topic: String, message: String): Unit = {
    //val msgWithIdAndTs = s"\"${getNextMessageId(topic)}\";$currentTime;$message"
    
    var messageID = getNextMessageId(topic)
    //val msgWithIdAndTs = "{\"" + messageID +"\":{" + message + "}}"
      val msgWithIdAndTs = "{\"messageID\":\"" + messageID +"\"," + message + "}"
    val record = new ProducerRecord[String, String](topic, msgWithIdAndTs)
    dataProducer.getKafkaProducer.send(record)
    logger.debug(s"Sent value $msgWithIdAndTs to topic $topic.")
  }
}

