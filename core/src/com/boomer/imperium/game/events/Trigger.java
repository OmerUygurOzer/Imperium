package com.boomer.imperium.game.events;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.GameWorld;

import java.util.ArrayList;
import java.util.List;

public final class Trigger implements Pool.Poolable {

    private GameWorld gameWorld;
    private Condition condition;
    private List<Event> results;

    public Trigger(GameWorld gameWorld){
        this.results = new ArrayList<Event>();
        this.gameWorld = gameWorld;
    }

    private Condition check(){
        return condition;
    }

    public List<Event> results(){
        return results;
    }

    public Trigger addResult(Event event){
        this.results.add(event);
        return this;
    }

    public Trigger setCondition(Condition condition){
        this.condition = condition;
        return this;
    }

    public boolean runEvent(Event event){
        return condition.check(event);
    }

    @Override
    public void reset() {
        this.results.clear();
    }
}
