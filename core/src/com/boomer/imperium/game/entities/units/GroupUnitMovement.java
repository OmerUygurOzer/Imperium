package com.boomer.imperium.game.entities.units;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.LogicUtils;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.map.Map;
import com.boomer.imperium.game.map.Tile;
import com.boomer.imperium.game.map.TileVector;

import java.util.*;

public class GroupUnitMovement implements TimedUpdateable, Pool.Poolable {

    private class DistanceComparator implements Comparator<Entity>{

        private final Map map;

        public DistanceComparator(Map map){
            this.map = map;
        }

        Tile to;

        @Override
        public int compare(Entity o1, Entity o2) {
            return Double.compare(LogicUtils.distance(map.getTileAt(o1.tileX(),o1.tileY()),to),
                    LogicUtils.distance(map.getTileAt(o2.tileX(),o2.tileY()),to));
        }
    }

    private final Map map;
    private final List<Unit> unitList;
    private final Set<Tile> tileSet;
    private Tile targetTile;
    private final Queue<Tile> tilesQueue;
    private final DistanceComparator distanceComparator;

    public GroupUnitMovement(Map map) {
        this.map = map;
        this.unitList = new ArrayList<>(50);
        this.tileSet = new LinkedHashSet<>(50);
        this.tilesQueue = new ArrayDeque<>(50);
        this.distanceComparator = new DistanceComparator(map);
    }

    public boolean complete() {
        return targetTile == null;
    }

    @Override
    public void update(float deltaTime) {
        if (allUnitsIdle())
            reset();
    }

    public void moveUnitsTo(List<Unit> units,Tile tile) {
        reset();
        this.unitList.addAll(units);
        this.targetTile = tile;
        adjustForNewOrder(tile);
    }

    private boolean allUnitsIdle() {
        for (Unit unit : unitList) {
            if (unit.shouldRemove() || !unit.getState().equals(UnitState.IDLE)) {
                return false;
            }
        }
        return true;
    }

    private void adjustForNewOrder(Tile tile) {
        distanceComparator.to = tile;
        Collections.sort(unitList,distanceComparator);
        tilesQueue.add(tile);
        int unitIndex = 0;
        while (!tilesQueue.isEmpty() && unitIndex < unitList.size()) {
            Tile curTile = tilesQueue.poll();
            Unit unit = unitList.get(unitIndex);
            if (!tileSet.contains(curTile) && doesUnitFitIntoSpace(unit, curTile, tileSet)) {
                for (TileVector tileVector : unit.getTileCoverageVectors()) {
                    tileSet.add(map.getTileAt(curTile.tileX + tileVector.x, curTile.tileY + tileVector.y));
                }
                unit.targetTile(curTile);
                unitIndex++;
            }
            for (Direction direction : Direction.values()) {
                if (!direction.equals(Direction.O)) {
                    Tile candidate
                            = map.getTileAt(curTile.tileX + direction.directionVector.x, curTile.tileY + direction.directionVector.y);
                    if (candidate != null)
                        tilesQueue.add(candidate);
                }
            }
        }
    }

    private boolean doesUnitFitIntoSpace(Unit unit, Tile tile, Set<Tile> alreadyTargetedTiles) {
        for (TileVector tileVector : unit.getTileCoverageVectors()) {
            Tile candidate = map.getTileAt(tile.tileX + tileVector.x, tile.tileY + tileVector.y);
            if (candidate == null || !candidate.canBeMovedTo() || alreadyTargetedTiles.contains(candidate)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void reset() {
        this.tilesQueue.clear();
        this.unitList.clear();
        this.tileSet.clear();
        this.targetTile = null;
        this.distanceComparator.to = null;
    }
}
