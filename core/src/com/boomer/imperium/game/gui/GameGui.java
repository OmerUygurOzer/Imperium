package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.ScreenSensitive;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.Resources;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.buildings.Buildable;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.events.EventManager;

import java.util.List;

public class GameGui extends Stage implements TimedUpdateable, ScreenSensitive, Renderable,BuilderTab.Listener {

    private final GuiHolder guiHolder;
    private final Resources resources;
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

    private final Table traitsTabPanel;
    private final ButtonGroup<ImageButton> traitsPanelButtons;
    private final Table traitDetailsPanel;
    private final Table actionButtonsPanel;

    private final Button buildButton;
    private final BuilderTab builderTab;

    public GameGui(GameContext gameContext,GuiHolder guiHolder ,Viewport gameViewport, SpriteBatch spriteBatch) {
        super(new ExtendViewport(gameViewport.getWorldWidth(), gameViewport.getWorldHeight(), new OrthographicCamera()), spriteBatch);
        this.guiHolder = guiHolder;
        this.resources = gameContext.getGameResources();
        gameContext.setGameGui(this);
        this.guiWidth = gameContext.getGameConfigs().guiWidth;
        this.gameViewport = gameViewport;
        getViewport().apply();
        this.gameBounds = guiHolder.getGameScreenBounds();
        this.leftSideGUIBounds = new Rectangle();
        this.rightSideGUIBounds = new Rectangle();
        this.skin = gameContext.getGameResources().skin;
        this.leftSideTable = new Table(skin);
        this.rightSideTable = new Table(skin);
        this.miniMapPanel = new Table(skin);
        this.detailsPanel = new Table(skin);
        this.traitsTabPanel = new Table(skin);
        this.miniMap = new MiniMap(gameContext.getGameWorld());
        this.traitsPanelButtons = new ButtonGroup<ImageButton>();
        this.traitDetailsPanel = new Table(skin);
        this.actionButtonsPanel = new Table(skin);
        this.builderTab = new BuilderTab(skin,this);
        this.buildButton = new ImageButton(resources.buildButton);
        this.buildButton.addListener(getBuildButtonListener());
        setGUIActors();
    }

    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;
    }

    private void setGUIActors() {
        rightSideTable.setDebug(true);
        miniMapPanel.setDebug(true);
        detailsPanel.setDebug(true);
        traitDetailsPanel.setDebug(true);
        traitsTabPanel.setDebug(true);
        actionButtonsPanel.setDebug(true);
        rightSideTable.add(miniMapPanel).height(Value.percentHeight(0.3f,rightSideTable)).expandX().fill().row();
        rightSideTable.add(detailsPanel).height(Value.percentHeight(0.7f,rightSideTable)).expandX().fill();

        detailsPanel.add(traitsTabPanel)
                .fillX()
                .expandX()
                .center()
                .row();

        detailsPanel.add(traitDetailsPanel)
                .fill()
                .expand()
                .row();

        traitDetailsPanel.add(builderTab)
                .fill()
                .expand()
                .top();

        detailsPanel.add(actionButtonsPanel)
                .expandX()
                .fillX()
                .center();

        actionButtonsPanel.add(buildButton)
                .center()
                .size(65,65)
                .pad(5);

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
        leftSideGUIBounds.set(Math.max(0f, gameLeftX - guiWidth), guiHolder.getInGameBoundsY(), guiWidth, gameViewport.getWorldHeight());
        rightSideGUIBounds.set(Math.min(gameRightX, getViewport().getWorldWidth() - guiWidth), guiHolder.getInGameBoundsY() , guiWidth, gameViewport.getWorldHeight());
        updateGUIActors();
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        getViewport().apply();
        draw();
    }


    public void selectedEntities(List<Entity> entities) {
        System.out.println("SELECT");
        if(entities.isEmpty()){
            System.out.println("SELECTED EMPTY");
            return;
        }
        adjustForEntity(entities.get(0));
    }


    public void entitiesDeSelected() {

    }

    private void adjustForEntity(Entity entity){
        if(GameFlags.checkTypeFlag(entity,GameFlags.UNIT)){
            System.out.println("UNIT");
            Unit unit = entity.asUnit();
            builderTab.setBuildables(unit.getBuildables());
            return;
        }
        if(GameFlags.checkTypeFlag(entity,GameFlags.BUILDING)){

        }
    }

    private ClickListener getBuildButtonListener(){
        return new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("BUILD BUTTON CLICKED");
            }
        };
    }

    @Override
    public void selectedBuildable(Buildable buildable) {
        System.out.println("FORT CLICKED");
    }
}
