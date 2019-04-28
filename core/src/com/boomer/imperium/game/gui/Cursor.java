package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boomer.imperium.core.ScreenSensitive;
import com.boomer.imperium.game.Bounds;
import com.boomer.imperium.game.Entity;
import com.boomer.imperium.game.Layer;
import com.boomer.imperium.game.configs.GameConfigs;

public class Cursor implements ScreenSensitive {

    private final Viewport gameViewport;
    private final Camera gameCamera;
    private final GameConfigs configs;
    private int screenHeight;
    private final Rectangle gameScreenBounds; //pixel-wise
    private float virtualXRatio;
    private float virtualYRatio;
    private Vector2 gameLocation;
    private Bounds bounds;

    public Cursor(Viewport gameViewport, GameConfigs configs, Sprite sprite) {
        this.gameViewport = gameViewport;
        this.gameCamera = gameViewport.getCamera();
        this.configs = configs;
        this.gameScreenBounds = new Rectangle();
        this.gameLocation = new Vector2();
        this.bounds = new Bounds(0f, 0f, configs.tileSize, configs.tileSize);
    }

    public Vector2 hover(int mouseX, int mouseY) {
        int invertedY = screenHeight - mouseY;
        gameLocation.x = ((mouseX - gameScreenBounds.x) * virtualXRatio)+(gameCamera.position.x-(gameViewport.getWorldWidth()/2f));
        gameLocation.y = ((invertedY - gameScreenBounds.y) * virtualYRatio)+(gameCamera.position.y-(gameViewport.getWorldHeight()/2f));
        bounds.setCenter(gameLocation);
        return gameLocation;
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

    public interface MouseListener {
        void mousePoint(Vector2 point);
        void mouseClicked(Vector2 point);
    }
}
