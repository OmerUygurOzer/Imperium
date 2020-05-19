package com.boomer.imperium.scripts.mirrors;

import java.util.*;

public final class AttributeList {

    private final Map<String,Attribute> value = new LinkedHashMap<>();

    public AttributeList(){}

    public Map<String,Attribute> getValue() {
        return value;
    }

    public Attribute getFirst(){
        for(Attribute attribute: value.values()){
            return attribute;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeList that = (AttributeList) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
