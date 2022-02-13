package aoc2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day1 {

    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("inputs/day1input.txt"));
        List<Integer> inputsAsInt = inputs.stream().map(Integer::valueOf).collect(Collectors.toList());


        List<Integer> integers2 = Arrays.asList(199, 200,
                208,
                210,
                200,
                207,
                240,
                269,
                260,
                263);
        int incr = 0;
        for (int i = 1; i < inputsAsInt.size(); i++) {
            if (inputsAsInt.get(i) - inputsAsInt.get(i - 1) > 0) incr++;
        }

        System.out.println(incr);

        List<Integer> list2 = Arrays.asList(199,
                200,
                208,
                210,
                200,
                207,
                240,
                269,
                260,
                263);

        int tripletIncr = 0;

        list2 = inputsAsInt;
        for (int i = 0; i + 3 < list2.size(); i++) {
            int prevSum = list2.get(i) + list2.get(i + 1) + list2.get(i + 2);
            int nextSum = list2.get(i + 1) + list2.get(i + 2) + list2.get(i + 3);
            if (nextSum - prevSum > 0) tripletIncr++;
        }

        System.out.println(tripletIncr);


        long count = getSlidingIncreases(inputsAsInt);
        System.out.println(count);
        long t0 = System.currentTimeMillis();


        List<Integer> count2 = IntStream.rangeClosed(0, inputsAsInt.size() - 3).boxed().parallel().
                map(i -> {
                    System.out.println(Thread.currentThread().getName());
                    return inputsAsInt.subList(i, i + 3);
                })
                .map(l -> {
                    System.out.println("sum part " + Thread.currentThread().getName());
                    return l.get(1) + l.get(0) + l.get(2);
                }).collect(Collectors.toList());


        System.out.println(getSlidingIncreases(count2));
        long t1 = System.currentTimeMillis();
        System.out.println("" + (t1 - t0));

    }

    private static long getSlidingIncreases(List<Integer> inputsAsInt) {
        return IntStream.rangeClosed(0, inputsAsInt.size() - 2).boxed().
                map(i -> inputsAsInt.subList(i, i + 2)).map(l -> l.get(1) - l.get(0)).filter(a -> a > 0).count();
    }
}
