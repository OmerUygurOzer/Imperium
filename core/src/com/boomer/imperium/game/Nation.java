package com.boomer.imperium.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.game.entities.buildings.defaults.DefaultBuildings;
import com.boomer.imperium.game.entities.units.UnitBuilders;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;

public class Nation {
    private float defaultMovementSpeed;
    private int   defaultRangeAttackDamage;
    private int   defaultMeleeAttackDamage;
    private int   defaultArmor;
    private float defaultHpRegen;
    private float defaultHp;
    private float defaultMaxHp;
    private float defaultMeleeAttackSpeed;
    private float defaultRangeAttackSpeed;
    private int   defaultRangeAttackRange;
    private float defaultProjectileSpeed;
    private float defaultLeadership;
    private float defaultCombat;
    private float defaultSneak;
    private float defaultResearch;
    private float defaultMining;
    private float defaultManufacturing;
    private float defaultFarming;
    private float defaultArtisanship;
    private float defaultTradesmanship;
    private float defaultConstruction;
    private DefaultBuildings defaultBuildings;
    private UnitBuilders unitBuilders;

    public UnitBuilders getUnitBuilders() {
        return unitBuilders;
    }

    public DefaultBuildings getDefaultBuildings() {
        return defaultBuildings;
    }

    public UnitSpriteAnimator getSpriteAnimator(String unitName){
        return null;
    }

    public Drawable getBuildableImageDrawable(String name){ return null; }

    public float getDefaultMovementSpeed() {
        return defaultMovementSpeed;
    }

    public int getDefaultRangeAttackDamage() {
        return defaultRangeAttackDamage;
    }

    public int getDefaultMeleeAttackDamage() {
        return defaultMeleeAttackDamage;
    }

    public int getDefaultArmor() {
        return defaultArmor;
    }

    public float getDefaultHpRegen() { return defaultHpRegen; }

    public float getDefaultHp() {
        return defaultHp;
    }

    public float getDefaultMaxHp() {
        return defaultMaxHp;
    }

    public float getDefaultMeleeAttackSpeed() {
        return defaultMeleeAttackSpeed;
    }

    public float getDefaultRangeAttackSpeed() {
        return defaultRangeAttackSpeed;
    }

    public int getDefaultRangeAttackRange() {
        return defaultRangeAttackRange;
    }

    public float getDefaultProjectileSpeed() {
        return defaultProjectileSpeed;
    }

    public float getDefaultLeadership() {
        return defaultLeadership;
    }

    public float getDefaultCombat() {
        return defaultCombat;
    }

    public float getDefaultSneak() {
        return defaultSneak;
    }

    public float getDefaultResearch() {
        return defaultResearch;
    }

    public float getDefaultMining() {
        return defaultMining;
    }

    public float getDefaultManufacturing() {
        return defaultManufacturing;
    }

    public float getDefaultFarming() {
        return defaultFarming;
    }

    public float getDefaultArtisanship() {
        return defaultArtisanship;
    }

    public float getDefaultTradesmanship() {
        return defaultTradesmanship;
    }

    public float getDefaultConstruction() { return defaultConstruction; }
}
