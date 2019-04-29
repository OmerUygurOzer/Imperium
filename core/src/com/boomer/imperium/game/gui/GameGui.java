package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.ScreenSensitive;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Entity;
import com.boomer.imperium.game.GameWorld;
import com.boomer.imperium.game.Resources;
import com.boomer.imperium.game.configs.GameConfigs;

import java.util.List;

public class GameGui extends Stage implements TimedUpdateable,ScreenSensitive,Renderable,GameWorld.EntitySelectionListener {

    private final float guiWidth;
    private final Viewport gameViewport;
    private final Rectangle gameBounds;
    private final Rectangle leftSideGUIBounds;
    private final Rectangle rightSideGUIBounds;
    private final Skin skin;

    private final Table leftSideTable;
    private final Table rightSideTable;

    private final Sprite test;

    public GameGui(GameConfigs gameConfigs, Viewport gameViewport , SpriteBatch spriteBatch, Resources resources){
        super(new ExtendViewport(gameViewport.getWorldWidth(),gameViewport.getWorldHeight(), new OrthographicCamera()),spriteBatch);
        this.guiWidth = gameConfigs.guiWidth;
        this.gameViewport = gameViewport;
        getViewport().apply();
        this.gameBounds = new Rectangle();
        this.leftSideGUIBounds = new Rectangle();
        this.rightSideGUIBounds = new Rectangle();
        this.skin = resources.skin;
        this.leftSideTable = new Table(skin);
        this.rightSideTable = new Table(skin);
        this.test = new Sprite(resources.desert);
        setGUIActors();
    }

    private void setGUIActors(){
        addActor(leftSideTable);
        TextButton startButton = new TextButton("START GAME",skin);
        TextButton endButton = new TextButton("END GAME",skin);

        leftSideTable.row();
        leftSideTable.add(startButton);
        leftSideTable.row();
        leftSideTable.add(endButton);
    }

    public void updateGUIActors(int width,int height){
        leftSideTable.setBounds(leftSideGUIBounds.x,leftSideGUIBounds.y,leftSideGUIBounds.width,leftSideGUIBounds.height);
    }

    @Override
    public void update(float deltaTime) {
        act(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width,height);
        getCamera().position.x = getViewport().getWorldWidth()/2f;
        getCamera().position.y = getViewport().getWorldHeight()/2f;
        getCamera().update();
        float gameLeftX = (getViewport().getWorldWidth()/2f)-(gameViewport.getWorldWidth()/2f);
        float gameRightX = (getViewport().getWorldWidth()/2f)+(gameViewport.getWorldWidth()/2f);
        leftSideGUIBounds.set(Math.max(0f,gameLeftX-guiWidth),0f,guiWidth,gameViewport.getWorldHeight());
        rightSideGUIBounds.set(Math.min(gameRightX,getViewport().getWorldWidth()-guiWidth),0f,guiWidth,gameViewport.getWorldHeight());
        updateGUIActors(width,height);
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        getViewport().apply();
        draw();
    }

    @Override
    public void selectedEntities(List<Entity> entities) {

    }

    @Override
    public void entitiesDeSelected() {

    }
}
