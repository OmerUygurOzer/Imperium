package com.boomer.imperium.game.map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface Bound {
    Rectangle getBounds();
    Vector2 getCenter();
}
