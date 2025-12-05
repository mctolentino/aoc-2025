//package aoc.day1;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PasswordCracker2 {
//
//    static int LOWER_LIMIT = 0;
//    static int UPPER_LIMIT = 99;
//    static int INITIAL_VALUE = 50;
//
//    public static void main(String[] args) {
////         String[] rotations = {"L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82"};
//         List<String> rotations = getRotationsFromFile();
//
//        var currentIndex = INITIAL_VALUE;
//        var zeroCounter = 0;
//
//        for (String rotation : rotations) {
//            var counterValues = processStep(currentIndex, rotation, zeroCounter);
//            currentIndex = counterValues.currentIndex;
//            zeroCounter = counterValues.zeroCounter;
//            if (currentIndex == 0) {
//                zeroCounter++;
//                System.out.println("Current Index is zero: " + zeroCounter);
//            }
//            currentIndex = Math.abs(currentIndex);
//            System.out.println("Current Index value: " + currentIndex);
//        }
//
//        System.out.println("--------------");
//        System.out.println("Final Current Index value: " + currentIndex);
//        System.out.println("Final Zero Counter value: " + zeroCounter);
//    }
//
//    static List<String> getRotationsFromFile() {
//        String filePath = "/Users/uc65ml/Developer/Projects/aoc/src/aoc.day1/input.txt"; // Replace with your file path
//        List<String> rotations = new ArrayList<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                rotations.add(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle exceptions
//        }
//        return rotations;
//    }
//
////    static int processStep(int currentIndex, String rotation) {
////        var step = parseStep(rotation);
////        if (step.rotation.equals("L")) {
////            currentIndex -= step.value;
////            if (currentIndex < LOWER_LIMIT) {
////                currentIndex = UPPER_LIMIT + 1 + currentIndex;
////            }
////        } else if (step.rotation.equals("R")) {
////            currentIndex += step.value;
////            if (currentIndex > UPPER_LIMIT) {
////                currentIndex = (UPPER_LIMIT + 1 - currentIndex);
////            }
////        }
////        return currentIndex;
////    }
//
//    static CounterValues processStep(int currentIndex, String rotation, int zeroCounter) {
//        var step = parseStep(rotation);
//        System.out.println("Processing step: " + step + " | Current Index: " + currentIndex + " | Zero Counter: " + zeroCounter);
////        zeroCounter = zeroCounter + (step.value / 100);
//        var initialCurrentIndex = currentIndex;
//
//        if (step.rotation.equals("L")) {
//            currentIndex -= step.value;
//            if (currentIndex < LOWER_LIMIT) {
//                currentIndex = UPPER_LIMIT + 1 + currentIndex;
////                if (initialCurrentIndex != 0 && currentIndex != 0) {
////                    zeroCounter++;
////                    System.out.println("Clicked 0: " + zeroCounter);
////                }
//            }
//
//        } else if (step.rotation.equals("R")) {
//            currentIndex += step.value;
//            if (currentIndex > UPPER_LIMIT) {
//                currentIndex = (UPPER_LIMIT + 1 - currentIndex);
////                if (initialCurrentIndex != 0 && currentIndex != 0) {
////                    zeroCounter++;
////                    System.out.println("Clicked 0: " + zeroCounter);
////                }
//            }
//        }
//        return new CounterValues(currentIndex, zeroCounter);
//    }
//
//    static class CounterValues {
//        int currentIndex;
//        int zeroCounter;
//
//        CounterValues(int currentIndex, int zeroCounter) {
//            this.currentIndex = currentIndex;
//            this.zeroCounter = zeroCounter;
//        }
//    }
//
//    static Step parseStep(String rotation) {
//        String direction = rotation.substring(0, 1);
//        int value = Integer.parseInt(rotation.substring(1));
//        return new Step(direction, value);
//    }
//
//    static class Step {
//        String rotation;
//        int value;
//
//        Step(String rotation, int value) {
//            this.rotation = rotation;
//            this.value = value;
//        }
//
//        @Override
//        public String toString() {
//            return "Step {" + rotation + value + '}';
//        }
//    }
//
//}
