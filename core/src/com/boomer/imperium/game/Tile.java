package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boomer.imperium.game.entities.Unit;

import java.util.LinkedList;

public class Tile implements Entity {

    public static final float SIZE = 64f;
    public static final float CROSS = (float)Math.sqrt(Math.pow(SIZE,2)+Math.pow(SIZE,2));

    public final Bounds bounds;
    private final Sprite tileSprite;
    private final LinkedList<Unit> unitsContained;
    public final int tileX,tileY;
    public boolean isPassable;
    public boolean isVacant;
    public boolean isBeingLeft;

    public Tile(Sprite sprite, int tileX, int tileY,float posX, float posY) {
        this.tileSprite = sprite;
        this.unitsContained = new LinkedList<Unit>();
        this.tileX = tileX;
        this.tileY = tileY;
        this.bounds = new Bounds(posX,posY,Tile.SIZE,Tile.SIZE);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(tileSprite, bounds.center.x - (Tile.SIZE / 2), bounds.center.y - (Tile.SIZE / 2), Tile.SIZE, Tile.SIZE);
    }

    public LinkedList<Unit> getUnitsContained(){
        return unitsContained;
    }

    @Override
    public void setMemoryIndex(int index) {

    }

    @Override
    public int getMemoryIndex() {
        return 0;
    }

    @Override
    public Layer getLayer() {
        return Layer.TILES;
    }

    @Override
    public int tileX() {
        return tileX;
    }

    @Override
    public int tileY() {
        return tileY;
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    public void update(float deltaTime) {

    }
}
