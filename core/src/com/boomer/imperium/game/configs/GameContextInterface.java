package com.boomer.imperium.game.configs;

import com.boomer.imperium.game.GameWorld;
import com.boomer.imperium.game.Resources;
import com.boomer.imperium.game.events.EventManager;
import com.boomer.imperium.game.gui.GameCursor;
import com.boomer.imperium.game.gui.GameGui;

public interface GameContextInterface {
    GameConfigs getGameConfigs();
    EventManager getEventManager();
    GameWorld getGameWorld();
    GameGui getGameGui();
    Resources getGameResources();
    GameCursor gameCursor();
}
