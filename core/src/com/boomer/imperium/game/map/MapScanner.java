package com.boomer.imperium.game.map;

import java.util.List;

public interface MapScanner {
    boolean scan(Map map,int tileX,int tileY,List<TileVector> tileVectorList);
}
