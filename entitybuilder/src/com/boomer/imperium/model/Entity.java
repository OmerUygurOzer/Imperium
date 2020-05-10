package com.boomer.imperium.model;

import com.google.common.collect.ImmutableList;
import com.boomer.imperium.model.attribute.Attribute;

import java.util.ArrayList;
import java.util.List;

public final class Entity {

    private String parent;
    private final String name;
    private final List<Attribute> attributes;

    public Entity(String name){
        this.name = name;
        this.attributes = new ArrayList<Attribute>();
    }

    public Entity(String parent,String name){
        this.parent = parent;
        this.name = name;
        this.attributes = new ArrayList<Attribute>();
    }

    public Entity(String name, ImmutableList<Attribute> attributes){
        this.name = name;
        this.attributes = new ArrayList<Attribute>(attributes);
    }

    public String getName() {
        return name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return name;
    }
}
