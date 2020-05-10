package com.boomer.imperium.model.attribute;

public final class RectMirror {

    static final String VALUE_TYPE = "rect";

    private float x;
    private float y;
    private float width;
    private float height;

    public RectMirror(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public RectMirror(RectMirror rectMirror) {
        this.x = rectMirror.x;
        this.y = rectMirror.y;
        this.width = rectMirror.width;
        this.height = rectMirror.height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Rect{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
