package com.boomer.imperium.game.entities.units.orders;

import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.events.GameCalendarTracker;

public interface UnitOrder extends TimedUpdateable,GameCalendarTracker.Listener {
    boolean completed();
    void reset();
}
