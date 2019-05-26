package com.boomer.imperium.game;

import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.events.Condition;
import com.boomer.imperium.game.events.Event;
import com.boomer.imperium.game.events.Parameters;

public final class GameFlags {

    //Type Flags
    public static final int TILE        = 1;
    public static final int UNIT        = 1 << 1;
    public static final int BUILDING    = 1 << 2;
    public static final int KING        = 1 << 3;
    public static final int TOWN        = 1 << 4;
    public static final int WEAPON      = 1 << 5;
    public static final int SHIP        = 1 << 6;
    public static final int GOD         = 1 << 7;
    public static final int EFFECT      = 1 << 8;
    public static final int PROJECTILE  = 1 << 9;
    public static final int RESOURCE    = 1 << 10;

    //Component Flags
    public static final int MELEE_ATTACK    = 1;
    public static final int RANGE_ATTACK    = 1 << 1;
    public static final int BUILDER         = 1 << 2;
    public static final int RESEARCH        = 1 << 3;
    public static final int MANUFACTURER    = 1 << 4;
    public static final int ARTISAN         = 1 << 5;
    public static final int FARMER          = 1 << 6;
    public static final int CARAVAN         = 1 << 7;
    public static final int TRADE_CARAVAN   = 1 << 8;
    public static final int MINER           = 1 << 9;
    public static final int GENERAL         = 1 << 10;
    public static final int FORT            = 1 << 11;
    public static final int MINE            = 1 << 12;
    public static final int FACTORY         = 1 << 13;
    public static final int GUILD           = 1 << 14;
    public static final int FARM            = 1 << 15;
    public static final int MARKET          = 1 << 16;
    public static final int ACADEMY         = 1 << 17;
    public static final int WEAPON_FACTORY  = 1 << 18;
    public static final int INN             = 1 << 19;
    public static final int TEMPLE          = 1 << 20;
    public static final int CIVILIAN        = 1 << 21;
    public static final int AURA            = 1 << 22;
    public static final int AOE_ATTACK      = 1 << 23;
    public static final int ARMOR           = 1 << 24;
    public static final int LAND_MOVEMENT   = 1 << 25;
    public static final int WATER_MOVEMENT  = 1 << 26;
    public static final int PRAYER          = 1 << 27;

    //State Flags
    public static final int INVULNERABLE    = 1;
    public static final int RENDERABLE      = 1 << 1;
    public static final int SELECTABLE      = 1 << 2;
    public static final int NO_ROOM         = 1 << 3;
    public static final int MOVABLE         = 1 << 4;

    public static final int UNCONTAINED     = RENDERABLE | SELECTABLE | NO_ROOM;
    public static final int CONTAINED       = Integer.MAX_VALUE ^ UNCONTAINED;

    public static boolean checkTypeFlag(Entity entity, int flag){
        return (entity.getTypeFlags() & flag) == flag;
    }

    public static boolean checkComponentFlag(Entity entity, int flag){
        return (entity.getComponentFlags() & flag) == flag;
    }

    public static boolean checkStateFlag(Entity entity, int flag){
        return (entity.getStateFlags() & flag) == flag;
    }

    public static Condition IS_SELECTABLE = new Condition() {
        @Override
        public boolean check(Event event) {
            return checkStateFlag(event.getParams().getUnit(Parameters.Key.UNIT),GameFlags.SELECTABLE);
        }
    };



    private GameFlags(){}
}
