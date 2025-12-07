package aoc.day6;

import aoc.Utils;

import java.util.ArrayList;
import java.util.List;

public class TrashCompactor {

    private static String SAMPLE = """
            123 328  51 64     
             45 64  387 23     
              6 98  215 314    
            *   +   *   +      
            """;

    public static void main(String[] args) {
        System.out.println("-------------- Part 1 - Sample");
        var grid = parseInput(SAMPLE);
        processGrid(grid);

        System.out.println("-------------- Part 1 - File");
        var gridF = parseInput(Utils.getStringFromFile("/Users/maki/dev/projects/aoc-2025/src/aoc/day6/input1.txt"));
        processGrid(gridF);

        System.out.println("-------------- Part 2 - Sample");
        var gridCSample = parseCephalopodInput(SAMPLE);
        processGridCephalopodMath(gridCSample);

        System.out.println("-------------- Part 2 - File");
        var gridC = parseCephalopodInput(Utils.getStringFromFile("/Users/maki/dev/projects/aoc-2025/src/aoc/day6/input1.txt"));
        processGridCephalopodMath(gridC);
        System.out.println("--------------");
    }

    private static String[][] parseInput(String input) {
        String[] rows = input.split("\n");
        var numberOfItems = rows[0].split("\\s+").length;
        String[][] grid = new String[rows.length][numberOfItems];

        for (int i = 0; i < rows.length; ++i) {
            String[] columns = rows[i].trim().split("\\s+");
            System.arraycopy(columns, 0, grid[i], 0, numberOfItems);
        }

        return grid;
    }

    private static String[] parseCephalopodInput(String input) {
        return input.split("\n");
    }

    private static void processGrid(String[][] grid) {
        List<Long> total = new ArrayList<>();
        var maxRows = grid.length;
        var maxCols = grid[0].length;

        for (int y = 0; y < maxCols; y++) {
            var currentProduct = 0L;
            var currentSum = 0L;
            var currentRow = 0;
            var operator = grid[maxRows - 1][y]; // maxRows - 1 to exclude operator row

            while (currentRow < maxRows - 1) {
                var currentValue = Long.parseLong(grid[currentRow][y]);
                if ("*".equals(operator)) {
                    if (currentRow == 0) {
                        currentProduct = currentValue;
                    } else {
                        currentProduct *= currentValue;
                    }
                } else if ("+".equals(operator)) {
                    currentSum += currentValue;
                } else {
                    throw new RuntimeException("Unsupported operator: " + operator);
                }
                currentRow++;
            }

            if ("*".equals(operator)) {
                total.add(currentProduct);
            } else if ("+".equals(operator)) {
                total.add(currentSum);
            } else {
                throw new RuntimeException("Unsupported operator");
            }
        }

        Long totalSum = total.stream().reduce(0L, Long::sum);
        System.out.println(totalSum);
    }

    private static void processGridCephalopodMath(String[] grid) {
        List<Long> total = new ArrayList<>();

        int maxRowSize = 0;
        for (String row : grid) {
            if (maxRowSize < row.length()) {
                maxRowSize = row.length();
            }
        }
        maxRowSize++;
        System.out.println("> max row size: " + maxRowSize);

        List<String> gridList = new ArrayList<>();
        for (String row : grid) {
            gridList.add(row + " ".repeat(maxRowSize - row.length()));
        }

        List<Long> digitsAccummulator = new ArrayList<>();
        var currentOperator = "";
        var currentDigit = "";

        for (int i = 0; i < maxRowSize; i++) {
            var operator = String.valueOf(gridList.get(gridList.size() - 1).charAt(i));
            if (!operator.isBlank()) currentOperator = operator;

            StringBuilder digit = new StringBuilder();
            for (int j = 0; j < gridList.size() - 1; j++) {
                digit.append(gridList.get(j).charAt(i));
            }
            currentDigit = digit.toString();


            if (currentDigit.isBlank()) {
                if ("*".equals(currentOperator)) {
                    var product = digitsAccummulator.stream().reduce(1L, (a, b) -> a * b);
//                    System.out.println("Product: " + product);
                    total.add(product);
                } else if ("+".equals(currentOperator)) {
                    var sum = digitsAccummulator.stream().reduce(0L, Long::sum);
//                    System.out.println("Sum: " + sum);
                    total.add(sum);
                } else {
                    throw new RuntimeException("Unsupported operator: " + currentOperator);
                }
                digitsAccummulator.clear();
            } else {
                digitsAccummulator.add(Long.parseLong(currentDigit.trim()));
            }
        }

        Long totalSum = total.stream().reduce(0L, Long::sum);
        System.out.println(totalSum);
    }

    static void gridPrinter(String[][] grid) {
        for (String[] row : grid) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
