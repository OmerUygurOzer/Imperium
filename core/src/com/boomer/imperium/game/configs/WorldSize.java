package com.boomer.imperium.game.configs;

public enum WorldSize {
    SMALL((2 * (float)Math.pow(2,4)) / 2), // 32 x 32
    MEDIUM((3 * (float)Math.pow(2,4)) / 2), // 42 x 42
    LARGE((4 * (float)Math.pow(2,4)) / 2),  // 52 x 52
    REAL_LIFE((5 * (float)Math.pow(2,4)) / 2); // 62 x 62

    private float radius;

    WorldSize(float radius){
        this.radius = radius;
    }

    public float getRadius(GameConfigs gameConfigs) {
        return radius*gameConfigs.tileSize;
    }
}
