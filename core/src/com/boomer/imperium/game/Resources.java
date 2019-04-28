package com.boomer.imperium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;

public class Resources implements Disposable {

    public final Texture tilesTexture;
    public final TextureRegion[][] manTextureRegions;
    public final TextureRegion[][] cursors;

    public final Sprite grassland;
    public final Sprite desert;
    public final Sprite water;

    public final UnitSpriteAnimator man;

    public final Sprite inGameCursor;

    public final Skin skin;

    public Resources(){
        this.tilesTexture = new Texture("tiles_grid.png");
        this.manTextureRegions = new TextureRegion(new Texture("test_man.png")).split(138,138);
        this.cursors = new TextureRegion(new Texture("cursors.png")).split(32,32);
        this.grassland = new Sprite(tilesTexture,0,0,64,64);
        this.desert = new Sprite(tilesTexture,64,0,64,64);
        this.water = new Sprite(tilesTexture,128,0,64,64);
        this.man = new UnitSpriteAnimator(manTextureRegions);
        this.skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        this.inGameCursor = new Sprite(cursors[0][0]);
        this.cursors[0][0].getTexture().getTextureData().prepare();

    }

    @Override
    public void dispose() {
        tilesTexture.dispose();
    }
}
