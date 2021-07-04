package com.sketchbench.ingestion.commons.output.model

abstract class Result {
  def toTable(): List[List[String]]
}
