package com.boomer.imperium.scripts.mirrors;

public final class AttributeTypeWrapper{

    private AttributeType attributeType;

    public AttributeTypeWrapper(){
        this.attributeType = AttributeType.NONE;
    }

    public AttributeTypeWrapper(AttributeType attributeType){ this.attributeType = attributeType; }

    public AttributeTypeWrapper(AttributeTypeWrapper attributeTypeWrapper){
        this.attributeType = attributeTypeWrapper.attributeType; }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeTypeWrapper that = (AttributeTypeWrapper) o;

        return attributeType == that.attributeType;
    }

    @Override
    public int hashCode() {
        return attributeType.hashCode();
    }

    @Override
    public String toString() {
        return attributeType.toString();
    }
}
