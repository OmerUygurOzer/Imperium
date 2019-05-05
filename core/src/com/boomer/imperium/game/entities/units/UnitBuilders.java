package com.boomer.imperium.game.entities.units;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.Nation;
import com.boomer.imperium.game.Player;
import com.boomer.imperium.game.configs.GameConfigs;

import java.util.Arrays;

import static com.boomer.imperium.game.GameFlags.*;

public class UnitBuilders {

    private final Pool<Unit> unitPool;
    private final GameConfigs gameConfigs;
    private final Nation nation;

    public UnitBuilders(Pool<Unit> unitPool, GameConfigs gameConfigs, Nation nation){
        this.unitPool = unitPool;
        this.gameConfigs = gameConfigs;
        this.nation = nation;
    }

   private void setBasics(Unit unit,Nation nation){
        unit.setTypeFlags(UNIT);
        unit.setMaxHp(nation.getDefaultMaxHp());
        unit.setHp(nation.getDefaultHp());
        unit.setHpRegen(nation.getDefaultHpRegen());
        unit.setMovementSpeed(nation.getDefaultMovementSpeed());
   }

   public Unit createMeleeSoldier(){
        Unit soldier = unitPool.obtain();
        setBasics(soldier,nation);
        soldier.setComponentFlags(MELEE_ATTACK | ARMOR);
        soldier.setStateFlags(RENDERABLE | SELECTABLE | MOVABLE);
        soldier.setNation(nation);
        soldier.setArmor(nation.getDefaultArmor());
        soldier.setCombat(nation.getDefaultCombat());
        soldier.setLeadership(nation.getDefaultLeadership());
        soldier.setMeleeAttackDamage(nation.getDefaultMeleeAttackDamage());
        soldier.setMeleeAttackSpeed(nation.getDefaultMeleeAttackSpeed());
        soldier.setBuildables(Arrays.asList(nation.getDefaultBuildings().FORT));
        soldier.setUnitSpriteAnimator(nation.getSpriteAnimator("Melee Soldier"));
        return soldier;
    }

    public Unit createRangedSoldier(){
        Unit soldier = unitPool.obtain();
        soldier.setComponentFlags(RANGE_ATTACK | ARMOR);
        soldier.setStateFlags(RENDERABLE | SELECTABLE | MOVABLE);
        setBasics(soldier,nation);
        soldier.setNation(nation);
        soldier.setArmor(nation.getDefaultArmor());
        soldier.setCombat(nation.getDefaultCombat());
        soldier.setLeadership(nation.getDefaultLeadership());
        soldier.setRangeAttackDamage(nation.getDefaultRangeAttackDamage());
        soldier.setRangeAttackSpeed(nation.getDefaultRangeAttackSpeed());
        soldier.setRangeAttackRange(nation.getDefaultRangeAttackRange());
        soldier.setProjectileSpeed(nation.getDefaultProjectileSpeed());
        soldier.setBuildables(Arrays.asList(nation.getDefaultBuildings().FORT));
        soldier.setUnitSpriteAnimator(nation.getSpriteAnimator("Ranged Soldier"));
        return soldier;
    }

    public Unit createBuilder(){
        Unit builder = unitPool.obtain();
        builder.setComponentFlags(BUILDER);
        builder.setStateFlags(RENDERABLE | SELECTABLE | MOVABLE);
        setBasics(builder,nation);
        builder.setConstruction(nation.getDefaultConstruction());
        builder.setBuildables(nation.getDefaultBuildings().ALL);
        builder.setUnitSpriteAnimator(nation.getSpriteAnimator("Builder"));
        return builder;
    }


}
