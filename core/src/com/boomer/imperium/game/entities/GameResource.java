package com.boomer.imperium.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.Layer;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.buildings.Building;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.map.Tile;
import com.boomer.imperium.game.map.TileVector;
import com.boomer.imperium.game.players.Nation;
import com.boomer.imperium.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class GameResource implements Entity {

    private GameContextInterface gameContext;
    private int maxAvailableResource;
    private int currentAvailableResourceAmount;
    private int renewPerMonth;

    private final List<TileVector> tileCoverageVectors;
    private final List<Tile> tilesCovered;
    private int tileX;
    private int tileY;

    private final int typeFlags;
    private final int componentFlags;
    private final int stateFlags;

    public GameResource(GameContext gameContext){
        this.tilesCovered = new ArrayList<>();
        this.tileCoverageVectors = new ArrayList<>();
        this.gameContext = gameContext;
        this.typeFlags = GameFlags.RESOURCE;
        this.stateFlags = GameFlags.SELECTABLE | GameFlags.RENDERABLE | GameFlags.NO_ROOM | GameFlags.INVULNERABLE;
        this.componentFlags = 0;
    }

    @Override
    public void targetTile(Tile tile) {

    }

    @Override
    public void targetEntity(Entity entity) {

    }

    @Override
    public void setMemoryIndex(int index) {

    }

    @Override
    public int getMemoryIndex() {
        return 0;
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
        return tileCoverageVectors;
    }

    @Override
    public void setTileCoverageVectors(List<TileVector> tileCoverageVectors) {
        this.tileCoverageVectors.clear();
        this.tileCoverageVectors.addAll(tileCoverageVectors);
    }

    @Override
    public void receiveDamage(int damage) {

    }

    @Override
    public void setPosition(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
        Entity.adjustEntityBounds(tilesCovered,this,gameContext,tileX,tileY);
    }

    @Override
    public boolean shouldRemove() {
        return currentAvailableResourceAmount <= 0;
    }

    @Override
    public void select() {

    }

    @Override
    public void deSelect() {

    }

    @Override
    public Layer getLayer() {
        return Layer.TILES;
    }

    @Override
    public void setLayer(Layer layer) {

    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public void setPlayer(Player player) {

    }

    @Override
    public Nation getNation() {
        return null;
    }

    @Override
    public void setNation(Nation nation) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public Drawable getIcon() {
        return null;
    }

    @Override
    public void setIcon(Drawable drawable) {

    }

    @Override
    public int getTypeFlags() {
        return 0;
    }

    @Override
    public int getComponentFlags() {
        return 0;
    }

    @Override
    public int getStateFlags() {
        return 0;
    }

    @Override
    public void setTypeFlags(int typeFlags) {

    }

    @Override
    public void setComponentFlags(int componentFlags) {

    }

    @Override
    public void setStateFlags(int stateFlags) {

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
        return null;
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
    public GameResource asResource() {
        return this;
    }

    @Override
    public void reset() {
        this.currentAvailableResourceAmount = 0;
        this.renewPerMonth = 0;
        this.maxAvailableResource = 0;
        this.tilesCovered.clear();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void dayPassed(int daysPassed) {

    }

    @Override
    public void weekPassed(int weeksPassed) {

    }

    @Override
    public void monthPassed(int monthsPassed) {
        currentAvailableResourceAmount += renewPerMonth;
        currentAvailableResourceAmount = Math.min(maxAvailableResource,currentAvailableResourceAmount);
    }

    @Override
    public void yearPassed(int yearsPassed) {

    }

    @Override
    public void setMinimapDrawable(Drawable minimapDrawable) {

    }

    @Override
    public Drawable getMinimapDrawable() {
        return null;
    }

    @Override
    public Rectangle getMinimapBounds() {
        return null;
    }

    @Override
    public void setMinimapBounds(float x, float y, float width, float height) {

    }

    @Override
    public void renderOnMinimap(Batch batch, int parentAlpha) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public Vector2 getCenter() {
        return null;
    }


}
