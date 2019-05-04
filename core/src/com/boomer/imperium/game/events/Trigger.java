package com.boomer.imperium.game.events;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.GameWorld;

import java.util.ArrayList;
import java.util.List;

public final class Trigger implements Pool.Poolable {

    private GameWorld gameWorld;
    private Condition condition;
    private List<Action> results;

    public Trigger(GameWorld gameWorld){
        this.results = new ArrayList<Action>();
        this.gameWorld = gameWorld;
    }

    public List<Action> results(){
        return results;
    }

    public Trigger addResult(Action action){
        this.results.add(action);
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
