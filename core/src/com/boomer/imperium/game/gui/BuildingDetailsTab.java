package com.boomer.imperium.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.boomer.imperium.game.entities.buildings.Building;

public class BuildingDetailsTab extends Table {

    private Building building;

    private final Image icon;
    private final ProgressBar healthBar;
    private final Label healthLabel;

    private float maxHP;
    private float curHP;

    public BuildingDetailsTab(Skin skin){
        super(skin);
        this.icon = new Image();
        this.healthBar = new ProgressBar(0f, 0f, 1f, false, skin);
        this.healthLabel = new Label("?/?", skin);
        add(icon)
                .expandX()
                .center()
                .row();
        add(healthBar)
                .expandX()
                .center()
                .row();
        add(healthLabel)
                .expandX()
                .center()
                .row();
    }

}
