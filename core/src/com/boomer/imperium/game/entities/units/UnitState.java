package com.boomer.imperium.game.entities.units;

public enum UnitState {

    CONTAINED(-1),
    IDLE(0),
    MOVING(0),//Todo(Omer): make this 1.
    ATTACKING_MELEE(0), //Todo(Omer): make this 9
    ATTACKING_RANGE(17),
    SHIELDING(24),
    DYING(31),
    DEAD(32);

    private int animationIndex;

    UnitState(int animationIndex){
        this.animationIndex = animationIndex;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }
}
