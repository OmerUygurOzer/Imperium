package com.boomer.imperium;

import com.google.common.collect.ImmutableList;
import com.boomer.imperium.gui.MainContainer;
import com.boomer.imperium.model.Context;
import com.boomer.imperium.model.Entity;
import com.boomer.imperium.model.EntityList;
import com.boomer.imperium.model.attribute.AttributeList;

public class Main {
    public static void main(String[] args) {

//        EntityList entityList = new EntityList();
//        entityList.getValue().addAll(ImmutableList.<Entity>of(
//                new Entity("omer"),
//                new Entity("orhun"),
//                new Entity("emre"),
//                new Entity("hakan"),
//                new Entity("ozi")));
//
//        Context sampleContext = new Context(
//                "C:\\Users\\bo_om\\OneDrive\\Masaüstü\\Imperium\\Game Projects\\test",
//                "ImperiumSample",
//                "",
//                entityList,
//                new AttributeList(),
//                System.currentTimeMillis());

        new MainContainer();//.receiveContext(sampleContext);
        System.out.println("ASDSADSADSAD");
    }
}
