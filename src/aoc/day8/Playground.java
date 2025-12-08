package aoc.day8;

import aoc.Utils;

import java.util.*;

public class Playground {

    private static String SAMPLE = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
            """;


    public static void main(String[] args) {
        System.out.println("---- Sample - Part 1");
        processJunctionBoxes(getJunctionBoxes(SAMPLE), 10);
        System.out.println();

        System.out.println("---- Sample - Part 2");
        processJunctionBoxesPart2(getJunctionBoxes(SAMPLE));
        System.out.println();

        System.out.println("--------------------------");
        System.out.println();

        var TEST = Utils.getStringFromFile("src/aoc/day8/input1.txt");
        System.out.println("---- Test - Part 1");
        processJunctionBoxes(getJunctionBoxes(TEST), 1000);
        System.out.println();

        System.out.println("---- Test - Part 2");
        processJunctionBoxesPart2(getJunctionBoxes(TEST));
        System.out.println();
    }

    private static void processJunctionBoxes(List<JunctionBox> jboxes, int topK) {
        var junctionBoxPairs = getJunctionBoxPairs(jboxes);
        var topPairs = getTopK(junctionBoxPairs, topK);

        var circuits = new ArrayList<HashSet<JunctionBox>>();
        var processed = new HashSet<JunctionBox>();

        processJunctionBoxes(jboxes, topPairs, processed, circuits);

        circuits.sort((Comparator<Set<?>>) (o1, o2) -> Integer.compare(o2.size(), o1.size()));
        var product = circuits.get(0).size() * circuits.get(1).size() * circuits.get(2).size();
        System.out.println("> Product: " + product);
    }

    private static void processJunctionBoxes(List<JunctionBox> junctionBoxes, List<JunctionBoxPair> topPairs, Set<JunctionBox> processed, List<HashSet<JunctionBox>> circuits) {
        for (JunctionBoxPair boxPair : topPairs) {
            var firstBox = boxPair.pair.getFirst();
            int circuitIndex1 = -1;
            var addedFirstBox = false;
            var secondBox = boxPair.pair.get(1);
            int circuitIndex2 = -1;
            var addedSecondBox = false;
            var canBeMerged = false;

            if (!processed.contains(firstBox) && !processed.contains(secondBox)) {
                circuits.add(new HashSet<>(Set.of(firstBox, secondBox)));
            } else {
                for (int i = 0; i < circuits.size(); i++) {
                    var circuit = circuits.get(i);
                    if (circuit.contains(firstBox)) {
                        circuit.add(secondBox);
                        circuitIndex1 = i;
                        addedFirstBox = true;
                    } else if (circuit.contains(secondBox)) {
                        circuit.add(firstBox);
                        circuitIndex2 = i;
                        addedSecondBox = true;
                    }
                    if (addedFirstBox && addedSecondBox) {
                        canBeMerged = true;
                        break;
                    }
                }
            }

            if (canBeMerged && circuitIndex1 != circuitIndex2) {
                var temp1 = circuits.get(circuitIndex1);
                var temp2 = circuits.get(circuitIndex2);

                if (circuitIndex2 > circuitIndex1) {
                    circuits.remove(circuitIndex2);
                    circuits.remove(circuitIndex1);
                } else {
                    circuits.remove(circuitIndex1);
                    circuits.remove(circuitIndex2);
                }

                temp1.addAll(temp2);
                circuits.add(temp1);

                if (circuits.size() == 1 && junctionBoxes.isEmpty()) {
                    break;
                }
            }

            processed.add(firstBox);
            processed.add(secondBox);
            junctionBoxes.removeAll(boxPair.pair);

            if (junctionBoxes.isEmpty()) {
                System.out.println("Last Box 1: " + firstBox);
                System.out.println("Last Box 2: " + secondBox);
                System.out.println("> Product of X: " + ((long) firstBox.x * (long) secondBox.x));
                break;
            }
        }
    }

    private static void processJunctionBoxesPart2(List<JunctionBox> jboxes) {
        TreeSet<JunctionBoxPair> junctionBoxPairs = getJunctionBoxPairs(jboxes);

        List<HashSet<JunctionBox>> circuits = new ArrayList<>();
        Set<JunctionBox> processed = new HashSet<>();

        processJunctionBoxes(jboxes, new ArrayList<>(junctionBoxPairs), processed, circuits);
    }

    private static TreeSet<JunctionBoxPair> getJunctionBoxPairs(List<JunctionBox> jboxes) {
        var junctionBoxPairs = new TreeSet<JunctionBoxPair>(Comparator.comparingDouble(o -> o.distance));
        for (int i = 0; i < jboxes.size(); i++) {
            for (int j = i + 1; j < jboxes.size(); j++) {
                var distance = jboxes.get(i).getDistanceFrom(jboxes.get(j));
                junctionBoxPairs.add(new JunctionBoxPair(distance, List.of(jboxes.get(i), jboxes.get(j))));
            }
        }
        return junctionBoxPairs;
    }

    private static List<JunctionBoxPair> getTopK(Set<JunctionBoxPair> junctionBoxPairs, int topK) {
        var treeSet = new TreeSet<JunctionBoxPair>((o1, o2) -> Double.compare(o2.distance, o1.distance));
        for (JunctionBoxPair t : junctionBoxPairs) {
            treeSet.add(t);
            while (treeSet.size() > topK) {
                treeSet.pollFirst();
            }
        }
        return new ArrayList<>(treeSet);
    }

    static class JunctionBoxPair {
        double distance;
        List<JunctionBox> pair;

        JunctionBoxPair(double distance, List<JunctionBox> pair) {
            this.distance = distance;
            this.pair = pair;
        }
    }

    private static List<JunctionBox> getJunctionBoxes(String input) {
        var jbList = new ArrayList<JunctionBox>();
        var jbs = input.split("\n");
        for (String jbString : jbs) {
            jbList.add(getJB(jbString));
        }
        return jbList;
    }

    private static JunctionBox getJB(String input) {
        var jb = input.split(",");
        return new JunctionBox(Integer.parseInt(jb[0]), Integer.parseInt(jb[1]), Integer.parseInt(jb[2]));
    }

    static class JunctionBox {
        int x;
        int y;
        int z;

        JunctionBox(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        double getDistanceFrom(JunctionBox secondBox) {
            return Math.pow(this.x - secondBox.x, 2) + Math.pow(this.y - secondBox.y, 2) + Math.pow(this.z - secondBox.z, 2);
        }

        @Override
        public String toString() {
            return "[" + this.x + ", " + this.y + ", " + this.z + "]";
        }
    }

}
