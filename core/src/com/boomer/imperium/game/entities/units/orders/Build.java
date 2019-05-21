package com.boomer.imperium.game.entities.units.orders;

import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.buildings.Buildable;
import com.boomer.imperium.game.entities.buildings.Building;
import com.boomer.imperium.game.entities.buildings.BuildingState;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.entities.units.UnitState;
import com.boomer.imperium.game.events.EventType;
import com.boomer.imperium.game.events.GameCalendarTracker;
import com.boomer.imperium.game.events.Parameters;
import com.boomer.imperium.game.map.Tile;

public final class Build implements UnitOrder,GameCalendarTracker.Listener {

    private final Unit unit;
    private final GameContextInterface gameContext;
    private Buildable buildable;
    private Tile tile;
    private Building building;
    private int builtSoFar = 0;
    private boolean finished = false;

    public Build(Unit unit, GameContextInterface gameContext) {
        this.unit = unit;
        this.gameContext = gameContext;
    }

    @Override
    public boolean completed() {
        return finished;
    }

    @Override
    public void reset() {
        this.buildable = null;
        this.tile = null;
        this.building = null;
        this.builtSoFar = 0;
    }

    @Override
    public void update(float deltaTime) {
        if(completed()){
            reset();
            return;
        }
        if (!unit.onTile(tile)) {
            unit.getPathTracker().update(deltaTime);
        } else {
            if(building!=null)
                return;
            unit.getPathTracker().stop();
            unit.setState(UnitState.IDLE);
            tile.removeEntity(unit);
            unit.setStateFlags(unit.getStateFlags() ^ (GameFlags.SELECTABLE | GameFlags.RENDERABLE));
            building = buildable.build();
            building.setPosition(tile);
            building.setState(BuildingState.BEING_CONSTRUCTED);
            gameContext.getGameWorld().addEntity(building);
            gameContext
                    .getEventManager()
                    .raiseEvent(EventType.BUILDING_CONSTRUCTION_STARTED)
                    .getParams()
                    .putParameter(Parameters.Key.BUILDING,building)
                    .putParameter(Parameters.Key.BUILDER,unit);
        }
    }

    public void build(Buildable buildable, Tile tile) {
        this.finished = false;
        this.buildable = buildable;
        this.tile = tile;
        this.unit.setState(UnitState.MOVING);
        this.unit.getPathTracker().activate(tile);
    }

    @Override
    public void dayPassed(int daysPassed) {
        if(building!=null){
            int curAddition =(int)((unit.getConstruction()/100f)*gameContext.getGameConfigs().maxConstructionPerDay);
            builtSoFar+=curAddition;
            building.setHp(building.getHp()+curAddition);
            if(builtSoFar>=building.getMaxHp()){
                finished = true;
                building.setState(BuildingState.IDLE);
                unit.setStateFlags(unit.getStateFlags() | GameFlags.UNCONTAINED);
                Tile availableTile = gameContext.getGameWorld().map.findAdjancentAvailableTile(tile);
                if(availableTile!=null){
                    availableTile.addEntity(unit);
                    unit.placeOnTile(availableTile);
                }
                gameContext
                        .getEventManager()
                        .raiseEvent(EventType.BUILDING_CONSTRUCTION_COMPLETE)
                        .getParams()
                        .putParameter(Parameters.Key.BUILDING,building)
                        .putParameter(Parameters.Key.BUILDER,unit);
                building = null;
            }
        }
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
