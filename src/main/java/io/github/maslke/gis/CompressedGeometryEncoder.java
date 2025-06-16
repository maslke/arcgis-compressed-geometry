package io.github.maslke.gis;

import java.util.List;
import java.util.stream.Collectors;

public final class CompressedGeometryEncoder {

    private static final String COORDINATE_FORMAT_XY = "xy";
    private static final String COORDINATE_FORMAT_XYM = "xym";
    private static final String COORDINATE_FORMAT_XYZ = "xyz";
    private static final String COORDINATE_FORMAT_XYZM = "xyzm";
    private static final int DEFAULT_FACTOR = 10000;

    private static final String COMRESSED_GEOMETRY_PREFIX = "+0+1+";


    private CompressedGeometryEncoder() {
    }

    
    public static String encode(List<double[]> points) {
        return encode(points, COORDINATE_FORMAT_XY);
    
    }

    public static String encode(List<double[]> points, String coordinateFormat) {
        return encode(points, coordinateFormat, DEFAULT_FACTOR);
    }

    public static String encode(List<double[]> points, String coordinateFormat, Integer xyFactor) {
        return encode(points, coordinateFormat, xyFactor, xyFactor, xyFactor);
    }

    public static String encode(List<double[]> points, String coordinateFormat, Integer xyFactor, Integer zFactor, Integer mFactor) {

        String xyGeometry = encodeXy(points, xyFactor);
        String zGeometry = "";
        String mGeometry = "";

        if (COORDINATE_FORMAT_XYZ.equals(coordinateFormat)) {
            zGeometry = "|" + encodeZm(points.stream().map(p -> p[2]).collect(Collectors.toList()), zFactor);
        }
        else if (COORDINATE_FORMAT_XYM.equals(coordinateFormat)) {
            mGeometry = "|" + encodeZm(points.stream().map(p -> p[2]).collect(Collectors.toList()), mFactor);
        }
        else if (COORDINATE_FORMAT_XYZM.equals(coordinateFormat)) {
            zGeometry = "|" + encodeZm(points.stream().map(p -> p[2]).collect(Collectors.toList()), zFactor);
            mGeometry = "|" + encodeZm(points.stream().map(p -> p[3]).collect(Collectors.toList()), mFactor);
        }

        int zFlag = zGeometry.length() > 0 ? 1 : 0;
        int mFlag = mGeometry.length() > 0 ? 2 : 0;
        int flag = zFlag | mFlag;

        return COMRESSED_GEOMETRY_PREFIX + flag + xyGeometry + zGeometry + mGeometry;
    }

    private static String encodeXy(List<double[]> points, Integer factor) {
        StringBuilder result = new StringBuilder();
        Integer firstX = (int) (points.get(0)[0] * factor);
        Integer firstY = (int) (points.get(0)[1] * factor);
        result.append(base32Encode(factor));
        result.append(base32Encode(firstX));
        result.append(base32Encode(firstY));

        Integer prevX = firstX;
        Integer prevY = firstY;

        for (int i = 1; i < points.size(); i++) {
            Integer currX = (int) (points.get(i)[0] * factor);
            Integer currY = (int) (points.get(i)[1] * factor);
            Integer diffX = currX - prevX;
            Integer diffY = currY - prevY;
            result.append(base32Encode(diffX));
            result.append(base32Encode(diffY));
            prevX = currX;
            prevY = currY;
        }

        return result.toString();
    }
    
    private static String base32Encode(Integer num) {
        if (num == 0) {
            return "+0";
        }
        char[] chars = "0123456789abcdefghijklmnopqrstuv".toCharArray();
        StringBuilder result = new StringBuilder();
        boolean isNegative = num < 0;
        num = Math.abs(num);

        while (num > 0) {
            int remainder = num % 32;
            result.append(chars[remainder]);
            num = num / 32;
        }

        String geometry = result.reverse().toString();
        return isNegative ? "-" + geometry : "+" + geometry;
    }

    private static String encodeZm(List<Double> coordinates, Integer factor) {
        StringBuilder result = new StringBuilder();
        Integer first = (int) (coordinates.get(0) * factor);
        result.append(base32Encode(factor));
        result.append(base32Encode(first));

        Integer prev = first;

        for (int i = 1; i < coordinates.size(); i++) {
            Integer curr = (int) (coordinates.get(i) * factor);
            Integer diff = curr - prev;
            result.append(base32Encode(diff));
            prev = curr;
        }
        return result.toString();
        
    }
}

