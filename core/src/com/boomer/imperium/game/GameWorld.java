package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.entities.Unit;
import com.boomer.imperium.game.map.Map;

public class GameWorld implements Renderable, TimedUpdateable {

    private static final int START_INDEX = 0;
    private static final int COUNT = 1;

    private final Resources worldResources;
    private final int[][] layerStartAndCounts = new int[][]{
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0}};
    private final Entity[] entities;
    public final Map map;

    public GameWorld(Resources resources, GameConfigs configs) {
        worldResources = resources;
        entities = new Entity[(int)((configs.worldSize.getRadius(configs) * 2) * (configs.worldSize.getRadius(configs) * 2) / configs.tileSize) * 6];
        layerStartAndCounts[Layer.TILES.getPriority()][START_INDEX] = 0;
        layerStartAndCounts[Layer.TILES_OVERLAY.getPriority()][START_INDEX] = (entities.length / 6);
        layerStartAndCounts[Layer.GROUND.getPriority()][START_INDEX] = (entities.length / 6) * 2;
        layerStartAndCounts[Layer.GROUND_OVERLAY.getPriority()][START_INDEX] = (entities.length / 6) * 3;
        layerStartAndCounts[Layer.AIR.getPriority()][START_INDEX] = (entities.length / 6) * 4;
        layerStartAndCounts[Layer.AIR_OVERLAY.getPriority()][START_INDEX] = (entities.length / 6) * 5;
        map = new Map(resources, configs);
        for(int i = 0 ; i < 200 ; i++)
            addEntity(new Unit(configs,this,resources.man,Layer.GROUND));
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < Layer.values().length; i++) {
            for (int j = layerStartAndCounts[i][START_INDEX]; j
                    < layerStartAndCounts[i][START_INDEX] + layerStartAndCounts[i][COUNT]; j++) {
                if(entities[j]!=null)
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
            layerStartAndCounts[i][COUNT] = nullTracker-layerStartAndCounts[i][START_INDEX];
        }
        map.quadTree.clear();
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
        map.getTileAt(entity.tileX(),entity.tileY()).getEntitiesContained().add(entity);
        return layerStartAndCounts[priority][START_INDEX] + layerStartAndCounts[priority][COUNT]++;
    }

    public void removeEntity(Entity entity) {
        entities[entity.getMemoryIndex()] = null;
        map.getTileAt(entity.tileX(),entity.tileY()).getEntitiesContained().remove(entity);
    }


}
