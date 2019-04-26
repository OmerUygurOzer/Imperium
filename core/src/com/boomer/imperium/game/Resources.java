package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;

public class Resources implements Disposable {

    final Texture tilesTexture;
    final TextureRegion[][] manTextureRegions;

    final Sprite grassland;
    final Sprite desert;

    final UnitSpriteAnimator man;

    public Resources(){
        this.tilesTexture = new Texture("tiles_grid.png");
        this.manTextureRegions = new TextureRegion(new Texture("test_man.png")).split(138,138);
        this.grassland = new Sprite(tilesTexture,0,0,64,64);
        this.desert = new Sprite(tilesTexture,64,0,64,64);
        this.man = new UnitSpriteAnimator(manTextureRegions);
    }

    @Override
    public void dispose() {
        tilesTexture.dispose();
    }
}
