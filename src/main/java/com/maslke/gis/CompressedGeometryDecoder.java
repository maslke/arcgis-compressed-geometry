package com.maslke.gis;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CompressedGeometryDecoder {

    private static final Pattern PATTERN = Pattern.compile("([+-])([^+-]+)");

    public static Line decode(String geometry) {
        String[] parts = geometry.split("\\|");
        String first = parts[0];
        if (!first.startsWith("+0")) {
            return decodeOldVersion(first);
        }
        else {
            return decodeNewVersion(parts);
        }
    }

    private static Line decodeOldVersion(String first) {
        return decodeXy(first);
    }

    private static Line decodeNewVersion(String[] parts) {
        String first = parts[0];
        int flag = Integer.parseInt(first.substring(5, 6));
        int version = Integer.parseInt(first.substring(3, 4));
        if (version != 1) {
            throw new IllegalArgumentException("Compressed geometry: Unexpected version.");
        }
        if (flag > 3) {
            throw new IllegalArgumentException("Compressed geometry: Invalid flags.");
        }
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
            decode(line, parts[parts.length - 1], consumer);
        }
        return line;
    }

    private static Line decodeXy(String part) {
        List<String> xys = extract(part);
        int factor = Integer.parseInt(xys.get(0), 32);
        int differencex = 0;
        int differencey = 0;
        Point[] points = new Point[(xys.size() - 1) / 2];
        int inx = 0;
        for (int i = 1; i < xys.size(); i = i + 2) {
            int x = Integer.parseInt(xys.get(i), 32);
            int y = Integer.parseInt(xys.get(i + 1), 32);
            differencex += x;
            differencey += y;
            points[inx++] = new Point(differencex * 1.0 / factor, differencey * 1.0 / factor);
        }

        return new Line(points);
    }

    private static void decode(Line line, String part, BiConsumer<Point, Double> consumer) {
        List<String> items = extract(part);
        int factor = Integer.parseInt(items.get(0), 32);
        int difference = 0;
        int inx = 0;
        for (int i = 1; i < items.size(); i++) {
            int value = Integer.parseInt(items.get(i), 32);
            difference += value;
            consumer.accept(line.getPoints()[inx++], difference * 1.0 / factor);
        }
    }

    private static List<String> extract(String part) {
        Matcher matcher = PATTERN.matcher(part);
        List<String> xys = new ArrayList<>();
        while (matcher.find()) {
            String p1 = matcher.group(1);
            String p2 = matcher.group(2);
            if ("-".equals(p1)) {
                xys.add("-" + p2);
            }
            else {
                xys.add(p2);
            }
        }
        return xys;
    }
}
