package com.boomer.imperium.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.boomer.imperium.game.*;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.graphics.FrameCounter;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;

public class Unit implements Entity {

    private final GameWorld gameWorld;
    public final Bounds bounds;
    private final UnitMovement unitMovement;
    private final UnitOrders unitOrders;
    private UnitState state;
    private int memoryIndex;
    private Layer unitLayer;
    private UnitSpriteAnimator unitSpriteAnimator;
    private FrameCounter frameCounter;
    private Direction facing;
    private int tileX, tileY;

    private float movementSpeed;
    private boolean canRangeAttack;
    private boolean canMeleeAttack;
    private boolean canWieldShield;
    private int rangeAttackDamage;
    private int meleeAttackDamage;
    private int armor;
    private int hpRegen;
    private int hp;
    private float attackSpeed;

    //temp
    private boolean selected;
    private Sprite selectedSprite;

    public Unit(GameConfigs gameConfigs, Resources resources,GameWorld gameWorld, UnitSpriteAnimator spriteAnimator, Layer layer) {
        this.gameWorld = gameWorld;
        this.unitOrders = new UnitOrders();
        this.unitMovement = new UnitMovement(gameConfigs.tileSize, 1f);
        this.facing = Direction.NE;
        this.unitLayer = layer;
        this.state = UnitState.MOVING;
        this.unitSpriteAnimator = spriteAnimator;
        this.tileX = 0;
        this.tileY = 0;
        this.frameCounter = new FrameCounter(8f, 8);
        Tile tile = gameWorld.map.getTileAt(MathUtils.random(1, 5), MathUtils.random(5, 10));
        this.bounds = new Bounds(tile.bounds.center.x, tile.bounds.center.y, gameConfigs.tileSize, gameConfigs.tileSize);
        this.unitOrders.targetTileX = tile.tileX + 1;
        this.unitOrders.targetTileY = tile.tileY + 1;
        UnitMovement.adjustMovementForTarget(gameConfigs,unitMovement, bounds, gameWorld.map.getTileAt(tile.tileX() + 1, tile.tileY() + 1).bounds);
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
                state = UnitState.IDLE;
                bounds.setCenterTile(gameWorld.map.getTileAt(unitOrders.targetTileX, unitOrders.targetTileY));
            }
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        unitSpriteAnimator.draw(spriteBatch, frameCounter.currentFrame, bounds, facing, state);
        if(selected){
            spriteBatch.draw(selectedSprite,bounds.boundRectangle.x,bounds.boundRectangle.y,bounds.width,bounds.height);
        }
    }

    @Override
    public void select() {
        selected = true;
        System.out.println("SELECTED UNIT");
    }

    @Override
    public void deSelect() {

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
    public Bounds getBounds() {
        return bounds;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public void setCanRangeAttack(boolean canRangeAttack) {
        this.canRangeAttack = canRangeAttack;
    }

    public void setCanMeleeAttack(boolean canMeleeAttack) {
        this.canMeleeAttack = canMeleeAttack;
    }

    public void setCanWieldShield(boolean canWieldShield) {
        this.canWieldShield = canWieldShield;
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



}
