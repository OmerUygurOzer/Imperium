package com.boomer.imperium.game.configs;

public class GameConfigs {
    public final WorldSize worldSize;
    public final float tileSize;
    public final float tileSizeCross;
    public final float aspectRatio;
    public final float guiWidth = 200f;

    public GameConfigs(float tileSize,float aspectRatio,WorldSize worldSize) {
        this.tileSize = tileSize;
        this.tileSizeCross = (float)Math.sqrt(Math.pow(tileSize,2)+Math.pow(tileSize,2));
        this.aspectRatio = aspectRatio;
        this.worldSize = worldSize;
    }
}
