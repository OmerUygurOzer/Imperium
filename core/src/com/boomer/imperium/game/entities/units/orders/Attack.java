package com.boomer.imperium.game.entities.units.orders;

import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.units.Unit;

public class Attack implements UnitOrder {

    private final Unit unit;
    private Entity targetEntity;

    public Attack(Unit unit){
        this.unit = unit;
    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public void reset() {
        this.targetEntity = null;
    }

    @Override
    public void update(float deltaTime) {

    }

    public void attackEntity(Entity entity){
        this.targetEntity = entity;
    }
}
