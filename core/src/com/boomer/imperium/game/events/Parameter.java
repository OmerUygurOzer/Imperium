package com.boomer.imperium.game.events;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Parameter {
    public int intVal = 0;
    public float floatVal = 0f;
    public final Rectangle rectVal = new Rectangle();
    public final Vector2 vectVal = new Vector2();

    public void setRectVal(Rectangle rectVal){
        this.rectVal.set(rectVal);
    }

    public void setVectVal(Vector2 vectVal){
        this.vectVal.set(vectVal);
    }

    public void setIntVal(int val){
        this.intVal = val;
    }

    public void setFloatVal(float val){
        this.floatVal = val;
    }
}
