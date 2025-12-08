package aoc.day3;

import aoc.Utils;

import java.util.Arrays;
import java.util.List;

import static java.lang.Long.parseLong;

public class Joltage {

    private static String sampleInput = """
                    987654321111111
                    811111111111119
                    234234234234278
                    818181911112111
            """;

    public static void main(String[] args) {
        List<String> batteries = parseInput(sampleInput);
        List<String> batteriesFromFile = Utils.getStringsFromFile("src/aoc/day3/input1.txt");
        List<Long> highestJoltages = batteriesFromFile.stream().map(Joltage::getHighestJoltageSecond).toList();
        var sum = highestJoltages.stream().mapToLong(Long::longValue).sum();
        System.out.println("Sum of highest joltages: " + sum);
    }

    private static Integer getHighestJoltageFirst(String input) {
        var highestJoltage = 0;

        for (int i = 0; i < input.length(); i++) {
            char d1 = input.charAt(i);
            for (int j = i + 1; j < input.length(); j++) {
                char d2 = input.charAt(j);
                var currentJoltage = Integer.parseInt("" + d1 + d2);
                if (currentJoltage > highestJoltage) {
                    highestJoltage = currentJoltage;
                }
            }
        }
        System.out.println("Processing input: " + input + " => Highest Joltage: " + highestJoltage);
        return highestJoltage;
    }

    private static Long getHighestJoltageSecond(String input) {
        var maxJolt = 0L;

        for (int i = 0; i < input.length(); i++) {
            char d1 = input.charAt(i);
            if (maxJolt >= parseLong(d1 + zpad(11))) continue;
            for (int j = i + 1; j < input.length(); j++) {
                char d2 = input.charAt(j);
                if (maxJolt >= parseLong("" + d1 + d2 + zpad(10))) continue;
                for (int k = j + 1; k < input.length(); k++) {
                    char d3 = input.charAt(k);
                    if (maxJolt >= parseLong("" + d1 + d2 + d3 + zpad(9))) continue;
                    for (int l = k + 1; l < input.length(); l++) {
                        char d4 = input.charAt(l);
                        if (maxJolt >= parseLong("" + d1 + d2 + d3 + d4 + zpad(8))) continue;
                        for (int m = l + 1; m < input.length(); m++) {
                            char d5 = input.charAt(m);
                            if (maxJolt >= parseLong("" + d1 + d2 + d3 + d4 + d5 + zpad(7))) continue;
                            for (int n = m + 1; n < input.length(); n++) {
                                char d6 = input.charAt(n);
                                if (maxJolt >= parseLong("" + d1 + d2 + d3 + d4 + d5 + d6 + zpad(6))) continue;
                                for (int o = n + 1; o < input.length(); o++) {
                                    char d7 = input.charAt(o);
                                    if (maxJolt >= parseLong("" + d1 + d2 + d3 + d4 + d5 + d6 + d7 + zpad(5))) continue;
                                    for (int p = o + 1; p < input.length(); p++) {
                                        char d8 = input.charAt(p);
                                        if (maxJolt >= parseLong("" + d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8 + zpad(4))) continue;
                                        for (int q = p + 1; q < input.length(); q++) {
                                            char d9 = input.charAt(q);
                                            if (maxJolt >= parseLong("" + d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8 + d9 + zpad(3))) continue;
                                            for (int r = q + 1; r < input.length(); r++) {
                                                char d10 = input.charAt(r);
                                                if (maxJolt >= parseLong("" + d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8 + d9 + d10 + zpad(2))) continue;
                                                for (int s = r + 1; s < input.length(); s++) {
                                                    char d11 = input.charAt(s);
                                                    if (maxJolt >= parseLong("" + d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8 + d9 + d10 + d11 + zpad(1))) continue;
                                                    for (int t = s + 1; t < input.length(); t++) {
                                                        char d12 = input.charAt(t);
                                                        var currentJolt = parseLong("" + d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8 + d9 + d10 + d11 + d12);
                                                        if (currentJolt > maxJolt) {
                                                            maxJolt = currentJolt;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Processing input: " + input + " => Highest Joltage: " + maxJolt);
        return maxJolt;
    }

    private static String zpad(int count) {
        return ("0").repeat(count);
    }

    private static List<String> parseInput(String input) {
        return Arrays.stream(input.split("\n")).map(String::trim).toList();
    }
}
