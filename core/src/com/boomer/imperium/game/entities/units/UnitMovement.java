package com.boomer.imperium.game.entities.units;

import com.badlogic.gdx.math.Vector2;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.GameContextInterface;

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
    private Unit unit;
    private final Vector2 speedVector;
    private float speed;
    private float length;
    private float lengthAccumulated;
    private float timeAccumulated = 0f;

    public UnitMovement(GameContextInterface gameContext, Unit unit, float secondsPerTile) {
        this.unit = unit;
        this.configs = gameContext.getGameConfigs();
        this.speedVector = new Vector2(0f, 0f);
        this.speed = secondsPerTile / configs.tileSize;
        System.out.println(speed);
    }

    public void setDirection(Direction facing) { //seconds per tile
        this.speedVector.x = facing.cos;
        this.speedVector.y = facing.sin;
        this.setLength(getMovementLengthForDirection(facing, configs));
        this.lengthAccumulated = 0f;
        this.timeAccumulated = 0f;
    }

    public void setLength(float length) {
        this.length = length;
        this.lengthAccumulated = 0;
    }

    @Override
    public void update(float deltaTime) {
        timeAccumulated = timeAccumulated + deltaTime;
        if (timeAccumulated >= speed) {
            while (timeAccumulated >= 0) {
                lengthAccumulated = lengthAccumulated + 1f;
                unit.getBounds().x = unit.getBounds().x + speedVector.x;
                unit.getBounds().y = unit.getBounds().y + speedVector.y;
                timeAccumulated -= speed;
            }
        }
    }

    public boolean isComplete() {
        return lengthAccumulated >= length;
    }

    public boolean hasStarted() {
        return lengthAccumulated > 0f;
    }
}
