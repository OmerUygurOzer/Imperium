package com.boomer.imperium.game.events;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.configs.GameContextInterface;

public final class Event implements Pool.Poolable {

    private GameContextInterface gameContext;
    private EventType eventType;
    private Parameters parameters;

    public Event(GameContextInterface gameContext) {
        this.gameContext = gameContext;
        this.parameters = new Parameters();

    }

    Event setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Parameters getParams() {
        return parameters;
    }

    public GameContextInterface getGameContext(){ return gameContext; }

    @Override
    public void reset() {
        this.parameters.clear();
        this.eventType = EventType.NULL;
    }
}
