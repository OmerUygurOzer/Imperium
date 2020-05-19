package com.boomer.imperium;

import com.boomer.imperium.gui.MainContainer;
import com.boomer.imperium.scripts.ScriptMirror;
import com.boomer.imperium.scripts.mirrors.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class Main {
    public static void main(String[] args) {

        EntityList entityList = new EntityList();
        entityList.getValue().addAll(ImmutableList.<Entity>of(
                new Entity("omer"),
                new Entity("orhun"),
                new Entity("emre"),
                new Entity("hakan"),
                new Entity("ozi")));

        AttributeList attributeList = new AttributeList();

        Attribute attribute = new Attribute("POSITION");
        attribute.setVector2Value(new Vector2Mirror(0.5f,0.6f));
        Attribute attribute1 = new Attribute("SPEED");
        attribute1.setFloatValue(5f);

        attributeList.getValue().putAll(ImmutableMap.of(
                "POSITION", attribute,
                    "SPEED",attribute1));

        ScriptList scriptList = new ScriptList();

        scriptList.getValue().putAll(ImmutableMap.of(
                "MOVEMENT",new ScriptMirror("MOVEMENT","",attributeList,null,null),
                "COLLIDES",new ScriptMirror("COLLIDES","",new AttributeList(),null,null)
        ));

        Context sampleContext = new Context(
                "C:\\Users\\bo_om\\OneDrive\\Masaüstü\\Imperium\\Game Projects\\test",
                "C:\\Users\\bo_om\\OneDrive\\Masaüstü\\Imperium\\Game Projects\\test",
                "newtest",
                "package.name",
                entityList,
                scriptList,
                System.currentTimeMillis());

        new MainContainer();//.receiveContext(sampleContext);
    }
}
