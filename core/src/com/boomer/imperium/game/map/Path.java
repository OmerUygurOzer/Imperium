package com.boomer.imperium.game.map;

import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.configs.GameConfigs;

import java.util.ArrayList;
import java.util.Collections;

public class Path {

    public final ArrayList<Direction> tasks;
    private int currentTask;


    public Path(GameConfigs configs){
        this.tasks = new ArrayList<Direction>(configs.calculatePathPerTask);
        this.currentTask = 0;
    }

    void addTask(Direction direction){
        tasks.add(direction);
    }

    void reverse(){
        Collections.reverse(tasks);
    }

    public boolean getNextOrder(){
        return ++currentTask==tasks.size();
    }


}
