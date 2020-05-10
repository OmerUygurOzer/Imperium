package com.boomer.imperium.model.attribute;

public class EnumMirror {

    static final String VALUE_TYPE = "enum";

    private String name;
    private String value;

    public EnumMirror(String name,String value){
        this.name = name;
        this.value = value;
    }
    public EnumMirror(EnumMirror enumMirror){
        this.name = enumMirror.name;
        this.value = enumMirror.value;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "EnumMirror{" +
                "value=" + value +
                '}';
    }
}
