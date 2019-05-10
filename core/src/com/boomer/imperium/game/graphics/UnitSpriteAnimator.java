package com.boomer.imperium.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.entities.units.UnitState;

public class UnitSpriteAnimator {

    private final TextureRegion allMovements[][];

    public UnitSpriteAnimator(TextureRegion[][] textureRegions) {
        this.allMovements = textureRegions;
    }

    public void draw(SpriteBatch spriteBatch, int currentFrame, Rectangle bounds, Direction facing, UnitState state){
        spriteBatch.draw(allMovements[state.getAnimationIndex()+facing.animationIndex][currentFrame],
                bounds.x,bounds.y,bounds.width,bounds.height);
    }



}
