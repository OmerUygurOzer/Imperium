package com.boomer.imperium.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.map.Bound;

public class HPBar implements Renderable {

    private final Bound bound;
    private float curHp;
    private float maxHp;
    private TextureRegion[] allStates;
    private int curThreshold;

    public HPBar(GameContextInterface gameContext, Bound bound) {
        this.bound = bound;
        this.allStates = gameContext.getGameResources().hpBar;
    }


    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.draw(allStates[curThreshold], bound.getBounds().x, bound.getBounds().y + bound.getBounds().height + 10f, bound.getBounds().width, 10f);
    }

    public void setHp(float maxHp, float curHp) {
        if (this.maxHp == maxHp && this.curHp == curHp)
            return;
        this.curHp = curHp;
        this.maxHp = maxHp;
        this.curThreshold = find5PercentThreshold();
    }


    private int find5PercentThreshold() {
        return (int) ((curHp / maxHp) / 0.1f);
    }
}
