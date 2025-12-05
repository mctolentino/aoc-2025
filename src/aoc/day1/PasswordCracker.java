package aoc.day1;

import aoc.Utils;

import java.util.List;

public class PasswordCracker {

//    static String[] rotations = {"L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82"};

    static int LOWER_LIMIT = 0;
    static int UPPER_LIMIT = 99;
    static int INITIAL_VALUE = 50;

    public static void main(String[] args) {
        List<String> rotations = Utils.getStringsFromFile("/Users/uc65ml/Developer/Projects/aoc/src/aoc/day1/input.txt");

        var currentIndex = INITIAL_VALUE;
        var zeroCounter = 0;

        for (String rotation : rotations) {
            var counterValues = processStep(currentIndex, rotation);
            currentIndex = counterValues.currentIndex;
            zeroCounter += counterValues.zeroClicksCounter;
            if (currentIndex == 0) {
                zeroCounter++;
            }
            currentIndex = Math.abs(currentIndex);
            System.out.println("Current Index value: " + currentIndex);
        }

        System.out.println("--------------");
        System.out.println("Final Current Index value: " + currentIndex);
        System.out.println("Zero Counter value: " + zeroCounter);
    }

//    static int processStep(int currentIndex, String rotation) {
//        var step = parseStep(rotation);
//        var relevantValue = step.value % 100;
//
//        if (step.rotation.equals("L")) {
//            currentIndex -= relevantValue;
//            if (currentIndex < LOWER_LIMIT) {
//                currentIndex = UPPER_LIMIT + 1 + currentIndex;
//            }
//        } else if (step.rotation.equals("R")) {
//            currentIndex += relevantValue;
//            if (currentIndex > UPPER_LIMIT) {
//                currentIndex = (UPPER_LIMIT + 1 - currentIndex);
//            }
//        }
//        return currentIndex;
//    }

    static CounterValues processStep(int currentIndex, String rotation) {
        var step = parseStep(rotation);
        var relevantValue = step.value % 100;
        var zeroClicksCounter = 0;
        System.out.println("Processing step: " + step + " | Current Index: " + currentIndex + " | Zero Clicks Counter: " + zeroClicksCounter);
        zeroClicksCounter = zeroClicksCounter + (step.value / 100);
        var initialCurrentIndex = currentIndex;

        if (step.rotation.equals("L")) {
            currentIndex -= relevantValue;
            if (currentIndex < LOWER_LIMIT) {
                currentIndex = UPPER_LIMIT + 1 + currentIndex;
                if (initialCurrentIndex != 0 && currentIndex != 0) {
                    zeroClicksCounter++;
                    System.out.println("Clicked 0: " + zeroClicksCounter);
                }
            }

        } else if (step.rotation.equals("R")) {
            currentIndex += relevantValue;
            if (currentIndex > UPPER_LIMIT) {
                currentIndex = (UPPER_LIMIT + 1 - currentIndex);
                if (initialCurrentIndex != 0 && currentIndex != 0) {
                    zeroClicksCounter++;
                    System.out.println("Clicked 0: " + zeroClicksCounter);
                }
            }
        }
        return new CounterValues(currentIndex, zeroClicksCounter);
    }

    static class CounterValues {
        int currentIndex;
        int zeroClicksCounter;

        CounterValues(int currentIndex, int zeroCounter) {
            this.currentIndex = currentIndex;
            this.zeroClicksCounter = zeroCounter;
        }
    }

    static Step parseStep(String rotation) {
        String direction = rotation.substring(0, 1);
        int value = Integer.parseInt(rotation.substring(1));
        return new Step(direction, value);
    }

    static class Step {
        String rotation;
        int value;

        Step(String rotation, int value) {
            this.rotation = rotation;
            this.value = value;
        }

        public String toString() {
            return "Step {" + rotation + value + '}';
        }
    }
}
