package com.boomer.imperium.gui;

import com.boomer.imperium.model.Context;
import com.boomer.imperium.model.ContextReceiver;

import javax.swing.*;
import java.awt.event.ActionEvent;

class MainMenuBar extends JMenuBar implements ContextReceiver {

    private final MainMenuListener mainMenuListener;
    private final JMenu editMenu;

    MainMenuBar(MainMenuListener mainMenuListener){
        this.mainMenuListener = mainMenuListener;
        JMenu fileMenu = new JMenu("File");
        JMenuItem newProject = new JMenuItem(createNewProjectButtonAction());
        JMenuItem loadItem = new JMenuItem(createLoadButtonAction());
        JMenuItem saveItem = new JMenuItem(createSaveButtonAction());
        fileMenu.add(newProject);
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        this.editMenu = new JMenu("Edit");
        JMenuItem newEntityItem = new JMenuItem(createNewEntityButtonAction());
        JMenuItem deleteEntityItem = new JMenuItem(createDeleteEntityButtonAction());
        this.editMenu.add(newEntityItem);
        this.editMenu.add(deleteEntityItem);
        add(fileMenu);
        add(editMenu);
    }

    private AbstractAction createNewProjectButtonAction(){
        return new AbstractAction("New Project") {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuListener.newProjectClicked();
            }
        };
    }

    private AbstractAction createLoadButtonAction(){
        return new AbstractAction("Load") {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuListener.loadClicked();
            }
        };
    }

    private AbstractAction createSaveButtonAction(){
        return new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuListener.saveClicked();
            }
        };
    }

    private AbstractAction createNewEntityButtonAction(){
        return new AbstractAction("New Entity") {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuListener.newEntityClicked();
            }
        };
    }

    private AbstractAction createDeleteEntityButtonAction(){
        return new AbstractAction("Delete Entity") {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuListener.deleteEntityClicked();
            }
        };
    }

    @Override
    public void receiveContext(Context context) {
        editMenu.setEnabled(context!=null);
    }
}
