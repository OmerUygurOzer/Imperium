package com.boomer.imperium.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.gui.GameGui;
import com.boomer.imperium.game.map.Bound;

public interface Entity extends Renderable,TimedUpdateable,Bound,GameGui.Selectable {
    void setMemoryIndex(int index);
    int getMemoryIndex();
    Layer getLayer();
    int tileX();
    int tileY();
    boolean shouldRemove();
}
