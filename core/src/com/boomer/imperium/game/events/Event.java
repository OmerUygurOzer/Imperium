package com.boomer.imperium.game.events;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.GameWorld;
import com.boomer.imperium.game.gui.GameGui;

import java.util.List;

public final class Event implements Pool.Poolable {

    private final GameWorld gameWorld;
    private final GameGui gameGui;
    private EventType eventType;
    private int paramIndex = 0;
    private final Parameter[] params;

    public Event(GameWorld gameWorld, GameGui gameGui) {
        this.gameWorld = gameWorld;
        this.gameGui = gameGui;
        this.params = new Parameter[22];
        for(int i = 0 ; i < 20 ; i++)
            this.params[i] = new Parameter();

    }

    public Event setParams(float value) {
        if (paramIndex == params.length) {
            throw new RuntimeException("MAX allowed params per event exceeded.");
        }
        this.params[paramIndex++].setFloatVal(value);
        return this;
    }

    public Event setParams(int value) {
        if (paramIndex == params.length) {
            throw new RuntimeException("MAX allowed params per event exceeded.");
        }
        this.params[paramIndex++].setIntVal(value);
        return this;
    }

    public Event setParams(Vector2 value) {
        if (paramIndex == params.length) {
            throw new RuntimeException("MAX allowed params per event exceeded.");
        }
        this.params[paramIndex++].setVectVal(value);
        return this;
    }

    public Event setParams(Rectangle value) {
        if (paramIndex == params.length) {
            throw new RuntimeException("MAX allowed params per event exceeded.");
        }
        this.params[paramIndex++].setRectVal(value);
        return this;
    }

    Event setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Parameter[] getParams() {
        return params;
    }

    @Override
    public void reset() {
        this.paramIndex = 0;
        this.eventType = EventType.NULL;
    }

    public void fire() {
        switch (eventType) {
            case MOUSE_RIGHT_CLICK:
                gameWorld.setOrderForSelected(params[0].vectVal);
                gameGui.entitiesDeSelected();
                gameWorld.clearSelection();
                break;
            case MOUSE_LEFT_CLICK:
                gameGui.entitiesDeSelected();
                gameWorld.clearSelection();
                List<Entity> entitiesContained = gameWorld.map.findTile(params[0].vectVal).getEntitiesContained();
                gameWorld.selectEntities(entitiesContained);
                gameGui.selectedEntities(entitiesContained);
                break;
            case MOUSE_DRAG:
                List<Entity> foundEntities = gameWorld.map.quadTree.findObjectsWithinRect(params[0].rectVal);
                gameWorld.selectEntities(foundEntities);
                gameGui.selectedEntities(foundEntities);
                break;
            case UNIT_CREATED:
//                gameWorld.addEntity(
//                        UnitBuilder.newUnit(gameWorld.unitPool.obtain())
//                                .setTypeFlags((int) params[0])
//                                .setComponentFlags((int) params[1])
//                                .setStateFlags((int) params[2])
//                                .setMeleeAttackDamage((int)params[3])
//                                .setRangeAttackDamage((int)params[4])
//                                .setMaxHp((int)params[5])
//                                .setPlayer(gameWorld.getPlayer((int)params[6]))
//                                .setNation(gameWorld.getNation((int)params[7]))
//                                .setCenterInTiles((int)params[8])
//                                .setHp((int)params[9])
//                                .setHpRegen(params[10])
//                                .setMeleeAttackSpeed(params[11])
//                                .setRangeAttackSpeed(params[12])
//                                .setAttackRange((int)params[13])
//                                .setRangeAttackAOE((int)params[14])
//                                .setProjectileSpeed(params[15])
//                                .setMovementSpeed(params[16])
//                                .setArmor((int)params[17])
//                                .setLayer(Layer.values()[(int)params[18]])
//                                .setPosition((int)params[19],(int)params[20])
//                                .build()
//                );

                break;
            case UNIT_SWITCH_TILES:
                break;
            case UNIT_ARRIVED_AT_TILE:
                break;
        }
    }
}
