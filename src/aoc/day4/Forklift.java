package aoc.day4;

import aoc.Utils;

import java.util.Objects;

public class Forklift {

    private static String SAMPLE = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
            """;

    public static void main(String[] args) {
        var grid2 = parseInput(SAMPLE);
        var grid = parseInput(Utils.getStringFromFile("src/aoc/day4/input1.txt"));

        System.out.println("-----------");
        gridPrinter(grid);
        System.out.println("-----------");
        processForkliftables(grid);
    }

    static void gridPrinter(String[][] grid) {
        for (String[] row : grid) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    static void processForkliftables(String[][] grid) {
        var updatedGrid = new String[grid.length][grid[0].length];
        var forkliftableCounter = 0;
        var hasForkliftable = false;

        do {
            hasForkliftable = false;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    String cell = grid[i][j];
                    if (Objects.equals(cell, "@") && isForkliftable(grid, j, i)) {
                        forkliftableCounter++;
                        updatedGrid[i][j] = "X";
                        hasForkliftable = true;
                        System.out.print("X");
                    } else {
                        System.out.print(cell);
                        updatedGrid[i][j] = cell;
                    }
                }
                System.out.print(" | Done processing row: " + i + "\n");
            }
            grid = updatedGrid;
        } while (hasForkliftable);

        System.out.println("Number of forkliftable positions: " + forkliftableCounter);
    }

    static boolean isForkliftable(String[][] grid, int x, int y) {
        int rows = grid.length;
        int cols = grid[0].length;

        var atCounter = 0;

        if (y - 1 >= 0) {
            if (x - 1 >= 0 && grid[y - 1][x - 1].equals("@")) atCounter++;
            if (grid[y - 1][x].equals("@")) atCounter++;
            if (x + 1 < cols && grid[y - 1][x + 1].equals("@")) atCounter++;
        }

        if (x - 1 >= 0 && grid[y][x - 1].equals("@")) atCounter++;
        if (x + 1 < cols && grid[y][x + 1].equals("@")) atCounter++;

        if (y + 1 < rows) {
            if (x - 1 >= 0 && grid[y + 1][x - 1].equals("@")) atCounter++;
            if (grid[y + 1][x].equals("@")) atCounter++;
            if (x + 1 < cols && grid[y + 1][x + 1].equals("@")) atCounter++;
        }

        return atCounter < 4;
    }

    private static String[][] parseInput(String input) {
        String[] lines = input.split("\n");
        String[][] grid = new String[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                grid[i][j] = lines[i].charAt(j) + "";
            }
        }
        return grid;
    }
}
