package com.github.maslke.gis;

public class Line {
    private Point[] points;

    public Line() {
        this.points = new Point[0];
    }

    public Line(Point[] points) {
        this.points = points;
    }

    public Point[] getPoints() {
        return this.points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }
}
