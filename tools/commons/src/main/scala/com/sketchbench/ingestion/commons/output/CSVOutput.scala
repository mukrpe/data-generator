package com.sketchbench.ingestion.commons.output

import java.io.File

import com.github.tototoshi.csv.CSVWriter
import com.sketchbench.ingestion.commons.util.Logging

object CSVOutput extends Logging {

  def write(table: Seq[Seq[Any]], directory: String, fileName: String): Unit = {
    val dir = new File(s"$directory")

    var directoryExists = dir.exists()
    if (!directoryExists) {
      if (dir.mkdir()) {
        directoryExists = true
      } else {
        logger.error("The validation results directory could not be created.")
      }
    }

    if (directoryExists) {
      val f = new File(dir, fileName)
      val writer = CSVWriter.open(f)
      writer.writeAll(table)
      writer.close()
    }
  }
}
