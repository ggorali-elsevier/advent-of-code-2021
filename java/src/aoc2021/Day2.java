package aoc2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Day2 {

    public static void main(String[] args) throws IOException {

        String sampleFilePath = "inputs/day2sample.txt";
        String inputFilePath = "inputs/day2input.txt";
        final AtomicInteger hVal = new AtomicInteger();
        final AtomicInteger dVal = new AtomicInteger();

        Files.readAllLines(Paths.get(inputFilePath)).stream().map(a -> a.split(" ")).parallel().forEach(
                line -> {
                    switch (line[0]) {
                        case "forward":
                            hVal.addAndGet(Integer.parseInt(line[1]));
                            break;
                        case "down":
                            dVal.addAndGet(Integer.parseInt(line[1]));
                            break;

                        case "up":
                            dVal.addAndGet(-Integer.parseInt(line[1]));
                            break;
                    }
                }
        );

        System.out.println(
                String.format("h: %d d: %d product: %d", hVal.get(), dVal.get(), hVal.get() * dVal.get())
        );

    }
}
