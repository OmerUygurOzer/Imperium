package com.boomer.imperium.game;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.map.Bound;

public interface Entity extends Renderable,TimedUpdateable,Bound,GameWorld.Selectable,Pool.Poolable {
    void setMemoryIndex(int index);
    int getMemoryIndex();
    Layer getLayer();
    int tileX();
    int tileY();
    void targetTile(Tile tile);
    void receiveDamage(Entity from,int damage);
    boolean shouldRemove();
    void setTypeFlags(int typeFlags);
    int getTypeFlags();
    void setComponentFlags();
    int getComponentFlags();
}
