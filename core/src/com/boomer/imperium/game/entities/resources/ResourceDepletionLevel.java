package com.boomer.imperium.game.entities.resources;

public enum ResourceDepletionLevel {
    FULL(0,1f),
    USED(1,0.75f),
    HALF(2,0.50f),
    ALMOST_DEPLETED(3,0.25f),
    DEPLETED(4,0f);

    private float percentage;
    private int animationIndex;

    ResourceDepletionLevel(int animationIndex,float percentage){
        this.animationIndex = animationIndex;
        this.percentage = percentage;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public float getPercentage() {
        return percentage;
    }
}
