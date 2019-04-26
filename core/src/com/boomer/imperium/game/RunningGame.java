package com.boomer.imperium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.GameState;

public class RunningGame extends GameState {

    private static final int WIDTH = 960;
    private static final int HEIGHT = 960;

    private float scale;
    private OrthographicCamera camera;
    private Viewport viewPort;
    private GameWorld gameWorld;
    private Resources resources;
    private WorldSize worldSize;

    float camX,camY;

    public RunningGame() {
        worldSize = WorldSize.MEDIUM;
        camera = new OrthographicCamera();
        viewPort = new ExtendViewport(WIDTH, HEIGHT, camera);
        viewPort.apply();
        resources = new Resources();
        gameWorld = new GameWorld(resources, worldSize);
    }

    @Override
    public void update() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            camY = 5f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            camY = -5f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            camX = -5f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            camX = 5f;
        }
        camera.translate(camX,camY);
        camera.update();
        gameWorld.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        gameWorld.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
        scale = (float) width / ((float) worldSize.getRadius() * 2f);
        System.out.println(width);
        System.out.println(height);
        System.out.println((float) worldSize.getRadius() * 2f);
        System.out.println(scale);
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
        if(keycode==Input.Keys.W || keycode==Input.Keys.S){
            camY=0f;
        }
        if(keycode==Input.Keys.A || keycode==Input.Keys.D){
            camX=0f;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
