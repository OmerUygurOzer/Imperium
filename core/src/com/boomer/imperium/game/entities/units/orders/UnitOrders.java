package com.boomer.imperium.game.entities.units.orders;

import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.buildings.Buildable;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.events.GameCalendarTracker;
import com.boomer.imperium.game.map.Tile;

public final class UnitOrders implements TimedUpdateable,GameCalendarTracker.Listener {

    private final Move move;
    private final Attack attack;
    private final Build build;

    private UnitOrder currentOrder;

    public UnitOrders(Unit unit, GameContextInterface gameContext){
        this.move = new Move(unit);
        this.attack = new Attack(unit,gameContext.getGameConfigs());
        this.build = new Build(unit,gameContext);
    }

    @Override
    public void update(float deltaTime) {
        if(currentOrder!=null){
            currentOrder.update(deltaTime);
            if(currentOrder.completed())
                currentOrder = null;
        }
    }

    public void moveToTile(Tile tile){
        resetCurentOrder(move);
        this.move.targetTile(tile);
        this.currentOrder = move;
    }

    public void attackEntity(Entity entity){
        resetCurentOrder(attack);
        this.attack.attackEntity(entity);
        this.currentOrder = attack;
    }

    public void build(Tile tile,Buildable buildable){
        resetCurentOrder(build);
        this.build.build(buildable,tile);
        this.currentOrder = build;
    }

    public boolean completedCurrentOrder(){
        return currentOrder.completed();
    }

    private void resetCurentOrder(UnitOrder order){
        if(currentOrder!=null){
            if(!currentOrder.equals(order)){
                currentOrder.reset();
            }
        }
    }

    @Override
    public void dayPassed(int daysPassed) {
        if(currentOrder!=null)
            currentOrder.dayPassed(daysPassed);
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
