package com.boomer.imperium.game;

public enum WorldSize {
    SMALL(960), // 30 x 30
    MEDIUM(1280), // 40 x 40
    LARGE(1600),  // 50 x 50
    REAL_LIFE(1920); // 60 x 60

    private int radius;

    WorldSize(int radius){
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }
}
