package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.ScreenSensitive;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Resources;
import com.boomer.imperium.game.configs.GameConfigs;

public class GameGui implements TimedUpdateable,ScreenSensitive,Renderable {

    private final float guiWidth;
    private final Stage stage;
    private final Viewport gameViewport;
    private final Rectangle gameBounds;
    private final Rectangle leftSideGUIBounds;
    private final Rectangle rightSideGUIBounds;
    private final Viewport guiViewport;
    private final OrthographicCamera guiCamera;


    public GameGui(GameConfigs gameConfigs, Viewport gameViewport , SpriteBatch spriteBatch, Resources resources){
        this.guiWidth = gameConfigs.guiWidth;
        this.gameViewport = gameViewport;
        this.guiCamera = new OrthographicCamera();
        this.guiViewport = new ExtendViewport(gameViewport.getWorldWidth(),gameViewport.getWorldHeight(), guiCamera);
        this.stage = new Stage(guiViewport,spriteBatch);
        this.gameBounds = new Rectangle();
        this.leftSideGUIBounds = new Rectangle();
        this.rightSideGUIBounds = new Rectangle();
        stage.addActor(new TextArea());
    }

    @Override
    public void update(float deltaTime) {
        this.stage.act(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        guiViewport.update(width,height);
        guiCamera.position.x = guiViewport.getWorldWidth()/2f;
        guiCamera.position.y = gameViewport.getWorldHeight()/2f;
        guiCamera.update();
        float gameLeftX = (guiViewport.getWorldWidth()/2f)-(gameViewport.getWorldWidth()/2f);
        float gameRightX = (guiViewport.getWorldWidth()/2f)+(gameViewport.getWorldWidth()/2f);
        leftSideGUIBounds.set(Math.max(0f,gameLeftX-guiWidth),0f,guiWidth,gameViewport.getWorldHeight());
        rightSideGUIBounds.set(Math.min(gameRightX,guiViewport.getWorldWidth()-guiWidth),0f,guiWidth,gameViewport.getWorldHeight());
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        stage.draw();
    }
}
