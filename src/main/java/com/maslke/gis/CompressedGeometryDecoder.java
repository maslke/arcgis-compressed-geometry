package com.maslke.gis;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CompressedGeometryDecoder {

    private static final Pattern pattern = Pattern.compile("([+-])([^+-]+)");

    public static Line decode(String geometry) {
        String[] parts = geometry.split("\\|");
        String first = parts[0];
        if (first.startsWith("+0")) {
            int flag = Integer.parseInt(first.substring(5, 6));
            first = first.substring(6);
            Line line = decodeXy(first);
            if (flag == 0) {
                return line;
            }
            if ((flag & 1) == 1) {
                BiConsumer<Point, Double> consumer = Point::setZ;
                decode(line, parts[1], consumer);
            }
            if ((flag & 2) == 2) {
                BiConsumer<Point, Double> consumer = Point::setM;
                decode(line, parts[2], consumer);
            }
            return line;
        } else {
            return decodeXy(first);
        }
    }

    private static void decode(Line line, String part, BiConsumer<Point, Double> consumer) {
        Matcher matcher = pattern.matcher(part);
        List<String> items = new ArrayList<>();
        while (matcher.find()) {
            String p1 = matcher.group(1);
            String p2 = matcher.group(2);
            if ("-".equals(p1)) {
                items.add("-" + p2);
            } else {
                items.add(p2);
            }
        }
        int difference = Integer.parseInt(items.get(0), 32);
        int current = 0;
        int inx = 0;
        for (int i = 1; i < items.size(); i++) {
            int value = Integer.parseInt(items.get(i), 32);
            current += value;
            consumer.accept(line.getPoints()[inx++], current * 1.0 / difference);
        }
    }


    private static Line decodeXy(String part) {
        Matcher matcher = pattern.matcher(part);
        List<String> xys = new ArrayList<>();
        while (matcher.find()) {
            String p1 = matcher.group(1);
            String p2 = matcher.group(2);
            if ("-".equals(p1)) {
                xys.add("-" + p2);
            } else {
                xys.add(p2);
            }
        }
        int difference = Integer.parseInt(xys.get(0), 32);
        int currentX = 0;
        int currentY = 0;
        Point[] points = new Point[(xys.size() - 1) / 2];
        int inx = 0;
        for (int i = 1; i < xys.size(); i = i + 2) {
            int x = Integer.parseInt(xys.get(i), 32);
            int y = Integer.parseInt(xys.get(i + 1), 32);
            currentX += x;
            currentY += y;
            points[inx++] = new Point(currentX * 1.0 / difference, currentY * 1.0 / difference);
        }

        return new Line(points);
    }
}
