package com.boomer.imperium.model.attribute;

import java.util.LinkedHashSet;
import java.util.Set;

public final class AttributeList {

    private final Set<Attribute> value = new LinkedHashSet<Attribute>();

    public AttributeList(){}

    public Set<Attribute> getValue() {
        return value;
    }
}
