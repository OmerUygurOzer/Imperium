package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.Unit;
import com.boomer.imperium.game.entities.UnitPool;
import com.boomer.imperium.game.gui.Cursor;
import com.boomer.imperium.game.map.Map;

import java.util.ArrayList;
import java.util.List;

public final class GameWorld implements Renderable, TimedUpdateable {

    private static final int START_INDEX = 0;
    private static final int COUNT = 1;

    private GameContextInterface gameContext;
    private final int[][] layerStartAndCounts = new int[][]{
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0}
    };
    private final Entity[] entities;
    public final Map map;
    private final ArrayList<Entity> selectedEntities;

    private final UnitPool unitPool;

    public GameWorld(GameContext gameContext) {
        gameContext.setGameWorld(this);
        this.gameContext = gameContext;
        this.entities = new Entity[(int) ((gameContext.getGameConfigs().worldSize.getRadius(gameContext.getGameConfigs()) * 2) *
                (gameContext.getGameConfigs().worldSize.getRadius(gameContext.getGameConfigs()) * 2) / gameContext.getGameConfigs().tileSize) * 7];
        layerStartAndCounts[Layer.TILES.getPriority()][START_INDEX] = 0;
        layerStartAndCounts[Layer.TILES_OVERLAY.getPriority()][START_INDEX] = (entities.length / 7);
        layerStartAndCounts[Layer.GROUND.getPriority()][START_INDEX] = (entities.length / 7) * 2;
        layerStartAndCounts[Layer.GROUND_OVERLAY.getPriority()][START_INDEX] = (entities.length / 7) * 3;
        layerStartAndCounts[Layer.AIR.getPriority()][START_INDEX] = (entities.length / 7) * 4;
        layerStartAndCounts[Layer.AIR_OVERLAY.getPriority()][START_INDEX] = (entities.length / 7) * 5;
        layerStartAndCounts[Layer.GUI.getPriority()][START_INDEX] = (entities.length / 7) * 6;
        this.map = new Map(gameContext.getGameResources(), gameContext.getGameConfigs());
        this.selectedEntities = new ArrayList<Entity>(12);
        this.unitPool = new UnitPool(gameContext);
        for (int i = 0; i < 30; i++){
            Unit unit = unitPool.obtain();
            unit.setUnitSpriteAnimator(gameContext.getGameResources().man);
            unit.setUnitLayer(Layer.GROUND);
            unit.setFacing(Direction.NE);
            unit.placeInTile(MathUtils.random(0,47),MathUtils.random(0,47));
            addEntity(unit);
        }

    }

    @Override
    public void update(float deltaTime) {
        map.quadTree.clear();
        for (int i = 0; i < Layer.values().length; i++) {
            for (int j = layerStartAndCounts[i][START_INDEX]; j
                    < layerStartAndCounts[i][START_INDEX] + layerStartAndCounts[i][COUNT]; j++) {
                if (entities[j] != null)
                    map.quadTree.attach(entities[j]);
            }
        }
        for (int i = 0; i < Layer.values().length; i++) {
            int startIndex = layerStartAndCounts[i][START_INDEX];
            int nullTracker = layerStartAndCounts[i][START_INDEX];
            int count = layerStartAndCounts[i][COUNT];
            for (int j = startIndex; j < startIndex + count; j++) {
                if (entities[j] != null && entities[nullTracker] != null) {
                    entities[j].update(deltaTime);
                    nullTracker++;
                } else if (entities[nullTracker] == null) {
                    entities[j].update(deltaTime);
                    entities[nullTracker] = entities[j];
                    entities[nullTracker].setMemoryIndex(nullTracker);
                    entities[j] = null;
                    nullTracker++;
                }
            }
            layerStartAndCounts[i][COUNT] = nullTracker - layerStartAndCounts[i][START_INDEX];
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        map.render(spriteBatch);
        for (int i = 0; i < Layer.values().length; i++) {
            for (int j = layerStartAndCounts[i][START_INDEX];
                 j < layerStartAndCounts[i][START_INDEX] + layerStartAndCounts[i][COUNT]; j++) {
                entities[j].render(spriteBatch);
            }
        }
    }

    public int addEntity(Entity entity) {
        int priority = entity.getLayer().getPriority();
        entities[layerStartAndCounts[priority][START_INDEX] + layerStartAndCounts[priority][COUNT]] = entity;
        entity.setMemoryIndex(layerStartAndCounts[priority][START_INDEX] + layerStartAndCounts[priority][COUNT]);
        map.getTileAt(entity.tileX(), entity.tileY()).getEntitiesContained().add(entity);
        return layerStartAndCounts[priority][START_INDEX] + layerStartAndCounts[priority][COUNT]++;
    }

    public void removeEntity(Entity entity) {
        entities[entity.getMemoryIndex()] = null;
        map.getTileAt(entity.tileX(), entity.tileY()).getEntitiesContained().remove(entity);
    }

    public void selectEntities(List<Entity> entities){
        this.selectedEntities.addAll(entities);
        for(Entity entity: selectedEntities)
                entity.select();
    }

    public void clearSelection() {
        for (Entity entity : selectedEntities) {
            entity.deSelect();
        }
        this.selectedEntities.clear();
    }
//    @Override
//    public void mouseHover(Vector2 point) {
//
//    }
//
//    @Override
//    public void mouseLeftClicked(Vector2 point) {
//        selectedEntities.clear();
//        selectedEntities.addAll(map.findTile(point).getEntitiesContained());
//        for(Entity entity: selectedEntities)
//            entity.select();
//
//    }
//
//    @Override
//    public void mouseRightClick(Vector2 point) {
//        Tile tile = map.findTile(point);
//        for(Entity entity: selectedEntities){
//            entity.targetTile(tile);
//            entity.deSelect();
//        }
//        selectedEntities.clear();
//    }
//
//    @Override
//    public void mouseDrag(Rectangle rectangle) {
//        selectedEntities.clear();
//        selectedEntities.addAll(map.quadTree.findObjectsWithinRect(rectangle));
//        for(Entity entity : selectedEntities){
//            entity.select();
//        }
//    }

}
