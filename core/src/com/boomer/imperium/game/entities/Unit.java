package com.boomer.imperium.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.boomer.imperium.game.*;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.graphics.FrameCounter;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;
import com.boomer.imperium.game.map.Path;
import com.boomer.imperium.game.map.PathFinder;

public class Unit implements Entity {

    private final GameWorld gameWorld;
    public final Rectangle bounds;
    private final UnitMovement unitMovement;
    private final UnitOrders unitOrders;
    private final Path unitPath;
    private UnitState state;
    private int memoryIndex;
    private Layer unitLayer;
    private UnitSpriteAnimator unitSpriteAnimator;
    private FrameCounter frameCounter;
    private Direction facing;
    private int tileX, tileY;

    private float movementSpeed;
    private int rangeAttackDamage;
    private int meleeAttackDamage;
    private int armor;
    private int hpRegen;
    private int hp;
    private float attackSpeed;

    //temp
    private boolean selected;
    private Sprite selectedSprite;

    public Unit(GameConfigs gameConfigs, Resources resources, GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.unitOrders = new UnitOrders();
        this.unitMovement = new UnitMovement(gameConfigs,gameConfigs.tileSize, 1f);
        this.unitPath = new Path(gameConfigs);
        this.tileX = 0;
        this.tileY = 0;
        this.frameCounter = new FrameCounter(8f, 8);
        this.state = UnitState.IDLE;
        Tile tile = gameWorld.map.getTileAt(0, 0);
        this.bounds = new Rectangle(tile.bounds.x, tile.bounds.y, gameConfigs.tileSize, gameConfigs.tileSize);
        this.selectedSprite = resources.inGameCursor;
    }

    @Override
    public void update(float deltaTime) {
        frameCounter.update(deltaTime);
        if (state.equals(UnitState.MOVING)) {
            unitMovement.update(deltaTime);
            float mov = unitMovement.updateBounds(this);
            if (mov >= 0.5f && tileX != unitOrders.targetTileX && tileY != unitOrders.targetTileY) {
                gameWorld.map.getTileAt(tileX, tileY).getEntitiesContained().remove(this);
                tileX = unitOrders.targetTileX;
                tileY = unitOrders.targetTileY;
                gameWorld.map.getTileAt(tileX, tileY).getEntitiesContained().add(this);
            } else if (mov >= 1f) {
                unitMovement.setLength(0);
                bounds.set(gameWorld.map.getTileAt(unitOrders.targetTileX, unitOrders.targetTileY).bounds);
                state = UnitState.IDLE;
            }
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        unitSpriteAnimator.draw(spriteBatch, frameCounter.currentFrame, bounds, facing, state);
        if (selected) {
            spriteBatch.draw(selectedSprite, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    @Override
    public void targetTile(Tile tile) {
        PathFinder.findPath(unitPath,gameWorld.map, gameWorld.map.getTileAt(tileX,tileY),tile);
        System.out.println(unitPath.tasks);
        //state = UnitState.MOVING;
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
    public void receiveDamage(Entity from, int damage) {
        if(!state.equals(UnitState.DYING)|!state.equals(UnitState.DEAD))
            return;
        hp=-damage;
        if(hp<=0){
            state = UnitState.DYING;
        }
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public void setRangeAttackDamage(int rangeAttackDamage) {
        this.rangeAttackDamage = rangeAttackDamage;
    }

    public void setMeleeAttackDamage(int meleeAttackDamage) {
        this.meleeAttackDamage = meleeAttackDamage;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setHpRegen(int hpRegen) {
        this.hpRegen = hpRegen;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
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

    public void placeInTile(int tileX , int tileY){
        Tile tile = gameWorld.map.getTileAt(tileX,tileY);
        this.tileX = tile.tileX;
        this.tileY = tile.tileY;
        bounds.set(tile.bounds);
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
    public void setTypeFlags(int typeFlags) {

    }

    @Override
    public int getTypeFlags() {
        return 0;
    }

    @Override
    public void setComponentFlags() {

    }

    @Override
    public int getComponentFlags() {
        return 0;
    }


    @Override
    public void reset() {

    }
}
