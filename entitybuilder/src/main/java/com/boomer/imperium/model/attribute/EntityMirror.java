package com.boomer.imperium.model.attribute;

public final class EntityMirror {

    private static final String KEY_TYPE = "type";
    private static final String KEY_NAME = "entity_name";
    static final String VALUE_TYPE = "entity";

    private String entityName;

    public EntityMirror(String entityName) {
        this.entityName = entityName;
    }

    public EntityMirror(EntityMirror entityMirror) {
        this.entityName = entityMirror.entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entityName='" + entityName + '\'' +
                '}';
    }
}
