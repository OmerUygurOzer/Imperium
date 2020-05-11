package com.boomer.imperium.model;

import com.boomer.imperium.scripts.mirrors.AttributeList;

/**
 * Context is the internal representation of a Project. All data required by a Project will be loaded and stored here.
 */
public final class Context {

    public static Context fromData(NewContextData newContextData){
        return new Context(newContextData);
    }

    private String filePath;
    private String name;
    private String projectPackageName;
    private EntityList entities;
    private AttributeList createdAttributes;
    private EnumList enumList;
    private long lastEditedEpochTime;

    public Context(String filePath,
                   String name,
                   String projectPackageName,
                   EntityList entityList,
                   AttributeList attributeList,
                   EnumList enumList,
                   long lastEditedEpochTime) {
        this.filePath = filePath;
        this.name = name;
        this.projectPackageName = projectPackageName;
        this.entities = entityList;
        this.createdAttributes = attributeList;
        this.enumList = enumList;
        this.lastEditedEpochTime = lastEditedEpochTime;
    }

    public Context(){}

    Context(NewContextData newContextData){
        this.filePath = newContextData.getPath();
        this.name = newContextData.getName();
        this.projectPackageName = newContextData.getJavaPackage();
        this.entities = new EntityList();
        this.createdAttributes = new AttributeList();
        this.enumList = new EnumList();
        this.lastEditedEpochTime = System.currentTimeMillis();
    }


    public String getFilePath() { return this.filePath;}

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

    public AttributeList getCreatedAttributes() { return createdAttributes; }

    public EnumList getEnumList() { return enumList; }
}
