package com.boomer.imperium.game;

public enum Direction {
    N(0,(float)Math.sin(Math.toRadians(90)),(float)Math.cos(Math.toRadians(90))),
    NE(1,(float)Math.sin(Math.toRadians(45)),(float)Math.cos(Math.toRadians(45))),
    E(2,(float)Math.sin(Math.toRadians(0)),(float)Math.cos(Math.toRadians(0))),
    SE(3,(float)Math.sin(Math.toRadians(315)),(float)Math.cos(Math.toRadians(315))),
    S(4,(float)Math.sin(Math.toRadians(270)),(float)Math.cos(Math.toRadians(270))),
    SW(5,(float)Math.sin(Math.toRadians(225)),(float)Math.cos(Math.toRadians(225))),
    W(6,(float)Math.sin(Math.toRadians(180)),(float)Math.cos(Math.toRadians(180))),
    NW(7,(float)Math.sin(Math.toRadians(135)),(float)Math.cos(Math.toRadians(135)));

    public final float sin;
    public final float cos;
    public final int animationIndex;

    Direction(int animationIndex, float sin, float cos){
        this.animationIndex = animationIndex;
        this.sin = sin;
        this.cos = cos;
    }

}
