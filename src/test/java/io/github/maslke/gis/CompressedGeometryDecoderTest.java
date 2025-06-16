package io.github.maslke.gis;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CompressedGeometryDecoderTest {

    @Test
    public void testDecodeWhenNoZAndNoM() {
        List<double[]> points = CompressedGeometryDecoder.decode("+1m91-6fkfr+202tp+k+f+7+3+34+2d");
        assertNotNull(points);
        assertEquals(4, points.size());

        assertEquals(-122.40645857695421, points.get(0)[0], 0.0001);
        assertEquals(37.78272915354862, points.get(0)[1], 0.0001);

        assertEquals(-122.40609876765315, points.get(1)[0], 0.0001);
        assertEquals(37.78299901052442, points.get(1)[1], 0.0001);

        assertEquals(-122.40597283439777, points.get(2)[0], 0.0001);
        assertEquals(37.78305298191958, points.get(2)[1], 0.0001);

        assertEquals(-122.40417378789242, points.get(3)[0], 0.0001);
        assertEquals(37.7844382477287, points.get(3)[1], 0.0001);
    }

    @Test
    public void testDecodeWhenNoZAndNoMNewVersion() {
        List<double[]> points = CompressedGeometryDecoder.decode("+0+1+0+1m91-6fkfr+202tp+k+f+7+3+34+2d");
        assertNotNull(points);
        assertEquals(4, points.size());

        assertEquals(-122.40645857695421, points.get(0)[0], 0.0001);
        assertEquals(37.78272915354862, points.get(0)[1], 0.0001);

        assertEquals(-122.40609876765315, points.get(1)[0], 0.0001);
        assertEquals(37.78299901052442, points.get(1)[1], 0.0001);

        assertEquals(-122.40597283439777, points.get(2)[0], 0.0001);
        assertEquals(37.78305298191958, points.get(2)[1], 0.0001);

        assertEquals(-122.40417378789242, points.get(3)[0], 0.0001);
        assertEquals(37.7844382477287, points.get(3)[1], 0.0001);
    }

    @Test
    public void testDecodeWhenHasZAndNoM() {
        List<double[]> points = CompressedGeometryDecoder.decode("+0+1+1+1+emjd+3j07m+3+0+0+1-3-1|+9og+0+lv4+0+lv4");
        assertNotNull(points);
        assertEquals(4, points.size());

        assertEquals(481901.0, points.get(0)[0], 0.0001);
        assertEquals(3768566.0, points.get(0)[1], 0.0001);
        assertEquals(0, points.get(0)[2], 0.0001);

        assertEquals(481904.0, points.get(1)[0], 0.0001);
        assertEquals(3768566.0, points.get(1)[1], 0.0001);
        assertEquals(2.25, points.get(1)[2], 0.0001);

        assertEquals(481904.0, points.get(2)[0], 0.0001);
        assertEquals(3768567.0, points.get(2)[1], 0.0001);
        assertEquals(2.25, points.get(2)[2], 0.0001);

        assertEquals(481901.0, points.get(3)[0], 0.0001);
        assertEquals(3768566.0, points.get(3)[1], 0.0001);
        assertEquals(4.5, points.get(3)[2], 0.0001);
    }

    @Test
    public void testDecodeWhenHasMAndNoZ() {
        List<double[]> points = CompressedGeometryDecoder.decode(
                "+0+1+2+1m91-6733n+1pjfe+g-e+1b-r+9-9+c-h+2-j-3-v-7-j-b-m-5-7-e-f-1a-u" +
                        "-6-7-4-9-3-a-1-n+1-4j|+5rg+81s+7n+i0+4f+7r+7g+ce+7n+9j+3h+7n+ib+3a+3q" +
                        "+45+97+1qs");
        assertNotNull(points);
        assertEquals(17, points.size());

        assertEquals(-117.37020778987137, points.get(0)[0], 0.0001);
        assertEquals(33.96106863362418, points.get(0)[1], 0.0001);
        assertEquals(1.3753333333333333, points.get(0)[2], 0.0001);

        assertEquals(-117.36991994243051, points.get(1)[0], 0.0001);
        assertEquals(33.96081676711343, points.get(1)[1], 0.0001);
        assertEquals(1.4165, points.get(1)[2], 0.0001);

        assertEquals(-117.3691463524332, points.get(2)[0], 0.0001);
        assertEquals(33.960331024556986, points.get(2)[1], 0.0001);
        assertEquals(1.5125, points.get(2)[2], 0.0001);

        assertEquals(-117.36898443824772, points.get(3)[0], 0.0001);
        assertEquals(33.9601691103715, points.get(3)[1], 0.0001);
        assertEquals(1.5363333333333333, points.get(3)[2], 0.0001);
    }

    @Test
    public void testDecodeWhenHasMAndZ() {
        List<double[]> points = CompressedGeometryDecoder.decode(
                "+0+1+3+1+emjd+3j07m+3+0+0+1-3-1|+9og+0+lv4+0+lv4|+5rg+uq+r9+au+168");
        assertNotNull(points);
        assertEquals(4, points.size());

        assertEquals(481901.0, points.get(0)[0], 0.0001);
        assertEquals(3768566.0, points.get(0)[1], 0.0001);
        assertEquals(0, points.get(0)[2], 0.0001);
        assertEquals(0.16433333333333333, points.get(0)[3], 0.0001);

        assertEquals(481904.0, points.get(1)[0], 0.0001);
        assertEquals(3768566.0, points.get(1)[1], 0.0001);
        assertEquals(2.25, points.get(1)[2], 0.0001);
        assertEquals(0.30983333333333335, points.get(1)[3], 0.0001);

        assertEquals(481904.0, points.get(2)[0], 0.0001);
        assertEquals(3768567.0, points.get(2)[1], 0.0001);
        assertEquals(2.25, points.get(2)[2], 0.0001);
        assertEquals(0.36816666666666664, points.get(2)[3], 0.0001);

        assertEquals(481901.0, points.get(3)[0], 0.0001);
        assertEquals(3768566.0, points.get(3)[1], 0.0001);
        assertEquals(4.5, points.get(3)[2], 0.0001);
        assertEquals(0.5721666666666667, points.get(3)[3], 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeInvalidVersion() {
        CompressedGeometryDecoder.decode("+0+2+3+1+emjd+3j07m+3+0+0+1-3-1|+9og+0+lv4+0+lv4|+5rg+uq+r9+au+168");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeInvalidFlag() {
        CompressedGeometryDecoder.decode("+0+1+4+1+emjd+3j07m+3+0+0+1-3-1|+9og+0+lv4+0+lv4|+5rg+uq+r9+au+168");
    }
}
