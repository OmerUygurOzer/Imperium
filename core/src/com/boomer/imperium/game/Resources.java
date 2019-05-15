package com.boomer.imperium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.boomer.imperium.game.graphics.BuildingSpriteAnimator;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;

public class Resources implements Disposable {

    public final Texture tilesTexture;
    public final TextureRegion[][] manTextureRegions;
    public final Texture cursors;
    public final Pixmap cursorsPixMap;
    public final Texture buttonsTexture;
    public final TextureRegion[][] buildingIconsTexture;
    public final TextureRegion[][] unitIcons;
    public final TextureRegion[][] temples;
    public final TextureRegion[] hpBar;

    public final Sprite grassland;
    public final Sprite desert;
    public final Sprite water;

    public final Drawable buildButton;
    public final Drawable townButton;

    public final UnitSpriteAnimator man;
    public final BuildingSpriteAnimator building;

    public final Skin skin;

    public final Pixmap standardCursorPixmap;
    public final Pixmap attackCursorPixmap;
    public final Pixmap enterBuildingCursorPixmap;
    public final Pixmap buildCursorPixmap;
    public final Pixmap enterTownCursorPixmap;
    public final Pixmap tradeCursorPixmap;
    public final Pixmap cantTradeCursorPixmap;

    public final Drawable fortButtonDrawable;
    public final Drawable factoryButtonDrawable;
    public final Drawable universityButtonDrawable;
    public final Drawable innButtonDrawable;
//    public final Drawable mineButtonDrawable;
//    public final Drawable marketButtonDrawable;
//    public final Drawable warFactoryDrawable;
//    public final Drawable harborDrawable;

    public final Drawable normanIcon;
    public final Drawable mayanIcon;
    public final Drawable greekIcon;
    public final Drawable vikingIcon;
    public final Drawable grokkenIcon;

    public final Drawable temple;
    public final Drawable dragonTemple;
    public final Drawable anotherTemple;

    public Resources() {
        this.tilesTexture = new Texture("tiles_grid.png");
        this.buttonsTexture = new Texture("buttons.png");
        this.manTextureRegions = new TextureRegion(new Texture("test_man.png")).split(138, 138);
        this.cursors = new Texture("cursors.png");
        cursors.getTextureData().prepare();
        this.cursorsPixMap = cursors.getTextureData().consumePixmap();
        this.buildingIconsTexture = new TextureRegion(new Texture("building_icons.png")).split(239, 146);
        this.unitIcons = new TextureRegion(new Texture("unit_icons.png")).split(46, 38);
        this.temples = new TextureRegion(new Texture("temples.png")).split(141, 146);
        this.grassland = new Sprite(tilesTexture, 0, 0, 64, 64);
        this.desert = new Sprite(tilesTexture, 64, 0, 64, 64);
        this.water = new Sprite(tilesTexture, 128, 0, 64, 64);
        this.man = new UnitSpriteAnimator(manTextureRegions);
        this.building = new BuildingSpriteAnimator(temples); //(Todo)change this to a real spritesheet
        this.skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        this.standardCursorPixmap = createPixmapFromTexturePixMap(cursorsPixMap,0,0,32,32);
        this.attackCursorPixmap = createPixmapFromTexturePixMap(cursorsPixMap,64,0,32,32);
        this.enterBuildingCursorPixmap = createPixmapFromTexturePixMap(cursorsPixMap,256,32,32,32);
        this.buildCursorPixmap = createPixmapFromTexturePixMap(cursorsPixMap,196,32,32,32);
        this.enterTownCursorPixmap = createPixmapFromTexturePixMap(cursorsPixMap,352,64,32,32);
        this.tradeCursorPixmap = createPixmapFromTexturePixMap(cursorsPixMap,288,32,32,32);
        this.cantTradeCursorPixmap = createPixmapFromTexturePixMap(cursorsPixMap,320,32,32,32);

        this.hpBar = new TextureRegion(new Texture("healthbar.png")).split(50,20)[0];

        this.buildButton = new TextureRegionDrawable(new TextureRegion(buttonsTexture, 0, 0, 109, 93));
        this.townButton = new TextureRegionDrawable(new TextureRegion(buttonsTexture, 109, 0, 109, 93));
        this.fortButtonDrawable = new TextureRegionDrawable(buildingIconsTexture[0][0]);
        this.factoryButtonDrawable = new TextureRegionDrawable(buildingIconsTexture[0][1]);
        this.universityButtonDrawable = new TextureRegionDrawable(buildingIconsTexture[1][1]);
        this.innButtonDrawable = new TextureRegionDrawable(buildingIconsTexture[1][1]);

        this.normanIcon = new TextureRegionDrawable(unitIcons[0][0]);
        this.mayanIcon = new TextureRegionDrawable(unitIcons[0][1]);
        this.greekIcon = new TextureRegionDrawable(unitIcons[0][2]);
        this.vikingIcon = new TextureRegionDrawable(unitIcons[0][3]);
        this.grokkenIcon = new TextureRegionDrawable(unitIcons[3][0]);
//        this.mineButtonDrawable = new TextureRegionDrawable(new TextureRegion(buildingIconsTexture,0,0,239,146));
//        this.marketButtonDrawable = new TextureRegionDrawable(new TextureRegion(buildingIconsTexture,0,0,239,146));
//        this.warFactoryDrawable = new TextureRegionDrawable(new TextureRegion(buildingIconsTexture,0,0,239,146));
//        this.harborDrawable = new TextureRegionDrawable(new TextureRegion(buildingIconsTexture,0,0,239,146));
        this.temple = new TextureRegionDrawable(temples[0][0]);
        this.dragonTemple = new TextureRegionDrawable(temples[0][1]);
        this.anotherTemple = new TextureRegionDrawable(temples[0][2]);
    }

    private Pixmap createPixmapFromTexturePixMap(Pixmap texture,int x,int y, int width,int height){
        Pixmap pixmap = new Pixmap(width,height,Pixmap.Format.RGBA8888);
        for(int i = 0 ; i < width; i++){
            for(int j = 0 ; j < height ; j++){
                pixmap.drawPixel(i,j, texture.getPixel(x+i,y+j));
            }
        }
        return pixmap;
    }

    @Override
    public void dispose() {
        tilesTexture.dispose();
    }
}
