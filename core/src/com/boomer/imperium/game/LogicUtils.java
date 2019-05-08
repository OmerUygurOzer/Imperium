package com.boomer.imperium.game;


import com.badlogic.gdx.math.MathUtils;
import com.boomer.imperium.game.map.Tile;

import java.util.List;

public final class LogicUtils {

    public static double distance(double x1, double y1, double x2,double y2){
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public static double distance(Tile t1 , Tile t2){
        return distance(t1.tileX,t1.tileY,t2.tileX,t2.tileY);
    }

    public static <T> T randomSelect(List<T> selectable){
        return selectable.get(MathUtils.random(0,selectable.size()-1));
    }

    private LogicUtils(){}
}
