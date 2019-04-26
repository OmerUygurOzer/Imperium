package com.boomer.imperium.game;

import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.TimedUpdateable;

public interface Entity extends Renderable,TimedUpdateable {
    void setMemoryIndex(int index);
    int getMemoryIndex();
    Layer getLayer();
    int tileX();
    int tileY();
    boolean shouldRemove();
}
