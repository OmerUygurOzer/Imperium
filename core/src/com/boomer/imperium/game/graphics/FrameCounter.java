package com.boomer.imperium.game.graphics;

import com.boomer.imperium.core.TimedUpdateable;

public class FrameCounter implements TimedUpdateable {
    private final float frameSkipper;
    private final int maxFrames;
    private float timeAccumulated = 0f;
    public int currentFrame;
    public FrameCounter(float framesPerSecond,int maxFrames){
        this.frameSkipper = 1f/framesPerSecond;
        this.maxFrames = maxFrames;
    }

    @Override
    public void update(float deltaTime) {
        timeAccumulated = timeAccumulated + deltaTime;
        if(timeAccumulated>=frameSkipper){
            timeAccumulated = 0f;
            currentFrame++;
        }
        if(currentFrame==maxFrames)
            currentFrame=0;
    }
}
