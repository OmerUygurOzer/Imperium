package com.boomer.imperium.game;


public class LogicUtils {

    public static double distance(double x1, double y1, double x2,double y2){
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public static double distance(Tile t1 , Tile t2){
        return distance(t1.tileX,t1.tileY,t2.tileX,t2.tileY);
    }



}
