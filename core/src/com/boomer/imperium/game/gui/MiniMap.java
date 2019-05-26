package com.boomer.imperium.game.gui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boomer.imperium.game.Layer;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.events.EventType;
import com.boomer.imperium.game.events.Parameters;
import com.boomer.imperium.game.map.Tile;

public class MiniMap extends Table {

    private final GameContextInterface gameContext;
    private final Vector2 mouseClickLocation;
    private float widthScale;
    private float heightScale;

    public MiniMap(GameContextInterface gameContext) {
        super();
        this.gameContext = gameContext;
        this.mouseClickLocation = new Vector2();
        setTouchable(Touchable.enabled);
        addCaptureListener(createClicklistener());
    }

    private ClickListener createClicklistener(){
        return new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mouseClickLocation.set(x / widthScale,y / heightScale);
                if (button == Input.Buttons.LEFT) {
                    gameContext.getEventManager().raiseEvent(EventType.MOUSE_LEFT_CLICK)
                            .getParams().putParameter(Parameters.Key.MOUSE_LOCATION, mouseClickLocation);
                }
                if (button == Input.Buttons.RIGHT) {
                    gameContext.getEventManager().raiseEvent(EventType.MOUSE_RIGHT_CLICK)
                            .getParams().putParameter(Parameters.Key.MOUSE_LOCATION, mouseClickLocation);
                }
                return true;
            }
        };
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        adjustScale();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (int i = 0; i < gameContext.getGameWorld().map.getTiles().length; i++) {
            adjustMinimapBounds(gameContext.getGameWorld().map.getTiles()[i]);
        }
        for (int i = 0; i < Layer.values().length; i++) {
            for (int j = 0;
                 j < gameContext.getGameWorld().getEntities()[i].size(); j++) {
                adjustMinimapBounds(gameContext.getGameWorld().getEntities()[i].get(j));
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int i = 0; i < gameContext.getGameWorld().map.getTiles().length; i++) {
            gameContext.getGameWorld().map.getTiles()[i].renderOnMinimap(batch, 1);
        }
        for (int i = 0; i < Layer.values().length; i++) {
            for (int j = 0;
                 j < gameContext.getGameWorld().getEntities()[i].size(); j++) {
                gameContext.getGameWorld().getEntities()[i].get(j).renderOnMinimap(batch, 1);
            }
        }

    }

    private void adjustScale() {
        this.widthScale = getWidth() / gameContext.getGameWorld().map.getMapRectangle().width;
        this.heightScale = getHeight() / gameContext.getGameWorld().map.getMapRectangle().height;
    }

    public void adjustMinimapBounds(Entity entity) {
        Rectangle mapBounds = entity.getBounds();
        entity.setMinimapBounds(getX() + mapBounds.x * widthScale, getY() + mapBounds.y * heightScale,
                mapBounds.width * widthScale, mapBounds.height * heightScale);
    }

    public void adjustMinimapBounds(Tile tile) {
        Rectangle mapBounds = tile.getBounds();
        tile.setMinimapBounds(getX() + mapBounds.x * widthScale, getY() + mapBounds.y * heightScale,
                mapBounds.width * widthScale, mapBounds.height * heightScale);
    }


    public float getWidthScale() {
        return widthScale;
    }

    public float getHeightScale() {
        return heightScale;
    }
}
