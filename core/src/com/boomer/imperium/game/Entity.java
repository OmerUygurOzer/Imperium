package com.boomer.imperium.game;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.core.Renderable;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.map.Bound;

public interface Entity extends Renderable,TimedUpdateable,Bound,Pool.Poolable {
    void setMemoryIndex(int index);
    int getMemoryIndex();

    int tileX();
    int tileY();
    void targetTile(Tile tile);
    void receiveDamage(int damage);
    void setPosition(int tileX, int tileY);
    boolean shouldRemove();
    void select();
    void deSelect();

    void setTypeFlags(int typeFlags);
    void setMeleeAttackDamage(int damage);
    void setRangeAttackDamage(int damange);
    void setMaxHp(int maxHp);
    void setComponentFlags(int componentFlags);
    void setStateFlags(int stateFlags);
    void setPlayer(Player player);
    void setNation(Nation nation);
    void setCenterInTiles(int tileCount);
    void setHp(int hp);
    void setHpRegen(float regenPerSecond);
    void setMeleeAttackSpeed(float attackPerSecond);
    void setRangeAttackSpeed(float attackPerSecond);
    void setAttackRange(int range);
    void setAttackAOE(int aoe);
    void setProjectileSpeed(float tilePersecond);
    void setMovementSpeed(float tilePerSecond);
    void setArmor(int armor);
    void setLayer(Layer layer);

    int getTypeFlags();
    int getComponentFlags();
    int getStateFlags();
    int getMeleeAttackDamage();
    int getRangeAttackDamage();
    Player getPlayer();
    Nation getNation();
    int getCenterInTiles();
    int getHp();
    int getMaxHp();
    float getHpRegen();
    float getMeleeAttackSpeed();
    float getRangeAttackSpeed();
    int getAttackRange();
    int getAttackAOE();
    float getProjectileSpeed();
    float getMovementSpeed();
    int getArmor();
    Layer getLayer();

}
