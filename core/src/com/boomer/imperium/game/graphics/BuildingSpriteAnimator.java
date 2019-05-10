package com.boomer.imperium.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.boomer.imperium.game.entities.buildings.BuildingState;

public class BuildingSpriteAnimator {
    private final TextureRegion allMovements[][];

    public BuildingSpriteAnimator(TextureRegion[][] textureRegions) {
        this.allMovements = textureRegions;
    }

    public void draw(SpriteBatch spriteBatch, int currentFrame, Rectangle bounds, BuildingState state){
        spriteBatch.draw(allMovements[state.getAnimationIndex()][currentFrame],
                bounds.x,bounds.y,bounds.width,bounds.height);
    }
}
