package com.boomer.imperium.game.map;

import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.entities.units.UnitMovement;
import com.boomer.imperium.game.entities.units.UnitState;
import com.boomer.imperium.game.events.EventType;
import com.boomer.imperium.game.events.Parameters;

public class PathTracker implements TimedUpdateable {

    private final GameContextInterface gameContext;
    private final Unit unit;
    private final Map map;
    private Path path;
    private final UnitMovement unitMovement;
    private Tile targetTile;
    private int curPath;
    private int tileX, tileY;
    private State state;

    public enum State {
        ACTIVE_PATH,
        ACTIVE_ENTITY,
        ACTIVE_TILE,
        IDLE
    }

    public PathTracker(GameContextInterface gameContext, Unit unit, Map map, UnitMovement unitMovement) {
        this.gameContext = gameContext;
        this.unit = unit;
        this.map = map;
        this.unitMovement = unitMovement;
        this.curPath = 0;
        this.state = State.IDLE;
    }

    @Override
    public void update(float deltaTime) {
        if (state.equals(State.IDLE))
            return;
        unitMovement.update(deltaTime);
        if ((unit.tileX() != tileX || unit.tileY() != tileY)) {
            Tile fromTile = map.getTileAt(unit.tileX(), unit.tileY());
            fromTile.removeEntity(unit);
            unit.setTile(tileX, tileY);
            Tile toTile = map.getTileAt(tileX, tileY);
            toTile.addEntity(unit);
            gameContext.getEventManager().raiseEvent(EventType.UNIT_SWITCH_TILES)
                    .getParams()
                    .putParameter(Parameters.Key.UNIT,unit)
                    .putParameter(Parameters.Key.FROM_TILE,fromTile)
                    .putParameter(Parameters.Key.TO_TILE,toTile);
        } else if(unitMovement.isComplete()){
            unitMovement.setLength(0);
            Direction direction = null;
            if (state.equals(State.ACTIVE_PATH)) {
                curPath++;
                if (curPath == path.tasks.size()) {
                    unit.setState(UnitState.IDLE);
                    state = State.IDLE;
                    return;
                }
                direction = path.tasks.get(curPath);
            } else if (state.equals(State.ACTIVE_TILE)) {
                if (map.getTileAt(tileX, tileY).equals(targetTile)) {
                    unit.setState(UnitState.IDLE);
                    state = State.IDLE;
                    return;
                }
                direction = PathFinder
                        .getNextMoveForTarget(map,
                                map.getTileAt(unit.tileX(), unit.tileY()),
                                targetTile);
                if(direction==Direction.O || direction==null)
                    state = State.IDLE;
            } else {
                direction = Direction.O;
            }

            unit.setPosition(tileX, tileY);
            if (!direction.equals(Direction.O)) {
                unit.setFacing(direction);
                setTargetForDirection(direction);
                unitMovement.setDirection(direction);
            }
            gameContext.getEventManager().raiseEvent(EventType.UNIT_ARRIVED_AT_TILE)
                   .getParams()
                    .putParameter(Parameters.Key.TILE,map.getTileAt(unit.tileX(),unit.tileY()));
        }
    }

    public State getState() {
        return state;
    }

    public void activate(Path path) {
        this.path = path;
        this.curPath = 0;
        this.state = State.ACTIVE_PATH;
        setTargetForDirection(path.tasks.get(curPath));
        unitMovement.setDirection(path.tasks.get(curPath));
        unit.setFacing(path.tasks.get(curPath));
    }

    public void activate(Tile targetTile) {
        this.targetTile = targetTile;
        this.state = State.ACTIVE_TILE;
        Direction direction = PathFinder.getNextMoveForTarget(map, map.getTileAt(unit.tileX(), unit.tileY()), targetTile);
        setTargetForDirection(direction);
        unitMovement.setDirection(direction);
        unit.setFacing(direction);
    }

    private void setTargetForDirection(Direction direction) {
        this.tileX = unit.tileX() + (int) direction.directionVector.x;
        this.tileY = unit.tileY() + (int) direction.directionVector.y;
    }
}
