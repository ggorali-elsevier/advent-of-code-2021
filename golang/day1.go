package main

import (
    "bufio"
    "fmt"
    "log"
    "os"
    "strconv"
)

func check(e error) {
    if e != nil {
        panic(e)
    }
}

func main() {
  file, err := os.Open("..inputs/day1input.txt")
    if err != nil {
        log.Fatal(err)
    }
    defer file.Close()

    var lines [] int
    scanner := bufio.NewScanner(file)
    for scanner.Scan() {
       var asInt, _ = strconv.Atoi(scanner.Text())
        lines = append(lines, asInt)
    }

   var cnt = 0

  for i := 1; i < len(lines); i++ {
   if lines[i]-lines[i-1] > 0 {
      cnt++
   }
  }

  fmt.Println(cnt)

  var tripleCnt = 0
  for i := 0; i < len(lines)-3; i++ {
   if lines[i]-lines[i+3] < 0 {
      tripleCnt++
   }
  }

  fmt.Println(tripleCnt)
 
    if err := scanner.Err(); err != nil {
        log.Fatal(err)
    }
}
