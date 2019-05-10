package com.boomer.imperium.game.entities.units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.game.*;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.Doodad;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.Projectile;
import com.boomer.imperium.game.entities.Town;
import com.boomer.imperium.game.entities.buildings.Buildable;
import com.boomer.imperium.game.entities.buildings.Building;
import com.boomer.imperium.game.graphics.FrameCounter;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;
import com.boomer.imperium.game.map.PathTracker;
import com.boomer.imperium.game.map.Tile;
import com.boomer.imperium.game.map.TileVector;

import java.util.ArrayList;
import java.util.List;

public final class Unit implements Entity {

    private final GameContextInterface gameContext;
    private final Rectangle bounds;
    private final UnitMovement unitMovement;
    private final UnitOrders unitOrders;
    private final PathTracker pathTracker;
    private int memoryIndex;
    private final List<Tile> tilesCovered;
    private final List<TileVector> tileCoverageVectors;

    private Drawable icon;
    private UnitSpriteAnimator unitSpriteAnimator;
    private FrameCounter frameCounter;
    private Direction facing;
    private int tileX, tileY;


    private UnitState state;
    private Layer unitLayer;

    private Nation nation;
    private Player player;

    private int typeFlags;
    private int componentFlags;
    private int stateFlags;
    private String name;

    private List<Buildable> buildables;

    private float movementSpeed;
    private int rangeAttackDamage;
    private int meleeAttackDamage;
    private int armor;
    private float hpRegen;
    private float hp;
    private float maxHp;
    private float meleeAttackSpeed;
    private float rangeAttackSpeed;
    private int rangeAttackRange;
    private float projectileSpeed;
    private float aoeRadius;

    private float leadership;
    private float combat;
    private float sneak;
    private float research;
    private float mining;
    private float manufacturing;
    private float farming;
    private float artisanship;
    private float tradesmanship;
    private float construction;

    private int unitCapacity;

    //temp
    private boolean selected;
    private Sprite selectedSprite;

    public Unit(GameContextInterface gameContext) {
        this.gameContext = gameContext;
        this.unitOrders = new UnitOrders(this);
        this.unitMovement = new UnitMovement(gameContext, this, gameContext.getGameConfigs().tileSize, 1f);
        this.tileX = 0;
        this.tileY = 0;
        this.frameCounter = new FrameCounter(8f, 8);
        this.state = UnitState.IDLE;
        Tile tile = gameContext.getGameWorld().map.getTileAt(0, 0);
        this.bounds = new Rectangle(tile.bounds.x, tile.bounds.y, gameContext.getGameConfigs().tileSize,
                gameContext.getGameConfigs().tileSize);
        this.selectedSprite = gameContext.getGameResources().inGameCursor;
        this.pathTracker = new PathTracker(gameContext, this, gameContext.getGameWorld().map, unitMovement);
        this.tilesCovered = new ArrayList<Tile>(9);
        this.tileCoverageVectors = new ArrayList<TileVector>(9);
        this.tileCoverageVectors.add(new TileVector(0,0));
    }

    @Override
    public void update(float deltaTime) {
        frameCounter.update(deltaTime);
        if (state.equals(UnitState.MOVING)) {
            pathTracker.update(deltaTime);
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        unitSpriteAnimator.draw(spriteBatch, frameCounter.currentFrame, bounds, facing, state);
        if (selected)
            spriteBatch.draw(selectedSprite, bounds.x, bounds.y, bounds.width, bounds.height);

    }

    public void targetTile(Tile tile) {
        unitOrders.setTargetTile(tile);
        pathTracker.activate(unitOrders);
        state = UnitState.MOVING;
    }

    @Override
    public void select() {
        selected = true;
    }

    @Override
    public void deSelect() {
        selected = false;
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
        //Do nothing. units only ever occupy a single tile
    }


    @Override
    public void receiveDamage(int damage) {
        if (!state.equals(UnitState.DYING) | !state.equals(UnitState.DEAD))
            return;
        hp = -damage;
        if (hp <= 0) {
            state = UnitState.DYING;
        }
    }

    @Override
    public void setPosition(int tileX, int tileY) {
        Tile tile = gameContext.getGameWorld().map.getTileAt(tileX, tileY);
        this.tileX = tile.tileX;
        this.tileY = tile.tileY;
        this.tilesCovered.clear();
        this.tilesCovered.add(tile);
        bounds.set(tile.bounds);
    }

    public void setTile(int x, int y) {
        this.tileX = x;
        this.tileY = y;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    @Override
    public void setLayer(Layer layer) {
        this.unitLayer = layer;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setPlayer(Player player) {

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
    public Drawable getIcon() { return icon; }

    @Override
    public void setIcon(Drawable drawable) { this.icon = drawable; }

    @Override
    public Nation getNation() {
        return this.nation;
    }

    @Override
    public int getTypeFlags() {
        return this.typeFlags;
    }

    @Override
    public int getComponentFlags() {
        return this.componentFlags;
    }

    @Override
    public int getStateFlags() {
        return this.stateFlags;
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

    public void setState(UnitState state) {
        this.state = state;
    }

    public void setUnitSpriteAnimator(UnitSpriteAnimator unitSpriteAnimator) {
        this.unitSpriteAnimator = unitSpriteAnimator;
    }

    @Override
    public void setMemoryIndex(int index) {
        memoryIndex = index;
    }

    @Override
    public int getMemoryIndex() {
        return memoryIndex;
    }

    @Override
    public Layer getLayer() {
        return unitLayer;
    }


    @Override
    public Unit asUnit() {
        return this;
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
    public boolean shouldRemove() {
        return state.equals(UnitState.DEAD);
    }


    @Override
    public void reset() {
        //width and height in tiles
        this.state = UnitState.IDLE;
        this.unitLayer = null;

        this.nation = null;
        this.player = null;

        this.typeFlags = 0;
        this.componentFlags = 0;
        this.stateFlags = 0;

        this.buildables.clear();

        this.movementSpeed = 0f;
        this.rangeAttackDamage = 0;
        this.meleeAttackDamage = 0;
        this.armor = 0;
        this.hpRegen = 0f;
        this.hp = 0f;
        this.maxHp = 0f;
        this.meleeAttackSpeed = 0f;
        this.rangeAttackSpeed = 0f;
        this.rangeAttackRange = 0;
        this.projectileSpeed = 0f;
        this.aoeRadius = 0f;

        this.leadership = 0f;
        this.combat = 0f;
        this.sneak = 0f;
        this.research = 0f;
        this.mining = 0f;
        this.manufacturing = 0f;
        this.farming = 0f;
        this.artisanship = 0f;
        this.tradesmanship = 0f;

        this.unitCapacity = 0;
    }

    public List<Buildable> getBuildables() { return buildables; }

    public void setBuildables(List<Buildable> buildables) {
        this.buildables = buildables;
    }

    public float getMovementSpeed() { return movementSpeed; }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getRangeAttackDamage() {
        return rangeAttackDamage;
    }

    public void setRangeAttackDamage(int rangeAttackDamage) {
        this.rangeAttackDamage = rangeAttackDamage;
    }

    public int getMeleeAttackDamage() {
        return meleeAttackDamage;
    }

    public void setMeleeAttackDamage(int meleeAttackDamage) {
        this.meleeAttackDamage = meleeAttackDamage;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public float getHpRegen() {
        return hpRegen;
    }

    public void setHpRegen(float hpRegen) {
        this.hpRegen = hpRegen;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(float maxHp) {
        this.maxHp = maxHp;
    }

    public float getMeleeAttackSpeed() {
        return meleeAttackSpeed;
    }

    public void setMeleeAttackSpeed(float meleeAttackSpeed) {
        this.meleeAttackSpeed = meleeAttackSpeed;
    }

    public float getRangeAttackSpeed() {
        return rangeAttackSpeed;
    }

    public void setRangeAttackSpeed(float rangeAttackSpeed) {
        this.rangeAttackSpeed = rangeAttackSpeed;
    }

    public int getRangeAttackRange() {
        return rangeAttackRange;
    }

    public void setRangeAttackRange(int rangeAttackRange) {
        this.rangeAttackRange = rangeAttackRange;
    }

    public float getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(float projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public float getAoeRadius() {
        return aoeRadius;
    }

    public void setAoeRadius(float aoeRadius) {
        this.aoeRadius = aoeRadius;
    }

    public float getLeadership() {
        return leadership;
    }

    public void setLeadership(float leadership) {
        this.leadership = leadership;
    }

    public float getCombat() {
        return combat;
    }

    public void setCombat(float combat) {
        this.combat = combat;
    }

    public float getSneak() { return sneak; }

    public void setSneak(float sneak) {
        this.sneak = sneak;
    }

    public float getResearch() {
        return research;
    }

    public void setResearch(float research) {
        this.research = research;
    }

    public float getMining() {
        return mining;
    }

    public void setMining(float mining) {
        this.mining = mining;
    }

    public float getManufacturing() {
        return manufacturing;
    }

    public void setManufacturing(float manufacturing) {
        this.manufacturing = manufacturing;
    }

    public float getFarming() {
        return farming;
    }

    public void setFarming(float farming) {
        this.farming = farming;
    }

    public float getArtisanship() {
        return artisanship;
    }

    public void setArtisanship(float artisanship) {
        this.artisanship = artisanship;
    }

    public float getTradesmanship() {
        return tradesmanship;
    }

    public void setTradesmanship(float tradesmanship) {
        this.tradesmanship = tradesmanship;
    }

    public float getConstruction() { return construction; }

    public void setConstruction(float construction) { this.construction = construction; }

    public int getUnitCapacity() {
        return unitCapacity;
    }

    public void setUnitCapacity(int unitCapacity) {
        this.unitCapacity = unitCapacity;
    }
}
