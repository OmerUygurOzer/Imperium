package com.boomer.imperium.game.events;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.GameWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Trigger implements Pool.Poolable {

    public static final Condition ALWAYS_RUN = new Condition() {
        @Override
        public boolean check(Event event) {
            return true;
        }
    };

    private Condition condition;
    private List<Action> results;

    public Trigger(){
        this.results = new ArrayList<Action>();
    }

    public Trigger(Condition condition,List<Action> results){
        this.condition = condition;
        this.results = results;
    }


    public Trigger(Condition condition,Action... results){
        this.condition = condition;
        this.results = Arrays.asList(results);
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
