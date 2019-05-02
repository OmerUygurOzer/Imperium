package com.boomer.imperium.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.boomer.imperium.game.*;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.events.EventManager;
import com.boomer.imperium.game.graphics.FrameCounter;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;
import com.boomer.imperium.game.map.Path;
import com.boomer.imperium.game.map.PathFinder;
import com.boomer.imperium.game.map.PathTracker;

public final class Unit implements Entity {

    private final GameContextInterface gameContext;
    public final Rectangle bounds;
    private final UnitMovement unitMovement;
    private final UnitOrders unitOrders;
    private final Path unitPath;
    private final PathTracker pathTracker;
    private int memoryIndex;

    private UnitSpriteAnimator unitSpriteAnimator;
    private FrameCounter frameCounter;
    private Direction facing;
    private int tileX, tileY;


    private UnitState state;
    private int tileCount;  //width and height in tiles
    private Layer unitLayer;

    private Nation nation;
    private Player player;

    private int typeFlags;
    private int componentFlags;
    private int stateFlags;

    private float movementSpeed;
    private int rangeAttackDamage;
    private int meleeAttackDamage;
    private int armor;
    private float hpRegen;
    private int hp;
    private int maxHp;
    private float meleeAttackSpeed;
    private float rangeAttackSpeed;
    private int rangeAttackAoe;
    private int rangeAttackRange;
    private float projectileSpeed;

    //temp
    private boolean selected;
    private Sprite selectedSprite;

    public Unit(GameContextInterface gameContext) {
        this.gameContext = gameContext;
        this.unitOrders = new UnitOrders();
        this.unitMovement = new UnitMovement(gameContext, this, gameContext.getGameConfigs().tileSize, 5f);
        this.unitPath = new Path(gameContext.getGameConfigs());
        this.tileX = 0;
        this.tileY = 0;
        this.frameCounter = new FrameCounter(8f, 8);
        this.state = UnitState.IDLE;
        Tile tile = gameContext.getGameWorld().map.getTileAt(0, 0);
        this.bounds = new Rectangle(tile.bounds.x, tile.bounds.y, gameContext.getGameConfigs().tileSize,
                gameContext.getGameConfigs().tileSize);
        this.selectedSprite = gameContext.getGameResources().inGameCursor;
        this.pathTracker = new PathTracker(gameContext, this, gameContext.getGameWorld().map, unitMovement);
    }

    @Override
    public void update(float deltaTime) {
        frameCounter.update(deltaTime);
        if (state.equals(UnitState.MOVING)) {
            pathTracker.update(deltaTime);
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        unitSpriteAnimator.draw(spriteBatch, frameCounter.currentFrame, bounds, facing, state);
        if (selected)
            spriteBatch.draw(selectedSprite, bounds.x, bounds.y, bounds.width, bounds.height);

    }

    @Override
    public void targetTile(Tile tile) {
        PathFinder.findPath(unitPath, gameContext.getGameWorld().map, gameContext.getGameWorld().map.getTileAt(tileX, tileY), tile);
        pathTracker.activate(unitPath);
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
        bounds.set(tile.bounds);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    @Override
    public void setArmor(int armor) {
        this.armor = armor;
    }

    @Override
    public void setLayer(Layer layer) {
        this.unitLayer = layer;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public void setRangeAttackDamage(int rangeAttackDamage) {
        this.rangeAttackDamage = rangeAttackDamage;
    }

    @Override
    public void setMeleeAttackDamage(int meleeAttackDamage) {
        this.meleeAttackDamage = meleeAttackDamage;
    }

    @Override
    public int getHp() {
        return this.hp;
    }

    @Override
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    @Override
    public int getMaxHp() {
        return this.maxHp;
    }

    @Override
    public void setHpRegen(float regenPerSecond) {
        this.hpRegen = regenPerSecond;
    }

    @Override
    public float getHpRegen() {
        return this.hpRegen;
    }

    @Override
    public void setMeleeAttackSpeed(float attackPerSecond) {
        this.meleeAttackSpeed = attackPerSecond;
    }

    @Override
    public float getMeleeAttackSpeed() {
        return this.meleeAttackSpeed;
    }

    @Override
    public void setRangeAttackSpeed(float attackPerSecond) {
        this.rangeAttackSpeed = attackPerSecond;
    }

    @Override
    public void setAttackRange(int range) {
        this.rangeAttackRange = range;
    }

    @Override
    public void setAttackAOE(int aoe) {
        this.rangeAttackAoe = aoe;
    }

    @Override
    public void setProjectileSpeed(float tilePersecond) {
        this.projectileSpeed = tilePersecond;
    }

    @Override
    public void setMovementSpeed(float tilePerSecond) {
        this.movementSpeed = tilePerSecond;
    }

    @Override
    public float getRangeAttackSpeed() {
        return this.rangeAttackSpeed;
    }

    @Override
    public int getAttackRange() {
        return this.rangeAttackRange;
    }

    @Override
    public int getAttackAOE() {
        return rangeAttackAoe;
    }

    @Override
    public float getProjectileSpeed() {
        return this.projectileSpeed;
    }

    @Override
    public float getMovementSpeed() {
        return this.movementSpeed;
    }

    @Override
    public int getArmor() {
        return this.armor;
    }

    @Override
    public void setTypeFlags(int typeFlags) {
        this.typeFlags = typeFlags;
    }

    @Override
    public int getTypeFlags() {
        return this.typeFlags;
    }

    @Override
    public void setComponentFlags(int componentFlags) {
        this.componentFlags = componentFlags;
    }

    @Override
    public int getComponentFlags() {
        return this.componentFlags;
    }

    @Override
    public void setStateFlags(int stateFlags) {
        this.stateFlags = stateFlags;
    }

    @Override
    public int getStateFlags() {
        return this.stateFlags;
    }

    @Override
    public int getMeleeAttackDamage() {
        return this.meleeAttackDamage;
    }

    @Override
    public int getRangeAttackDamage() {
        return this.rangeAttackDamage;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setNation(Nation nation) {
        this.nation = nation;
    }

    @Override
    public Nation getNation() {
        return this.nation;
    }

    @Override
    public void setCenterInTiles(int tileCount) {
        this.tileCount = tileCount;
    }

    @Override
    public int getCenterInTiles() {
        return this.tileCount;
    }


    public void setState(UnitState state) {
        this.state = state;
    }

    public void setUnitLayer(Layer unitLayer) {
        this.unitLayer = unitLayer;
    }

    public void setUnitSpriteAnimator(UnitSpriteAnimator unitSpriteAnimator) {
        this.unitSpriteAnimator = unitSpriteAnimator;
    }

    public void placeInTile(int tileX, int tileY) {
        Tile tile = gameContext.getGameWorld().map.getTileAt(tileX, tileY);
        this.tileX = tile.tileX;
        this.tileY = tile.tileY;
        bounds.set(tile.bounds);
    }

    public void setTile(int x, int y) {
        this.tileX = x;
        this.tileY = y;
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
    public boolean shouldRemove() {
        return state.equals(UnitState.DEAD);
    }



    @Override
    public void reset() {
//width and height in tiles
        this.state = UnitState.IDLE;
        this.tileCount = 0;
        this.unitLayer = Layer.TILES;

        this.nation = null;
        this.player = null;

        this.typeFlags = 0;
        this.componentFlags = 0;
        this.stateFlags = 0;

        this.movementSpeed = 0f;
        this.rangeAttackDamage = 0;
        this.meleeAttackDamage = 0;
        this.armor = 0;
        this.hpRegen = 0f;
        this.hp = 0;
        this.maxHp = 0;
        this.meleeAttackSpeed = 0f;
        this.rangeAttackSpeed= 0f;
        this.rangeAttackAoe = 0;
        this.rangeAttackRange = 0;
        this.projectileSpeed = 0f;
    }
}
