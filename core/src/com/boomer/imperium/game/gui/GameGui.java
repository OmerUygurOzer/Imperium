package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.boomer.imperium.game.events.EventType;

import java.util.ArrayList;
import java.util.List;

import static com.boomer.imperium.game.events.Parameters.Key.BUILDABLE_TO_BUILD;

public final class GameGui extends Stage implements TimedUpdateable, ScreenSensitive, Renderable,BuilderTab.Listener {

    private enum State{
        IDLE,
        SELECTED,
        BUILDING
    }

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

    private final Table detailsTabHolder;
    private final ComponentRadioTab componentRadioTab;

    private final ImageButton buildButton;
    private final ImageButton detailsButton;
    private final BuilderTab builderTab;
    private final UnitDetailsTab unitDetailsTab;

    private State state;

    private final List<Entity> selectedEntities;
    private Buildable currentSelectedBuildable;

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
        this.componentRadioTab = new ComponentRadioTab(skin);
        this.miniMapPanel = new Table(skin);
        this.detailsPanel = new Table(skin);
        this.detailsTabHolder = new Table(skin);
        this.miniMap = new MiniMap(gameContext.getGameWorld());
        this.builderTab = new BuilderTab(skin,this);
        this.buildButton = new ImageButton(resources.buildButton);
        this.detailsButton = new ImageButton(resources.townButton);
        this.detailsButton.addListener(getDetailsButtonListener());
        this.buildButton.addListener(getBuildButtonListener());
        this.unitDetailsTab = new UnitDetailsTab(skin);
        this.selectedEntities = new ArrayList<>();
        this.state = State.IDLE;
        setGUIActors();
    }

    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;
    }

    private void setGUIActors() {
        rightSideTable.setDebug(true);
        miniMapPanel.setDebug(true);
        detailsPanel.setDebug(true);
        detailsTabHolder.setDebug(true);

        rightSideTable.add(miniMapPanel).height(Value.percentHeight(0.3f,rightSideTable))
                .expandX()
                .fill()
                .row();

        rightSideTable.add(detailsPanel).height(Value.percentHeight(0.7f,rightSideTable))
                .expandX()
                .fill();

        detailsPanel.add(componentRadioTab)
                .expandX()
                .height(Value.percentHeight(0.10f,detailsPanel))
                .center()
                .row();

        detailsPanel.add(detailsTabHolder)
                .expand()
                .fill()
                .center();



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
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        getViewport().apply();
        draw();
    }


    public void selectedEntities(List<Entity> entities) {
        if(entities.isEmpty()) {
            return;
        }
        state = State.SELECTED;
        this.selectedEntities.clear();
        this.selectedEntities.addAll(entities);
        adjustForEntity(entities.get(0));
    }

    public void selectEntity(Entity entity){
        state = State.SELECTED;
        this.selectedEntities.add(entity);
        adjustForEntity(entity);
    }


    public void entitiesDeSelected() {
        state = State.IDLE;
        setCurrentTrainDetailsTab(unitDetailsTab);
        currentSelectedBuildable = null;
        builderTab.clearBuildables();
        unitDetailsTab.clearUnit();
        componentRadioTab.clearButtons();
    }

    private void adjustForEntity(Entity entity){
        componentRadioTab.clearButtons();
        if(GameFlags.checkTypeFlag(entity,GameFlags.UNIT)){
            Unit unit = entity.asUnit();
            unitDetailsTab.setUnit(unit);
            detailsTabHolder.clear();
            detailsTabHolder
                    .add(unitDetailsTab)
                    .expand()
                    .fill()
                    .center();
            detailsTabHolder.pack();
            componentRadioTab.addComponentButton(detailsButton)
                    .center()
                    .size(65,65)
                    .pad(5);

            if(!unit.getBuildables().isEmpty()){
                builderTab.setBuildables(unit.getBuildables());
                componentRadioTab.addComponentButton(buildButton)
                        .center()
                        .size(65,65)
                        .pad(5);
            }
            return;
        }
        if(GameFlags.checkTypeFlag(entity,GameFlags.BUILDING)){

        }
    }

    private ClickListener getBuildButtonListener(){
        return new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               setCurrentTrainDetailsTab(builderTab);
            }
        };
    }

    private ClickListener getDetailsButtonListener(){
        return new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               setCurrentTrainDetailsTab(unitDetailsTab);
            }
        };
    }

    private void setCurrentTrainDetailsTab(Actor actor){
        detailsTabHolder.clear();
        detailsTabHolder.add(actor)
                .fill()
                .expand()
                .center();
        detailsTabHolder.pack();
    }

    @Override
    public void selectedBuildable(Buildable buildable) {
        state = State.BUILDING;
        eventManager.raiseEvent(EventType.BUILDABLE_PICKED)
                .getParams().putParameter(BUILDABLE_TO_BUILD,buildable);
    }
}
