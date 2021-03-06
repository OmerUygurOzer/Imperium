package com.boomer.imperium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.GameState;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.events.*;
import com.boomer.imperium.game.events.defaults.DefaultActions;
import com.boomer.imperium.game.gui.GuiHolder;
import com.boomer.imperium.game.map.Map;

import javax.script.Invocable;

public final class RunningGame extends GameState {

    private static final int SCREEN_WIDTH_IN_TILES = 20;
    private static final int SCREEN_HEIGHT_IN_TILES = 18;

    private GameContext gameContext;
    private ShapeRenderer shapeRenderer;
    private final GameConfigs configs;
    private float scale;
    private final OrthographicCamera camera;
    private final Viewport viewPort;

    private final EventManager eventManager;
    private final GameWorld gameWorld;
    private final GuiHolder guiHolder;

    private float camX, camY;

    public RunningGame(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, GameConfigs gameConfigs) {
        this.shapeRenderer = shapeRenderer;
        this.configs = gameConfigs;
        this.camera = new OrthographicCamera();
        this.viewPort = new FitViewport(SCREEN_WIDTH_IN_TILES * 64, SCREEN_HEIGHT_IN_TILES * 64, camera);
        this.camera.position.x = viewPort.getWorldWidth() / 2f;
        this.camera.position.y = viewPort.getWorldHeight() / 2f;
        this.gameContext = new GameContext();
        this.gameContext.setGameResources(new Resources());
        this.gameContext.setGameConfigs(gameConfigs);
        this.eventManager = new EventManager(gameContext,
                new Pool<Event>(gameConfigs.eventsInitialCapacity) {
                    @Override
                    protected Event newObject() {
                        return new Event(gameContext);
                    }
                },
                new Pool<Trigger>(gameConfigs.eventsInitialCapacity) {
                    @Override
                    protected Trigger newObject() {
                        return new Trigger();
                    }
                });
        this.guiHolder = new GuiHolder(gameContext,viewPort, spriteBatch);
        this.gameContext.setGameGui(guiHolder.getGUI());
        this.gameContext.setGameCursor(guiHolder.getCursor());
        this.gameWorld = new GameWorld(gameContext);

        this.eventManager.registerTrigger(EventType.MOUSE_LEFT_CLICK)
                .addResult(DefaultActions.MOUSE_LEFT_CLICK_IN_GAME_WORLD)
                .setCondition(Map.IS_POINT_WITHIN_MAP);

        this.eventManager.registerTrigger(EventType.MOUSE_RIGHT_CLICK)
                .addResult(DefaultActions.RESET_CURSOR_TO_STANDARD)
                .setCondition(GameWorld.IS_BUILDING);

        this.eventManager.registerTrigger(EventType.MOUSE_RIGHT_CLICK)
                .addResult(DefaultActions.MOUSE_RIGHT_CLICK_IN_GAME_WORLD)
                .setCondition(Map.IS_POINT_WITHIN_MAP);

        this.eventManager.registerTrigger(EventType.MOUSE_DRAG)
                .addResult(DefaultActions.MOUSE_DRAG_IN_GAME_WORLD)
                .setCondition(Map.IS_RECTANGLE_WITHIN_MAP);

        this.eventManager.registerTrigger(EventType.MOUSE_MOVE)
                .addResult(DefaultActions.MOUSE_HOVER_IN_GAME_WORLD)
                .setCondition(Map.IS_POINT_WITHIN_MAP);

        this.eventManager.registerTrigger(EventType.ENTITY_HOVERED_OVER)
                .addResult(DefaultActions.ADJUST_CURSOR_IN_GUI)
                .setCondition(Trigger.ALWAYS_RUN);

        this.eventManager.registerTrigger(EventType.ENTITY_HOVERED_OFF)
                .addResult(DefaultActions.RESET_CURSOR_TO_STANDARD)
                .setCondition(Trigger.ALWAYS_RUN);

        this.eventManager.registerTrigger(EventType.ENTITIES_SELECTED)
                .addResult(DefaultActions.SELECT_ENTITIES_IN_GUI)
                .setCondition(Trigger.ALWAYS_RUN);

        this.eventManager.registerTrigger(EventType.ENTITIES_DESELECTED)
                .addResult(DefaultActions.DESELECTED_ENTITIES_IN_GUI)
                .setCondition(Trigger.ALWAYS_RUN);

        this.eventManager.registerTrigger(EventType.BUILDABLE_PICKED)
                .addResult(DefaultActions.PICK_BUILDABLE_IN_GAME_WORLD)
                .addResult(DefaultActions.ADJUST_CURSOR_FOR_BUILDING)
                .setCondition(Trigger.ALWAYS_RUN);

        this.eventManager.registerTrigger(EventType.UNIT_BUILD_ORDER_GIVEN)
                .addResult(DefaultActions.RESET_CURSOR_TO_STANDARD)
                .setCondition(Trigger.ALWAYS_RUN);

        this.eventManager.registerTrigger(EventType.DAY_PASSED)
                .addResult(DefaultActions.DAYS_PASSED)
                .setCondition(Trigger.ALWAYS_RUN);

        this.eventManager.registerTrigger(EventType.WEEK_PASSED)
                .addResult(DefaultActions.WEEKS_PASSED)
                .setCondition(Trigger.ALWAYS_RUN);

        this.eventManager.registerTrigger(EventType.MONTH_PASSED)
                .addResult(DefaultActions.MONTH_PASSED)
                .setCondition(Trigger.ALWAYS_RUN);

        this.eventManager.registerTrigger(EventType.YEAR_PASSED)
                .addResult(DefaultActions.YEARS_PASSED)
                .setCondition(Trigger.ALWAYS_RUN);

        this.eventManager.registerTrigger(EventType.SCREEN_RESIZE)
                .addResult(DefaultActions.ADJUST_GAMEWORLD_MINIMAP)
                .setCondition(Trigger.ALWAYS_RUN);


        addProcessor(guiHolder.getGUI());
        addProcessor(guiHolder.getCursor());
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camY = 4f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camY = -4f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camX = -4f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camX = 4f;
        }
        camera.translate(camX, camY);
        camera.update();
        float delta = Gdx.graphics.getDeltaTime();
        gameWorld.update(delta);
        guiHolder.update(delta);
        eventManager.update(delta);
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        viewPort.apply();
        gameWorld.render(spriteBatch,shapeRenderer);
        spriteBatch.end();
        guiHolder.render(spriteBatch,shapeRenderer);
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
        guiHolder.resize(width, height);
        scale = (float) width / (gameContext.getGameConfigs().worldSize.getRadius(configs) * 2f);
//        System.out.println(width);
//        System.out.println(height);
//        System.out.println(worldSize.getRadius(configs) * 2f);
//        System.out.println(scale);
        eventManager.raiseEvent(EventType.SCREEN_RESIZE)
                .getParams()
                .putParameter(Parameters.Key.WIDTH,width)
                .putParameter(Parameters.Key.HEIGHT,height);
    }

    @Override
    public void dispose() {
        gameContext.getGameResources().dispose();
    }

    @Override
    public boolean isOverLay() {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.W || keycode == Input.Keys.S) {
            camY = 0f;
        }
        if (keycode == Input.Keys.A || keycode == Input.Keys.D) {
            camX = 0f;
        }

        return false;
    }

}
