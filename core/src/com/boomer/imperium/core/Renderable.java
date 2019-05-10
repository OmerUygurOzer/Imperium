package com.boomer.imperium.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Renderable {
    void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer);
}
