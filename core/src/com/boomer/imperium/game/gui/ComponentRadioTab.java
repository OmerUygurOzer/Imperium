package com.boomer.imperium.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.*;

public class ComponentRadioTab extends Table {

    private Table radioButtonHolder;
    private ButtonGroup<ImageButton> componentSelection;

    public ComponentRadioTab(Skin skin){
        super(skin);
        this.radioButtonHolder = new Table(skin);
        this.componentSelection = new ButtonGroup<ImageButton>();
        this.componentSelection.setMaxCheckCount(1);
        this.componentSelection.setMinCheckCount(0);
        this.componentSelection.setUncheckLast(true);
        add(radioButtonHolder)
                .expandX()
                .center()
                .row();
    }

    public Cell<ImageButton> addComponentButton(ImageButton imageButton){
        componentSelection.add(imageButton);
        return radioButtonHolder.add(imageButton);
    }

    public void clearButtons(){
        componentSelection.clear();
        radioButtonHolder.clear();
    }
}
