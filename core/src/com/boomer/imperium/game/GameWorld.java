package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.buildings.Buildable;
import com.boomer.imperium.game.entities.buildings.Building;
import com.boomer.imperium.game.entities.buildings.BuildingPool;
import com.boomer.imperium.game.entities.units.GroupUnitMovement;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.entities.units.UnitPool;
import com.boomer.imperium.game.events.*;
import com.boomer.imperium.game.map.Map;
import com.boomer.imperium.game.map.MapScanner;
import com.boomer.imperium.game.map.Tile;
import com.boomer.imperium.game.map.TileVector;
import com.boomer.imperium.game.players.Nation;
import com.boomer.imperium.game.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class GameWorld implements Renderable, TimedUpdateable, GameCalendarTracker.Listener {

    public static final Condition IS_BUILDING = new Condition() {
        @Override
        public boolean check(Event event) {
            return event.getGameContext().getGameWorld().buildingToBuild != null;
        }
    };

    private final GameContextInterface gameContext;
    private final List<Entity>[] entities;
    private final List<Entity> removal;
    public final Map map;
    private final GameCalendarTracker gameCalendarTracker;
    private final Pool<GroupUnitMovement> groupUnitMovementPool;
    private final List<GroupUnitMovement> groupUnitMovements;
    private final List<GroupUnitMovement> completeGroupMovements;

    private final ArrayList<Entity> selectedEntities;

    private final UnitPool unitPool;
    private final BuildingPool buildingPool;

    private Entity entityBeingHovered;
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
        this.entities = new List[Layer.values().length];
        this.removal = new ArrayList<>(100);
        for (Layer layer : Layer.values()) {
            entities[layer.getPriority()] = new ArrayList<>(600);
        }
        this.map = new Map(gameContext.getGameResources(), gameContext.getGameConfigs());
        this.groupUnitMovementPool = new Pool<GroupUnitMovement>() {
            @Override
            protected GroupUnitMovement newObject() {
                return new GroupUnitMovement(map);
            }
        };
        this.groupUnitMovements = new ArrayList<>(20);
        this.completeGroupMovements = new ArrayList<>(20);
        this.selectedEntities = new ArrayList<Entity>(20);
        this.unitPool = new UnitPool(gameContext);
        this.buildingPool = new BuildingPool(gameContext);
        this.mouseHoverLocation = new Vector2();
        this.buildDrawRectangle = new Rectangle();
        this.buildDrawCenter = new Vector2();
        this.connectionRadiusRect = new Rectangle(0f, 0f, 300f, 300f);//Todo: get radius from configs
        this.connectionBuildings = new ArrayList<>();
        this.foundEntities = new ArrayList<>(60);
        for (int i = 0; i <1; i++) {
            Building building = buildingPool.obtain();
            building.setLayer(Layer.GROUND);
            building.setTypeFlags(GameFlags.BUILDING);
            building.setBuildingSpriteAnimator(gameContext.getGameResources().building);
            building.setMinimapDrawable(gameContext.getGameResources().anotherTemple);
            building.setTileCoverageVectors(Arrays.asList(new TileVector(0, 0), new TileVector(-1, 0), new TileVector(-1, 1), new TileVector(0, 1)));
            building.setPosition(MathUtils.random(2, 24), MathUtils.random(2, 24));
            building.setComponentFlags(GameFlags.MARKET);
            building.setStateFlags(GameFlags.SELECTABLE);
            building.setHp(MathUtils.random(0, 400));
            building.setConnectionRadius(700);
            //building.setHp(0);
            building.setMaxHp(400);
            addEntity(building);
        }
        for (int i = 0; i < 10; i++) {
            Unit unit = unitPool.obtain();
            unit.setTypeFlags(GameFlags.UNIT);
            unit.setUnitSpriteAnimator(gameContext.getGameResources().man);
            unit.setLayer(Layer.GROUND);
            unit.setFacing(Direction.NE);
            unit.setTileCoverageVectors(Arrays.asList(new TileVector(0, 0)));
            unit.setPosition(MathUtils.random(0, 10), MathUtils.random(0, 10));
            unit.setIcon(LogicUtils.randomSelect(Arrays.asList(gameContext.getGameResources().normanIcon, gameContext.getGameResources().grokkenIcon,
                    gameContext.getGameResources().mayanIcon, gameContext.getGameResources().greekIcon, gameContext.getGameResources().vikingIcon)));
            unit.setMaxHp(200);
            unit.setHp(MathUtils.random(0, 200));
            unit.setConstruction(MathUtils.random(0, 100));
            unit.setComponentFlags(GameFlags.BUILDER);
            unit.setStateFlags(GameFlags.SELECTABLE | GameFlags.RENDERABLE);

            unit.setBuildables(Arrays.<Buildable>asList(new Buildable() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public Drawable getCursorFiller() {
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
                public MapScanner getMapScanner() {
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
                public int connectionRadius() {
                    return 100;
                }

                @Override
                public Building build() {
                    Building building = buildingPool.obtain();
                    building.setLayer(Layer.GROUND);
                    building.setName("KERANE");
                    building.setTypeFlags(GameFlags.BUILDING);
                    building.setTileCoverageVectors(Arrays.asList(new TileVector(0, 0), new TileVector(-1, 0), new TileVector(-1, 1), new TileVector(0, 1)));
                    building.setIcon(gameContext.getGameResources().anotherTemple);
                    building.setStateFlags(GameFlags.RENDERABLE | GameFlags.SELECTABLE);
                    building.setBuildingSpriteAnimator(gameContext.getGameResources().building);
                    building.setMinimapDrawable(gameContext.getGameResources().anotherTemple);
                    building.setComponentFlags(GameFlags.FORT);
                    building.setMaxHp(50);
                    return building;
                }
            }, new Buildable() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public Drawable getCursorFiller() {
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
                public MapScanner getMapScanner() {
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
                public int connectionRadius() {
                    return 100;
                }

                @Override
                public Building build() {
                    Building building = buildingPool.obtain();
                    building.setLayer(Layer.GROUND);
                    building.setName("KERANE");
                    building.setTypeFlags(GameFlags.BUILDING);
                    building.setTileCoverageVectors(Arrays.asList(new TileVector(0, 0), new TileVector(-1, 0), new TileVector(-1, 1), new TileVector(0, 1)));
                    building.setIcon(gameContext.getGameResources().anotherTemple);
                    building.setStateFlags(GameFlags.RENDERABLE | GameFlags.SELECTABLE);
                    building.setBuildingSpriteAnimator(gameContext.getGameResources().building);
                    building.setMinimapDrawable(gameContext.getGameResources().temple);
                    building.setComponentFlags(GameFlags.FORT);
                    building.setMaxHp(200);
                    return building;
                }
            }, new Buildable() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public Drawable getCursorFiller() {
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
                public MapScanner getMapScanner() {
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
                public int connectionRadius() {
                    return 100;
                }

                @Override
                public Building build() {
                    Building building = buildingPool.obtain();
                    building.setLayer(Layer.GROUND);
                    building.setName("KERANE");
                    building.setTypeFlags(GameFlags.BUILDING);
                    building.setTileCoverageVectors(Arrays.asList(new TileVector(0, 0), new TileVector(-1, 0), new TileVector(-1, 1), new TileVector(0, 1)));
                    building.setIcon(gameContext.getGameResources().anotherTemple);
                    building.setStateFlags(GameFlags.RENDERABLE | GameFlags.SELECTABLE);
                    building.setMinimapDrawable(gameContext.getGameResources().dragonTemple);
                    building.setBuildingSpriteAnimator(gameContext.getGameResources().building);
                    building.setComponentFlags(GameFlags.FORT);
                    building.setMaxHp(100);
                    return building;
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
            for (int j = 0; j
                    < entities[i].size(); j++) {
                map.quadTree.attach(entities[i].get(j));
            }
        }
        for(GroupUnitMovement groupUnitMovement : groupUnitMovements){
            groupUnitMovement.update(deltaTime);
            if(groupUnitMovement.complete()){
                completeGroupMovements.add(groupUnitMovement);
                groupUnitMovementPool.free(groupUnitMovement);
            }
        }
        groupUnitMovements.removeAll(completeGroupMovements);
        completeGroupMovements.clear();
        for (int i = 0; i < Layer.values().length; i++) {
            for (int j = 0; j < entities[i].size(); j++) {
                Entity entity = entities[i].get(j);
                entity.update(deltaTime);
                if (dayPassedFlag)
                    entity.dayPassed(daysPassed);
                if (weekPassedFlag)
                    entity.weekPassed(weeksPassed);
                if (monthPassedFlag)
                    entity.monthPassed(monthsPassed);
                if (yearPassedFlag)
                    entity.yearPassed(yearsPassed);
                if (entity.shouldRemove()) {
                    removal.add(entity);
                    removeEntity(entity);
                }
            }
            entities[i].removeAll(removal);
            removal.clear();
        }
        if (dayPassedFlag)
            dayPassedFlag = false;
        if (weekPassedFlag)
            weekPassedFlag = false;
        if (monthPassedFlag)
            monthPassedFlag = false;
        if (yearPassedFlag)
            yearPassedFlag = false;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        map.render(spriteBatch, shapeRenderer);
        for (int i = 0; i < Layer.values().length; i++) {
            for (int j = 0;
                 j < entities[i].size(); j++) {
                entities[i].get(j).render(spriteBatch, shapeRenderer);
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
            buildingToBuild.getCursorFiller().draw(spriteBatch, buildDrawRectangle.x, buildDrawRectangle.y, buildDrawRectangle.width, buildDrawRectangle.height);
        }
    }

    public void addEntity(Entity entity) {
        entities[entity.getLayer().getPriority()].add(entity);
        int x = entity.tileX();
        int y = entity.tileY();
        for (TileVector tileVector : entity.getTileCoverageVectors()) {
            map.getTileAt(x + tileVector.x, y + tileVector.y).addEntity(entity);
        }
    }

    public void removeEntity(Entity entity) {
        for (Tile tile : entity.getTilesCovered()) {
            tile.removeEntity(entity);
        }
        entities[entity.getLayer().getPriority()].remove(entity);
        if (GameFlags.checkTypeFlag(entity, GameFlags.UNIT)) {
            unitPool.free(entity.asUnit());
        } else if (GameFlags.checkTypeFlag(entity, GameFlags.BUILDING)) {
            buildingPool.free(entity.asBuilding());
        }
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

    public void mouseDrag(Rectangle dragRectangle) {
        deselectEntities(selectedEntities);
        List<Entity> entitiesSelected = map.quadTree.findObjectsWithinRect(dragRectangle);
        filterEntitiesForSelectability(entitiesSelected);
        selectedEntities.clear();
        selectedEntities.addAll(entitiesSelected);
        selectEntities(selectedEntities);
        gameContext.getEventManager()
                .raiseEvent(EventType.ENTITIES_SELECTED)
                .getParams()
                .putParameter(Parameters.Key.SELECTED_ENTITIES, entitiesSelected);
    }

    public void mouseRightClick(Vector2 point) {
        buildingToBuild = null;
        Tile tile = map.findTile(point);
        if (tile.canBeMovedTo()) {
            if(selectedEntities.size()>1){
                GroupUnitMovement  groupUnitMovement = groupUnitMovementPool.obtain();
                groupUnitMovement.moveUnitsTo(getSelectedUnits(),tile);
                groupUnitMovements.add(groupUnitMovement);
                return;
            }
            for (Entity entity : selectedEntities) {
                entity.targetTile(tile);
            }
            return;
        }
//        Entity targetEntity = tile.getEntitiesContained().get(0);
//        for (Entity entity : selectedEntities) {
//            entity.targetEntity(targetEntity);
//        }
    }


    public void mouseLeftClick(Vector2 point) {
        Tile tile = map.findTile(point);
        if (buildingToBuild != null) {
            for (Entity entity : selectedEntities) {
                if (GameFlags.checkComponentFlag(entity, GameFlags.BUILDER)) {
                    entity.asUnit().build(buildingToBuild, tile);
                    gameContext.getEventManager()
                            .raiseEvent(EventType.UNIT_BUILD_ORDER_GIVEN);
                }
            }
            buildingToBuild = null;
            return;
        }
        deselectEntities(selectedEntities);
        List<Entity> entitiesSelected = tile.getEntitiesContained();
        filterEntitiesForSelectability(entitiesSelected);
        selectedEntities.clear();
        selectedEntities.addAll(entitiesSelected);
        selectEntities(selectedEntities);
        gameContext.getEventManager()
                .raiseEvent(EventType.ENTITIES_SELECTED)
                .getParams()
                .putParameter(Parameters.Key.SELECTED_ENTITIES, entitiesSelected);
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
                map.quadTree.findObjectsWithinRect(connectionRadiusRect, foundEntities);
                for (Entity entity : foundEntities) {
                    if (GameFlags.checkTypeFlag(entity, GameFlags.BUILDING)) {
                        Building building = entity.asBuilding();
                        if (LogicUtils.distance(hoverLocation, building.getCenter())
                                < building.getConnectionRadius()+buildingToBuild.connectionRadius()) {
                            connectionBuildings.add(building);
                        }
                    }
                }
            }
            return;
        }
        for (Entity entity : map.findTile(hoverLocation).getEntitiesContained()) {
            if (GameFlags.checkStateFlag(entity, GameFlags.SELECTABLE)) {
                entityBeingHovered = entity;
                gameContext.getEventManager()
                        .raiseEvent(EventType.ENTITY_HOVERED_OVER)
                        .getParams()
                        .putParameter(Parameters.Key.ENTITY, entity);
                return;
            }
        }
        gameContext.getEventManager()
                .raiseEvent(EventType.ENTITY_HOVERED_OFF);
    }

    private List<Unit> getSelectedUnits(){
        return selectedEntities.stream().map(entity -> entity.asUnit()).collect(Collectors.toList());
    }

    public void setBuildingToBuild(Buildable buildingToBuild) {
        this.buildingToBuild = buildingToBuild;
    }

    public void setTargetTileForSelected(Tile targetTileForSelected) {
        for (Entity entity : selectedEntities) {
            entity.targetTile(targetTileForSelected);
        }
    }

    private void selectEntities(List<Entity> entitiesToSelect) {
        for (Entity entity : entitiesToSelect)
            entity.select();
    }

    private void filterEntitiesForSelectability(List<Entity> entitiesToFilter) {
        entitiesToFilter.removeIf(entity -> !GameFlags.checkStateFlag(entity, GameFlags.SELECTABLE));
    }

    private void deselectEntities(List<Entity> deselectEntities) {
        for (Entity entity : deselectEntities)
            entity.deSelect();
    }

    public Nation getNation(int nationIndex) {
        return null;
    }

    public Player getPlayer(int playerIndex) {
        return null;
    }

    public Buildable getBuildingToBuild() {
        return buildingToBuild;
    }

    @Override
    public void dayPassed(int daysPassed) {
        this.dayPassedFlag = true;
        this.daysPassed = daysPassed;
    }

    @Override
    public void weekPassed(int weeksPassed) {
        this.weekPassedFlag = true;
        this.weeksPassed = weeksPassed;
    }

    @Override
    public void monthPassed(int monthsPassed) {
        this.monthPassedFlag = true;
        this.monthsPassed = monthsPassed;
    }

    @Override
    public void yearPassed(int yearsPassed) {
        this.yearPassedFlag = true;
        this.yearsPassed = yearsPassed;
    }

    public UnitPool getUnitPool() {
        return unitPool;
    }

    public BuildingPool getBuildingPool() {
        return buildingPool;
    }

    public List<Entity>[] getEntities() {
        return entities;
    }
}
