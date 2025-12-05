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
        List<String> strings = new ArrayList<>();

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
}
