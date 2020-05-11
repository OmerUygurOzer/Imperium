package com.boomer.imperium.scripts.mirrors;

public final class CircleMirror {

    static final String VALUE_TYPE = "circle";

    private float x;
    private float y;
    private float radius;

    public CircleMirror(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public CircleMirror(CircleMirror circleMirror) {
        this.x = circleMirror.x;
        this.y = circleMirror.y;
        this.radius = circleMirror.radius;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "x=" + x +
                ", y=" + y +
                ", radius=" + radius +
                '}';
    }
}
