package com.boomer.imperium.game.events;

public final class Conditions {
    public Condition or(Condition... conditions){
        return event -> {
            for(Condition c : conditions){
                if(c.check(event))
                    return true;
            }
            return false;
        };
    }

    public Condition and(Condition... conditions){
        return event -> {
            for(Condition c : conditions){
                if(!c.check(event))
                    return false;
            }
            return true;
        };
    }
    private Conditions(){}
}
