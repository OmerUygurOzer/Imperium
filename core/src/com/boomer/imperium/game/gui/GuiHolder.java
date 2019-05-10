package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.ScreenSensitive;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.entities.buildings.Buildable;

public class GuiHolder implements TimedUpdateable, ScreenSensitive, Renderable{

    private final GameContext gameContext;
    private final Viewport gameViewport;
    private int screenHeight;
    private final Rectangle gameScreenBounds; //pixel-wise
    private float virtualXRatio;
    private float virtualYRatio;
    private final GameGui gameGui;
    private final GameCursor cursor;

    public GuiHolder(GameContext gameContext, Viewport gameViewport, SpriteBatch spriteBatch){
        this.gameContext = gameContext;
        this.gameViewport = gameViewport;
        this.gameScreenBounds = new Rectangle();
        this.gameGui = new GameGui(gameContext,this,gameViewport,spriteBatch);
        this.gameGui.setEventManager(gameContext.getEventManager());
        this.cursor = new GameCursor(gameContext,this,gameViewport);
    }

    public GameCursor getCursor(){
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
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        cursor.render(spriteBatch,shapeRenderer);
        spriteBatch.end();
        gameGui.render(spriteBatch,shapeRenderer);
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
