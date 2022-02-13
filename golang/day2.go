package main

import (
    "bufio"
    "fmt"
    "log"
    "os"
    "strconv"
    "strings"
)

func check(e error) {
    if e != nil {
        panic(e)
    }
}

func main() {

  //input := "inputs/day2sample.txt"
  input := "..inputs/day2input.txt"

  file, err := os.Open(input)
    if err != nil {
        log.Fatal(err)
    }
    defer file.Close()
    var  depth = 0
    var h = 0
    scanner := bufio.NewScanner(file)
    for scanner.Scan() {
       var line = strings.Fields(scanner.Text())
       var step,_ =  strconv.Atoi(line[1])
       switch line[0] {
         case "forward":
           h+= step
         case "up":
           depth -= step
         case "down":
          depth += step
      }

    }

  fmt.Printf("Depth: %d horizontal:%d product: %d\n",depth, h, h*depth)

    if err := scanner.Err(); err != nil {
        log.Fatal(err)
    }
}
