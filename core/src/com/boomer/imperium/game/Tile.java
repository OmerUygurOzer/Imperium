package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boomer.imperium.game.configs.GameConfigs;

import java.util.ArrayList;

public class Tile implements Entity {

    public final Bounds bounds;
    private final Sprite tileSprite;
    private final ArrayList<Entity> entitiesContained;
    public final int tileX,tileY;
    public boolean isPassable;
    public boolean isVacant;
    public boolean isBeingLeft;

    public Tile(GameConfigs gameConfigs, Sprite sprite,float posX, float posY) {
        this.tileSprite = sprite;
        this.entitiesContained = new ArrayList<Entity>();
        this.tileX = (int)Math.floor(posX/gameConfigs.tileSize);
        this.tileY = (int)Math.floor(posY/gameConfigs.tileSize);
        this.bounds = new Bounds(posX,posY,gameConfigs.tileSize,gameConfigs.tileSize);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(tileSprite, bounds.center.x - (bounds.width / 2), bounds.center.y - (bounds.height / 2), bounds.width, bounds.height);
    }

    public ArrayList<Entity> getEntitiesContained(){
        return entitiesContained;
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

    @Override
    public Bounds getBounds() {
        return bounds;
    }

    @Override
    public void select() {

    }

    @Override
    public void deSelect() {

    }
}
