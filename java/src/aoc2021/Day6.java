package aoc2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 {
    public static void main(String[] args) throws IOException {
        String sampleFilePath = "inputs/day6sample.txt";
        String inputFilePath = "inputs/day6input.txt";
        String input = Files.readAllLines(Paths.get(inputFilePath)).get(0);
        String[] split = input.split(",");
        List<Integer> fishes = Arrays.asList(split).stream().map(a -> Integer.valueOf(a)).collect(Collectors.toList());

        // naiveParallel(fishes);
        // System.out.println("total:" + fishes.size());

        Map<Integer, Long> fishCountsPerDay = new HashMap<>();
        fishes.stream().forEach(
                number -> {
                    if (fishCountsPerDay.get(number) != null) {
                        fishCountsPerDay.put(number, fishCountsPerDay.get(number) + 1);
                    } else {
                        fishCountsPerDay.put(number, 1L);
                    }
                }
        );

        System.out.println(fishCountsPerDay);
        for (int i = 0; i < 256; i++) {
            Long s0 = fishCountsPerDay.getOrDefault(0, 0L);
            Long s1 = fishCountsPerDay.getOrDefault(1, 0L);
            Long s2 = fishCountsPerDay.getOrDefault(2, 0L);
            Long s3 = fishCountsPerDay.getOrDefault(3, 0L);
            Long s4 = fishCountsPerDay.getOrDefault(4, 0L);
            Long s5 = fishCountsPerDay.getOrDefault(5, 0L);
            Long s6 = fishCountsPerDay.getOrDefault(6, 0L);
            Long s7 = fishCountsPerDay.getOrDefault(7, 0L);
            Long s8 = fishCountsPerDay.getOrDefault(8, 0L);

            fishCountsPerDay.put(8, s0);
            fishCountsPerDay.put(7, s8);
            fishCountsPerDay.put(6, s7 + s0);
            fishCountsPerDay.put(5, s6);
            fishCountsPerDay.put(4, s5);
            fishCountsPerDay.put(3, s4);
            fishCountsPerDay.put(2, s3);
            fishCountsPerDay.put(1, s2);
            fishCountsPerDay.put(0, s1);

        }

        Long aLong = fishCountsPerDay.values().stream().reduce((a, b) -> a + b).get();
        System.out.println(aLong);


    }

    private static void naiveParallel(List<Integer> fishes) {
        for (int day = 0; day < 80; day++) {
            System.out.println("Starting Day " + day);
            IntStream.rangeClosed(0, fishes.size() - 1).parallel().forEach(
                    i -> {
                        if (fishes.get(i) == 0) {
                            synchronized (fishes) {
                                fishes.add(8);
                                fishes.set(i, 6);
                            }
                        } else {
                            synchronized (fishes) {
                                fishes.set(i, fishes.get(i) - 1);
                            }
                        }
                    }
            );
        }
    }
}
