package com.github.maslke.gis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Point {
    private double x;
    private double y;
    private double z;
    private double m;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, double z, double m) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.m = m;
    }
}
