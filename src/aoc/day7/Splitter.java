package aoc.day7;

import aoc.Utils;

import java.util.List;

import static aoc.Utils.gridPrinter;

public class Splitter {

    private static String SAMPLE = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............
            """;

    public static void main(String[] args) {
//        List<String> currentGrid = List.of(SAMPLE.split("\n"));
        List<String> currentGrid = Utils.getStringsFromFile("src/aoc/day7/input1.txt");

        String[][] processedGrid = new String[currentGrid.size()][currentGrid.getFirst().length()];
        int splitCounter = 0;

        for (int i = 0; i < currentGrid.size(); i++) {
            var currentRow = currentGrid.get(i);
//            System.out.println("Row to process: " + currentRow);

            for (int j = 0; j < currentRow.length(); j++) {
                var character = String.valueOf(currentRow.charAt(j));

                if (i == 0) {
                    processedGrid[0][j] = character;
                } else {
                    var prev = processedGrid[i - 1][j];

                    if (prev.equals("S")) {
                        processedGrid[i][j] = "|";
                    } else if (character.equals("^")) {
                        if (prev.equals("|")) {
                            processedGrid[i][j - 1] = "|";
                            processedGrid[i][j] = "^";
                            processedGrid[i][j + 1] = "|";
                            splitCounter++;
                        } else {
                            processedGrid[i][j] = "^";
                        }
                    } else if (prev.equals("|")) {
                        processedGrid[i][j] = "|";
                    } else if (prev.equals("^")) {
                        processedGrid[i][j] = ".";
                    } else if (prev.equals(".")) {
                        if (processedGrid[i][j] == null || !processedGrid[i][j].equals("|")) {
                            processedGrid[i][j] = ".";
                        }
                    }
                }
            }
//            System.out.println(">");
        }

        System.out.println("----------");
        gridPrinter(processedGrid);
        System.out.println("> splits: " + splitCounter);

        System.out.println("---------- Part 2");

        long[][] memo = new long[processedGrid.length][processedGrid[0].length];

        for (int i = 1; i < processedGrid.length; i++) {
            for (int j = 0; j < processedGrid[0].length; j++) {
                var currentValue = processedGrid[i][j];
                if (processedGrid[i - 1][j].equals("S")) {
                    memo[i][j] = 1;
                }
                if (currentValue.equals("^")) {
                    memo[i][j-1] += memo[i-1][j];
                    memo[i][j+1] += memo[i-1][j];
                }
                if (currentValue.equals("|")) {
                    memo[i][j] += memo[i-1][j];
                }
            }

        }

        long total = 0;
        for (int i = 0; i < memo[processedGrid.length-1].length; i++) {
            System.out.print("[" + memo[processedGrid.length-1][i] + "]");
            total += memo[processedGrid.length-1][i];
        }
        System.out.println();
        System.out.println("Total : " + total);
    }
}
