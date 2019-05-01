package com.boomer.imperium.game.entities;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.GameWorld;
import com.boomer.imperium.game.Resources;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.configs.GameContextInterface;

public class UnitPool extends Pool<Unit> {

   private GameContextInterface gameContext;

    public UnitPool(GameContextInterface gameContext){
        super(200);
       this.gameContext = gameContext;
    }

    @Override
    protected Unit newObject() {
        return new Unit(gameContext);
    }

}
