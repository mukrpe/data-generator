package com.sketchbench.ingestion.datasender.metrics

abstract class Metric {
  def getMetrics(): Map[String, String]
}
