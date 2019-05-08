package com.boomer.imperium.game.entities.buildings;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.game.map.TileVector;

import java.util.List;

public interface Buildable {
    String getName();
    Drawable getCursorFillerSprite();
    Rectangle getCursorFillerRectangle();
    Drawable getUIIcon();
    List<TileVector> getTileCoverage();
    int widthInTiles();
    int heightInTiles();
    Building build();
}
