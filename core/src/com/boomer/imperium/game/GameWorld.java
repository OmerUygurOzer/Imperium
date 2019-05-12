package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.boomer.imperium.game.entities.buildings.BuildingPool;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.entities.units.UnitPool;
import com.boomer.imperium.game.events.GameCalendarTracker;
import com.boomer.imperium.game.map.Map;
import com.boomer.imperium.game.map.Tile;
import com.boomer.imperium.game.map.TileVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GameWorld implements Renderable, TimedUpdateable,GameCalendarTracker.Listener {

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
    private final GameCalendarTracker gameCalendarTracker;

    private final ArrayList<Entity> selectedEntities;

    private final UnitPool unitPool;
    private final BuildingPool buildingPool;

    private Buildable buildingToBuild;
    private Tile buildTile;
    private final Rectangle buildDrawRectangle;
    private final Vector2 buildDrawCenter;
    private final Rectangle connectionRadiusRect;
    private final Vector2 mouseHoverLocation;
    private final List<Building> connectionBuildings;
    private final ArrayList<Entity> foundEntities;

    private boolean dayPassedFlag = false;
    private boolean weekPassedFlag = false;
    private boolean monthPassedFlag = false;
    private boolean yearPassedFlag = false;
    private int daysPassed = 0;
    private int weeksPassed = 0;
    private int monthsPassed = 0;
    private int yearsPassed = 0;

    public GameWorld(final GameContext gameContext) {
        gameContext.setGameWorld(this);
        this.gameContext = gameContext;
        this.gameCalendarTracker = new GameCalendarTracker(gameContext);
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
        this.buildingPool = new BuildingPool(gameContext);
        this.mouseHoverLocation = new Vector2();
        this.buildDrawRectangle = new Rectangle();
        this.buildDrawCenter = new Vector2();
        this.connectionRadiusRect = new Rectangle(0f, 0f, 300f, 300f);//Todo: get radius from configs
        this.connectionBuildings = new ArrayList<>();
        this.foundEntities = new ArrayList<>(60);
        for (int i = 0; i < 5; i++) {
            Building building = buildingPool.obtain();
            building.setLayer(Layer.GROUND);
            building.setTypeFlags(GameFlags.BUILDING);
            building.setBuildingSpriteAnimator(gameContext.getGameResources().building);
            building.setTileCoverageVectors(Arrays.asList(new TileVector(0, 0), new TileVector(-1, 0), new TileVector(-1, 1), new TileVector(0, 1)));
            building.setPosition(MathUtils.random(2, 24), MathUtils.random(2, 24));
            building.setComponentFlags(GameFlags.MARKET);
            building.setStateFlags(GameFlags.SELECTABLE);
            addEntity(building);
        }
        for (int i = 0; i < 10; i++) {
            Unit unit = unitPool.obtain();
            unit.setTypeFlags(GameFlags.UNIT);
            unit.setUnitSpriteAnimator(gameContext.getGameResources().man);
            unit.setLayer(Layer.GROUND);
            unit.setFacing(Direction.NE);
            unit.setPosition(MathUtils.random(0, 20), MathUtils.random(0, 20));
            unit.setIcon(LogicUtils.randomSelect(Arrays.asList(gameContext.getGameResources().normanIcon, gameContext.getGameResources().grokkenIcon,
                    gameContext.getGameResources().mayanIcon, gameContext.getGameResources().greekIcon, gameContext.getGameResources().vikingIcon)));
            unit.setMaxHp(200);
            unit.setHp(MathUtils.random(0, 200));
            unit.setConstruction(MathUtils.random(0, 100));
            unit.setStateFlags(GameFlags.SELECTABLE);
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
                public Drawable getUIIcon() {
                    return gameContext.getGameResources().fortButtonDrawable;
                }

                @Override
                public List<TileVector> getTileCoverage() {
                    return null;
                }

                @Override
                public List<Integer> getConnectableComponents() {
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
                public Drawable getUIIcon() {
                    return gameContext.getGameResources().factoryButtonDrawable;
                }

                @Override
                public List<TileVector> getTileCoverage() {
                    return null;
                }

                @Override
                public List<Integer> getConnectableComponents() {
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
                public Drawable getUIIcon() {
                    return gameContext.getGameResources().universityButtonDrawable;
                }

                @Override
                public List<TileVector> getTileCoverage() {
                    return null;
                }

                @Override
                public List<Integer> getConnectableComponents() {
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
        gameCalendarTracker.update(deltaTime);
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
                if(entities[j]!=null){
                    if(dayPassedFlag)
                        entities[j].dayPassed(daysPassed);
                    if(weekPassedFlag)
                        entities[j].weekPassed(weeksPassed);
                    if(monthPassedFlag)
                        entities[j].monthPassed(monthsPassed);
                    if(yearPassedFlag)
                        entities[j].yearPassed(yearsPassed);
                }
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
        if(dayPassedFlag)
            dayPassedFlag = false;
        if(weekPassedFlag)
            weekPassedFlag = false;
        if(monthPassedFlag)
            monthPassedFlag = false;
        if(yearPassedFlag)
            yearPassedFlag = false;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        map.render(spriteBatch, shapeRenderer);
        for (int i = 0; i < Layer.values().length; i++) {
            for (int j = layerStartAndCounts[i][START_INDEX];
                 j < layerStartAndCounts[i][START_INDEX] + layerStartAndCounts[i][COUNT]; j++) {
                entities[j].render(spriteBatch, shapeRenderer);
            }
        }
        if (buildingToBuild != null) {
            shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.YELLOW);
            //shapeRenderer.rect(connectionRadiusRect.x,connectionRadiusRect.y,connectionRadiusRect.width,connectionRadiusRect.height);
            for (Building building : connectionBuildings) {
                shapeRenderer.line(building.getCenter(), buildDrawRectangle.getCenter(buildDrawCenter));
            }
            shapeRenderer.end();
            buildingToBuild.getCursorFillerSprite().draw(spriteBatch, buildDrawRectangle.x, buildDrawRectangle.y, buildDrawRectangle.width, buildDrawRectangle.height);
        }
    }

    public int addEntity(Entity entity) {
        int priority = entity.getLayer().getPriority();
        entities[layerStartAndCounts[priority][START_INDEX] + layerStartAndCounts[priority][COUNT]] = entity;
        entity.setMemoryIndex(layerStartAndCounts[priority][START_INDEX] + layerStartAndCounts[priority][COUNT]);
        int x = entity.tileX();
        int y = entity.tileY();
        for (TileVector tileVector : entity.getTileCoverageVectors()) {
            map.getTileAt(x + tileVector.x, y + tileVector.y).addEntity(entity);
        }
        return layerStartAndCounts[priority][START_INDEX] + layerStartAndCounts[priority][COUNT]++;
    }

    public void removeEntity(Entity entity) {
        entities[entity.getMemoryIndex()] = null;
        for (Tile tile : entity.getTilesCovered()) {
            tile.removeEntity(entity);
        }
    }

    public void selectEntities(List<Entity> entities) {
        this.selectedEntities.addAll(entities);
        for (Entity entity : selectedEntities)
            entity.select();
    }

    public void selectEntity(Entity entity) {
        this.selectedEntities.add(entity);
        entity.select();
    }

    public void clearSelection() {
        for (Entity entity : selectedEntities) {
            entity.deSelect();
        }
        this.selectedEntities.clear();
        this.buildingToBuild = null;
        this.buildTile = null;
        this.connectionBuildings.clear();
    }

    public void mouseHover(Vector2 hoverLocation) {
        this.mouseHoverLocation.set(hoverLocation);
        this.connectionBuildings.clear();
        if (buildingToBuild != null) {
            if (buildTile == null)
                buildTile = gameContext.getGameWorld().map.findTile(hoverLocation);
            if (!buildTile.equals(gameContext.getGameWorld().map.findTile(hoverLocation))) {
                buildTile = gameContext.getGameWorld().map.findTile(hoverLocation);
                float width = buildingToBuild.widthInTiles() * gameContext.getGameConfigs().tileSize;
                float height = buildingToBuild.heightInTiles() * gameContext.getGameConfigs().tileSize;
                Tile leftEndTile = gameContext.getGameWorld().map.getTileAt(buildTile.tileX - (buildingToBuild.widthInTiles() - 1), buildTile.tileY);
                float x = leftEndTile.bounds.x;
                float y = leftEndTile.bounds.y;
                buildDrawRectangle.set(x, y, width, height);
                connectionRadiusRect.setCenter(hoverLocation);
                foundEntities.clear();
                map.quadTree.findObjectsWithinRect(connectionRadiusRect,foundEntities);
                for (Entity entity : foundEntities) {
                    if (GameFlags.checkTypeFlag(entity, GameFlags.BUILDING)) {
                        Building building = entity.asBuilding();
                        if (LogicUtils.distance(hoverLocation, building.getCenter()) < 200d) {
                            connectionBuildings.add(building);
                        }
                    }
                }
            }
        }
    }

    public void setBuildingToBuild(Buildable buildingToBuild) {
        this.buildingToBuild = buildingToBuild;
    }

    public void setTargetTileForSelected(Tile targetTileForSelected) {
        for (Entity entity : selectedEntities) {

        }
    }

    public Nation getNation(int nationIndex) {
        return null;
    }

    public Player getPlayer(int playerIndex) {
        return null;
    }


    @Override
    public void dayPassed(int daysPassed) {
        this.dayPassedFlag = true;
        this.daysPassed = daysPassed;
        System.out.println("DAY PASSED");
    }

    @Override
    public void weekPassed(int weeksPassed) {
        this.weekPassedFlag = true;
        this.weeksPassed = weeksPassed;
        System.out.println("WEEK PASSED");
    }

    @Override
    public void monthPassed(int monthsPassed) {
        this.monthPassedFlag = true;
        this.monthsPassed = monthsPassed;
        System.out.println("MONTH PASSED");
    }

    @Override
    public void yearPassed(int yearsPassed) {
        this.yearPassedFlag = true;
        this.yearsPassed = yearsPassed;
        System.out.println("YEAR PASSED");
    }
}
