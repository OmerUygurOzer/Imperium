package com.boomer.imperium.model.attribute;

import com.google.common.base.Objects;

public final class Attribute {

    private static final String KEY_VALUE = "value";

    public static Attribute getRawAttribute(Attribute attribute){
        return new Attribute(attribute.name,attribute.attributeTypeWrapper);
    }

    public static Attribute getValuedAttribute(Attribute attribute,AttributeValue attributeValue){
        return new Attribute(attribute.name,attribute.attributeTypeWrapper,attributeValue);
    }

    public static Attribute createRawAttribute(String name,AttributeType attributeType){
        return new Attribute(name,new AttributeTypeWrapper(attributeType));
    }

    public static boolean compareValuedTypeWithRawType(Attribute valuedType,Attribute rawType){
        return Objects.equal(valuedType.name,rawType.name)
                && Objects.equal(valuedType.attributeTypeWrapper.getAttributeType(),
                rawType.attributeTypeWrapper.getAttributeType());
    }

    private final String name;
    private final AttributeTypeWrapper attributeTypeWrapper;
    private final AttributeValue attributeValue;

    public Attribute(String name){
        this.name = name;
        this.attributeTypeWrapper = new AttributeTypeWrapper();
        this.attributeValue = new AttributeValue();
    }

    public Attribute(Attribute attribute){
        this.name = attribute.name;
        this.attributeTypeWrapper = new AttributeTypeWrapper(attribute.attributeTypeWrapper);
        this.attributeValue = new AttributeValue(attribute.attributeValue);
    }

    private Attribute(String name,AttributeTypeWrapper attributeTypeWrapper){
        this.name = name;
        this.attributeTypeWrapper = new AttributeTypeWrapper(attributeTypeWrapper);
        this.attributeValue = new AttributeValue();
    }

    private Attribute(String name,AttributeTypeWrapper attributeTypeWrapper, AttributeValue value){
        this.name = name;
        this.attributeTypeWrapper = new AttributeTypeWrapper(attributeTypeWrapper);
        this.attributeValue = new AttributeValue(value);
    }

    public AttributeType getAttributeType(){
        return attributeTypeWrapper.getAttributeType();
    }

    public AttributeValue getAttributeValue() {
        return attributeValue;
    }

    public String getName() { return name; }

    public void setBooleanValue(Boolean booleanValue) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.BOOLEAN);
        this.attributeValue.setBooleanValue(booleanValue);
    }

    public void setIntegerValue(Integer integerValue) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.INTEGER);
        this.attributeValue.setIntegerValue(integerValue);
    }

    public void setLongValue(Long longValue) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.LONG);
        this.attributeValue.setLongValue(longValue);
    }

    public void setFloatValue(Float floatValue) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.FLOAT);
        this.attributeValue.setFloatValue(floatValue);
    }

    public void setDoubleValue(Double doubleValue) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.DOUBLE);
        this.attributeValue.setDoubleValue(doubleValue);
    }

    public void setStringValue(String stringValue) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.STRING);
        this.attributeValue.setStringValue(stringValue);
    }

    public void setVector2Value(Vector2Mirror vector2Value) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.VECTOR2);
        this.attributeValue.setVector2Value(vector2Value);
    }

    public void setVector3Value(Vector3Mirror vector3Value) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.VECTOR3);
        this.attributeValue.setVector3Value(vector3Value);
    }

    public void setRectValue(RectMirror rectValue) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.RECT);
        this.attributeValue.setRectValue(rectValue);
    }

    public void setCircleValue(CircleMirror circleValue) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.CIRCLE);
        this.attributeValue.setCircleValue(circleValue);
    }

    public void setEntityValue(EntityMirror entityValue) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.ENTITY);
        this.attributeValue.setEntityValue(entityValue);
    }

    public void setEnumValue(EnumMirror enumValue) {
        this.attributeTypeWrapper.setAttributeType(AttributeType.ENUM);
        this.attributeValue.setEnumValue(enumValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (!name.equals(attribute.name)) return false;
        if (!attributeTypeWrapper.equals(attribute.attributeTypeWrapper)) return false;
        return attributeValue.equals(attribute.attributeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name,attributeTypeWrapper,attributeValue);
    }

    @Override
    public String toString() {
        return name + ", type=" + attributeTypeWrapper;
    }
}
