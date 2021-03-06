package com.boomer.imperium.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.ScreenSensitive;
import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.events.EventType;
import com.boomer.imperium.game.events.Parameters;

public final class GameCursor implements ScreenSensitive, InputProcessor, Renderable {

    private final GuiHolder guiHolder;
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

    private int lastButtonPressed;

    private Cursor standardCursor;
    private Cursor attackCursor;
    private Cursor enterBuildingCursor;
    private Cursor tradeCursor;
    private Cursor buildCursor;
    private Cursor enterTownCursor;


    public GameCursor(GameContext gameContext, GuiHolder guiHolder, Viewport gameViewport) {
        this.guiHolder = guiHolder;
        this.gameContext = gameContext;
        this.gameViewport = gameViewport;
        this.gameCamera = gameViewport.getCamera();
        this.gameScreenBounds = guiHolder.getGameScreenBounds();
        this.gameLocation = new Vector2();
        this.bounds = new Rectangle(0f, 0f, gameContext.getGameConfigs().tileSize, gameContext.getGameConfigs().tileSize);
        this.dragRectangle = new Rectangle();
        this.dragStart = new Vector2();
        this.standardCursor = Gdx.graphics.newCursor(gameContext.getGameResources().standardCursorPixmap, 0, 0);
        this.attackCursor = Gdx.graphics.newCursor(gameContext.getGameResources().attackCursorPixmap, 0, 0);
        this.enterBuildingCursor = Gdx.graphics.newCursor(gameContext.getGameResources().enterBuildingCursorPixmap, 0, 0);
        this.tradeCursor = Gdx.graphics.newCursor(gameContext.getGameResources().cantTradeCursorPixmap, 0, 0);
        this.buildCursor = Gdx.graphics.newCursor(gameContext.getGameResources().buildCursorPixmap, 31, 31);
        this.enterTownCursor = Gdx.graphics.newCursor(gameContext.getGameResources().enterTownCursorPixmap, 0, 0);
        Gdx.graphics.setCursor(standardCursor);
    }

    @Override
    public void resize(int width, int height) {
        this.screenHeight = height;
        this.virtualXRatio = guiHolder.getVirtualXRatio();
        this.virtualYRatio = guiHolder.getVirtualYRatio();
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
        this.lastButtonPressed = button;
        if (button == Input.Buttons.LEFT) {
            gameContext.getEventManager().raiseEvent(EventType.MOUSE_LEFT_CLICK)
                    .getParams().putParameter(Parameters.Key.MOUSE_LOCATION, gameLocation);
            return true;
        }
        if (button == Input.Buttons.RIGHT) {
            gameContext.getEventManager().raiseEvent(EventType.MOUSE_RIGHT_CLICK)
                    .getParams().putParameter(Parameters.Key.MOUSE_LOCATION, gameLocation);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        hover(screenX, screenY);
        if (isDragging && button == Input.Buttons.LEFT) {
            isDragging = false;
            gameContext.getEventManager().raiseEvent(EventType.MOUSE_DRAG)
                    .getParams().putParameter(Parameters.Key.MOUSE_DRAG_RECTANGLE, dragRectangle);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        hover(screenX, screenY);
        if (!isDragging && lastButtonPressed == Input.Buttons.LEFT) {
            isDragging = true;
            dragStart.x = gameLocation.x;
            dragStart.y = gameLocation.y;
            dragRectangle.width = 0f;
            dragRectangle.height = 0f;
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
        gameContext.getEventManager().raiseEvent(EventType.MOUSE_MOVE)
                .getParams().putParameter(Parameters.Key.MOUSE_LOCATION, gameLocation);

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        if (isDragging) {
            shapeRenderer.setProjectionMatrix(gameCamera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(dragRectangle.x, dragRectangle.y, dragRectangle.width, dragRectangle.height);
            shapeRenderer.end();
            return;
        }
    }

    public void entityHoveredOver(Entity entity) {
        if (!gameContext.getGameGui().getSelectedEntities().isEmpty()) {
            Entity selected = gameContext.getGameGui().getSelectedEntities().get(0);
            if (GameFlags.checkTypeFlag(selected, GameFlags.UNIT)
                    && GameFlags.checkTypeFlag(entity, GameFlags.BUILDING)) { //Todo update this logic
                Gdx.graphics.setCursor(enterBuildingCursor);
            }
        }
    }

    public void building() {
        Gdx.graphics.setCursor(buildCursor);
    }

    public void clearCursor() {
        Gdx.graphics.setCursor(standardCursor);
    }

}
