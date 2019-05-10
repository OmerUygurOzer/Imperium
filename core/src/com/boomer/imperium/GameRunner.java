package com.boomer.imperium;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.boomer.imperium.core.GameStateManager;
import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.RunningGame;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.WorldSize;

public class GameRunner extends ApplicationAdapter {

    public enum Mode{
        GAME,
        EDITOR
    }

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private GameStateManager gameStateManager;
    private final Mode mode;

    public GameRunner(Mode mode){
        this.mode = mode;
    }

    @Override
    public void create() {
//        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.gameStateManager = new GameStateManager();
        this.gameStateManager.pushGameState(new RunningGame(batch,shapeRenderer,new GameConfigs(64f,0f,WorldSize.SMALL)));
    }

    @Override
    public void render() {
        gameStateManager.getCurrentGameState().update();
        gameStateManager.getCurrentGameState().render(batch,shapeRenderer);
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
