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
    private final PathFinder pathFinder;
    private final Unit unit;
    private final Map map;
    private Path path;
    private final UnitMovement unitMovement;
    private Tile targetTile;
    private int curPath;
    private int tileX, tileY;
    private State state;
    private Direction currentDirection;
    private boolean midTile;

    public enum State {
        ACTIVE_PATH,
        ACTIVE_ENTITY,
        ACTIVE_TILE,
        IDLE
    }

    public PathTracker(GameContextInterface gameContext, Unit unit, Map map, UnitMovement unitMovement) {
        this.gameContext = gameContext;
        this.pathFinder = new PathFinder();
        this.unit = unit;
        this.map = map;
        this.unitMovement = unitMovement;
        this.curPath = 0;
        this.state = State.IDLE;
        this.midTile = false;
    }

    @Override
    public void update(float deltaTime) {
        if (state.equals(State.IDLE))
            return;
        if (!midTile) {
            midTile = true;
            Tile fromTile = map.getTileAt(unit.tileX(), unit.tileY());
            Tile toTile = map.getTileAt(tileX, tileY);
            for(Tile tile : unit.getTilesCovered()){
                tile.removeEntity(unit);
                map.getTileAt(tile.tileX+currentDirection.directionVector.x,
                        tile.tileY+currentDirection.directionVector.y).addEntity(unit);
            }
            gameContext.getEventManager().raiseEvent(EventType.UNIT_SWITCH_TILES)
                    .getParams()
                    .putParameter(Parameters.Key.UNIT, unit)
                    .putParameter(Parameters.Key.FROM_TILE, fromTile)
                    .putParameter(Parameters.Key.TO_TILE, toTile);
        } else if (unitMovement.isComplete()) {
            midTile = false;
            unitMovement.setLength(0);
            unit.setPosition(tileX, tileY);
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
                if (unit.onTile(targetTile)) {
                    unit.setState(UnitState.IDLE);
                    state = State.IDLE;
                    return;
                }
                direction = pathFinder
                        .getNextMoveForTarget(map,
                                map.getTileAt(unit.tileX(), unit.tileY()),
                                targetTile);
                if (direction == Direction.O || direction == null)
                    state = State.IDLE;
            } else {
                direction = Direction.O;
            }
            if (!direction.equals(Direction.O)) {
                startMovingTowardsDirection(direction);
            }
            gameContext.getEventManager().raiseEvent(EventType.UNIT_ARRIVED_AT_TILE)
                    .getParams()
                    .putParameter(Parameters.Key.TILE, map.getTileAt(unit.tileX(), unit.tileY()));
        }
        unitMovement.update(deltaTime);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void stop(){
        this.state = State.IDLE;
        this.unitMovement.setLength(0);
        this.currentDirection = null;
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
        if (!midTile) {
            this.state = State.ACTIVE_TILE;
            startMovingTowardsDirection(pathFinder
                    .getNextMoveForTarget(map, map.getTileAt(unit.tileX(), unit.tileY()), targetTile));
        }
    }

    public void activate(int tileX, int tileY) {
        activate(gameContext.getGameWorld().map.getTileAt(tileX, tileY));
    }

    private void startMovingTowardsDirection(Direction direction){
        unit.setFacing(direction);
        setTargetForDirection(direction);
        unitMovement.setDirection(direction);
    }

    private void setTargetForDirection(Direction direction) {
        this.currentDirection = direction;
        this.tileX = unit.tileX() + direction.directionVector.x;
        this.tileY = unit.tileY() + direction.directionVector.y;
    }
}