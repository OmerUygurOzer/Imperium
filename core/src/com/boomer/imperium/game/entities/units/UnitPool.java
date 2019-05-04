package com.boomer.imperium.game.entities.units;

import com.badlogic.gdx.utils.Pool;
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
