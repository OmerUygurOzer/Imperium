package com.boomer.imperium.game.events;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.Entity;
import com.boomer.imperium.game.GameWorld;
import com.boomer.imperium.game.gui.GameGui;

import java.util.List;

public final class Event implements Pool.Poolable {

    private final GameWorld gameWorld;
    private final GameGui gameGui;
    private final float[] params;
    private EventType eventType;


    public Event(GameWorld gameWorld, GameGui gameGui) {
        this.gameWorld = gameWorld;
        this.gameGui = gameGui;
        this.params = new float[20];
    }

    public Event setParams(int param, float value) {
        this.params[param] = value;
        return this;
    }

    Event setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public EventType getEventType() {
        return eventType;
    }

    public float[] getParams() {
        return params;
    }

    @Override
    public void reset() {
        this.eventType = EventType.NULL;
    }

    public void fire() {
        switch (eventType) {
            case MOUSE_RIGHT_CLICK:
                gameGui.entitiesDeSelected();
                gameWorld.clearSelection();
                break;
            case MOUSE_LEFT_CLICK:
                List<Entity> entitiesContained = gameWorld.map.findTile(params[0], params[1]).getEntitiesContained();
                gameWorld.selectEntities(entitiesContained);
                gameGui.selectedEntities(entitiesContained);
                break;
            case MOUSE_DRAG:
                List<Entity> foundEntities = gameWorld.map.quadTree.findObjectsWithinRect(params[0], params[1], params[2], params[3]);
                gameWorld.selectEntities(foundEntities);
                gameGui.selectedEntities(foundEntities);
                break;
        }
    }
}
