package com.boomer.imperium.gui;

import com.boomer.imperium.Context;
import com.boomer.imperium.model.ContextReceiver;
import com.boomer.imperium.Entity;
import com.boomer.imperium.model.EntityReceiver;

import javax.swing.*;

class EditingPanel extends JTabbedPane implements EntityReceiver, ContextReceiver {

    private final ComponentEditingTab componentEditingTab;
    private final GraphicsEditingTab graphicsEditingTab;

    EditingPanel(){
        this.componentEditingTab = new ComponentEditingTab();
        this.graphicsEditingTab = new GraphicsEditingTab();
        addTab("Components", componentEditingTab);
        addTab("Graphics",graphicsEditingTab);
    }

    @Override
    public void receiveEntity(Entity entity) {
        componentEditingTab.receiveEntity(entity);
        graphicsEditingTab.receiveEntity(entity);
    }

    @Override
    public void receiveContext(Context context) {
        componentEditingTab.receiveContext(context);
    }

    public void storeEdits(){
        componentEditingTab.storeEditions();
    }
}
