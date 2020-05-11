package com.boomer.imperium.scripts.mirrors;

import com.google.common.base.Objects;

public final class AttributeValue {

    private static final String KEY_TYPE = "type";
    static final String KEY_BOOLEAN = "boolean";
    static final String KEY_INTEGER = "int";
    static final String KEY_LONG = "long";
    static final String KEY_FLOAT = "float";
    static final String KEY_DOUBLE = "double";
    static final String KEY_STRING = "string";

    private Boolean booleanValue;
    private Integer integerValue;
    private Long longValue;
    private Float floatValue;
    private Double doubleValue;
    private String stringValue;
    private Vector2Mirror vector2Value;
    private Vector3Mirror vector3Value;
    private RectMirror rectValue;
    private CircleMirror circleValue;
    private EntityMirror entityValue;
    private EnumMirror enumValue;

    public AttributeValue(){}
    public AttributeValue(AttributeValue attributeValue){
        this.booleanValue = attributeValue.booleanValue;
        this.integerValue = attributeValue.integerValue;
        this.longValue = attributeValue.longValue;
        this.floatValue = attributeValue.floatValue;
        this.doubleValue = attributeValue.doubleValue;
        this.stringValue = attributeValue.stringValue;
        if(attributeValue.vector2Value!=null){ this.vector2Value = new Vector2Mirror(attributeValue.vector2Value);}
        if(attributeValue.vector3Value!=null){  this.vector3Value = new Vector3Mirror(attributeValue.vector3Value);}
        if(attributeValue.rectValue!=null){ this.rectValue = new RectMirror(attributeValue.rectValue);}
        if(attributeValue.circleValue!=null){ this.circleValue = new CircleMirror(attributeValue.circleValue);}
        if(attributeValue.entityValue!=null){ this.entityValue = new EntityMirror(attributeValue.entityValue);}
        if(attributeValue.enumValue!=null){ this.enumValue = new EnumMirror(attributeValue.enumValue);}
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        clearData();
        this.booleanValue = booleanValue;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        clearData();
        this.integerValue = integerValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        clearData();
        this.longValue = longValue;
    }

    public Float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(Float floatValue) {
        clearData();
        this.floatValue = floatValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        clearData();
        this.stringValue = stringValue;
    }

    public Vector2Mirror getVector2Value() {
        return vector2Value;
    }

    public void setVector2Value(Vector2Mirror vector2Value) {
        clearData();
        this.vector2Value = vector2Value;
    }

    public Vector3Mirror getVector3Value() {
        return vector3Value;
    }

    public void setVector3Value(Vector3Mirror vector3Value) {
        clearData();
        this.vector3Value = vector3Value;
    }

    public RectMirror getRectValue() {
        return rectValue;
    }

    public void setRectValue(RectMirror rectValue) {
        clearData();
        this.rectValue = rectValue;
    }

    public CircleMirror getCircleValue() {
        return circleValue;
    }

    public void setCircleValue(CircleMirror circleValue) {
        clearData();
        this.circleValue = circleValue;
    }

    public EntityMirror getEntityValue() {
        return entityValue;
    }

    public void setEntityValue(EntityMirror entityValue) {
        clearData();
        this.entityValue = entityValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        clearData();
        this.doubleValue = doubleValue;
    }

    public EnumMirror getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(EnumMirror enumMirror) {
        clearData();
        this.enumValue = enumMirror;
    }

    private void clearData(){
        booleanValue = null;
        integerValue = null;
        longValue = null;
        floatValue = null;
        doubleValue = null;
        stringValue = null;
        vector2Value = null;
        vector3Value = null;
        rectValue = null;
        circleValue = null;
        entityValue = null;
        enumValue = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeValue that = (AttributeValue) o;

        if (booleanValue != null ? !booleanValue.equals(that.booleanValue) : that.booleanValue != null) return false;
        if (integerValue != null ? !integerValue.equals(that.integerValue) : that.integerValue != null) return false;
        if (longValue != null ? !longValue.equals(that.longValue) : that.longValue != null) return false;
        if (floatValue != null ? !floatValue.equals(that.floatValue) : that.floatValue != null) return false;
        if (doubleValue != null ? !doubleValue.equals(that.doubleValue) : that.doubleValue != null) return false;
        if (stringValue != null ? !stringValue.equals(that.stringValue) : that.stringValue != null) return false;
        if (vector2Value != null ? !vector2Value.equals(that.vector2Value) : that.vector2Value != null) return false;
        if (vector3Value != null ? !vector3Value.equals(that.vector3Value) : that.vector3Value != null) return false;
        if (rectValue != null ? !rectValue.equals(that.rectValue) : that.rectValue != null) return false;
        if (circleValue != null ? !circleValue.equals(that.circleValue) : that.circleValue != null) return false;
        if (entityValue != null ? !entityValue.equals(that.entityValue) : that.entityValue != null) return false;
        return enumValue != null ? enumValue.equals(that.enumValue) : that.enumValue == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                booleanValue,
                integerValue,
                floatValue,
                longValue,
                doubleValue,
                stringValue,
                vector2Value,
                vector3Value,
                rectValue,
                circleValue,
                entityValue,
                enumValue);
    }
}
