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

    public void setBuilding(Building building){
        this.building = building;
        icon.setDrawable(building.getIcon());
        healthBar.setRange(0f,building.getMaxHp());
        healthBar.setValue(building.getHp());
    }

    public void clearBuilding(){
        this.building = null;
        this.curHP = 0f;
        this.maxHP = 0f;

        this.icon.setDrawable(null);
        this.healthBar.setRange(0f, maxHP);
        this.healthBar.setValue(curHP);
        this.healthLabel.setText((int) maxHP + "/" + (int) curHP);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (building != null) {
            if (building.getMaxHp() != maxHP || building.getHp() != curHP) {
                maxHP = building.getMaxHp();
                curHP = building.getHp();
                healthBar.setRange(0f, maxHP);
                healthBar.setValue(curHP);
                healthLabel.setText((int) maxHP + "/" + (int) curHP);
            }

        }
    }

}
