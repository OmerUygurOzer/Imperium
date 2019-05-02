package com.boomer.imperium.game.entities;

import com.boomer.imperium.game.Layer;
import com.boomer.imperium.game.Nation;
import com.boomer.imperium.game.Player;

public final class UnitBuilder {

    private static final UnitBuilder instance = new UnitBuilder();

    public static UnitBuilder newUnit(Unit unit) {
        return instance.setUnit(unit);
    }

    private Unit unit;

    private UnitBuilder() {
    }

    private UnitBuilder setUnit(Unit unit) {
        this.unit = unit;
        this.unit.reset();
        return this;
    }

    public UnitBuilder setNation(Nation nation) {
        this.unit.setNation(nation);
        return this;
    }

    public UnitBuilder setPlayer(Player player) {
        this.unit.setPlayer(player);
        return this;
    }

    public UnitBuilder setMeleeAttackDamage(int meleeDamage) {
        this.unit.setMeleeAttackDamage(meleeDamage);
        return this;
    }

    public UnitBuilder setRangeAttackDamage(int rangeDamage) {
        this.unit.setRangeAttackDamage(rangeDamage);
        return this;
    }

    public UnitBuilder setHpRegen(float regen) {
        this.unit.setHpRegen(regen);
        return this;
    }

    public UnitBuilder setMeleeAttackSpeed(float attackPerSecond) {
        this.unit.setMeleeAttackSpeed(attackPerSecond);
        return this;
    }

    public UnitBuilder setRangeAttackSpeed(float attackPerSecond) {
        this.unit.setRangeAttackSpeed(attackPerSecond);
        return this;
    }

    public UnitBuilder setAttackRange(int attackRange) {
        this.unit.setAttackRange(attackRange);
        return this;
    }

    public UnitBuilder setMaxHp(int maxHp) {
        this.unit.setMaxHp(maxHp);
        return this;
    }

    public UnitBuilder setHp(int hp) {
        this.unit.setHp(hp);
        return this;
    }

    public UnitBuilder setCenterInTiles(int tiles) {
        this.unit.setCenterInTiles(tiles);
        return this;
    }

    public UnitBuilder setRangeAttackAOE(int aoe) {
        this.unit.setAttackAOE(aoe);
        return this;
    }

    public UnitBuilder setProjectileSpeed(float tilePersecond) {
        this.unit.setProjectileSpeed(tilePersecond);
        return this;
    }

    public UnitBuilder setMovementSpeed(float tilePerSecond) {
        this.unit.setMovementSpeed(tilePerSecond);
        return this;
    }

    public UnitBuilder setArmor(int armor) {
        this.unit.setArmor(armor);
        return this;
    }

    public UnitBuilder setPosition(int tileX, int tileY) {
        this.unit.setPosition(tileX, tileY);
        return this;
    }

    public UnitBuilder setLayer(Layer layer) {
        this.unit.setLayer(layer);
        return this;
    }

    public UnitBuilder addTypeFlag(int flag) {
        this.unit.setTypeFlags(unit.getTypeFlags() | flag);
        return this;
    }

    public UnitBuilder addComponentFlag(int flag) {
        this.unit.setComponentFlags(unit.getComponentFlags() | flag);
        return this;
    }

    public UnitBuilder addStateFlag(int flag) {
        this.unit.setStateFlags(unit.getStateFlags() | flag);
        return this;
    }

    public UnitBuilder setTypeFlags(int flags) {
        this.unit.setTypeFlags(flags);
        return this;
    }

    public UnitBuilder setComponentFlags(int flags) {
        this.unit.setComponentFlags(flags);
        return this;
    }

    public UnitBuilder setStateFlags(int flags) {
        this.unit.setStateFlags(flags);
        return this;
    }

    public Unit build() {
        return this.unit;
    }
}
