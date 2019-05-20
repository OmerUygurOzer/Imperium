package com.boomer.imperium.game.entities.units.orders;

import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.GameWorld;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.entities.buildings.Buildable;
import com.boomer.imperium.game.entities.buildings.Building;
import com.boomer.imperium.game.entities.buildings.BuildingState;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.entities.units.UnitState;
import com.boomer.imperium.game.events.GameCalendarTracker;
import com.boomer.imperium.game.map.Tile;

public final class Build implements UnitOrder,GameCalendarTracker.Listener {

    private final Unit unit;
    private final GameWorld gameWorld;
    private final GameConfigs gameConfigs;
    private Buildable buildable;
    private Tile tile;
    private Building building;
    private int builtSoFar = 0;
    private boolean finished = false;

    public Build(Unit unit, GameConfigs gameConfigs, GameWorld gameWorld) {
        this.unit = unit;
        this.gameConfigs = gameConfigs;
        this.gameWorld = gameWorld;
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
            System.out.println("BUILT");
            tile.removeEntity(unit);
            unit.setStateFlags(unit.getStateFlags() ^ (GameFlags.SELECTABLE | GameFlags.RENDERABLE));
            building = buildable.build();
            building.setPosition(tile);
            building.setState(BuildingState.BEING_CONSTRUCTED);
            gameWorld.addEntity(building);
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
            System.out.println("BUILDING");
            int curAddition =(int)((unit.getConstruction()/100f)*gameConfigs.maxConstructionPerDay);
            builtSoFar+=curAddition;
            building.setHp(building.getHp()+curAddition);
            if(builtSoFar>=building.getMaxHp()){
                System.out.println("BUILDING DONE");
                finished = true;
                building.setState(BuildingState.IDLE);
                unit.setStateFlags(unit.getStateFlags() | GameFlags.UNCONTAINED);
                Tile availableTile = gameWorld.map.findAdjancentAvailableTile(tile);
                if(availableTile!=null){
                    availableTile.addEntity(unit);
                    unit.placeOnTile(availableTile);
                }
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
