package com.boomer.imperium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;

public class Resources implements Disposable {

    public final Texture tilesTexture;
    public final TextureRegion[][] manTextureRegions;
    public final TextureRegion[][] cursors;
    public final Texture buttonsTexture;
    public final TextureRegion[][] buildingIconsTexture;
    public final TextureRegion[][] unitIcons;

    public final Sprite grassland;
    public final Sprite desert;
    public final Sprite water;

    public final Drawable buildButton;
    public final Drawable townButton;

    public final UnitSpriteAnimator man;

    public final Sprite inGameCursor;

    public final Skin skin;

    public final Drawable fortButtonDrawable;
    public final Drawable factoryButtonDrawable;
    public final Drawable universityButtonDrawable;
    public final Drawable innButtonDrawable;
//    public final Drawable mineButtonDrawable;
//    public final Drawable marketButtonDrawable;
//    public final Drawable warFactoryDrawable;
//    public final Drawable harborDrawable;

    public final Drawable normanIcon;
    public final Drawable grokkenIcon;

    public Resources(){
        this.tilesTexture = new Texture("tiles_grid.png");
        this.buttonsTexture = new Texture("buttons.png");
        this.manTextureRegions = new TextureRegion(new Texture("test_man.png")).split(138,138);
        this.cursors = new TextureRegion(new Texture("cursors.png")).split(32,32);
        this.buildingIconsTexture = new TextureRegion(new Texture("building_icons.png")).split(239,146);
        this.unitIcons = new TextureRegion(new Texture("unit_icons.png")).split(46,38);
        this.grassland = new Sprite(tilesTexture,0,0,64,64);
        this.desert = new Sprite(tilesTexture,64,0,64,64);
        this.water = new Sprite(tilesTexture,128,0,64,64);
        this.man = new UnitSpriteAnimator(manTextureRegions);
        this.skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        this.inGameCursor = new Sprite(cursors[0][0]);

        this.buildButton = new TextureRegionDrawable(new TextureRegion(buttonsTexture,0,0,109,93));
        this.townButton = new TextureRegionDrawable(new TextureRegion(buttonsTexture,109,0,109,93));
        this.fortButtonDrawable = new TextureRegionDrawable(buildingIconsTexture[0][0]);
        this.factoryButtonDrawable = new TextureRegionDrawable(buildingIconsTexture[0][1]);
        this.universityButtonDrawable =new TextureRegionDrawable(buildingIconsTexture[1][1]);
        this.innButtonDrawable = new TextureRegionDrawable(buildingIconsTexture[1][1]);

        this.normanIcon = new TextureRegionDrawable(unitIcons[0][0]);
        this.grokkenIcon = new TextureRegionDrawable(unitIcons[3][0]);
//        this.mineButtonDrawable = new TextureRegionDrawable(new TextureRegion(buildingIconsTexture,0,0,239,146));
//        this.marketButtonDrawable = new TextureRegionDrawable(new TextureRegion(buildingIconsTexture,0,0,239,146));
//        this.warFactoryDrawable = new TextureRegionDrawable(new TextureRegion(buildingIconsTexture,0,0,239,146));
//        this.harborDrawable = new TextureRegionDrawable(new TextureRegion(buildingIconsTexture,0,0,239,146));
    }

    @Override
    public void dispose() {
        tilesTexture.dispose();
    }
}
