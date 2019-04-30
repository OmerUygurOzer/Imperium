package com.boomer.imperium.game.map;

import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.entities.Unit;
import com.boomer.imperium.game.entities.UnitMovement;
import com.boomer.imperium.game.entities.UnitState;

public class PathTracker implements TimedUpdateable {

    private Unit unit;
    private final Map map;
    private Path path;
    private final UnitMovement unitMovement;
    private int curPath;
    private int tileX, tileY;
    private State state;

    private enum State{
        ACTIVE,
        IDLE
    }

    public PathTracker(Unit unit, Map map,UnitMovement unitMovement) {
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
            map.getTileAt(unit.tileX(), unit.tileY()).getEntitiesContained().remove(unit);
            unit.setTile(tileX, tileY);
            map.getTileAt(tileX, tileY).getEntitiesContained().add(unit);
        } else if (mov >= 1f) {
            curPath++;
            unitMovement.setLength(0);
            if (curPath == path.tasks.size()) {
                unit.setState(UnitState.IDLE);
                state = State.IDLE;
                return;
            }
            unit.placeInTile(tileX, tileY);
            unit.setFacing(path.tasks.get(curPath));
            setTargetForDirection(path.tasks.get(curPath));
            unitMovement.setDirection(path.tasks.get(curPath));
        }
    }

    public void activate(Path path){
        this.path = path;
        this.curPath = 0;
        this.state = State.ACTIVE;
        setTargetForDirection(path.tasks.get(curPath));
        unitMovement.setDirection(path.tasks.get(curPath));
        unit.setFacing(path.tasks.get(curPath));
    }

    private void setTargetForDirection(Direction direction){
        this.tileX = unit.tileX() + (int)direction.directionVector.x;
        this.tileY = unit.tileY() + (int)direction.directionVector.y;
    }
}
