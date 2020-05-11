package com.boomer.imperium;

import com.boomer.imperium.gui.MainContainer;

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
    }
}
