package com.boomer.imperium.game;

import com.badlogic.gdx.math.Vector2;

public class Bounds {
    public final Vector2 center;
    public final float width;
    public final float height;
    public Bounds(float x, float y, float width, float height){
        this.center = new Vector2(x,y);
        this.width = width;
        this.height = height;
    }
}
