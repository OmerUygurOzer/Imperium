package com.boomer.imperium.game.gui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.ScreenSensitive;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.events.EventManager;
import com.boomer.imperium.game.events.EventType;

public class Cursor implements ScreenSensitive, InputProcessor, Renderable {

    private final ShapeRenderer shapeRenderer;
    private final Viewport gameViewport;
    private final Camera gameCamera;
    private final GameContext gameContext;
    private int screenHeight;
    private final Rectangle gameScreenBounds; //pixel-wise
    private float virtualXRatio;
    private float virtualYRatio;
    private final Vector2 gameLocation;
    private final Rectangle bounds;
    private boolean isDragging;
    private final Rectangle dragRectangle;
    private final Vector2 dragStart;

    public Cursor(GameContext gameContext, ShapeRenderer shapeRenderer, Viewport gameViewport) {
        this.gameContext = gameContext;
        this.shapeRenderer = shapeRenderer;
        this.gameViewport = gameViewport;
        this.gameCamera = gameViewport.getCamera();
        this.gameScreenBounds = new Rectangle();
        this.gameLocation = new Vector2();
        this.bounds = new Rectangle(0f, 0f, gameContext.getGameConfigs().tileSize, gameContext.getGameConfigs().tileSize);
        this.dragRectangle = new Rectangle();
        this.dragStart = new Vector2();
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
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    private void hover(int x, int y) {
        int invertedY = screenHeight - y;
        gameLocation.x = ((x - gameScreenBounds.x) * virtualXRatio) + (gameCamera.position.x - (gameViewport.getWorldWidth() / 2f));
        gameLocation.y = ((invertedY - gameScreenBounds.y) * virtualYRatio) + (gameCamera.position.y - (gameViewport.getWorldHeight() / 2f));
        bounds.setCenter(gameLocation);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        hover(screenX, screenY);
        if (button == Input.Buttons.LEFT) {
            gameContext.getEventManager().raiseEvent(EventType.MOUSE_LEFT_CLICK)
                    .setParams(0, gameLocation.x)
                    .setParams(1, gameLocation.y);
            return true;
        }
        if (button == Input.Buttons.RIGHT) {
            gameContext.getEventManager().raiseEvent(EventType.MOUSE_RIGHT_CLICK)
                    .setParams(0, gameLocation.x)
                    .setParams(1, gameLocation.y);
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        hover(screenX, screenY);
        if (isDragging) {
            isDragging = false;
            gameContext.getEventManager().raiseEvent(EventType.MOUSE_DRAG)
                    .setParams(0, dragRectangle.x)
                    .setParams(1, dragRectangle.y)
                    .setParams(2, dragRectangle.width)
                    .setParams(3, dragRectangle.height);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        hover(screenX, screenY);
        if (!isDragging) {
            isDragging = true;
            dragStart.x = gameLocation.x;
            dragStart.y = gameLocation.y;
        } else {
            dragRectangle.x = Math.min(gameLocation.x, dragStart.x);
            dragRectangle.y = Math.min(gameLocation.y, dragStart.y);
            dragRectangle.width = Math.abs(gameLocation.x - dragStart.x);
            dragRectangle.height = Math.abs(gameLocation.y - dragStart.y);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        hover(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (isDragging) {
            shapeRenderer.setProjectionMatrix(gameCamera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(dragRectangle.x, dragRectangle.y, dragRectangle.width, dragRectangle.height);
            shapeRenderer.end();
        }
    }


}
