package com.boomer.imperium.game.entities.units.orders;

import com.boomer.imperium.core.TimedUpdateable;

public interface UnitOrder extends TimedUpdateable {
    boolean completed();
    void reset();
}
