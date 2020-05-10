package com.boomer.imperium.game.map;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.buildings.Building;
import com.boomer.imperium.game.entities.units.orders.Build;
import com.boomer.imperium.game.gui.MiniMap;
import com.boomer.imperium.game.gui.MiniMapEntity;

import java.util.ArrayList;

public class Tile implements Renderable,MiniMapEntity {

    private final GameConfigs gameConfigs;
    private final MiniMap minimap;
    private final float mapWidth;
    private final float mapHeight;
    public final Rectangle bounds;
    private final Rectangle minimapBounds;
    private final Sprite tileSprite;
    private final Drawable minimapDrawable;
    private final ArrayList<Entity> entitiesContained;
    public final int tileX,tileY;
    public boolean isPassable = true;
    public boolean isVacant = true;
    private final Vector2 center;

    public Tile(MiniMap minimap, GameConfigs gameConfigs, Sprite sprite, Drawable minimapDrawable , float posX, float posY,float mapWidth,float mapHeight) {
        this.minimap = minimap;
        this.gameConfigs = gameConfigs;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileSprite = sprite;
        this.minimapDrawable = minimapDrawable;
        this.entitiesContained = new ArrayList<Entity>();
        this.tileX = (int)Math.floor(posX/gameConfigs.tileSize);
        this.tileY = (int)Math.floor(posY/gameConfigs.tileSize);
        this.bounds = new Rectangle();
        this.minimapBounds = new Rectangle();
        setPosition(posX,posY);
        this.center = new Vector2();
    }

    public void setPosition(float posX, float posY){
        bounds.set(posX-(gameConfigs.tileSize/2f),posY-(gameConfigs.tileSize/2f),gameConfigs.tileSize,gameConfigs.tileSize);
        minimapBounds.set(minimap.getX() + (bounds.x * minimap.getWidthScale(mapWidth)), minimap.getY() + (bounds.y * minimap.getHeightScale(mapHeight)),
                bounds.width * minimap.getWidthScale(mapWidth) ,bounds.height * minimap.getHeightScale(mapHeight));
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
        isVacant = isVacantStill();
    }

    public void removeEntity(Entity entity){
        entitiesContained.remove(entity);
        isVacant = isVacantStill();
    }

    public Vector2 getCenter(){
        return bounds.getCenter(center);
    }

    public boolean canBeMovedTo(){
        return isPassable && isVacant;
    }

    private boolean isVacantStill(){
        for(Entity entity : entitiesContained){
            if(!GameFlags.checkStateFlag(entity,GameFlags.NO_ROOM)){
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (tileX != tile.tileX) return false;
        if (tileY != tile.tileY) return false;
        if (!bounds.equals(tile.bounds)) return false;
        return center.equals(tile.center);
    }

    @Override
    public int hashCode() {
        int result = bounds.hashCode();
        result = 31 * result + tileX;
        result = 31 * result + tileY;
        result = 31 * result + center.hashCode();
        return result;
    }

    @Override
    public void setMinimapDrawable(Drawable minimapDrawable) {

    }

    @Override
    public Drawable getMinimapDrawable() {
        return minimapDrawable;
    }

    @Override
    public Rectangle getMinimapBounds() {
        return minimapBounds;
    }

    @Override
    public void setMinimapBounds(float x, float y, float width, float height) {
        this.minimapBounds.set(x,y,width,height);
    }

    @Override
    public void renderOnMinimap(Batch batch, int parentAlpha) {
        minimapDrawable.draw(batch,minimapBounds.x,minimapBounds.y,minimapBounds.width,minimapBounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
