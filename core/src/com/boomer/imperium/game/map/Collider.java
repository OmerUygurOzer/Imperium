package com.boomer.imperium.game.map;

import com.badlogic.gdx.math.Rectangle;
import com.boomer.imperium.game.entities.units.Unit;

public class Collider {

    private final Unit unit;
    private final Rectangle dynamicRectangle;

    public Collider(Unit unit){
        this.unit = unit;
        this.dynamicRectangle = new Rectangle();
    }

    public void checkForCollision(QuadNode quadNode, float deltaX, float deltaY){
        dynamicRectangle.set(unit.getBounds());
    }

}
