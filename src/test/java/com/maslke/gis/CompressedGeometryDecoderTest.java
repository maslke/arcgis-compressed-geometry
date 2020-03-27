package com.maslke.gis;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CompressedGeometryDecoderTest {

    @Test
    public void decodeTestWhenNoZAndM() {
        Line line = CompressedGeometryDecoder.decode("+1m91-6fkfr+202tp+k+f+7+3+34+2d");
        assertNotNull(line);
        assertEquals(4, line.getPoints().length);
        Point[] points = line.getPoints();
        assertEquals(-122.40645857695421, points[0].getX(), 0.0001);
        assertEquals(37.78272915354862, points[0].getY(), 0.0001);
        assertEquals(0, points[0].getZ(), 0.0001);
        assertEquals(0, points[0].getM(), 0.0001);
        assertEquals(-122.40609876765315, points[1].getX(), 0.0001);
        assertEquals(37.78299901052442, points[1].getY(), 0.0001);
        assertEquals(0, points[1].getZ(), 0.0001);
        assertEquals(0, points[1].getM(), 0.0001);
        assertEquals(-122.40597283439777, points[2].getX(), 0.0001);
        assertEquals(37.78305298191958, points[2].getY(), 0.0001);
        assertEquals(0, points[2].getZ(), 0.0001);
        assertEquals(0, points[2].getM(), 0.0001);
        assertEquals(-122.40417378789242, points[3].getX(), 0.0001);
        assertEquals(37.7844382477287, points[3].getY(), 0.0001);
        assertEquals(0, points[3].getZ(), 0.0001);
        assertEquals(0, points[3].getM(), 0.0001);
    }

    @Test
    public void decodeTestWhenNoZAndMSatrtWithZero() {
        Line line = CompressedGeometryDecoder.decode("+0+1+0+1m91-6fkfr+202tp+k+f+7+3+34+2d");
        assertNotNull(line);
        assertEquals(4, line.getPoints().length);
        Point[] points = line.getPoints();
        assertEquals(-122.40645857695421, points[0].getX(), 0.0001);
        assertEquals(37.78272915354862, points[0].getY(), 0.0001);
        assertEquals(0, points[0].getZ(), 0.0001);
        assertEquals(0, points[0].getM(), 0.0001);
        assertEquals(-122.40609876765315, points[1].getX(), 0.0001);
        assertEquals(37.78299901052442, points[1].getY(), 0.0001);
        assertEquals(0, points[1].getZ(), 0.0001);
        assertEquals(0, points[1].getM(), 0.0001);
        assertEquals(-122.40597283439777, points[2].getX(), 0.0001);
        assertEquals(37.78305298191958, points[2].getY(), 0.0001);
        assertEquals(0, points[2].getZ(), 0.0001);
        assertEquals(0, points[2].getM(), 0.0001);
        assertEquals(-122.40417378789242, points[3].getX(), 0.0001);
        assertEquals(37.7844382477287, points[3].getY(), 0.0001);
        assertEquals(0, points[3].getZ(), 0.0001);
        assertEquals(0, points[3].getM(), 0.0001);
    }

    @Test
    public void decodeTestWhenHasZAndNoM() {

    }

    @Test
    public void decodeTestWhenHasMAndNoZ() {

    }

    @Test
    public void decodeTestWhenHasMAndZ() {

    }
}
