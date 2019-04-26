package com.boomer.imperium.game.entities;

public enum UnitState {

    IDLE(0),
    MOVING(1),
    ATTACKING_MELEE(9),
    ATTACKING_RANGE(17),
    SHIELDING(24),
    DYING(31),
    DEAD(32);

    public int animationIndex;

    UnitState(int animationIndex){
        this.animationIndex = animationIndex;
    }
}
