package com.boomer.imperium.game.players;

import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.entities.Town;
import com.boomer.imperium.game.entities.units.Unit;

import java.util.ArrayList;
import java.util.List;

public final class GlobalMetrics {

    //Population
    private final List<Town> towns;
    private int monthlyPopGrowth;

    //Military
    private Unit king;
    private final List<Unit> generals;

    //Economy
    private int currentWealth;
    private int monthlyIncome;

    //Research

    public GlobalMetrics(){
        this.generals = new ArrayList<>();
        this.towns = new ArrayList<>();
    }

    public boolean throneKing(Unit unit){
        if(!GameFlags.checkTypeFlag(unit,GameFlags.KING))
            return false;
        this.king = unit;
        this.generals.add(unit);
        return true;
    }

    public boolean assignGeneral(Unit unit){
        if(!GameFlags.checkComponentFlag(unit,GameFlags.GENERAL)||generals.contains(unit))
            return false;
        this.generals.add(unit);
        return true;
    }

    public void dethroneKing(){
        this.generals.remove(king);
        this.king = null;
    }

    public boolean hasKing(){
        return king==null || king.shouldRemove();
    }

    public Unit getKing() {
        return king;
    }

    public List<Unit> getGenerals() {
        return generals;
    }
}
