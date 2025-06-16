# arcgis-compressed-geometry
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![codecov](https://codecov.io/gh/maslke/arcgis-compressed-geometry/graph/badge.svg?token=9KD71BQQG2)](https://codecov.io/gh/maslke/arcgis-compressed-geometry)

A Java library for encoding and decoding ArcGIS Compressed Geometry Format.

## Introduction

This library provides encoding and decoding functionality for ArcGIS Compressed Geometry Format. It supports the following coordinate formats:

- xy (2D coordinates)
- xyz (3D coordinates with elevation)
- xym (coordinates with measure values)
- xyzm (coordinates with elevation and measure values)


## How to use

### Encode

```java

List<double[]> coordinates = Arrays.asList(
            new double[]{-122.40645857695421, 37.78272915354862},
            new double[]{-122.40609876765315, 37.78299901052442}, 
            new double[]{-122.40597283439777, 37.78305298191958},
            new double[]{-122.40417378789242, 37.7844382477287}
        );
String geometry = CompressedGeometryEncoder.encode(coordinates, "xy", 55585);

```

### Decode

```java

List<double[]> points = CompressedGeometryDecoder.decode("+1m91-6fkfr+202tp+k+f+7+3+34+2d");

```
