package adventofcode2021

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Set
import scala.collection.mutable.Map
import scala.io.Source

object Day11 {
  def main(args: Array[String]): Unit = {
    val sample = "inputs/day11sample.txt"
    val input = "inputs/day11input.txt"

    val coords = ListBuffer[List[Int]]()

    val padding = -100

    Source.fromFile(sample).getLines().map(_.toList.map(_.toString.toInt)).foreach(
      l => {
        coords += padding :: l ::: List(padding)
        println(l)
      })

    val pad = List.fill(coords(0).size)(padding)
    coords.prepend(pad)
    coords.append(pad)
    println(coords)
    var highlights = 0

    val coordsAsMap = Map[(Int, Int), Int]()

    // caveman style

    for (i <- 0 until coords.size; j <- 0 until coords(0).size) {
      coordsAsMap.put((i, j), coords(i)(j))
    }
    println(coordsAsMap)
    var highlightedInThisStep = Set[(Int, Int)]()

    for (i <- 1 until 3) {
      coordsAsMap.keys.foreach(a => coordsAsMap.put(a, coordsAsMap(a) + 1))
      coordsAsMap.keys.filter(k => {
        coordsAsMap(k) > 9
      }).foreach(l => {
        coordsAsMap.put(l, 0)
        highlightedInThisStep.add(l)
        highlights = highlights + 1
        highlights += highlighter(coordsAsMap, l, highlightedInThisStep)
      })

      printMap(coordsAsMap)

    }

    println(highlights)
  }

  def printMap(coordsAsMap: Map[(Int, Int), Int]) = {
    for (i <- 1 to 10; j <- 1 to 10) {
      print(coordsAsMap((i, j)))
      if (j == 10) print("\n")
    }
    print("\n")
  }

  private def highlighter(coordsAsMap: Map[(Int, Int), Int], here: (Int, Int), highlightsSet: Set[(Int, Int)]): Int = {
    val self = here
    val top = (here._1 - 1, here._2)
    val topLeft = (here._1 - 1, here._2 - 1)
    val topRight = (here._1 - 1, here._2 + 1)
    val left = (here._1, here._2 - 1)
    val right = (here._1, here._2 + 1)
    val bottom = (here._1 + 1, here._2)
    val bottomLeft = (here._1 + 1, here._2 - 1)
    val bottomRight = (here._1 + 1, here._2 + 1)

    val neighbours = List(top, topLeft, topRight, left, right, bottom, bottomLeft, bottomRight)

    //First, the energy level of each octopus increases by 1.
    neighbours.foreach(t => {
      if (coordsAsMap(t._1, t._2) != None && coordsAsMap(t._1, t._2) != 0)
        coordsAsMap.put((t._1, t._2), coordsAsMap(t._1, t._2) + 1)
    })

    var highlightsthisStep = 0
    // energy > 9 flashes
    neighbours.filter(t => coordsAsMap(t._1, t._2) > 9)
      .foreach(a => {
        coordsAsMap.put((a._1, a._2), 0)
        highlightsSet.add((a._1, a._2))
        highlightsthisStep += 1
        highlightsthisStep += highlighter(coordsAsMap, a, highlightsSet)
      })

    return highlightsthisStep

  }
}
