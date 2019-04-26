package com.boomer.imperium.core;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Disposable;

public abstract class GameState implements Renderable,Updateable,InputProcessor,Disposable,ScreenSensitive {

    protected boolean isRunning = false;

    public void stop(){
        isRunning = false;
    }

    public void start(){
        isRunning = true;
    }

    public abstract boolean isOverLay();

    public boolean isRunning(){
        return isRunning;
    }
}

