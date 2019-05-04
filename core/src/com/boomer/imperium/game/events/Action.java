package com.boomer.imperium.game.events;

import com.boomer.imperium.game.configs.GameContextInterface;

public interface Action {
    void perform(GameContextInterface gameContext,Parameter[] parameters);
}
