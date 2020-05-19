package com.boomer.imperium.gui;

import com.boomer.imperium.Context;
import com.boomer.imperium.Entity;
import com.boomer.imperium.NewContextData;
import com.boomer.imperium.model.*;
import com.boomer.imperium.model.io.ContextIOListener;
import com.boomer.imperium.model.io.ContextReader;
import com.boomer.imperium.model.io.ContextWriter;
import com.boomer.imperium.model.io.NewContextGenerator;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class MainContainer extends JFrame implements ContextReceiver,EntityTreeListener , MainMenuListener,
        NewContextDataReceiver, ContextIOListener {

    private static final String TITLE = "Entity Editor";
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 1200;

    private final MainMenuBar menuBar;
    private final EntityTreeView entityTreeView;
    private final EditingPanel editingPanel;

    private Context currentContext;

    public MainContainer(){
        setTitle(TITLE);
        setSize(WIDTH,HEIGHT);
        this.entityTreeView = new EntityTreeView(this);
        this.editingPanel = new EditingPanel();
        this.menuBar = new MainMenuBar(this);


        add(entityTreeView,BorderLayout.WEST);
        add(editingPanel, BorderLayout.CENTER);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        currentContext = null;
        receiveContext(null);
    }

    @Override
    public void receiveContext(Context context) {
        currentContext = context;
        menuBar.receiveContext(context);
        entityTreeView.receiveContext(context);
        editingPanel.receiveContext(context);
    }

    @Override
    public void entitySelected(Entity entity) {
        setTitle(TITLE + "- Currently Editing: "  + entity.getName());
        editingPanel.receiveEntity(entity);
    }

    @Override
    public void newProjectClicked() {
        NewProjectDialog.show(this,this);
    }

    @Override
    public void loadClicked() {
        FileFilter jsonFilter = new FileNameExtensionFilter("Json File","json");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Where is the project you'd like to load?");
        chooser.setFileFilter(jsonFilter);
        int option = chooser.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            new ContextReader(file.getAbsolutePath(),this).execute();
        }
    }

    @Override
    public void saveClicked() {
        editingPanel.storeEdits();
        new ContextWriter(currentContext,this).execute();
    }

    @Override
    public void newEntityClicked() {

    }

    @Override
    public void deleteEntityClicked() {

    }

    @Override
    public void receiveNewContextData(NewContextData newContextData) {
        new NewContextGenerator(newContextData,this).execute();
    }

    @Override
    public void contextWritten(Context context) {
        receiveContext(context);
    }

    @Override
    public void contextRead(Context context) {
        receiveContext(context);
    }

    @Override
    public void contextGenerated(Context context) {
        //receiveContext(context);
    }
}
