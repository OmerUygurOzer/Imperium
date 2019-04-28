package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;

public class Resources implements Disposable {

    public final Texture tilesTexture;
    public final TextureRegion[][] manTextureRegions;

    public final Sprite grassland;
    public final Sprite desert;
    public final Sprite water;

    public final UnitSpriteAnimator man;

    public Resources(){
        this.tilesTexture = new Texture("tiles_grid.png");
        this.manTextureRegions = new TextureRegion(new Texture("test_man.png")).split(138,138);
        this.grassland = new Sprite(tilesTexture,0,0,64,64);
        this.desert = new Sprite(tilesTexture,64,0,64,64);
        this.water = new Sprite(tilesTexture,128,0,64,64);
        this.man = new UnitSpriteAnimator(manTextureRegions);
    }

    @Override
    public void dispose() {
        tilesTexture.dispose();
    }
}
