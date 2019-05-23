package com.boomer.imperium.game.entities.units.orders;

import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.entities.units.UnitState;
import com.boomer.imperium.game.map.Tile;

public final class Move implements UnitOrder {

    private final Unit unit;
    private Tile target;

    public Move(Unit unit){
        this.unit = unit;
    }

    @Override
    public boolean completed() {
        return unit.onTile(target);
    }

    @Override
    public void reset() {
        target = null;
        unit.setState(UnitState.IDLE);
    }

    @Override
    public void update(float deltaTime) {
        unit.getPathTracker().update(deltaTime);
    }

    public void targetTile(Tile tile){
        this.target = tile;
        unit.setState(UnitState.MOVING);
        unit.getPathTracker().activate(tile);
    }

    @Override
    public void dayPassed(int daysPassed) {

    }

    @Override
    public void weekPassed(int weeksPassed) {

    }

    @Override
    public void monthPassed(int monthsPassed) {

    }

    @Override
    public void yearPassed(int yearsPassed) {

    }
}
