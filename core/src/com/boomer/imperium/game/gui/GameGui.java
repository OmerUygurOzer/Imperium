package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.ScreenSensitive;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.events.EventManager;

import java.util.List;

public class GameGui extends Stage implements TimedUpdateable, ScreenSensitive, Renderable {

    private final float guiWidth;
    private final Viewport gameViewport;
    private final Rectangle gameBounds;
    private final Rectangle leftSideGUIBounds;
    private final Rectangle rightSideGUIBounds;
    private final Skin skin;

    private EventManager eventManager;

    private final Table leftSideTable;
    private final Table rightSideTable;

    private final Table detailsPanel;
    private final Table miniMapPanel;
    private final MiniMap miniMap;


    public GameGui(GameContext gameContext, Viewport gameViewport, SpriteBatch spriteBatch) {
        super(new ExtendViewport(gameViewport.getWorldWidth(), gameViewport.getWorldHeight(), new OrthographicCamera()), spriteBatch);
        gameContext.setGameGui(this);
        this.guiWidth = gameContext.getGameConfigs().guiWidth;
        this.gameViewport = gameViewport;
        getViewport().apply();
        this.gameBounds = new Rectangle();
        this.leftSideGUIBounds = new Rectangle();
        this.rightSideGUIBounds = new Rectangle();
        this.skin = gameContext.getGameResources().skin;
        this.leftSideTable = new Table(skin);
        this.rightSideTable = new Table(skin);
        this.miniMapPanel = new Table(skin);
        this.detailsPanel = new Table(skin);
        this.miniMap = new MiniMap(gameContext.getGameWorld().map);
        setGUIActors();
    }

    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;
    }

    private void setGUIActors() {
        rightSideTable.add(miniMapPanel).height(Value.percentHeight(0.3f,rightSideTable)).row();
        rightSideTable.add(detailsPanel).height(Value.percentHeight(0.7f,rightSideTable));

        TextButton textButton = new TextButton("GAME",skin);
        miniMapPanel.add(textButton);

        TextButton textButton1 = new TextButton("GAME2",skin);
        detailsPanel.add(textButton1).expand().row();

        TextButton textButton3 = new TextButton("TEST",skin);
        detailsPanel.add(textButton3).bottom();//.align(Align.bottom);

        addActor(leftSideTable);
        addActor(rightSideTable);
    }

    public void updateGUIActors() {
        leftSideTable.setBounds(leftSideGUIBounds.x, leftSideGUIBounds.y, leftSideGUIBounds.width, leftSideGUIBounds.height);
        rightSideTable.setBounds(rightSideGUIBounds.x,rightSideGUIBounds.y,rightSideGUIBounds.width,rightSideGUIBounds.height);
    }

    @Override
    public void update(float deltaTime) {
        act(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height);
        getCamera().position.x = getViewport().getWorldWidth() / 2f;
        getCamera().position.y = getViewport().getWorldHeight() / 2f;
        getCamera().update();
        float gameLeftX = (getViewport().getWorldWidth() / 2f) - (gameViewport.getWorldWidth() / 2f);
        float gameRightX = (getViewport().getWorldWidth() / 2f) + (gameViewport.getWorldWidth() / 2f);
        leftSideGUIBounds.set(Math.max(0f, gameLeftX - guiWidth), 0f, guiWidth, gameViewport.getWorldHeight());
        rightSideGUIBounds.set(Math.min(gameRightX, getViewport().getWorldWidth() - guiWidth), 0f, guiWidth, gameViewport.getWorldHeight());
        updateGUIActors();
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        getViewport().apply();
        draw();
    }


    public void selectedEntities(List<Entity> entities) {

    }


    public void entitiesDeSelected() {

    }
}
