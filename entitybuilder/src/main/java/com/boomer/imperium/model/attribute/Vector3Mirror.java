package com.boomer.imperium.model.attribute;

public final class Vector3Mirror {

    static final String VALUE_TYPE = "vector3";

    private float x;
    private float y;
    private float z;

    public Vector3Mirror(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3Mirror(Vector3Mirror vector3Mirror) {
        this.x = vector3Mirror.x;
        this.y = vector3Mirror.y;
        this.z = vector3Mirror.z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
