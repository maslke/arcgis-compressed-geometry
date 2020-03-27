package com.maslke.gis;

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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public Point withX(double x) {
        this.x = x;
        return this;
    }

    public Point withY(double y) {
        this.y = y;
        return this;
    }

    public Point withZ(double z) {
        this.z = z;
        return this;
    }

    public Point withM(double m) {
        this.m = m;
        return this;
    }


}
