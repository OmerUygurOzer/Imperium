package com.boomer.imperium.game.entities.units.orders;

import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.entities.units.Unit;
import com.boomer.imperium.game.map.Tile;

public class UnitOrders implements TimedUpdateable {

    private final Move move;
    private final Attack attack;

    private UnitOrder currentOrder;

    public UnitOrders(Unit unit){
        this.move = new Move(unit);
        this.attack = new Attack(unit);
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

}
