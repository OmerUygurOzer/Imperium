package com.boomer.imperium.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.boomer.imperium.game.Bounds;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.entities.UnitState;

public class UnitSpriteAnimator {

    private final TextureRegion allMovements[][];

    public UnitSpriteAnimator(TextureRegion[][] textureRegions) {
        this.allMovements = textureRegions;
    }

    public void draw(SpriteBatch spriteBatch, int currentFrame, Bounds bounds, Direction facing, UnitState state){
        spriteBatch.draw(allMovements[state.animationIndex+facing.animationIndex][currentFrame],
                bounds.center.x-(bounds.width/2),bounds.center.y-(bounds.height/2),bounds.width,bounds.height);
    }



}
