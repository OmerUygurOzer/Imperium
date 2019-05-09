package com.boomer.imperium.game.map;

import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.entities.units.UnitMovement;
import com.boomer.imperium.game.entities.units.UnitOrders;
import com.boomer.imperium.game.entities.units.UnitState;
import com.boomer.imperium.game.events.EventManager;
import com.boomer.imperium.game.events.EventType;

public class PathTracker implements TimedUpdateable {

    private final GameContextInterface gameContext;
    private final Unit unit;
    private final Map map;
    private Path path;
    private final UnitMovement unitMovement;
    private UnitOrders unitOrders;
    private int curPath;
    private int tileX, tileY;
    private State state;

    private enum State {
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
        float mov = unitMovement.updateBounds(deltaTime);
        if (mov >= 0.5f && (unit.tileX() != tileX || unit.tileY() != tileY)) {
            map.getTileAt(unit.tileX(), unit.tileY()).removeEntity(unit);
            unit.setTile(tileX, tileY);
            map.getTileAt(tileX, tileY).addEntity(unit);
//            gameContext.getEventManager().raiseEvent(EventType.UNIT_SWITCH_TILES)
//                    .setParams(unit.getMemoryIndex())
//                    .setParams(unit.tileX())
//                    .setParams(unit.tileY())
//                    .setParams(tileX)
//                    .setParams(tileY);
        } else if (mov >= 1f) {
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
                if (map.getTileAt(tileX, tileY).equals(unitOrders.getDestinationTile())) {
                    unit.setState(UnitState.IDLE);
                    state = State.IDLE;
                    return;
                }
                direction = PathFinder
                        .getNextMoveForTarget(map,
                                map.getTileAt(unitOrders.getUnit().tileX(), unitOrders.getUnit().tileY()),
                                unitOrders.getDestinationTile());
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
//            gameContext.getEventManager().raiseEvent(EventType.UNIT_ARRIVED_AT_TILE)
//                    .setParams(tileX)
//                    .setParams(tileY);
        }
    }

    public void activate(Path path) {
        this.path = path;
        this.curPath = 0;
        this.state = State.ACTIVE_PATH;
        setTargetForDirection(path.tasks.get(curPath));
        unitMovement.setDirection(path.tasks.get(curPath));
        unit.setFacing(path.tasks.get(curPath));
    }

    public void activate(UnitOrders unitOrders) {
        this.unitOrders = unitOrders;
        this.state = State.ACTIVE_TILE;
        Direction direction = PathFinder.getNextMoveForTarget(map, map.getTileAt(unitOrders.getUnit().tileX(), unitOrders.getUnit().tileY()), unitOrders.getDestinationTile());
        setTargetForDirection(direction);
        unitMovement.setDirection(direction);
        unit.setFacing(direction);
    }

    private void setTargetForDirection(Direction direction) {
        this.tileX = unit.tileX() + (int) direction.directionVector.x;
        this.tileY = unit.tileY() + (int) direction.directionVector.y;
    }
}
