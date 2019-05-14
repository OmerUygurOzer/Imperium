package com.boomer.imperium.game.configs;

public final class GameConfigs {
    public final WorldSize worldSize;
    public final float tileSize;
    public final float tileSizeCross;
    public final float aspectRatio;
    public final float guiWidth = 400f;
    public final int calculatePathPerTask = 32;
    public final int eventsInitialCapacity = 400;
    public final float secondsPerGameDay = 1f;

    public final float yellowHPBarThreshold = 100f;
    public final float purpeHPBarThreshold = 150f;
    public final String gameStartingDate = "";

    public GameConfigs(float tileSize,float aspectRatio,WorldSize worldSize) {
        this.tileSize = tileSize;
        this.tileSizeCross = (float)Math.sqrt(Math.pow(tileSize,2)+Math.pow(tileSize,2));
        this.aspectRatio = aspectRatio;
        this.worldSize = worldSize;
    }

    public int getMaxUnitHP(){
        return 200;
    }

    public int getMaxUnitCombat(){
        return 100;
    }

    public int getMaxUnitLeadership(){
        return 100;
    }
}
