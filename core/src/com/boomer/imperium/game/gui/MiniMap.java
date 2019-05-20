package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boomer.imperium.game.GameWorld;
import com.boomer.imperium.game.map.Map;

public class MiniMap extends Actor {

    private final GameWorld gameWorld;

    private Pixmap tilesPixMap;
    private Pixmap mapPixMap;

    public MiniMap(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        //this.tilesPixMap = new Pixmap();
        //this.mapPixMap = new Pixmap();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }



}
