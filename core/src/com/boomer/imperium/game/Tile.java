package com.boomer.imperium.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.boomer.imperium.game.configs.GameConfigs;

import java.util.ArrayList;

public class Tile implements Entity {

    public final Rectangle bounds;
    private final Sprite tileSprite;
    private final ArrayList<Entity> entitiesContained;
    public final int tileX,tileY;
    public boolean isPassable = true;
    public boolean isVacant = true;
    public boolean isBeingLeft;

    public Tile(GameConfigs gameConfigs, Sprite sprite,float posX, float posY) {
        this.tileSprite = sprite;
        this.entitiesContained = new ArrayList<Entity>();
        this.tileX = (int)Math.floor(posX/gameConfigs.tileSize);
        this.tileY = (int)Math.floor(posY/gameConfigs.tileSize);
        this.bounds = new Rectangle(posX-(gameConfigs.tileSize/2f),posY-(gameConfigs.tileSize/2f),gameConfigs.tileSize,gameConfigs.tileSize);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(tileSprite, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public ArrayList<Entity> getEntitiesContained(){
        return entitiesContained;
    }

    @Override
    public void setMemoryIndex(int index) {

    }

    @Override
    public int getMemoryIndex() {
        return 0;
    }

    @Override
    public Layer getLayer() {
        return Layer.TILES;
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
    public void targetTile(Tile tile) {

    }

    @Override
    public void receiveDamage(int damage) {

    }

    @Override
    public void setPosition(int tileX, int tileY) {

    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    public void setTypeFlags(int typeFlags) {

    }

    @Override
    public int getTypeFlags() {
        return 0;
    }

    @Override
    public void setComponentFlags(int componentFlags) {

    }

    @Override
    public int getComponentFlags() {
        return 0;
    }

    @Override
    public void setStateFlags(int stateFlags) {

    }

    @Override
    public int getStateFlags() {
        return 0;
    }

    @Override
    public void setPlayer(Player player) {

    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public void setNation(Nation nation) {

    }

    @Override
    public Nation getNation() {
        return null;
    }

    @Override
    public void setCenterInTiles(int tileCount) {

    }

    @Override
    public int getCenterInTiles() {
        return 0;
    }

    @Override
    public void setHp(int hp) {

    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public void setMaxHp(int maxHp) {

    }

    @Override
    public int getMaxHp() {
        return 0;
    }

    @Override
    public void setHpRegen(float regenPerSecond) {

    }

    @Override
    public float getHpRegen() {
        return 0;
    }

    @Override
    public void setMeleeAttackSpeed(float attackPerSecond) {

    }

    @Override
    public float getMeleeAttackSpeed() {
        return 0;
    }

    @Override
    public void setRangeAttackSpeed(float attackPerSecond) {

    }

    @Override
    public void setAttackRange(int range) {

    }

    @Override
    public void setAttackAOE(int aoe) {

    }

    @Override
    public void setProjectileSpeed(float tilePersecond) {

    }

    @Override
    public void setMovementSpeed(float tilePerSecond) {

    }

    @Override
    public void setArmor(int armor) {

    }

    @Override
    public float getRangeAttackSpeed() {
        return 0;
    }

    @Override
    public int getAttackRange() {
        return 0;
    }

    @Override
    public int getAttackAOE() {
        return 0;
    }

    @Override
    public float getProjectileSpeed() {
        return 0;
    }

    @Override
    public float getMovementSpeed() {
        return 0;
    }

    @Override
    public int getArmor() {
        return 0;
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public boolean canBeMovedTo(){
        return isVacant && isPassable;
    }

    @Override
    public void select() {

    }

    @Override
    public void deSelect() {

    }

    @Override
    public void reset() {

    }
}
