package com.boomer.imperium.game.entities.buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public interface Buildable {
    String getName();
    Sprite getCursorFillerSprite();
    Rectangle getCursorFillerRectangle();
    Sprite getUIIcon();
    Building build();
}
