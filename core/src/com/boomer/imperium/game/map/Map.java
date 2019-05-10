package com.boomer.imperium.game.map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.game.*;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.events.Condition;
import com.boomer.imperium.game.events.Event;
import com.boomer.imperium.game.events.Parameters;

public class Map implements Renderable {

    public static final Condition IS_POINT_WITHIN_MAP = new Condition() {
        @Override
        public boolean check(Event event) {
            return event.getGameContext().getGameWorld().map.mapRectangle.contains(event.getParams().getVector(Parameters.Key.MOUSE_LOCATION));
        }
    };

    public static final Condition IS_RECTANGLE_WITHIN_MAP = new Condition() {
        @Override
        public boolean check(Event event) {
            return event.getGameContext().getGameWorld().map.mapRectangle.overlaps(event.getParams().getRectangle(Parameters.Key.MOUSE_DRAG_RECTANGLE));
        }
    };

    private final GameConfigs configs;
    private final Resources mapResources;
    private final int sizeInTiles;
    private final Tile[] tiles;
    private final Rectangle mapRectangle;
    public final QuadNode<Entity> quadTree;

    public Map(Resources resources, GameConfigs gameConfigs) {
        this.configs = gameConfigs;
        this.mapResources = resources;
        this.sizeInTiles = (int) ((gameConfigs.worldSize.getRadius(gameConfigs) * 2) / gameConfigs.tileSize);
        this.tiles = new Tile[sizeInTiles * sizeInTiles];
        float x, y;
        int itr;
        float mapCenter = gameConfigs.worldSize.getRadius(gameConfigs);
        for (int i = 0; i < sizeInTiles; i++) {
            for (int j = 0; j < sizeInTiles; j++) {
                y = (i * gameConfigs.tileSize) + (gameConfigs.tileSize / 2);
                x = (j * gameConfigs.tileSize) + (gameConfigs.tileSize / 2);

                Sprite sprite = resources.grassland;
                if (LogicUtils.distance(x, y, mapCenter, mapCenter) > mapCenter) {
                    sprite = resources.desert;
                }

                itr = j + (i * sizeInTiles);
                tiles[itr] = new Tile(gameConfigs,sprite,null,x, y);
            }
        }
        this.mapRectangle = new Rectangle(0, 0, gameConfigs.worldSize.getRadius(gameConfigs) * 2, gameConfigs.worldSize.getRadius(gameConfigs) * 2);
        this.quadTree = new QuadNode<Entity>(mapRectangle, 4);
    }

    public Tile getTileAt(int x, int y) {
        if (x < 0 || y < 0 || x >= sizeInTiles || y >= sizeInTiles) {
            return null;
        }
        return tiles[x + (y * sizeInTiles)];
    }

    public Tile findTile(Vector2 point) {
        return getTileAt((int)Math.floor(point.x/configs.tileSize),(int)Math.floor(point.y/configs.tileSize));
    }

    public Tile findTile(float x, float y) {
        return getTileAt((int)Math.floor(x/configs.tileSize),(int)Math.floor(y/configs.tileSize));
    }

    public boolean contains(Vector2 point){
        return mapRectangle.contains(point);
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i].render(spriteBatch,shapeRenderer);
        }
    }
}
