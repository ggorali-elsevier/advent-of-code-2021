package adventofcode2021

import scala.io.Source

object Day5 {

  def main(args: Array[String]): Unit = {

    val sampleFilePath = "inputs/day5sample.txt"
    val inputFilePath = "inputs/day5input.txt"

    var theMap = scala.collection.mutable.Map[Point, Int]().withDefaultValue(0)

    Source.fromFile(inputFilePath)

      .getLines.toList.map(a =>
      a.split(" -> ")
        .map(p => {
          val entry = p.split(",")
          Point(Integer.parseInt(entry(0)), Integer.parseInt(entry(1)))
        }))
//      .filter(pl => pl(0).x == pl(1).x || pl(0).y == pl(1).y)
      .map(_.sorted)
      .foreach(pl => {


        if (pl(0).x == pl(1).x) {
          (pl(0).y to pl(1).y).map(a => {
            theMap(Point(pl(0).x, a)) += 1
          })
        } else
        if (pl(0).y == pl(1).y) {
          (pl(0).x to pl(1).x).map(a => {
            theMap(Point(a, pl(0).y)) += 1
          })
        } else
        {
          var xStep, yStep = 0
          if(pl(0).x <= pl(1).x) {
            xStep = 1
          } else {
            xStep = -1
          }

          if(pl(0).y <= pl(1).y) {
            yStep = 1
          } else {
            yStep = -1
          }

          (pl(0).x to pl(1).x by xStep)
            .zip(pl(0).y to pl(1).y by yStep)
            .map(step =>theMap(Point(step._1, step._2)) += 1)
       }

//        println(s"${pl(0)} ** ${pl(1)} ")

      })

    val overlaps = theMap.values.filter(i => i > 1).toList.length
    println(overlaps)
  }

  case class Point(x: Int, y: Int) extends Ordered[Point] {
    def compare(that: Point) = this.x + this.y compare that.x + that.y
  }

}
