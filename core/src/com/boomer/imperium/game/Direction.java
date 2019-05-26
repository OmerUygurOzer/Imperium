package com.boomer.imperium.game;

import com.boomer.imperium.game.map.TileVector;

public enum Direction {

    N(0, (float) Math.sin(Math.toRadians(90)), (float) Math.cos(Math.toRadians(90)), 0, 1),
    NE(1, (float) Math.sin(Math.toRadians(45)), (float) Math.cos(Math.toRadians(45)), 1, 1),
    E(2, (float) Math.sin(Math.toRadians(0)), (float) Math.cos(Math.toRadians(0)), 1, 0),
    SE(3, (float) Math.sin(Math.toRadians(315)), (float) Math.cos(Math.toRadians(315)), 1, -1),
    S(4, (float) Math.sin(Math.toRadians(270)), (float) Math.cos(Math.toRadians(270)), 0, -1),
    SW(5, (float) Math.sin(Math.toRadians(225)), (float) Math.cos(Math.toRadians(225)), -1, -1),
    W(6, (float) Math.sin(Math.toRadians(180)), (float) Math.cos(Math.toRadians(180)), -1, 0),
    NW(7, (float) Math.sin(Math.toRadians(135)), (float) Math.cos(Math.toRadians(135)), -1, 1),
    O(4,0f,0f,0,0);//CENTER

    public final float sin;
    public final float cos;
    public final int animationIndex;
    public final TileVector directionVector;

    Direction(int animationIndex, float sin, float cos, int x, int y) {
        this.animationIndex = animationIndex;
        this.sin = sin;
        this.cos = cos;
        this.directionVector = new TileVector(x, y);
    }

    public Direction getReverse() {
        switch (this) {
            case N:
                return S;
            case NE:
                return SW;
            case E:
                return W;
            case SE:
                return NW;
            case S:
                return N;
            case SW:
                return NE;
            case W:
                return E;
            case NW:
                return SE;
        }
        return null;
    }

}
