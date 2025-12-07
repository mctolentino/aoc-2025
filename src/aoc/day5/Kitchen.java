package aoc.day5;

import aoc.Utils;

import java.util.ArrayList;
import java.util.List;

public class Kitchen {

    private static String SAMPLE = """
            3-5
            10-14
            16-20
            12-18
            1-21
            
            1
            5
            8
            11
            17
            32
            """;


    public static void main(String[] args) {
        var TEST = Utils.getStringFromFile("input1.txt");
        var kitchenInventory = getInventory(TEST);
//        var kitchenInventory = getInventory(SAMPLE);

        System.out.println(kitchenInventory);

        processKitchenProduce(kitchenInventory);
        processProduceIds(kitchenInventory);
    }

    static void processProduceIds(KitchenInventory kitchenInventory) {
        var processedRanges = new ArrayList<FreshRange>();

        for (FreshRange freshRange : kitchenInventory.getFreshRanges()) {
            Long rangeStart = freshRange.startId;
            Long rangeEnd = freshRange.endId;

            for (FreshRange processedRange : processedRanges) {
                if (rangeStart >= processedRange.startId && rangeStart <= processedRange.endId) {
                    rangeStart = processedRange.endId + 1;
                }
                if (rangeEnd >= processedRange.startId && rangeEnd <= processedRange.endId) {
                    rangeEnd = processedRange.startId - 1;
                }
            }

            if (rangeStart <= rangeEnd) {
                var processedRange = new FreshRange(rangeStart, rangeEnd);
                System.out.println(processedRange);
                processedRanges.add(processedRange);
                processedRanges = mergeRanges(processedRanges);
            } else {
                System.out.println("Range " + freshRange + " is fully processed already.");
            }
        }

        long sum = processedRanges.stream().map(r -> r.endId - r.startId + 1).reduce(0L, Long::sum);
        System.out.println("Calculated sum: " + sum);

    }

    static ArrayList<FreshRange> mergeRanges(ArrayList<FreshRange> ranges) {
        if (ranges.isEmpty()) return ranges;
        ranges.sort((a, b) -> Long.compare(a.startId, b.startId));
        var merged = new ArrayList<FreshRange>();
        FreshRange prev = ranges.get(0);

        for (int i = 1; i < ranges.size(); i++) {
            FreshRange curr = ranges.get(i);
            if (curr.startId <= prev.endId + 1) {
                prev = new FreshRange(prev.startId, Math.max(prev.endId, curr.endId));
            } else {
                merged.add(prev);
                prev = curr;
            }
        }
        merged.add(prev);
        return merged;
    }

    static void processKitchenProduce(KitchenInventory kitchenInventory) {
        var counter = 0;

        for (Long currentId : kitchenInventory.getProduceIds()) {
            for (FreshRange freshRange : kitchenInventory.getFreshRanges()) {
                if (currentId >= freshRange.startId && currentId <= freshRange.endId) {
                    counter++;
                    break;
                }
            }
        }
        System.out.println("Fresh Ingredients: " + counter);
    }

    static class KitchenInventory {
        private final List<FreshRange> freshRanges;
        private final List<Long> produceIds;

        KitchenInventory(List<FreshRange> freshRanges, List<Long> produceIds) {
            this.freshRanges = freshRanges;
            this.produceIds = produceIds;
        }

        public List<FreshRange> getFreshRanges() {
            return freshRanges;
        }

        public List<Long> getProduceIds() {
            return produceIds;
        }

        @Override
        public String toString() {
            return "KitchenInventory{" +
                    "freshRanges=" + freshRanges +
                    ", produceIds=" + produceIds +
                    '}';
        }
    }

    static KitchenInventory getInventory(String input) {
        List<FreshRange> freshRanges = new ArrayList<>();
        List<Long> productIds = new ArrayList<>();
        String[] inputArray = input.split("\n");
        boolean doneRanges = false;

        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i].equals("")) {
                doneRanges = true;
                continue;
            }
            if (!doneRanges) {
                freshRanges.add(FreshRange.toRange(inputArray[i]));
            } else {
                productIds.add(Long.parseLong(inputArray[i]));
            }
        }
        return new KitchenInventory(freshRanges, productIds);
    }

    static class FreshRange {
        private final Long startId;
        private final Long endId;

        FreshRange(Long startId, Long endId) {
            this.startId = startId;
            this.endId = endId;
        }

        static FreshRange toRange(String rawRange) {
            String[] range = rawRange.split("-");
            Long startId = Long.parseLong(range[0]);
            Long endId = Long.parseLong(range[1]);
            return new FreshRange(startId, endId);
        }

        @Override
        public String toString() {
            return "[" + startId + " - " + endId + "]";
        }
    }

}
