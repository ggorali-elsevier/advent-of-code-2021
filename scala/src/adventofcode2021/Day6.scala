package adventofcode2021

import scala.io.Source

object Day6 {
  def main(args: Array[String]): Unit = {
    val sampleFilePath = "inputs/day6sample.txt"
    val inputFilePath = "inputs/day6input.txt"

    val lines = Source.fromFile(inputFilePath).getLines.next()
    val initialList = lines.split(",").map(a => a.toInt).toList;
    val theMap = scala.collection.mutable.Map[Int, Long]().withDefaultValue(0L)

    initialList.foreach(theMap(_) += 1L)

    println(theMap)

    for (_ <- 1 to 256) {
      val d0 = theMap(0)
      theMap(0) = theMap(1)
      theMap(1) = theMap(2)
      theMap(2) = theMap(3)
      theMap(3) = theMap(4)
      theMap(4) = theMap(5)
      theMap(5) = theMap(6)
      theMap(6) = theMap(7) + d0
      theMap(7) = theMap(8)
      theMap(8) = d0
    }
    println(theMap.values.sum)

  }
}
