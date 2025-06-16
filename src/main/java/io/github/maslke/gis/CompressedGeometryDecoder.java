package io.github.maslke.gis;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class CompressedGeometryDecoder {

    private static final Pattern PATTERN = Pattern.compile("([+-])([^+-]+)");

    private CompressedGeometryDecoder() {
    }

    public static List<double[]> decode(String geometry) {
        String[] parts = geometry.split("\\|");
        String first = parts[0];
        if (!first.startsWith("+0")) {
            return decodeOldVersion(first);
        } else {
            return decodeNewVersion(parts);
        }
    }

    private static List<double[]> decodeOldVersion(String first) {
        List<List<Double>> points = decodeXy(first);
        return points.stream().map(l -> new double[]{l.get(0), l.get(1)}).collect(Collectors.toList());
    }

    private static List<double[]> decodeNewVersion(String[] parts) {
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
        List<List<Double>> line = decodeXy(first);
        if (flag == 0) {
            return line.stream().map(l -> new double[]{l.get(0), l.get(1)}).collect(Collectors.toList());
        }
        if ((flag & 1) == 1) {
            BiConsumer<List<Double>, Double> consumer = List::add;
            decode(line, parts[1], consumer);
        }
        if ((flag & 2) == 2) {
            BiConsumer<List<Double>, Double> consumer = List::add;
            decode(line, parts[parts.length - 1], consumer);
        }
        if (flag == 3) {
            return line.stream().map(l -> new double[]{l.get(0), l.get(1), l.get(2), l.get(3)}).collect(Collectors.toList());
        }
        return line.stream().map(l -> new double[]{l.get(0), l.get(1), l.get(2)}).collect(Collectors.toList());
    }

    private static List<List<Double>> decodeXy(String part) {
        List<List<Double>> points = new ArrayList<>();
        List<String> xys = extract(part);
        int factor = Integer.parseInt(xys.get(0), 32);
        int differencex = 0;
        int differencey = 0;
        for (int i = 1; i < xys.size(); i = i + 2) {
            int x = Integer.parseInt(xys.get(i), 32);
            int y = Integer.parseInt(xys.get(i + 1), 32);
            differencex += x;
            differencey += y;
            List<Double> point = new ArrayList<>();
            point.add(differencex * 1.0 / factor);
            point.add(differencey * 1.0 / factor);
            points.add(point);
        }
        return points;
    }

    private static void decode(List<List<Double>> points, String part, BiConsumer<List<Double>, Double> consumer) {
        List<String> items = extract(part);
        int factor = Integer.parseInt(items.get(0), 32);
        int difference = 0;
        int inx = 0;
        for (int i = 1; i < items.size(); i++) {
            int value = Integer.parseInt(items.get(i), 32);
            difference += value;
            consumer.accept(points.get(inx++), difference * 1.0 / factor);
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
            } else {
                xys.add(p2);
            }
        }
        return xys;
    }
}
