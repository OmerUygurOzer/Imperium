package com.boomer.imperium.scripts.mirrors;

import com.boomer.imperium.scripts.ScriptMirror;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class ScriptList {

    private final Map<String,ScriptMirror> value = new LinkedHashMap<>();

    public ScriptList(){}

    public Map<String,ScriptMirror> getValue() {
        return value;
    }

    public ScriptMirror getFirst(){
        for(ScriptMirror scriptMirror: value.values()){
            return scriptMirror;
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
