package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.buildings.Buildable;
import com.boomer.imperium.game.entities.buildings.Building;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.entities.units.UnitPool;
import com.boomer.imperium.game.map.Map;
import com.boomer.imperium.game.map.TileVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GameWorld implements Renderable, TimedUpdateable {

    private static final int START_INDEX = 0;
    private static final int COUNT = 1;

    private final GameContextInterface gameContext;
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

    public final UnitPool unitPool;

    public GameWorld(final GameContext gameContext) {
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
        this.selectedEntities = new ArrayList<Entity>(20);
        this.unitPool = new UnitPool(gameContext);
        for (int i = 0; i < 30; i++) {
            Unit unit = unitPool.obtain();
            unit.setTypeFlags(GameFlags.UNIT);
            unit.setUnitSpriteAnimator(gameContext.getGameResources().man);
            unit.setUnitLayer(Layer.GROUND);
            unit.setFacing(Direction.NE);
            unit.setPosition(MathUtils.random(0, 47), MathUtils.random(0, 47));
            unit.setIcon(LogicUtils.randomSelect(Arrays.asList(gameContext.getGameResources().normanIcon, gameContext.getGameResources().grokkenIcon,
                    gameContext.getGameResources().mayanIcon, gameContext.getGameResources().greekIcon, gameContext.getGameResources().vikingIcon)));
            unit.setMaxHp(200);
            unit.setHp(MathUtils.random(0, 200));
            unit.setConstruction(MathUtils.random(0, 100));
            unit.setBuildables(Arrays.<Buildable>asList(new Buildable() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public Drawable getCursorFillerSprite() {
                    return gameContext.getGameResources().anotherTemple;
                }

                @Override
                public Rectangle getCursorFillerRectangle() {
                    return null;
                }

                @Override
                public Drawable getUIIcon() {
                    return gameContext.getGameResources().fortButtonDrawable;
                }

                @Override
                public List<TileVector> getTileCoverage() {
                    return null;
                }

                @Override
                public int widthInTiles() {
                    return 2;
                }

                @Override
                public int heightInTiles() {
                    return 2;
                }

                @Override
                public Building build() {
                    return null;
                }
            }, new Buildable() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public Drawable getCursorFillerSprite() {
                    return gameContext.getGameResources().temple;
                }

                @Override
                public Rectangle getCursorFillerRectangle() {
                    return null;
                }

                @Override
                public Drawable getUIIcon() {
                    return gameContext.getGameResources().factoryButtonDrawable;
                }

                @Override
                public List<TileVector> getTileCoverage() {
                    return null;
                }

                @Override
                public int widthInTiles() {
                    return 2;
                }

                @Override
                public int heightInTiles() {
                    return 2;
                }

                @Override
                public Building build() {
                    return null;
                }
            }, new Buildable() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public Drawable getCursorFillerSprite() {
                    return gameContext.getGameResources().dragonTemple;
                }

                @Override
                public Rectangle getCursorFillerRectangle() {
                    return null;
                }

                @Override
                public Drawable getUIIcon() {
                    return gameContext.getGameResources().universityButtonDrawable;
                }

                @Override
                public List<TileVector> getTileCoverage() {
                    return null;
                }

                @Override
                public int widthInTiles() {
                    return 2;
                }

                @Override
                public int heightInTiles() {
                    return 2;
                }

                @Override
                public Building build() {
                    return null;
                }
            }));
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
        map.getTileAt(entity.tileX(), entity.tileY()).addEntity(entity);
        return layerStartAndCounts[priority][START_INDEX] + layerStartAndCounts[priority][COUNT]++;
    }

    public void removeEntity(Entity entity) {
        entities[entity.getMemoryIndex()] = null;
        map.getTileAt(entity.tileX(), entity.tileY()).removeEntity(entity);
    }

    public void selectEntities(List<Entity> entities) {
        this.selectedEntities.addAll(entities);
        for (Entity entity : selectedEntities)
            entity.select();
    }

    public void clearSelection() {
        for (Entity entity : selectedEntities) {
            entity.deSelect();
        }
        this.selectedEntities.clear();
    }

    public Nation getNation(int nationIndex) {
        return null;
    }

    public Player getPlayer(int playerIndex) {
        return null;
    }

    public void setOrderForSelected(Vector2 vector2){
        for (Entity entity : selectedEntities)
            entity.asUnit().targetTile(map.findTile(vector2));
    }
}
