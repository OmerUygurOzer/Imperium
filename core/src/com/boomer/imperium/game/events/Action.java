package com.boomer.imperium.game.events;

import com.boomer.imperium.game.configs.GameContextInterface;

public interface Action {
    boolean perform(GameContextInterface gameContext,Parameters parameters,float deltaTime);
}
