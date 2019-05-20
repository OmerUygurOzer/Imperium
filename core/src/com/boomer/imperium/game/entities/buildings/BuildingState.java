package com.boomer.imperium.game.entities.buildings;

public enum BuildingState {
    IDLE(0),
    ACTIVE(1),
    BEING_CONSTRUCTED(0), //Todo(Omer): make this 2
    COLLAPSING(3),
    RUBBLE(4);

    private int animationIndex;

    BuildingState(int animationIndex){
        this.animationIndex = animationIndex;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }
}
