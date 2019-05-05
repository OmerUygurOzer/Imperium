package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.ScreenSensitive;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.configs.GameContext;

public class GuiHolder implements TimedUpdateable, ScreenSensitive, Renderable {

    private final GameContext gameContext;
    private final Viewport gameViewport;
    private int screenHeight;
    private final Rectangle gameScreenBounds; //pixel-wise
    private float virtualXRatio;
    private float virtualYRatio;
    private final GameGui gameGui;
    private final Cursor cursor;

    public GuiHolder(GameContext gameContext, ShapeRenderer shapeRenderer, Viewport gameViewport, SpriteBatch spriteBatch){
        this.gameContext = gameContext;
        this.gameViewport = gameViewport;
        this.gameScreenBounds = new Rectangle();
        this.gameGui = new GameGui(gameContext,this,gameViewport,spriteBatch);
        this.cursor = new Cursor(gameContext,this,shapeRenderer,gameViewport);
    }

    public Cursor getCursor(){
        return cursor;
    }

    public GameGui getGUI(){
        return gameGui;
    }

    Rectangle getGameScreenBounds(){
        return gameScreenBounds;
    }

    float getVirtualXRatio(){
        return virtualXRatio;
    }

    float getVirtualYRatio(){
        return virtualYRatio;
    }

    float getInGameBoundsX(){
        return gameScreenBounds.x * virtualXRatio;
    }

    float getInGameBoundsY(){
        return gameScreenBounds.y * virtualYRatio;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        cursor.render(spriteBatch);
        gameGui.render(spriteBatch);
    }

    @Override
    public void resize(int width, int height) {
        screenHeight = height;
        float gameAspectRatio = gameViewport.getWorldHeight() / gameViewport.getWorldWidth();
        float screenAspectRatio = (float) height / (float) width;
        if (screenAspectRatio <= gameAspectRatio) {
            gameScreenBounds.set((width - gameViewport.getScreenWidth()) / 2f, 0f, gameViewport.getScreenWidth(), gameViewport.getScreenHeight());
        } else {
            gameScreenBounds.set(0, (height - gameViewport.getScreenHeight()) / 2f, gameViewport.getScreenWidth(), gameViewport.getScreenHeight());
        }
        virtualXRatio = gameViewport.getWorldWidth() / (float) gameViewport.getScreenWidth();
        virtualYRatio = gameViewport.getWorldHeight() / (float) gameViewport.getScreenHeight();
        cursor.resize(width,height);
        gameGui.resize(width,height);
    }

    @Override
    public void update(float deltaTime) {
        gameGui.update(deltaTime);
    }
}
