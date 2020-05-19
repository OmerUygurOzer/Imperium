package com.boomer.imperium.scripts;

import com.boomer.imperium.scripts.mirrors.AttributeList;
import com.google.common.base.Objects;

public class ScriptMirror {

    private final String name;
    private final String script;
    private final AttributeList attributeList;
    private final Type type;
    private final String className;

    public ScriptMirror(String name, String script, AttributeList attributeList,Type type,String className){
        this.name = name;
        this.script = script;
        this.attributeList = attributeList;
        this.type = type;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getScript() {
        return script;
    }

    public AttributeList getAttributeList() {
        return attributeList;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScriptMirror that = (ScriptMirror) o;

        if (!name.equals(that.name)) return false;
        if (!script.equals(that.script)) return false;
        return attributeList.equals(that.attributeList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name,script,attributeList);
    }

    enum Type{
        PREMADE,
        CUSTOM
    }
}
