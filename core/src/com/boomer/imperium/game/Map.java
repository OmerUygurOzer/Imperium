package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boomer.imperium.core.Renderable;

public class Map implements Renderable {

    private Resources mapResources;
    private int sizeInTiles;
    private Tile[] tiles;

    public Map(Resources resources, WorldSize worldSize) {
        mapResources = resources;
        sizeInTiles = (int)((worldSize.getRadius() * 2) / Tile.SIZE);
        tiles = new Tile[sizeInTiles * sizeInTiles];
        float x, y;
        int itr;
        int mapCenter = worldSize.getRadius();
        for (int i = 0; i < sizeInTiles; i++) {
            for (int j = 0; j < sizeInTiles; j++) {
                y = (i * Tile.SIZE) + (Tile.SIZE / 2);
                x = (j * Tile.SIZE) + (Tile.SIZE / 2);

                Sprite sprite = resources.grassland;
                if (LogicUtils.distance(x, y, mapCenter, mapCenter) > mapCenter) {
                    sprite = resources.desert;
                }

                itr = j + (i * sizeInTiles);
                tiles[itr] = new Tile(sprite, j, i, x, y);
            }
        }
    }

    public Tile getTileAt(int x, int y) {
        if (x < 0 || y < 0 || x >= sizeInTiles || y >= sizeInTiles) {
            return null;
        }
        return tiles[x + (y * sizeInTiles)];
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i].render(spriteBatch);
        }
    }
}
