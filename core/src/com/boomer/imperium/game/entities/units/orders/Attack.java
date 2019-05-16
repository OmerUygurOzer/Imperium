package com.boomer.imperium.game.entities.units.orders;

import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.LogicUtils;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.entities.units.UnitState;
import com.boomer.imperium.game.map.PathTracker;

public class Attack implements UnitOrder {

    private final Unit unit;
    private Entity targetEntity;

    private int currentTargetTileX,currentTargetTileY;

    public Attack(Unit unit){
        this.unit = unit;
    }

    @Override
    public boolean completed() {
        return targetEntity.shouldRemove();
    }

    @Override
    public void reset() {
        this.targetEntity = null;
        this.currentTargetTileY = -1;
        this.currentTargetTileX = -1;
    }

    @Override
    public void update(float deltaTime) {
        if(completed()){
            unit.setState(UnitState.IDLE);
            unit.getPathTracker().setState(PathTracker.State.IDLE);
            reset();
        }else if(GameFlags.checkComponentFlag(unit,GameFlags.RANGE_ATTACK)
                && unit.getRangeCircle().contains(targetEntity.getCenter())){
            unit.setState(UnitState.ATTACKING_RANGE);
            unit.getPathTracker().setState(PathTracker.State.IDLE);
        }else if(LogicUtils.areEntitiesAdjacent(unit,targetEntity)) {
            unit.setState(UnitState.ATTACKING_MELEE);
            unit.getPathTracker().setState(PathTracker.State.IDLE);
        }else if(currentTargetTileX!=targetEntity.tileX()||currentTargetTileY!=targetEntity.tileY()) {
            currentTargetTileX = targetEntity.tileX();
            currentTargetTileY = targetEntity.tileY();
            unit.setState(UnitState.MOVING);
            unit.getPathTracker().activate(currentTargetTileX,currentTargetTileY);
        }else{
            unit.getPathTracker().update(deltaTime);
        }
    }

    public void attackEntity(Entity entity){
        this.targetEntity = entity;
    }
}
