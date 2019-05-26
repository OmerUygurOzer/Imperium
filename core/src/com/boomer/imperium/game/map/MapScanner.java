package com.boomer.imperium.game.map;

import java.util.List;

public interface MapScanner {
    boolean scan(Map map,List<TileVector> tileVectorList);
}
