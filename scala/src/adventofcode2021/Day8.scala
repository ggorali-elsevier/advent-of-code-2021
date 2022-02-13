package adventofcode2021

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day8 {


  def createLegend(keys: List[String]): Map[String, Int] = {
    val helperMap = scala.collection.mutable.Map[Int, ListBuffer[String]]()
    val legend = scala.collection.mutable.Map[String, Int]()
    val iLegend = scala.collection.mutable.Map[Int, String]()

    // construct a helper map to grouping words with same number of characters
    keys.foreach(
      key => {
        helperMap.get(key.length) match {
          case None => helperMap.put(key.length, ListBuffer(key))
          case _ => helperMap.get(key.length).get += (key)
        }
      }
    )

    // Put the only 2,4,3,7 character words which correspond to numbers 1,4,7,8 respectively
    iLegend.put(1, helperMap(2)(0))
    iLegend.put(4, helperMap(4)(0))
    iLegend.put(7, helperMap(3)(0))
    iLegend.put(8, helperMap(7)(0))

    // Find 3 which is the only 5 letter word has 7 in it
    helperMap.get(5).get.filter(a => {
      (iLegend.get(7).get diff a).size == 0
    }).foreach(a => {
      iLegend.put(3, a)
    }
    )

    // 1* is the pipe shape on the left of the display
    //    ..
    //   |  .
    //   |  .
    //    ..
    // we get it by subtracting 8 from 3
    val oneStar = iLegend.get(8).get diff iLegend.get(3).get
    val one = iLegend.get(1).get
    val oneAndOneStar = one.concat(oneStar)

    helperMap.get(6).get.foreach(
      a => {
        if ((oneAndOneStar diff a).size == 0) {
          iLegend.put(0, a)
        } else if (one intersect a equals (one)) {
          iLegend.put(9, a)
        } else {
          iLegend.put(6, a)
        }
      })


    val rightTop = iLegend.get(0).get diff iLegend.get(6).get
    helperMap.get(5).get -= iLegend.get(3).get
    helperMap.get(5).get.foreach(a => {
      if (a.contains(rightTop)) {
        iLegend.put(2, a)
      } else {
        iLegend.put(5, a)
      }
    })


    iLegend.keys.foreach(key => {
      legend.put(iLegend.get(key).get.sorted, key)
    }
    )

    legend.toMap
  }

  def main(args: Array[String]): Unit = {
    val sample = "inputs/day8sample.txt"
    val input = "inputs/day8input.txt"

    //part1(input)

    val decodedNumbers = ListBuffer[Int]()
    Source.fromFile(input).getLines.map(_.split(" \\| "))
      .map(a => (a(0).trim().split(" ").toList, a(1).trim().split(" ").toList)).
      foreach(a => {
        val legend = createLegend(a._1)
        val decodedNumber = a._2.map(a => legend.get(a.sorted).get).map(String.valueOf(_)).reduce((a, b) => a.concat(b)).toInt;
        decodedNumbers += decodedNumber
      })

    println(decodedNumbers.sum)

  }

  private def part1(input: _root_.java.lang.String) = {
    val lines = Source.fromFile(input).getLines.map(_.split(" \\| "))
      .map(a => a(1).trim().split(" ")).reduce((a, b) => a ++ b).filter(
      a => {
        a.length == 2 || a.length == 3 || a.length == 4 || a.length == 7
      }).toList.size

    println(lines)
  }


  /*
     aaaa
    b    c
    b    c
     dddd
    e    f
    e    f
     gggg
  */

  // be dbe cgeb cbdgef fgaecd  fdcge agebfd fecdb fabcd cfbegad
  //
  val dict = Map(
    "cf" -> 1,
    "acf" -> 7,
    "bcdf" -> 4,
    "acdeg" -> 2,
    "acdfg" -> 3,
    "abdfg" -> 5,
    "abcefg" -> 0,
    "abdefg" -> 6,
    "abcdfg" -> 9,
    "abcdefg" -> 8
  )


}
