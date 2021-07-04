package com.sketchbench.ingestion.util

import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar

import scala.collection.mutable

class TopicManagementTest extends FunSuite with MockitoSugar {

  test("testGetMatchingTopics") {

    val topicsToDelete = mutable.Buffer("SketchBench_IN_0", "SketchBench_OUT_O", "SketchBench_STATISTICS_0")
    val topicsToKeep = mutable.Buffer("sketchbench_new_IN_0", "sketchbench_new_OUT_0", "sketchbench_new_STATISTICS_0", "topic1", "topic2")
    val allTopics = topicsToDelete ++ topicsToKeep
    val prefix =  "SketchBench_"

    assert(allTopics.containsSlice(topicsToDelete))
    assert(allTopics.containsSlice(topicsToKeep))
    assert(allTopics.size == topicsToDelete.size + topicsToKeep.size)
    assert(TopicManagement.getMatchingTopics(allTopics, prefix) == topicsToDelete)
  }
}
