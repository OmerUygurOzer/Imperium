package com.boomer.imperium.game.entities.units;

import com.boomer.imperium.game.map.Tile;
import com.boomer.imperium.game.entities.Entity;

public class UnitOrders {
    private Entity targetEntity;
    private Tile targetTile;
    private Unit unit;

    public UnitOrders(Unit unit){
        this.unit = unit;
    }

    public void setTargetEntity(Entity entity){
        this.targetTile = null;
        this.targetEntity = entity;
    }

    public void setTargetTile(Tile targetTile){
        this.targetEntity = null;
        this.targetTile = targetTile;
    }

    public Tile getDestinationTile(){
        if(targetTile!=null)
            return targetTile;
        return targetEntity.getTilesCovered().get(0);
    }

    public Unit getUnit() {
        return unit;
    }

    public void clear(){
        targetEntity = null;
        targetTile = null;
    }

}
