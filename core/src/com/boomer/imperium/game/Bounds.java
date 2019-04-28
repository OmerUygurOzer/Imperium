package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bounds {
    public final Vector2 center;
    public final float width;
    public final float height;
    public final Rectangle boundRectangle;
    public Bounds(float x, float y, float width, float height){
        this.center = new Vector2(x,y);
        this.width = width;
        this.height = height;
        this.boundRectangle = new Rectangle(x-(width/2),y-(height/2),width,height);
    }
    public void add(Vector2 vector2){
        this.center.add(vector2);
        this.boundRectangle.setCenter(this.center);
    }
    public void setCenterTile(Tile tile){
        this.center.x = tile.bounds.center.x;
        this.center.y = tile.bounds.center.y;
        this.boundRectangle.setCenter(center);
    }
    public void setCenter(Vector2 vector2){
        this.center.x = vector2.x;
        this.center.y = vector2.y;
        this.boundRectangle.setCenter(center);
    }
}
