package com.boomer.imperium.game.entities.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.Layer;
import com.boomer.imperium.game.players.Nation;
import com.boomer.imperium.game.players.Player;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.*;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.graphics.BuildingSpriteAnimator;
import com.boomer.imperium.game.graphics.FrameCounter;
import com.boomer.imperium.game.map.Tile;
import com.boomer.imperium.game.map.TileVector;

import java.util.ArrayList;
import java.util.List;

public final class Building implements Entity {

    private int memoryIndex;
    private Layer layer;

    private int tileX;
    private int tileY;
    private final Rectangle bounds;
    private final Vector2 center;
    private final GameContextInterface gameContext;

    private BuildingState state;
    private Drawable icon;
    private HPBar hpBar;

    private Nation nation;
    private Player player;

    private BuildingSpriteAnimator buildingSpriteAnimator;
    private final FrameCounter frameCounter;

    private final List<TileVector> tileCoverageVector;
    private final List<Tile> tilesCovered;
    private final List<Integer> connectables;
    private final List<Unit> containedUnits;

    private String name;
    private int typeFlags;
    private int componentFlags;
    private int stateFlags;
    private int maxHp;
    private int hp;

    public Building(GameContextInterface gameContext) {
        this.gameContext = gameContext;
        this.state = BuildingState.IDLE;
        this.bounds = new Rectangle();
        this.center = new Vector2();
        this.tileX = 0;
        this.tileY = 0;
        this.frameCounter = new FrameCounter(8f,1);
        this.tileCoverageVector = new ArrayList<>(9);
        this.tilesCovered = new ArrayList<>();
        this.connectables = new ArrayList<>();
        this.containedUnits = new ArrayList<>(12);
        this.typeFlags = GameFlags.BUILDING;
        this.componentFlags = 0;
        this.stateFlags = 0;
        this.hpBar = new HPBar(gameContext,this);
        this.hpBar.setHp(maxHp,hp);
    }

    @Override
    public void targetTile(Tile tile) {

    }

    @Override
    public void targetEntity(Entity entity) {

    }

    @Override
    public void setMemoryIndex(int index) {
        this.memoryIndex = index;
    }

    @Override
    public int getMemoryIndex() {
        return memoryIndex;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        hpBar.render(spriteBatch,shapeRenderer);
        buildingSpriteAnimator.draw(spriteBatch,frameCounter.currentFrame,bounds,state);
    }

    @Override
    public void update(float deltaTime) {
        hpBar.setHp(maxHp,hp);
        frameCounter.update(deltaTime);
    }

    @Override
    public int tileX() {
        return tileX;
    }

    @Override
    public int tileY() {
        return tileY;
    }

    @Override
    public List<Tile> getTilesCovered() {
        return tilesCovered;
    }

    @Override
    public void setTilesCovered(List<Tile> tilesCovered) {
        this.tilesCovered.clear();
        this.tilesCovered.addAll(tilesCovered);
    }

    @Override
    public List<TileVector> getTileCoverageVectors() {
        return tileCoverageVector;
    }

    @Override
    public void setTileCoverageVectors(List<TileVector> tileCoverageVectors) {
        this.tileCoverageVector.clear();
        this.tileCoverageVector.addAll(tileCoverageVectors);
    }

    @Override
    public void receiveDamage(int damage) {
        if (state.equals(BuildingState.COLLAPSING) | state.equals(BuildingState.RUBBLE))
            return;
        hp = -damage;
        if (hp <= 0) {
            state = BuildingState.COLLAPSING;
        }
    }

    public void setBuildingSpriteAnimator(BuildingSpriteAnimator buildingSpriteAnimator) {
        this.buildingSpriteAnimator = buildingSpriteAnimator;
    }

    public void containUnit(Unit unit){
        this.containedUnits.add(unit);
        unit.setStateFlags(GameFlags.CONTAINED);
    }

    public void uncontainUnit(Unit unit){
        this.containedUnits.remove(unit);
        unit.setStateFlags(GameFlags.UNCONTAINED);
    }

    @Override
    public void setPosition(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
        for(Tile tile : tilesCovered){
            tile.removeEntity(this);
        }
        tilesCovered.clear();
        Tile tile = gameContext.getGameWorld().map.getTileAt(tileX,tileY);
        float x = tile.bounds.x,y = tile.bounds.y,maxX = 0f,maxY = 0f;
        for(TileVector tileVector : tileCoverageVector){
            tile = gameContext.getGameWorld().map.getTileAt(tileX + tileVector.x,tileY + tileVector.y);
            tilesCovered.add(tile);
            tile.addEntity(this);
            x = Math.min(x,tile.bounds.x);
            y = Math.min(y,tile.bounds.y);
            maxX = Math.max(maxX,tile.bounds.x+tile.bounds.width);
            maxY = Math.max(maxY,tile.bounds.y+tile.bounds.height);
        }
        bounds.set(x,y,maxX-x,maxY-y);
    }

    @Override
    public boolean shouldRemove() {
        return state.equals(BuildingState.RUBBLE);
    }

    @Override
    public void select() {

    }

    @Override
    public void deSelect() {

    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Nation getNation() {
        return nation;
    }

    @Override
    public void setNation(Nation nation) {
        this.nation = nation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Drawable getIcon() {
        return icon;
    }

    @Override
    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    @Override
    public int getTypeFlags() {
        return typeFlags;
    }

    @Override
    public int getComponentFlags() {
        return componentFlags;
    }

    @Override
    public int getStateFlags() {
        return stateFlags;
    }

    @Override
    public void setTypeFlags(int typeFlags) {
        this.typeFlags = typeFlags;
    }

    @Override
    public void setComponentFlags(int componentFlags) {
        this.componentFlags = componentFlags;
    }

    @Override
    public void setStateFlags(int stateFlags) {
        this.stateFlags = stateFlags;
    }

    @Override
    public Unit asUnit() {
        return null;
    }

    @Override
    public Doodad asDoodad() {
        return null;
    }

    @Override
    public Building asBuilding() {
        return this;
    }

    @Override
    public Projectile asProjectile() {
        return null;
    }

    @Override
    public Town asTown() {
        return null;
    }

    @Override
    public void reset() {

    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Vector2 getCenter() {
        return bounds.getCenter(center);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public BuildingState getState() {
        return state;
    }

    public void setState(BuildingState state) {
        this.state = state;
    }

    public List<Integer> getConnectables() {
        return connectables;
    }

    public void setCollectables(List<Integer> collectables) {
        this.connectables.clear();
        this.connectables.addAll(collectables);
    }


    @Override
    public void dayPassed(int daysPassed) {

    }

    @Override
    public void weekPassed(int weeksPassed) {

    }

    @Override
    public void monthPassed(int monthsPassed) {

    }

    @Override
    public void yearPassed(int yearsPassed) {

    }
}
