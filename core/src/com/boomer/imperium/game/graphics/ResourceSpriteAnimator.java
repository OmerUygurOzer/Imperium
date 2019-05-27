package com.boomer.imperium.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.boomer.imperium.game.entities.resources.ResourceDepletionLevel;

public class ResourceSpriteAnimator {

    private final TextureRegion allRegions[][];

    public ResourceSpriteAnimator(TextureRegion[][] textureRegions) {
        this.allRegions = textureRegions;
    }

    public void draw(SpriteBatch spriteBatch, int currentFrame, Rectangle bounds, ResourceDepletionLevel state){
        spriteBatch.draw(allRegions[state.getAnimationIndex()][currentFrame],
                bounds.x,bounds.y,bounds.width,bounds.height);
    }

}
