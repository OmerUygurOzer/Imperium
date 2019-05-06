package com.boomer.imperium.game.entities;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Layer;
import com.boomer.imperium.game.Nation;
import com.boomer.imperium.game.Player;
import com.boomer.imperium.game.entities.buildings.Building;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.map.Bound;

public interface Entity extends Renderable,TimedUpdateable,Bound,Pool.Poolable {
    void setMemoryIndex(int index);
    int getMemoryIndex();

    int tileX();
    int tileY();
    void receiveDamage(int damage);
    void setPosition(int tileX, int tileY);
    boolean shouldRemove();
    void select();
    void deSelect();
    Layer getLayer();
    void setLayer(Layer layer);
    void setCenterInTiles(int centerInTiles);
    int getCenterInTiles();

    Player getPlayer();
    void setPlayer(Player player);
    Nation getNation();
    void setNation(Nation nation);
    String getName();
    void setName(String name);
    Drawable getIcon();
    void setIcon(Drawable drawable);


    int getTypeFlags();
    int getComponentFlags();
    int getStateFlags();
    void setTypeFlags(int typeFlags);
    void setComponentFlags(int componentFlags);
    void setStateFlags(int stateFlags);

    Unit asUnit();
    Doodad asDoodad();
    Building asBuilding();
    Projectile asProjectile();
    Town asTown();
}
