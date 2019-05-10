package com.boomer.imperium.game.entities.buildings;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.configs.GameContextInterface;

public final class BuildingPool extends Pool<Building> {
    private GameContextInterface gameContext;

    public BuildingPool(GameContextInterface gameContext){
        super(200);
        this.gameContext = gameContext;
    }

    @Override
    protected Building newObject() {
        return new Building(gameContext);
    }
}
