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
import com.boomer.imperium.game.configs.WorldSize;
import com.boomer.imperium.game.events.Event;
import com.boomer.imperium.game.events.EventManager;
import com.boomer.imperium.game.events.Trigger;
import com.boomer.imperium.game.gui.Cursor;
import com.boomer.imperium.game.gui.GameGui;

public final class RunningGame extends GameState {

    private static final int SCREEN_WIDTH_IN_TILES = 20;
    private static final int SCREEN_HEIGHT_IN_TILES = 18;

    private GameContext gameContext;
    private final GameConfigs configs;
    private float scale;
    private final OrthographicCamera camera;
    private final Viewport viewPort;

    private final EventManager eventManager;
    private final GameWorld gameWorld;
    private final GameGui gui;

    private float camX, camY;
    private final Cursor cursor;

    public RunningGame(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, GameConfigs gameConfigs) {
        this.configs = gameConfigs;
        this.camera = new OrthographicCamera();
        this.viewPort = new FitViewport(SCREEN_WIDTH_IN_TILES * gameConfigs.tileSize, SCREEN_HEIGHT_IN_TILES * gameConfigs.tileSize, camera);
        this.camera.position.x = viewPort.getWorldWidth() / 2f;
        this.camera.position.y = viewPort.getWorldHeight() / 2f;
        this.gameContext = new GameContext();
        this.gameContext.setGameResources(new Resources());
        this.gameContext.setGameConfigs(gameConfigs);
        this.gameWorld = new GameWorld(gameContext);
        this.gui = new GameGui(gameContext, viewPort, spriteBatch);
        this.gameContext.setGameGui(gui);
        this.eventManager = new EventManager(gameContext,
                new Pool<Event>(gameConfigs.eventsInitialCapacity) {
                    @Override
                    protected Event newObject() {
                        return new Event(gameWorld, gui);
                    }
                },
                new Pool<Trigger>(gameConfigs.eventsInitialCapacity) {
                    @Override
                    protected Trigger newObject() {
                        return new Trigger(gameWorld);
                    }
                });
        this.cursor = new Cursor(gameContext, shapeRenderer, viewPort);
        addProcessor(gui);
        addProcessor(cursor);
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
        gui.update(delta);
        eventManager.update(delta);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);
        viewPort.apply();
        gameWorld.render(spriteBatch);
        spriteBatch.end();
        cursor.render(spriteBatch);
        gui.render(spriteBatch);
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
        cursor.resize(width, height);
        gui.resize(width, height);
        scale = (float) width / (gameContext.getGameConfigs().worldSize.getRadius(configs) * 2f);
//        System.out.println(width);
//        System.out.println(height);
//        System.out.println(worldSize.getRadius(configs) * 2f);
//        System.out.println(scale);
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
