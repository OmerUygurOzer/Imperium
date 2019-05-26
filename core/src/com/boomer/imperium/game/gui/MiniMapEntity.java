package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public interface MiniMapEntity {
    void setMinimapDrawable(Drawable minimapDrawable);
    Drawable getMinimapDrawable();

    Rectangle getMinimapBounds();
    void setMinimapBounds(float x, float y, float width, float height);

    void renderOnMinimap(Batch batch, int parentAlpha);
}
