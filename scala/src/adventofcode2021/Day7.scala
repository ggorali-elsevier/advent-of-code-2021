package adventofcode2021

import scala.io.Source

object Day7 {

  def part2Diff(c: Int, pos: Int): Int = Math.abs(c - pos) * (Math.abs(c - pos) + 1) / 2

  def part1Diff(c: Int, pos: Int): Int = Math.abs(c - pos)

  def main(args: Array[String]): Unit = {
    val sample = "inputs/day7sample.txt"
    val input = "inputs/day7input.txt"

    val lines = Source.fromFile(input).getLines.next()
    val initialList = lines.split(",").map(a => a.toInt).toList;


    val a = (initialList.min to initialList.max).map(
      pos => {
        (pos, initialList.map(c => part2Diff(c, pos)).sum)
      }
    ).sortBy(_._2).toList(0)

    println(a)


  }
}
