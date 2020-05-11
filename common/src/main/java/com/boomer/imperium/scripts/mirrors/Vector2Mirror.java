package com.boomer.imperium.scripts.mirrors;

public final class Vector2Mirror {

    static final String VALUE_TYPE = "vector2";

    private float x;
    private float y;

    public Vector2Mirror(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2Mirror(Vector2Mirror vector2Mirror){
        this.x = vector2Mirror.x;
        this.y = vector2Mirror.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
