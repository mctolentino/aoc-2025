package aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String getStringFromFile(String filePath) {
        return String.join("\n", getStringsFromFile(filePath));
    }

    public static List<String> getStringsFromFile(String filePath) {
        var strings = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }
        return strings;
    }

    public static void gridPrinter(String[][] grid) {
        for (String[] row : grid) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
