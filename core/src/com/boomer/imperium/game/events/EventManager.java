package com.boomer.imperium.game.events;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.GameWorld;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.gui.GameGui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public final class EventManager implements TimedUpdateable {

    private GameContext gameContext;
    private final Pool<Event> eventPool;
    private final Pool<Trigger> triggerPool;
    private final ArrayList<Event> eventsToRun;
    private LinkedHashMap<EventType, LinkedList<Trigger>> triggerMap;

    public EventManager(GameContext gameContext, Pool<Event> eventPool, Pool<Trigger> triggerPool) {
        this.gameContext = gameContext;
        this.gameContext.setEventManager(this);
        this.eventsToRun = new ArrayList<>(gameContext.getGameConfigs().eventsInitialCapacity);
        this.triggerMap = new LinkedHashMap<EventType, LinkedList<Trigger>>();
        for (EventType eventType : EventType.values()) {
            triggerMap.put(eventType, new LinkedList<Trigger>());
        }
        this.eventPool = eventPool;
        this.triggerPool = triggerPool;
    }

    public Event raiseEvent(EventType eventType) {
        Event event = eventPool.obtain();
        eventsToRun.add(event);
        return event.setEventType(eventType);
    }

    public Trigger registerTrigger(EventType eventType) {
        Trigger trigger = triggerPool.obtain();
        triggerMap.get(eventType).add(trigger);
        return trigger;
    }


    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < eventsToRun.size(); i++) {
            Event event = eventsToRun.get(i);
            Iterator<Trigger> triggerIterator = triggerMap.get(event.getEventType()).iterator();
            while (triggerIterator.hasNext()) {
                Trigger trigger = triggerIterator.next();
                if (trigger.runEvent(event)) {
                    for(Action action : trigger.results()){
                       boolean done = action.perform(gameContext,event.getParams(),deltaTime);
                       if(done){
                           triggerPool.free(trigger);
                           triggerIterator.remove();
                       }
                    }

                }
            }
            eventPool.free(event);
        }
        eventsToRun.clear();
    }
}
