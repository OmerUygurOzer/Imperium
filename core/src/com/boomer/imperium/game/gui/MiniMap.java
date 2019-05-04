package com.boomer.imperium.game.gui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boomer.imperium.game.map.Map;

public class MiniMap extends Actor {

    private final Map map;

    private Pixmap tilesPixMap;
    private Pixmap mapPixMap;

    public MiniMap(Map map){
        this.map = map;
        //this.tilesPixMap = new Pixmap();
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
