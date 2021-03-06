package com.boomer.imperium.game.events;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.buildings.Buildable;
import com.boomer.imperium.game.entities.buildings.Building;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.map.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Parameters {
    public enum Key{
        WIDTH,
        HEIGHT,
        SELECTED_ENTITIES,
        MOUSE_LOCATION,
        MOUSE_DRAG_RECTANGLE,
        BUILDABLE_TO_BUILD,
        UNIT,
        ENTITY,
        FROM_TILE,
        TO_TILE,
        TILE,
        DAYS,
        WEEKS,
        MONTHS,
        YEARS,
        BUILDING,
        BUILDER
    }

    private Map<Key,Object> paramsMap = new HashMap<Key,Object>();

    public Parameters(){}

    public Parameters putParameter(Key key,Object param){
        this.paramsMap.put(key,param);
        return this;
    }

    public Entity getEntity(Key key){
        if(!paramsMap.containsKey(key))
            return null;
        return this.<Entity>castOrNull(paramsMap.get(key));
    }

    public Unit getUnit(Key key){
        if(!paramsMap.containsKey(key))
            return null;
        return this.<Unit>castOrNull(paramsMap.get(key));
    }

    public Building getBuilding(Key key){
        if(!paramsMap.containsKey(key))
            return null;
        return this.<Building>castOrNull(paramsMap.get(key));
    }

    public List<Entity> getEntities(Key key){
        if(!paramsMap.containsKey(key))
            return null;
        return this.<List<Entity>>castOrNull(paramsMap.get(key));
    }

    public Buildable getBuildable(Key key){
        if(!paramsMap.containsKey(key))
            return null;
        return this.<Buildable>castOrNull(paramsMap.get(key));
    }

    public Rectangle getRectangle(Key key){
        if(!paramsMap.containsKey(key))
            return null;
        return this.<Rectangle>castOrNull(paramsMap.get(key));
    }

    public Vector2 getVector(Key key){
        if(!paramsMap.containsKey(key))
            return null;
        return this.<Vector2>castOrNull(paramsMap.get(key));
    }

    public Integer getInt(Key key){
        if(!paramsMap.containsKey(key))
            return null;
        return this.<Integer>castOrNull(paramsMap.get(key));
    }

    public Float getFloat(Key key){
        if(!paramsMap.containsKey(key))
            return null;
        return this.<Float>castOrNull(paramsMap.get(key));
    }

    public Tile getTile(Key key){
        if(!paramsMap.containsKey(key))
            return null;
        return this.<Tile>castOrNull(paramsMap.get(key));
    }

    public void clear(){
        paramsMap.clear();
    }


    private <T> T castOrNull(Object param){
        try{
            return (T)param;
        }catch (Exception e){
            return null;
        }
    }
}
