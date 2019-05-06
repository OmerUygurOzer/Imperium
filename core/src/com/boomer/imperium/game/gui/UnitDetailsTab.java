package com.boomer.imperium.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.boomer.imperium.game.entities.units.Unit;

public class UnitDetailsTab extends Table {

    private Unit unit;

    private final Image icon;
    private final ProgressBar healthBar;
    private final Label healthLabel;
    private final Label combatLabel;
    private final Label leadershipLabel;
    private final Label constructionLabel;
    private final Label researchLabel;
    private final Label miningLabel;
    private final Label manufacturingLabel;
    private final Label farmingLabel;
    private final Label artisanshipLabel;
    private final Label sneakLabel;
    private final Label tradesmanshipLabel;
    private float maxHP;
    private float curHP;
    private float combat;
    private float leadership;
    private float construction;
    private float research;
    private float mining;
    private float manufacturing;
    private float farming;
    private float artisanship;
    private float sneak;
    private float tradesmanship;

    public UnitDetailsTab(Skin skin) {
        super(skin);
        this.icon = new Image();
        this.healthBar = new ProgressBar(0f, 0f, 1f, false, skin);
        this.healthLabel = new Label("?/?", skin);
        this.combatLabel = new Label(null, skin);
        this.leadershipLabel = new Label(null, skin);
        this.constructionLabel = new Label(null, skin);
        this.researchLabel = new Label(null, skin);
        this.miningLabel = new Label(null, skin);
        this.manufacturingLabel = new Label(null, skin);
        this.farmingLabel = new Label(null, skin);
        this.artisanshipLabel = new Label(null, skin);
        this.sneakLabel = new Label(null, skin);
        this.tradesmanshipLabel = new Label(null, skin);
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
        add(combatLabel)
                .expandX()
                .padLeft(5f)
                .left()
                .row();
        add(leadershipLabel)
                .expandX()
                .padLeft(5f)
                .left()
                .row();
        add(constructionLabel)
                .expandX()
                .padLeft(5f)
                .left()
                .row();
        add(researchLabel)
                .expandX()
                .padLeft(5f)
                .left()
                .row();
        add(miningLabel)
                .expandX()
                .padLeft(5f)
                .left()
                .row();
        add(manufacturingLabel)
                .expandX()
                .padLeft(5f)
                .left()
                .row();
        add(farmingLabel)
                .expandX()
                .padLeft(5f)
                .left()
                .row();
        add(artisanshipLabel)
                .expandX()
                .padLeft(5f)
                .left()
                .row();
        add(sneakLabel)
                .expandX()
                .padLeft(5f)
                .left()
                .row();
        add(tradesmanshipLabel)
                .expandX()
                .padLeft(5f)
                .left()
                .row();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (unit != null) {
            if (unit.getMaxHp() != maxHP || unit.getHp() != curHP) {
                maxHP = unit.getMaxHp();
                curHP = unit.getHp();
                healthBar.setRange(0f, maxHP);
                healthBar.setValue(curHP);
                healthLabel.setText((int) maxHP + "/" + (int) curHP);
            }
            if (unit.getCombat() != combat) {
                combat = unit.getCombat();
                combatLabel.setText("Combat:" + (int) combat);
            }
            if (unit.getLeadership() != leadership) {
                leadership = unit.getLeadership();
                leadershipLabel.setText("Leadership:" + (int) leadership);
            }
            if (unit.getConstruction() != construction) {
                construction = unit.getConstruction();
                constructionLabel.setText("Construction:" + (int) construction);
            }
            if (unit.getResearch() != research) {
                research = unit.getResearch();
                researchLabel.setText("Research:" + (int) research);
            }
            if (unit.getMining() != mining) {
                mining = unit.getMining();
                miningLabel.setText("Mining:" + (int) mining);
            }
            if (unit.getManufacturing() != manufacturing) {
                manufacturing = unit.getManufacturing();
                manufacturingLabel.setText("Manufacturing:" + (int) manufacturing);
            }
            if (unit.getFarming() != farming) {
                farming = unit.getFarming();
                farmingLabel.setText("Farming:" + (int) farming);
            }
            if (unit.getArtisanship() != artisanship) {
                artisanship = unit.getArtisanship();
                artisanshipLabel.setText("Artisanship:" + (int) artisanship);
            }
            if (unit.getSneak() != sneak) {
                sneak = unit.getSneak();
                sneakLabel.setText("Sneak:" + (int) sneak);
            }
            if (unit.getTradesmanship() != tradesmanship) {
                tradesmanship = unit.getTradesmanship();
                tradesmanshipLabel.setText("Tradesmanship:" + (int) tradesmanship);
            }
        }
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
        this.curHP = unit.getHp();
        this.maxHP = unit.getMaxHp();
        this.combat = unit.getCombat();
        this.leadership = unit.getLeadership();
        this.construction = unit.getConstruction();
        this.research = unit.getResearch();
        this.mining = unit.getMining();
        this.manufacturing = unit.getManufacturing();
        this.farming = unit.getFarming();
        this.artisanship = unit.getArtisanship();
        this.sneak = unit.getSneak();
        this.tradesmanship = unit.getTradesmanship();

        this.icon.setDrawable(unit.getIcon());
        this.healthBar.setRange(0f, maxHP);
        this.healthBar.setValue(curHP);
        this.healthLabel.setText((int) maxHP + "/" + (int) curHP);
        this.combatLabel.setText("Combat:" + (int) combat);
        this.leadershipLabel.setText("Leadership:" + (int) leadership);
        this.constructionLabel.setText("Construction:" + (int) construction);
        this.researchLabel.setText("Research:" + (int) research);
        this.miningLabel.setText("Mining:" + (int) mining);
        this.manufacturingLabel.setText("Manufacturing:" + (int) manufacturing);
        this.farmingLabel.setText("Farming:" + (int) farming);
        this.artisanshipLabel.setText("Artisanship:" + (int) artisanship);
        this.sneakLabel.setText("Sneak:" + (int) sneak);
        this.tradesmanshipLabel.setText("Tradesmanship:" + (int) tradesmanship);
    }
}
