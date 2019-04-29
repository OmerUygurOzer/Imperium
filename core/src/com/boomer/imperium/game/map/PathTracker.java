package com.boomer.imperium.game.map;

import com.boomer.imperium.game.entities.UnitMovement;

public class PathTracker {

    private final Map map;
    private final Path path;

    public PathTracker(Map map, Path path, UnitMovement unitMovement){
        this.map = map;
        this.path = path;
    }


}
