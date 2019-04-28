package com.boomer.imperium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.GameState;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.WorldSize;
import com.boomer.imperium.game.gui.Cursor;
import com.boomer.imperium.game.gui.GameGui;

public class RunningGame extends GameState {

    private static final int WIDTH_IN_TILES = 20;
    private static final int HEIGHT_IN_TILES = 18;

    private final GameConfigs configs;
    private float scale;
    private final OrthographicCamera camera;
    private final Viewport viewPort;
    private final GameWorld gameWorld;
    private final Resources resources;
    private final WorldSize worldSize;
    private final GameGui gui;

    private float camX, camY;
    private final Cursor cursor;

    public RunningGame(SpriteBatch spriteBatch, GameConfigs gameConfigs) {
        this.configs = gameConfigs;
        this.worldSize = WorldSize.MEDIUM;
        this.camera = new OrthographicCamera();
        this.viewPort = new FitViewport(WIDTH_IN_TILES * gameConfigs.tileSize, HEIGHT_IN_TILES * gameConfigs.tileSize, camera);
        this.camera.position.x = viewPort.getWorldWidth() / 2f;
        this.camera.position.y = viewPort.getWorldHeight() / 2f;
        this.resources = new Resources();
        this.cursor = new Cursor(viewPort, configs, resources.inGameCursor);
        this.gameWorld = new GameWorld(cursor, resources, gameConfigs);
        this.gui = new GameGui(gameConfigs, viewPort, spriteBatch, resources);
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
        camera.translate(camX,camY);
        camera.update();
        float delta = Gdx.graphics.getDeltaTime();
        gameWorld.update(delta);
        gui.update(delta);
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
        gui.render(spriteBatch);
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
        cursor.resize(width, height);
        gui.resize(width, height);
        scale = (float) width / (worldSize.getRadius(configs) * 2f);
//        System.out.println(width);
//        System.out.println(height);
//        System.out.println(worldSize.getRadius(configs) * 2f);
//        System.out.println(scale);
    }

    @Override
    public void dispose() {
        resources.dispose();
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

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            gameWorld.mouseClicked(cursor.hover(screenX,screenY));
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
