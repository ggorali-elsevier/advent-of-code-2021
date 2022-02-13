package adventofcode2021

import scala.io.Source

object Day2 {

  def main(args: Array[String]): Unit = {
    val sampleFilePath = "inputs/day2sample.txt"
    val inputFilePath = "inputs/day2input.txt"
    var depth = 0
    var x = 0
    var aim = 0

    def part1Calculate(entry: Array[String]): Unit = {
      entry(0) match {
        case "forward" => x += entry(1).toInt
        case "down" => depth += entry(1).toInt
        case "up" => depth -= entry(1).toInt
      }
    }

    def part2Calculate(entry: Array[String]): Unit = {
      entry(0) match {
        case "forward" => { x += entry(1).toInt; depth += entry(1).toInt*aim}
        case "down" => aim += entry(1).toInt
        case "up" => aim -= entry(1).toInt
      }
    }

    Source.fromFile(inputFilePath)
      .getLines.toList.map(_.split(" ")).foreach(
      part2Calculate
    )

    println(s"h: ${x} depth: ${depth} product=${depth * x}")
  }



}
