package adventofcode2021

import scala.collection.mutable.{ListBuffer, Stack}
import scala.io.Source

object Day10 {

  def main(args: Array[String]): Unit = {

    val sample = "inputs/day10sample.txt"
    val input = "inputs/day10input.txt"

    val openingParens = List("{", "[", "(", "<")
    val closingParens = List("}", "]", ")", ">")
    val closures = Map(
      "{" -> "}",
      "[" -> "]",
      "(" -> ")",
      "<" -> ">"
    )

    val syntaxErrorScores = Map(
      ")" -> 3,
      "]" -> 57,
      "}" -> 1197,
      ">" -> 25137
    )

    val syntaxErrorsPerLine = ListBuffer[ListBuffer[String]]()
    val incompleteLines = ListBuffer[List[String]]()


    Source.fromFile(input).getLines().map(_.toList.map(_.toString)).foreach(
      line => {
        val stack = Stack[String]()
        val syntaxErrors = ListBuffer[String]()
        var syntaxError = false
        line.foreach(
          char => {

            if (openingParens.contains(char)) {
              stack.push(char)
            }

            if (closingParens.contains(char)) {
              val open = stack.pop()
              if (!closures(open).equalsIgnoreCase(char)) {
                syntaxErrors += char
                syntaxError = true
              }
            }
          }
        )
        if (!syntaxError) incompleteLines += line // for part ii
        syntaxErrorsPerLine += syntaxErrors
      })


    val errorScore = syntaxErrorsPerLine.filter(_.size > 0).map(_ (0)).map(syntaxErrorScores(_)).sum
    println(s"Part I: ${errorScore}")

    // Part II

    val missingClosuresPerLine = ListBuffer[ListBuffer[String]]()

    incompleteLines.foreach(line => {
      val missingClosures = ListBuffer[String]()
      val stack = Stack[String]()
      line.foreach(
        char => {

          if (openingParens.contains(char)) stack.push(char)

          if (closingParens.contains(char)) stack.pop()

        }
      )
      stack.toList.foreach(missingClosures += closures(_))
      missingClosuresPerLine += missingClosures
    })


    val missingScores = Map(
      ")" -> 1,
      "]" -> 2,
      "}" -> 3,
      ">" -> 4
    )

    val missingScoreTable = ListBuffer[Long]()
    missingClosuresPerLine.foreach(missingParens => {
      var startScore = 0L
      missingParens.foreach(
        paren => {
          startScore = startScore * 5 + missingScores(paren)
        }
      )
      missingScoreTable += startScore
    })

    val sortedForMiddleGuy = missingScoreTable.sorted
    println(s"Part 2: ${sortedForMiddleGuy((sortedForMiddleGuy.size / 2))}")
  }
}
