package com.boomer.imperium.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.configs.GameConfigs;

public class UnitMovement implements TimedUpdateable {

    private static float getMovementLengthForDirection(Direction direction, GameConfigs gameConfigs) {
        switch (direction) {
            case N:
            case E:
            case S:
            case W:
                return gameConfigs.tileSize;
            default:
                return gameConfigs.tileSizeCross;
        }
    }

    private GameConfigs configs;
    private final Vector2 speedVector;
    private float speed;
    private float length;
    private float lengthAccumulated;
    private float timeAccumulated = 0f;

    public UnitMovement(GameConfigs gameConfigs,float tileSize, float secondsPerTile) {
        this.configs = gameConfigs;
        this.speedVector = new Vector2(0f, 0f);
        this.speed = tileSize / (1000f * secondsPerTile);
    }

    public void setDirection(Direction facing) { //seconds per tile
        this.speedVector.x = facing.cos;
        this.speedVector.y = facing.sin;
        this.setLength(getMovementLengthForDirection(facing,configs));
        this.lengthAccumulated = 0f;
    }

    public void setLength(float length) {
        this.length = length;
        this.lengthAccumulated = 0;
    }

    public float updateBounds(Unit unit) {
        unit.bounds.x = unit.bounds.x + speedVector.x;
        unit.bounds.y = unit.bounds.y + speedVector.y;
        return lengthAccumulated / length;
    }

    @Override
    public void update(float deltaTime) {
        timeAccumulated = timeAccumulated + deltaTime;
        if (timeAccumulated >= speed)
            lengthAccumulated = lengthAccumulated + 1f;
    }

}
