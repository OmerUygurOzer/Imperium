package com.boomer.imperium.game.players;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.game.entities.buildings.defaults.DefaultBuildings;
import com.boomer.imperium.game.entities.units.UnitBuilder;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;

public class Nation {
    private String name;
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

    private int defaultFortMaxHP;
    private int defaultMarketMaxHP;
    private int defaultMineMaxHP;
    private int defaultFactoryMaxHP;
    private int defaultFarmMaxHP;
    private int defaultGuildMaxHP;
    private int defaultInnMaxHP;
    private int defaultUniversityMaxHP;
    private int defaultHarborMaxHP;
    private int defaultWarFactoryMaxHP;
    private int defaultTempleMaxHP;

    private DefaultBuildings defaultBuildings;
    private UnitBuilder unitBuilder;

    public UnitBuilder getUnitBuilder() {
        return unitBuilder;
    }

    public DefaultBuildings getDefaultBuildings() {
        return defaultBuildings;
    }

    public UnitSpriteAnimator getSpriteAnimator(String unitName){
        return null;
    }

    public Drawable getBuildingCursorFiller(String buildingName){return null;}

    public Drawable getBuildableImageDrawable(String buildingName){ return null; }

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

    public String getName() {
        return name;
    }

    public int getDefaultFortMaxHP() {
        return defaultFortMaxHP;
    }

    public int getDefaultMarketMaxHP() {
        return defaultMarketMaxHP;
    }

    public int getDefaultMineMaxHP() {
        return defaultMineMaxHP;
    }

    public int getDefaultFactoryMaxHP() {
        return defaultFactoryMaxHP;
    }

    public int getDefaultFarmMaxHP() {
        return defaultFarmMaxHP;
    }

    public int getDefaultGuildMaxHP() {
        return defaultGuildMaxHP;
    }

    public int getDefaultInnMaxHP() {
        return defaultInnMaxHP;
    }

    public int getDefaultUniversityMaxHP() {
        return defaultUniversityMaxHP;
    }

    public int getDefaultHarborMaxHP() {
        return defaultHarborMaxHP;
    }

    public int getDefaultWarFactoryMaxHP() {
        return defaultWarFactoryMaxHP;
    }

    public int getDefaultTempleMaxHP() {
        return defaultTempleMaxHP;
    }

}
