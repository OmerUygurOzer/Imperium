package com.boomer.imperium.game.entities.buildings;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.game.map.TileVector;

import java.util.List;

public interface Buildable {
    String getName();
    Drawable getCursorFillerSprite();
    Drawable getUIIcon();
    List<TileVector> getTileCoverage();
    List<Integer> getConnectableComponents();
    int widthInTiles();
    int heightInTiles();
    Building build();
}
