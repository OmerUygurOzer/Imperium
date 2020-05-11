package com.boomer.imperium.scripts.mirrors;

public enum AttributeType {

    NONE("none"),
    BOOLEAN(AttributeValue.KEY_BOOLEAN),
    INTEGER(AttributeValue.KEY_INTEGER),
    LONG(AttributeValue.KEY_LONG),
    FLOAT(AttributeValue.KEY_FLOAT),
    DOUBLE(AttributeValue.KEY_DOUBLE),
    STRING(AttributeValue.KEY_STRING),
    VECTOR2(Vector2Mirror.VALUE_TYPE),
    VECTOR3(Vector3Mirror.VALUE_TYPE),
    RECT(RectMirror.VALUE_TYPE),
    CIRCLE(CircleMirror.VALUE_TYPE),
    ENTITY(EntityMirror.VALUE_TYPE),
    ENUM(EnumMirror.VALUE_TYPE);

    private String name;

    AttributeType(String name){
        this.name = name;
    }
}
