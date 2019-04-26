package com.boomer.imperium;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boomer.imperium.core.GameStateManager;
import com.boomer.imperium.game.RunningGame;

public class GameRunner extends ApplicationAdapter {

    private SpriteBatch batch;
    private GameStateManager gameStateManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        gameStateManager.pushGameState(new RunningGame());
        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

    @Override
    public void render() {
        gameStateManager.getCurrentGameState().update();
        gameStateManager.getCurrentGameState().render(batch);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        gameStateManager.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
