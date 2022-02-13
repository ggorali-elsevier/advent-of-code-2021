package aoc2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {

    public static void main(String[] args) throws IOException {
        String sampleFilePath = "inputs/day3sample.txt";
        String inputFilePath = "inputs/day3input.txt";
        trialWithStreams(inputFilePath);
        List<String> input = Files.readAllLines(Paths.get(inputFilePath));
        int totalCount = input.size();
        int wordSize = input.get(0).length();

        List<List<Integer>> matrix = input.stream().map(
                a -> a.toCharArray()
        ).map(carr -> {
            ArrayList<Integer> integers = new ArrayList<>();
            for (char c : carr) {
                integers.add(Integer.parseInt(String.valueOf(c)));
            }
            return integers;
        }).collect(Collectors.toList());


//        List<Integer> significantBits = calcSigBits(totalCount, wordSize, matrix);
//
//        System.out.println(significantBits);

        List<List<Integer>> stepList = new ArrayList<>();
        stepList = matrix;

        int step = 0;
        while (stepList.size() > 1) {
            List<List<Integer>> filtered = new ArrayList<>();
                int sigBit = calcSigBitAtPosition(stepList, step);
            for (List<Integer> integers : stepList) {
                if (integers.get(step) == sigBit) {
                    filtered.add(integers);
                }
            }
            stepList = filtered;
            step++;
        }

        String s = stepList.get(0).stream().map(Objects::toString).reduce(String::concat).get();
        System.out.println(Integer.valueOf(s,2));

        //23//10
        //2005*2919
        System.out.println(2005*2919);

//1529×2566
    }

    private static int calcSigBitAtPosition(List<List<Integer>> integers, int step) {
        int sum = 0;
        for (List<Integer> integerList : integers) {
            sum += integerList.get(step);
        }

        if ((sum >= integers.size() / 2.0)) {
            return 1;
        } else {
            return 0;
        }

    }

    private static List<Integer> calcSigBits(int totalCount, int wordSize, List<List<Integer>> matrix) {
        List<Integer> significantBits = new ArrayList<>();
        for (int i = 0; i < wordSize; i++) {
            int totesForStep = 0;
            for (int j = 0; j < totalCount; j++) {
                totesForStep += matrix.get(j).get(i);
            }
            if ((totesForStep >= totalCount / 2))
                significantBits.add(1);
            else significantBits.add(0);
        }
        return significantBits;
    }

    private static void trialWithStreams(String inputFilePath) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(inputFilePath));
        final Integer sampleSize = input.size();

        String integer = input.stream().map(
                a -> a.toCharArray()
        ).map(carr -> {
            ArrayList<Integer> integers = new ArrayList<>();
            for (char c : carr) {
                integers.add(Integer.parseInt(String.valueOf(c)));
            }
            return integers;
        }).reduce((integers1, integers2) -> {
            ArrayList<Integer> result = new ArrayList<>();
            for (int i = 0; i < integers1.size(); i++) {
                result.add(integers1.get(i) + integers2.get(i));
            }
            return result;
        }).get().stream().map(
                n -> {
                    if (n > sampleSize / 2.0)
                        return "1";
                    else return "0";
                }
        ).reduce((s1, s2) -> s1.concat(s2)).get();


        System.out.println(Integer.parseInt(integer, 2));
        System.out.println(Integer.parseInt(integer, 2));
    }
}


/*

00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010


11110
10110
10111
10101
11100
10000
11001

10110
10111
10101
10000

10110
10111
10101

 */