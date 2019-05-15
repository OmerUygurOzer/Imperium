package com.boomer.imperium.game.configs;

import com.boomer.imperium.game.GameWorld;
import com.boomer.imperium.game.Resources;
import com.boomer.imperium.game.events.EventManager;
import com.boomer.imperium.game.gui.GameCursor;
import com.boomer.imperium.game.gui.GameGui;

public final class GameContext implements GameContextInterface {

    private GameWorld gameWorld;
    private GameGui gameGui;
    private GameCursor gameCursor;
    private EventManager eventManager;
    private GameConfigs gameConfigs;
    private Resources gameResources;


    @Override
    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public GameGui getGameGui() {
        return gameGui;
    }

    public void setGameGui(GameGui gameGui) {
        this.gameGui = gameGui;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public GameConfigs getGameConfigs() {
        return gameConfigs;
    }

    public void setGameConfigs(GameConfigs gameConfigs) {
        this.gameConfigs = gameConfigs;
    }

    @Override
    public Resources getGameResources() {
        return gameResources;
    }

    @Override
    public GameCursor gameCursor() {
        return gameCursor;
    }

    public void setGameCursor(GameCursor gameCursor){
        this.gameCursor = gameCursor;
    }

    public void setGameResources(Resources gameResources) {
        this.gameResources = gameResources;
    }
}
