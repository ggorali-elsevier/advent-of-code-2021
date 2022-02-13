package adventofcode2021

import scala.io.Source

object Day1 {


  def main(args: Array[String]): Unit = {

    val list = Source.fromFile("inputs/day1input.txt").getLines.toList.map(_.toInt)

    val size = {
      list.sliding(2).toList.map(a => a(0) - a(1)).filter(_ < 0).size
    }

    val tripLetSize = {
      list.sliding(3)
        .toList.map(a => a.sum)
        .sliding(2).toList
        .map(a => a(0) - a(1))
        .filter(_ < 0).size
    }
    println(size)
    println(tripLetSize)
  }
}
