package com.boomer.imperium.game.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.entities.Entity;

import java.util.ArrayList;

public class Tile implements Renderable {

    public final Rectangle bounds;
    private final Sprite tileSprite;
    private final Color minimapColor;
    private final ArrayList<Entity> entitiesContained;
    public final int tileX,tileY;
    public boolean isPassable = true;
    public boolean isVacant = true;
    private final Vector2 center;

    public Tile(GameConfigs gameConfigs, Sprite sprite, Color minimapColor ,float posX, float posY) {
        this.tileSprite = sprite;
        this.minimapColor = minimapColor;
        this.entitiesContained = new ArrayList<Entity>();
        this.tileX = (int)Math.floor(posX/gameConfigs.tileSize);
        this.tileY = (int)Math.floor(posY/gameConfigs.tileSize);
        this.bounds = new Rectangle(posX-(gameConfigs.tileSize/2f),posY-(gameConfigs.tileSize/2f),gameConfigs.tileSize,gameConfigs.tileSize);
        this.center = new Vector2();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.draw(tileSprite, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public ArrayList<Entity> getEntitiesContained(){
        return entitiesContained;
    }

    public void addEntity(Entity entity){
        entitiesContained.add(entity);
        if(!GameFlags.checkStateFlag(entity,GameFlags.NO_ROOM))
            isVacant = false;
    }

    public void removeEntity(Entity entity){
        entitiesContained.remove(entity);
        if(!GameFlags.checkStateFlag(entity,GameFlags.NO_ROOM))
            isVacant = true;
    }

    public Vector2 getCenter(){
        return bounds.getCenter(center);
    }

    public boolean canBeMovedTo(){
        return isPassable && isVacant;
    }

    public Color getMiniMapColor(){
        return minimapColor;
    }

}
