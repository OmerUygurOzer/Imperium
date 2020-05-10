package com.boomer.imperium.model;

public final class NewContextData {

    private String path;
    private String javaPackage;
    private String name;

    public NewContextData(String path, String javaPackage, String name) {
        this.path = path;
        this.javaPackage = javaPackage;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    public String getName() {
        return name;
    }
}
