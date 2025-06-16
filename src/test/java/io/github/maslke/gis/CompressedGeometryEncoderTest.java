package io.github.maslke.gis;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompressedGeometryEncoderTest {

    @Test
    public void testEncodeWhenNoZAndNoM() {
        List<double[]> coordinates = Arrays.asList(
            new double[]{-122.40645857695421, 37.78272915354862},
            new double[]{-122.40609876765315, 37.78299901052442}, 
            new double[]{-122.40597283439777, 37.78305298191958},
            new double[]{-122.40417378789242, 37.7844382477287}
        );
        String geometry = CompressedGeometryEncoder.encode(coordinates, "xy", 55585);
        assertEquals("+0+1+0+1m91-6fkfr+202tp+k+f+7+3+34+2d", geometry);
    }

    @Test
    public void testEncodeWhenHasZAndNoM() {
        List<double[]> coordinates = Arrays.asList(
            new double[]{481901.0, 3768566.0, 0},
            new double[]{481904.0, 3768566.0, 2.25},
            new double[]{481904.0, 3768567.0, 2.25},
            new double[]{481901.0, 3768566.0, 4.5}
        );
        String geometry = CompressedGeometryEncoder.encode(coordinates, "xyz", 1, 10000, null);
        assertEquals("+0+1+1+1+emjd+3j07m+3+0+0+1-3-1|+9og+0+lv4+0+lv4", geometry);
    }

    @Test
    public void testEncodeWhenHasMAndNoZ() {
        List<double[]> coordinates = Arrays.asList(
            new double[]{-117.37020778987137, 33.96106863362418, 1.3753333333333333},
            new double[]{-117.36991994243051, 33.96081676711343, 1.4165},
            new double[]{-117.3691463524332, 33.960331024556986, 1.5125},
            new double[]{-117.36898443824772, 33.9601691103715, 1.5363333333333333}
        );
        String geometry = CompressedGeometryEncoder.encode(coordinates, "xym", 55585, null, 6000);
        assertEquals("+0+1+2+1m91-6733n+1pjfe+g-e+1b-r+9-9|+5rg+81s+7n+i0+4f", geometry);
    }

    @Test
    public void testEncodeWhenHasMAndHasZ() {
        List<double[]> coordinates = Arrays.asList(
            new double[]{481901.0, 3768566.0, 0, 0.16433333333333333},
            new double[]{481904.0, 3768566.0, 2.25, 0.30983333333333335},
            new double[]{481904.0, 3768567.0, 2.25, 0.36816666666666664},
            new double[]{481901.0, 3768566.0, 4.5, 0.5721666666666667}
        );
        String geometry = CompressedGeometryEncoder.encode(coordinates, "xyzm", 1, 10000, 6000);
        assertEquals("+0+1+3+1+emjd+3j07m+3+0+0+1-3-1|+9og+0+lv4+0+lv4|+5rg+uq+r9+au+168", geometry);
    }
}
