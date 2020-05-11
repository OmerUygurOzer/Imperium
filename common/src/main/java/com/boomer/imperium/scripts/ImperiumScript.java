package com.boomer.imperium.scripts;

import com.boomer.imperium.Entity;
import com.boomer.imperium.GameContext;

public abstract class ImperiumScript {
    abstract void initialize(GameContext gameContext, Entity owner);
    protected void update(float deltaTime){
        // Leave empty. Parent should override only if necessary.
    };
    protected void dispose(){
        // Leave empty. Parent should override only if necessary.
    }

}
