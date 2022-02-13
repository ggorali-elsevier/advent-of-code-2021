package aoc2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {

    public static void main(String[] args) throws IOException {
        //      String sampleFilePath = "inputs/day4sample.txt";
        String sampleFilePath = "inputs/day4input.txt";
        List<String> input = Files.readAllLines(Paths.get(sampleFilePath));
        Stream<Integer> bingoNumbers = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::valueOf).boxed();
        List<Board> boards = new ArrayList<>();

        Board aBoard = new Board();
        for (int i = 2; i < input.size(); i++) {
            if (input.get(i).trim().length() > 0) {
                List<Integer> row = Arrays.asList(input.get(i).trim().split("[ ]+")).stream().mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
                aBoard.addRow(row);
            } else {
                aBoard.init();
                boards.add(aBoard);
                aBoard = new Board();
            }
        }

        for (Integer number : bingoNumbers.collect(Collectors.toList())) {
            for (Board board : boards) {
                if (!board.winner && board.accept(number) != -1) {
                    System.out.println("Winning number:" + board.getWinningNumber());
                    board.markWinner(true);
                }
            }
        }


    }

    static class Board {
        private List<List<Integer>> rows = new ArrayList<>();
        private List<List<Integer>> cols = new ArrayList<>();

        final AtomicInteger win = new AtomicInteger(-1);
        private boolean winner = false;

        public void addRow(List<Integer> row) {
            rows.add(row);
        }

        public Integer accept(Integer number) {
            if (win.get() != -1) return win.get();

            rows.forEach(row -> {
                row.remove(number);
                if (row.size() == 0) {
                    win.set(number);
                }
            });

            cols.forEach(col -> {
                col.remove(number);
                if (col.size() == 0) {
                    win.set(number);
                }
            });
            return win.get();
        }


        public Integer getWinningNumber() {
            int sum = 0;
            for (List<Integer> row : rows) {
                for (Integer integer : row) {
                    sum += integer;
                }
            }
            return win.get() * sum;
        }


        public void print() {
            for (List<Integer> row : rows) {
                System.out.println(row);
            }
            System.out.println("----------");
            for (List<Integer> col : cols) {
                System.out.println(col);
            }
        }

        public void init() {
            int dimension = rows.get(0).size();
            for (int i = 0; i < dimension; i++) {
                List<Integer> aCol = new ArrayList<>();
                for (List<Integer> row : rows) {
                    aCol.add(row.get(i));
                }
                cols.add(aCol);
            }
        }

        public void markWinner(boolean winner) {
            this.winner = winner;
        }
    }
}
