package com.boomer.imperium.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boomer.imperium.game.*;
import com.boomer.imperium.game.graphics.FrameCounter;
import com.boomer.imperium.game.graphics.UnitSpriteAnimator;

public class Unit implements Entity {

    private final GameWorld gameWorld;
    public final Bounds bounds;
    private final UnitMovement unitMovement;
    private UnitState state;
    private int memoryIndex;
    private Layer unitLayer;
    private UnitSpriteAnimator unitSpriteAnimator;
    private FrameCounter frameCounter;
    private Direction facing;
    private int tileX,tileY;

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

    public Unit(GameWorld gameWorld,UnitSpriteAnimator spriteAnimator, Layer layer){
        this.gameWorld = gameWorld;
        this.unitMovement = new UnitMovement(Tile.SIZE,1f);
        this.facing = Direction.NE;
        this.unitLayer = layer;
        this.state = UnitState.MOVING;
        this.unitSpriteAnimator = spriteAnimator;
        Tile tile = gameWorld.map.getTileAt(0,0);
        this.bounds = new Bounds(tile.bounds.center.x,tile.bounds.center.y,Tile.SIZE,Tile.SIZE);
        this.tileX = 0;
        this.tileY = 0;
        this.frameCounter = new FrameCounter(8f,8);
        UnitMovement.adjustMovementForTarget(unitMovement,bounds,gameWorld.map.getTileAt(1,1).bounds);
    }
    @Override
    public void update(float deltaTime) {
        frameCounter.update(deltaTime);
        if(state.equals(UnitState.MOVING)){
            unitMovement.update(deltaTime);
            if(unitMovement.updateBounds(this)){
                unitMovement.setLength(0);
                state = UnitState.IDLE;
            }
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        unitSpriteAnimator.draw(spriteBatch,frameCounter.currentFrame,bounds,facing,state);
    }

    @Override
    public int tileX() {
        return tileX;
    }

    @Override
    public int tileY() {
        return tileY;
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
