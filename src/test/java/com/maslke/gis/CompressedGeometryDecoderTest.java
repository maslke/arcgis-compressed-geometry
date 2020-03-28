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
        Line line = CompressedGeometryDecoder.decode("+0+1+1+1+emjd+3j07m+3+0+0+1-3-1|+9og+0+lv4+0+lv4");
        assertNotNull(line);
        assertEquals(4, line.getPoints().length);
        Point[] points = line.getPoints();

        assertEquals(481901.0, points[0].getX(), 0.0001);
        assertEquals(3768566.0, points[0].getY(), 0.0001);
        assertEquals(0, points[0].getZ(), 0.0001);
        assertEquals(0.0, points[0].getM(), 0.0001);

        assertEquals(481904.0, points[1].getX(), 0.0001);
        assertEquals(3768566, points[1].getY(), 0.0001);
        assertEquals(2.25, points[1].getZ(), 0.0001);
        assertEquals(0.0, points[1].getM(), 0.0001);

        assertEquals(481904.0, points[2].getX(), 0.0001);
        assertEquals(3768567.0, points[2].getY(), 0.0001);
        assertEquals(2.25, points[2].getZ(), 0.0001);
        assertEquals(0.0, points[2].getM(), 0.0001);

        assertEquals(481901.0, points[3].getX(), 0.0001);
        assertEquals(3768566.0, points[3].getY(), 0.0001);
        assertEquals(4.5, points[3].getZ(), 0.0001);
        assertEquals(0.0, points[3].getM(), 0.0001);
    }

    @Test
    public void decodeTestWhenHasMAndNoZ() {
        Line line = CompressedGeometryDecoder.decode("+0+1+2+1m91-6733n+1pjfe+g-e+1b-r+9-9+c-h+2-j-3-v-7-j-b-m-5-7-e-f-1a-u-6-7-4-9-3-a-1-n+1-4j|+5rg+81s+7n+i0+4f+7r+7g+ce+7n+9j+3h+7n+ib+3a+3q+45+97+1qs");
        assertNotNull(line);
        assertEquals(17, line.getPoints().length);
        Point[] points = line.getPoints();
        assertEquals(-117.37020778987137, points[0].getX(), 0.0001);
        assertEquals(33.96106863362418, points[0].getY(), 0.0001);
        assertEquals(0, points[0].getZ(), 0.0001);
        assertEquals(1.3753333333333333, points[0].getM(), 0.0001);

        assertEquals(-117.36991994243051, points[1].getX(), 0.0001);
        assertEquals(33.96081676711343, points[1].getY(), 0.0001);
        assertEquals(0, points[1].getZ(), 0.0001);
        assertEquals(1.4165, points[1].getM(), 0.0001);

        assertEquals(-117.3691463524332, points[2].getX(), 0.0001);
        assertEquals(33.960331024556986, points[2].getY(), 0.0001);
        assertEquals(0, points[2].getZ(), 0.0001);
        assertEquals(1.5125, points[2].getM(), 0.0001);

        assertEquals(-117.36898443824772, points[3].getX(), 0.0001);
        assertEquals(33.9601691103715, points[3].getY(), 0.0001);
        assertEquals(0, points[3].getZ(), 0.0001);
        assertEquals(1.5363333333333333, points[3].getM(), 0.0001);

        assertEquals(-117.36876855266709, points[4].getX(), 0.0001);
        assertEquals(33.95986327246559, points[4].getY(), 0.0001);
        assertEquals(0, points[4].getZ(), 0.0001);
        assertEquals(1.5781666666666667, points[4].getM(), 0.0001);

        assertEquals(-117.36873257173698, points[5].getX(), 0.0001);
        assertEquals(33.95952145362958, points[5].getY(), 0.0001);
        assertEquals(0, points[5].getZ(), 0.0001);
        assertEquals(1.6181666666666668, points[5].getM(), 0.0001);

        assertEquals(-117.36878654313215, points[6].getX(), 0.0001);
        assertEquals(33.95896374921292, points[6].getY(), 0.0001);
        assertEquals(0, points[6].getZ(), 0.0001);
        assertEquals(1.6845, points[6].getM(), 0.0001);

        assertEquals(-117.36891247638752, points[7].getX(), 0.0001);
        assertEquals(33.9586219303769, points[7].getY(), 0.0001);
        assertEquals(0, points[7].getZ(), 0.0001);
        assertEquals(1.7256666666666667, points[7].getM(), 0.0001);

        assertEquals(-117.3691103715031, points[8].getX(), 0.0001);
        assertEquals(33.958226140145726, points[8].getY(), 0.0001);
        assertEquals(0, points[8].getZ(), 0.0001);
        assertEquals(1.7768333333333333, points[8].getM(), 0.0001);

        assertEquals(-117.36920032382837, points[9].getX(), 0.0001);
        assertEquals(33.95810020689035, points[9].getY(), 0.0001);
        assertEquals(0, points[9].getZ(), 0.0001);
        assertEquals(1.7956666666666667, points[9].getM(), 0.0001);

        assertEquals(-117.36945219033912, points[10].getX(), 0.0001);
        assertEquals(33.957830349914545, points[10].getY(), 0.0001);
        assertEquals(0, points[10].getZ(), 0.0001);
        assertEquals(1.8368333333333333, points[10].getM(), 0.0001);

        assertEquals(-117.37020778987137, points[11].getX(), 0.0001);
        assertEquals(33.95729063596294, points[11].getY(), 0.0001);
        assertEquals(0, points[11].getZ(), 0.0001);
        assertEquals(1.9346666666666668, points[11].getM(), 0.0001);

        assertEquals(-117.3703157326617, points[12].getX(), 0.0001);
        assertEquals(33.957164702707566, points[12].getY(), 0.0001);
        assertEquals(0, points[12].getZ(), 0.0001);
        assertEquals(1.9523333333333333, points[12].getM(), 0.0001);

        assertEquals(-117.37038769452191, points[13].getX(), 0.0001);
        assertEquals(33.95700278852208, points[13].getY(), 0.0001);
        assertEquals(0, points[13].getZ(), 0.0001);
        assertEquals(1.9726666666666666, points[13].getM(), 0.0001);

        assertEquals(-117.37044166591707, points[14].getX(), 0.0001);
        assertEquals(33.956822883871546, points[14].getY(), 0.0001);
        assertEquals(0, points[14].getZ(), 0.0001);
        assertEquals(1.9948333333333332, points[14].getM(), 0.0001);

        assertEquals(-117.37045965638212, points[15].getX(), 0.0001);
        assertEquals(33.95640910317532, points[15].getY(), 0.0001);
        assertEquals(0, points[15].getZ(), 0.0001);
        assertEquals(2.044, points[15].getM(), 0.0001);

        assertEquals(-117.37044166591707, points[16].getX(), 0.0001);
        assertEquals(33.95376450481245, points[16].getY(), 0.0001);
        assertEquals(0, points[16].getZ(), 0.0001);
        assertEquals(2.358, points[16].getM(), 0.0001);
    }

    @Test
    public void decodeTestWhenHasMAndZ() {
        Line line = CompressedGeometryDecoder.decode("+0+1+3+1+emjd+3j07m+3+0+0+1-3-1|+9og+0+lv4+0+lv4|+5rg+uq+r9+au+168");
        assertNotNull(line);
        assertEquals(4, line.getPoints().length);
        Point[] points = line.getPoints();

        assertEquals(481901.0, points[0].getX(), 0.0001);
        assertEquals(3768566.0, points[0].getY(), 0.0001);
        assertEquals(0, points[0].getZ(), 0.0001);
        assertEquals(0.16433333333333333, points[0].getM(), 0.0001);

        assertEquals(481904.0, points[1].getX(), 0.0001);
        assertEquals(3768566.0, points[1].getY(), 0.0001);
        assertEquals(2.25, points[1].getZ(), 0.0001);
        assertEquals(0.30983333333333335, points[1].getM(), 0.0001);

        assertEquals(481904.0, points[2].getX(), 0.0001);
        assertEquals(3768567.0, points[2].getY(), 0.0001);
        assertEquals(2.25, points[2].getZ(), 0.0001);
        assertEquals(0.36816666666666664, points[2].getM(), 0.0001);

        assertEquals(481901.0, points[3].getX(), 0.0001);
        assertEquals(3768566.0, points[3].getY(), 0.0001);
        assertEquals(4.5, points[3].getZ(), 0.0001);
        assertEquals(0.5721666666666667, points[3].getM(), 0.0001);

    }
}
