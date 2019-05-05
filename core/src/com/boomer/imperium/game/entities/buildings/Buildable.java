package com.boomer.imperium.game.entities.buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public interface Buildable {
    String getName();
    Drawable getCursorFillerSprite();
    Rectangle getCursorFillerRectangle();
    Drawable getUIIcon();
    Building build();
}
