package com.boomer.imperium;

public final class NewContextData {

    private String path;
    private String javaPackage;
    private String name;
    private String scriptPath;

    public NewContextData(String path, String javaPackage, String name, String scriptPath) {
        this.path = path;
        this.javaPackage = javaPackage;
        this.name = name;
        this.scriptPath = scriptPath;
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

    public String getScriptPath() { return scriptPath; }
}
