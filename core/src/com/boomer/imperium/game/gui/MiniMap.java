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


    public float getWidthScale() {
        if(widthScale==0f)
            widthScale = getWidth() / gameContext.getGameWorld().map.getMapRectangle().width;
        return widthScale;
    }

    public float getHeightScale() {
        if(heightScale==0f)
            heightScale = getHeight() / gameContext.getGameWorld().map.getMapRectangle().height;
        return heightScale;
    }

    public float getWidthScale(float mapWidth) {
        if(widthScale==0f)
            widthScale = getWidth() / mapWidth;
        return widthScale;
    }

    public float getHeightScale(float mapHeight) {
        if(heightScale==0f)
            heightScale = getHeight() / mapHeight;
        return heightScale;
    }
}
