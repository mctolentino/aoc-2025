package aoc.day9;

import aoc.Utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class Floor {

    Rectangle2D test;
    Polygon test2;

    private static String SAMPLE = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
            7,1
            """;

    public static void main(String[] args) {
        var TEST = Utils.getStringFromFile("src/aoc/day9/input1.txt");
        processTiles(SAMPLE);
        System.out.println("-------------------");
        processTiles(TEST);

        System.out.println("-------------------");
        processRedGreenTiles(SAMPLE);
    }

    private static void processRedGreenTiles(String input) {
        var tiles = getPoints(input.split("\n"));
        var greenTiles = getGreenTiles(tiles);
        var areas = getAreas(tiles);
        var maxArea = (Area) null;

        for (Area area : areas) {
            var isValidArea = true;
            for (LineSegment lineSegment : greenTiles) {
                if (isPointInsideRectangle(lineSegment.first, area)
                        || isPointInsideRectangle(lineSegment.second, area)) {
                    isValidArea = false;
                    break;
                }
            }
            if (isValidArea) {
                maxArea = area;
                break;
            }
        }
        System.out.println(maxArea);

    }


    public static boolean isPointInsideRectangle(Point p, Area a) {
        return p.x > a.first.x && p.x < a.second.x
                && p.y > a.second.y && p.y < a.first.y;
    }

    private static List<LineSegment> getGreenTiles(List<Point> tiles) {
        var lineSegments = new ArrayList<LineSegment>();
        for (int i = 0; i < tiles.size() - 1; i++) {
            lineSegments.add(new LineSegment(tiles.get(i), tiles.get(i + 1)));
        }
        return lineSegments;
    }


    private static void processTiles(String input) {
        var tiles = getPoints(input.split("\n"));

        var maxTiles1 = (Point) null;
        var maxTiles2 = (Point) null;
        var maxArea = Long.MIN_VALUE;

        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                var area = getArea(tiles.get(i), tiles.get(j));
                if (area > maxArea) {
                    maxArea = area;
                    maxTiles1 = tiles.get(i);
                    maxTiles2 = tiles.get(j);
                }
            }
        }

        System.out.println("Max Area: " + maxArea);
        System.out.println("Max Tiles 1: " + maxTiles1);
        System.out.println("Max Tiles 2: " + maxTiles2);
    }

    private static List<Area> getAreas(List<Point> tiles) {
        var areas = new ArrayList<Area>();
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                var area = getArea(tiles.get(i), tiles.get(j));
                var lineSegment = getBottomLeftAndTopRightPoints(tiles.get(i), tiles.get(j));
                areas.add(new Area(area, lineSegment.first, lineSegment.second));
            }
        }

        areas.sort((o1, o2) -> Long.compare(o2.area, o1.area));

        return areas;
    }

    private static LineSegment getBottomLeftAndTopRightPoints(Point first, Point second) {
        var leftmostPoint = (Point) null;
        var higherPointOnTheGrid = (Point) null;
        var bottomLeftPoint = (Point) null;
        var topRightPoint = (Point) null;

        // determine left point
        leftmostPoint = first.x > second.x ? second : first;

        // determine higher point on the grid
        higherPointOnTheGrid = first.y > second.y ? second : first;

        if (leftmostPoint == higherPointOnTheGrid) {
            bottomLeftPoint = leftmostPoint == first ? first : second;
            topRightPoint = higherPointOnTheGrid == first ? first : second;
        } else {
            if (leftmostPoint == first) {
                bottomLeftPoint = first;
                topRightPoint = second;
            } else {
                bottomLeftPoint = new Point(first.x, second.y);
                topRightPoint = new Point(second.x, first.y);
            }
        }
        return new LineSegment(bottomLeftPoint, topRightPoint);
    }


    private static List<Point> getPoints(String[] input) {
        return Arrays.stream(input).map(str -> {
            var pointStr = str.split(",");
            return new Point(Integer.parseInt(pointStr[0]), Integer.parseInt(pointStr[1]));
        }).toList();
    }

    record Area(long area, Point first, Point second) {
        @Override
        public String toString() {
            return "[area=" + area + ", first=" + first + ", second=" + second + ']';
        }
    }

    record LineSegment(Point first, Point second) {
    }

    record Point(long x, long y) {
        @Override
        public String toString() {
            return "[" + x + ", " + y + ']';
        }
    }

    private static Long getArea(Point first, Point second) {
        return (Math.abs(first.x - second.x) + 1) * (Math.abs(first.y - second.y) + 1);
    }

}
