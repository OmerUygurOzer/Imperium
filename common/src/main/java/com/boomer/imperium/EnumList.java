package com.boomer.imperium;

import java.util.*;

public final class EnumList {

    private final Map<String,Set<String>> value;

    public EnumList(){
        this.value = new LinkedHashMap<>();
    }

    public Map<String,Set<String>> getValue() {
        return value;
    }

    public void addEnum(String name, String enumValue){
        if(!value.containsKey(name)){
            value.put(name,new LinkedHashSet<>());
        }
        value.get(name).add(enumValue);
    }

    public boolean removeEnum(String name, String enumValue){
        if(!value.containsKey(name)){
            return false;
        }
        if(!value.get(name).contains(enumValue)){
            return false;
        }
        return value.get(name).remove(enumValue);
    }

    public boolean removeEnum(String name){
        if(!value.containsKey(name)){
            return false;
        }
        value.remove(name);
        return true;
    }
}
