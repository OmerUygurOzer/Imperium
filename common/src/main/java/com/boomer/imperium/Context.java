package com.boomer.imperium;

import com.boomer.imperium.scripts.mirrors.ScriptList;

/**
 * Context is the internal representation of a Project. All data required by a Project will be loaded and stored here.
 */
public final class Context {

    public static Context fromData(NewContextData newContextData){
        return new Context(newContextData);
    }

    private String filePath;
    private String scriptsPath;
    private String name;
    private String projectPackageName;
    private EntityList entities;
    private ScriptList scriptList;
    private long lastEditedEpochTime;

    public Context(String filePath,
                   String scriptsPath,
                   String name,
                   String projectPackageName,
                   EntityList entityList,
                   ScriptList scriptList,
                   long lastEditedEpochTime) {
        this.filePath = filePath;
        this.scriptsPath = scriptsPath;
        this.name = name;
        this.projectPackageName = projectPackageName;
        this.entities = entityList;
        this.scriptList = scriptList;
        this.lastEditedEpochTime = lastEditedEpochTime;
    }

    public Context(){}

    Context(NewContextData newContextData){
        this.filePath = newContextData.getPath();
        this.name = newContextData.getName();
        this.projectPackageName = newContextData.getJavaPackage();
        this.entities = new EntityList();
        this.scriptList = new ScriptList();
        this.scriptsPath = newContextData.getScriptPath();
        this.lastEditedEpochTime = System.currentTimeMillis();
    }


    public String getFilePath() { return this.filePath;}

    public String getScriptsPath() { return scriptsPath; }

    public String getName() {
        return this.name;
    }

    public String getProjectPackageName() {
        return this.projectPackageName;
    }

    public long getLastEditedEpochTime() {
        return this.lastEditedEpochTime;
    }

    public EntityList getEntities() {
        return this.entities;
    }

    public ScriptList getScriptList() { return scriptList; }
}
