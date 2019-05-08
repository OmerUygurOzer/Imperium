package com.boomer.imperium.game.entities.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.game.Layer;
import com.boomer.imperium.game.Nation;
import com.boomer.imperium.game.Player;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.map.Tile;
import com.boomer.imperium.game.entities.Doodad;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.Projectile;
import com.boomer.imperium.game.entities.Town;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.map.TileVector;

import java.util.List;

public final class Building implements Entity {

    public enum State{
        IDLE,
        ACTIVE,
        BEING_CONSTRUCTED,
    }


    private final GameContextInterface gameContext;
    private State state;


    public Building(GameContextInterface gameContext){
        this.gameContext = gameContext;
        this.state = State.IDLE;
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
        return 0;
    }

    @Override
    public int tileY() {
        return 0;
    }

    @Override
    public List<Tile> getTilesCovered() {
        return null;
    }

    @Override
    public List<TileVector> getTileCoverageVectors() {
        return null;
    }

    @Override
    public void setTileCoverageVectors(List<TileVector> tileCoverageVectors) {

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
    public void select() {

    }

    @Override
    public void deSelect() {

    }

    @Override
    public Layer getLayer() {
        return null;
    }

    @Override
    public void setLayer(Layer layer) {

    }

    @Override
    public void setCenterInTiles(int centerInTiles) {

    }

    @Override
    public int getCenterInTiles() {
        return 0;
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

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
