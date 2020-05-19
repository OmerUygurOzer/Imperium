package com.boomer.imperium;

import com.boomer.imperium.scripts.ScriptMirror;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public final class Entity {

    private String parent;
    private final String name;
    private final List<ScriptMirror> scripts;

    public Entity(String name){
        this.name = name;
        this.scripts = new ArrayList<ScriptMirror>();
    }

    public Entity(String parent,String name){
        this.parent = parent;
        this.name = name;
        this.scripts = new ArrayList<ScriptMirror>();
    }

    public Entity(String name, ImmutableList<ScriptMirror> scripts){
        this.name = name;
        this.scripts = new ArrayList<ScriptMirror>(scripts);
    }

    public String getName() {
        return name;
    }

    public List<ScriptMirror> getScripts() {
        return scripts;
    }

    @Override
    public String toString() {
        return name;
    }
}
