package com.boomer.imperium.game;

public enum Layer {
    TILES(0),
    TILES_OVERLAY(1),
    GROUND(2),
    GROUND_OVERLAY(3),
    AIR(4),
    AIR_OVERLAY(5);

    private int priority;

    Layer(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
