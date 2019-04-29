package com.boomer.imperium.game.entities;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.GameWorld;
import com.boomer.imperium.game.Resources;
import com.boomer.imperium.game.configs.GameConfigs;

public class UnitPool extends Pool<Unit> {

    private final GameConfigs configs;
    private final Resources resources;
    private final GameWorld gameWorld;

    public UnitPool(GameConfigs gameConfigs, Resources resources, GameWorld gameWorld){
        super(200);
        this.configs = gameConfigs;
        this.resources = resources;
        this.gameWorld = gameWorld;
    }

    @Override
    protected Unit newObject() {
        return new Unit(configs,resources,gameWorld);
    }

}
