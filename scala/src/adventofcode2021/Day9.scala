package adventofcode2021

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day9 {

  def main(args: Array[String]): Unit = {
    val sample = "inputs/day9sample.txt"
    val input = "inputs/day9input.txt"

    val coords = ListBuffer[List[Int]]()

    val padding = 9

    Source.fromFile(input).getLines().map(_.toList.map(_.toString.toInt)).foreach(

      l => {

        coords += padding :: l ::: List(padding)
      }
    )

    val pad = List.fill(coords(0).size)(padding)

    coords.prepend(pad)
    coords.append(pad)

    val minima = ListBuffer[Int]()
    val basins = ListBuffer[scala.collection.mutable.Set[(Int, Int)]]()

    for (i <- 1 to coords.size - 2; j <- 1 to coords(0).size - 2) {

      if (coords(i)(j) < coords(i - 1)(j) &
        coords(i)(j) < coords(i + 1)(j) &
        coords(i)(j) < coords(i)(j - 1) &
        coords(i)(j) < coords(i)(j + 1)
      ) {
        minima += coords(i)(j)
        basins += calculateBasin(i, j, coords, scala.collection.mutable.Set((i, j)))
      }
    }


    val totalRisk = minima.map(_ + 1).sum
    println(totalRisk)
    println(basins)

    val product = basins.sortBy(_.size).reverse.take(3).map(_.size).product
    println(product)

  }

  // ugliest recursive function ever
  def calculateBasin(i: Int, j: Int, coords: ListBuffer[List[Int]], basin: scala.collection.mutable.Set[(Int, Int)]): scala.collection.mutable.Set[(Int, Int)] = {
    val top = coords(i - 1)(j)
    val bottom = coords(i + 1)(j)
    val right = coords(i)(j - 1)
    val left = coords(i)(j + 1)
    if (top != 9) {
      if (basin.add((i - 1, j))) {
        calculateBasin(i - 1, j, coords, basin)
      }
    }
    if (bottom != 9) {
      if (basin.add((i + 1, j))) {
        calculateBasin(i + 1, j, coords, basin)
      }
    }
    if (right != 9) {
      if (
        basin.add((i, j - 1))) {
        calculateBasin(i, j - 1, coords, basin)
      }
    }
    if (left != 9) {
      if (basin.add((i, j + 1))) {
        calculateBasin(i, j + 1, coords, basin)
      }
    }

    return basin
  }

}
