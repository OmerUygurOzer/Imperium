package com.boomer.imperium.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;

import java.util.Stack;

public final class GameStateManager implements Disposable,ScreenSensitive {

    private Stack<GameState> gameStates;

    public GameStateManager(){
        gameStates = new Stack<GameState>();
    }

    public void pushGameState(GameState gameState){
        if(!gameStates.isEmpty()){
            gameStates.peek().stop();
        }
        Gdx.input.setInputProcessor(gameState);
        gameState.start();
        gameStates.push(gameState);
    }

    public GameState popGameState(){
        return gameStates.pop();
    }

    public GameState getCurrentGameState(){
        return gameStates.peek();
    }

    @Override
    public void dispose() {
        if(!gameStates.isEmpty()){
            gameStates.peek().dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        if(!gameStates.isEmpty()){
            gameStates.peek().resize(width,height);
        }
    }
}
