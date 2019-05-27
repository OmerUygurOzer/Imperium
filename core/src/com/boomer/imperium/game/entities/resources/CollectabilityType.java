package com.boomer.imperium.game.entities.resources;

public interface CollectabilityType {
    boolean onTile();
    boolean withinRange();
    int getRange();
}
