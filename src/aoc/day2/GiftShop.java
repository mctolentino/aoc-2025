//package aoc.day2;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class GiftShop {
//
//    private static String SAMPLE = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
//            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
//            "824824821-824824827,2121212118-2121212124";
//
//    private static String INPUT = "24-46,124420-259708,584447-720297,51051-105889,6868562486-6868811237,55-116,895924-1049139,307156-347325,372342678-372437056,1791-5048,3172595555-3172666604,866800081-866923262,5446793-5524858,6077-10442,419-818,57540345-57638189,2143479-2274980,683602048-683810921,966-1697,56537997-56591017,1084127-1135835,1-14,2318887654-2318959425,1919154462-1919225485,351261-558210,769193-807148,4355566991-4355749498,809094-894510,11116-39985,9898980197-9898998927,99828221-99856128,9706624-9874989,119-335";
//
//    public static void main(String[] args) {
//        List<String> ranges = Arrays.stream(SAMPLE.split(",")).toList();
//        List<Long> invalidIds = new ArrayList<>();
//
//        for (String range : ranges) {
//            Range r = parseRange(range);
//            System.out.println("Processing range: " + range);
//            long count = 0;
//            for (long i = r.start; i <= r.end; i++) {
//                if (isInvalidGiftCode(i)) {
//                    System.out.println("Invalid gift code found: " + i);
//                    invalidIds.add(i);
//                    count++;
//                }
//            }
//            System.out.println("Range " + range + " has " + count + " invalid gift codes.");
//        }
//
//        var sum = invalidIds.stream().mapToLong(Long::valueOf).sum();
//        System.out.println("Total invalid gift codes: " + invalidIds.size());
//        System.out.println("Sum of invalid gift codes: " + sum);
//
//    }
//
//    private static boolean isInvalidGiftCode(long code) {
//        String codeStr = String.valueOf(code);
//
//        if (codeStr.length() % 2 != 0) {
//            return false;
//        }
//        var charArray = codeStr.toCharArray();
//        var midChar = charArray.length / 2;
//        return codeStr.substring(0, midChar).equals(codeStr.substring(midChar));
//    }
//
//    private static Range parseRange(String rangeStr) {
//        String[] parts = rangeStr.split("-");
//        long start = Long.parseLong(parts[0]);
//        long end = Long.parseLong(parts[1]);
//        return new Range(start, end);
//    }
//
//    static class Range {
//        long start;
//        long end;
//
//        Range(long start, long end) {
//            this.start = start;
//            this.end = end;
//        }
//    }
//}
