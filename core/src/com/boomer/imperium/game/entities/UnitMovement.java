package com.boomer.imperium.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.Bounds;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.Tile;

public class UnitMovement implements TimedUpdateable {

    public static void adjustMovementForTarget(UnitMovement unitMovement, Bounds from, Bounds to){
        if(to.center.x>from.center.x && to.center.y>from.center.y){
            unitMovement.setDirection(Direction.NE);
            unitMovement.setLength(Tile.CROSS);
            return;
        }
        if(to.center.x>from.center.x && to.center.y==from.center.y){
            unitMovement.setDirection(Direction.E);
            unitMovement.setLength(Tile.SIZE);
            return;
        }
        if(to.center.x>from.center.x && to.center.y<from.center.y){
            unitMovement.setDirection(Direction.SE);
            unitMovement.setLength(Tile.CROSS);
            return;
        }
        if(to.center.x==from.center.x && to.center.y<from.center.y){
            unitMovement.setDirection(Direction.S);
            unitMovement.setLength(Tile.SIZE);
            return;
        }
        if(to.center.x<from.center.x && to.center.y<from.center.y){
            unitMovement.setDirection(Direction.SW);
            unitMovement.setLength(Tile.CROSS);
            return;
        }
        if(to.center.x<from.center.x && to.center.y==from.center.y){
            unitMovement.setDirection(Direction.W);
            unitMovement.setLength(Tile.SIZE);
            return;
        }
        if(to.center.x<from.center.x && to.center.y>from.center.y){
            unitMovement.setDirection(Direction.NW);
            unitMovement.setLength(Tile.CROSS);
            return;
        }
        if(to.center.x==from.center.x && to.center.y>from.center.y){
            unitMovement.setDirection(Direction.N);
            unitMovement.setLength(Tile.SIZE);
            return;
        }
    }

    private final Vector2 speedVector;
    private float speed;
    private float length;
    private float lengthAccumulated;
    private float timeAccumulated = 0f;

    public UnitMovement(float tileSize,float secondsPerTile){
        this.speedVector = new Vector2(0f,0f);
        this.speed = tileSize/(1000f*secondsPerTile);
    }

    public void setDirection(Direction facing) { //seconds per tile
        this.speedVector.x = facing.cos;
        this.speedVector.y = facing.sin;
    }

    public void setLength(float length){
        this.length = length;
        this.lengthAccumulated = 0;
    }

    public boolean updateBounds(Unit unit){
        unit.bounds.center.add(speedVector);
        return lengthAccumulated>=length;
    }

    @Override
    public void update(float deltaTime) {
        timeAccumulated = timeAccumulated + deltaTime;
        if(timeAccumulated>=speed)
          lengthAccumulated = lengthAccumulated + 1f;
    }

}
