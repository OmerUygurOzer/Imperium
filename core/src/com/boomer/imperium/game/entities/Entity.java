package com.boomer.imperium.game.entities;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Layer;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.resources.GameResource;
import com.boomer.imperium.game.gui.MiniMapEntity;
import com.boomer.imperium.game.players.Nation;
import com.boomer.imperium.game.players.Player;
import com.boomer.imperium.game.entities.buildings.Building;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.events.GameCalendarTracker;
import com.boomer.imperium.game.map.Bound;
import com.boomer.imperium.game.map.Tile;
import com.boomer.imperium.game.map.TileVector;

import java.util.List;

public interface Entity extends Renderable,TimedUpdateable,Bound,Pool.Poolable,GameCalendarTracker.Listener
        ,MiniMapEntity {

    static void adjustEntityBounds(List<Tile> tilesCovered, Entity entity, GameContextInterface gameContext, int tileX, int tileY) {
        tilesCovered.clear();
        Tile tile = gameContext.getGameWorld().map.getTileAt(tileX, tileY);
        float x = tile.bounds.x, y = tile.bounds.y, maxX = 0f, maxY = 0f;
        for (TileVector tileVector : entity.getTileCoverageVectors()) {
            tile = gameContext.getGameWorld().map.getTileAt(tileX + tileVector.x, tileY + tileVector.y);
            tilesCovered.add(tile);
            x = Math.min(x, tile.bounds.x);
            y = Math.min(y, tile.bounds.y);
            maxX = Math.max(maxX, tile.bounds.x + tile.bounds.width);
            maxY = Math.max(maxY, tile.bounds.y + tile.bounds.height);
        }
        entity.getBounds().set(x, y, maxX - x, maxY - y);
    }

    void targetTile(Tile tile);
    void targetEntity(Entity entity);

    void setMemoryIndex(int index);
    int getMemoryIndex();

    int tileX();
    int tileY();
    List<Tile> getTilesCovered();
    void setTilesCovered(List<Tile> tilesCovered);

    List<TileVector> getTileCoverageVectors();
    void setTileCoverageVectors(List<TileVector> tileCoverageVectors);
    void receiveDamage(int damage);
    void setPosition(int tileX, int tileY);
    boolean shouldRemove();
    void select();
    void deSelect();
    Layer getLayer();
    void setLayer(Layer layer);

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
    GameResource asResource();
}
