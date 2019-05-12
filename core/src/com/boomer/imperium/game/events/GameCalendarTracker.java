package com.boomer.imperium.game.events;

import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.configs.GameContextInterface;

public final class GameCalendarTracker implements TimedUpdateable {

    private final EventManager eventManager;
    private float timeAccumulated;
    private final float secondsPerGameDay;
    private int daysPassed;
    private int weeksPassed;
    private int monthsPassed;
    private int yearsPassed;

    public GameCalendarTracker(GameContextInterface gameContext) {
        this.eventManager = gameContext.getEventManager();
        this.secondsPerGameDay = gameContext.getGameConfigs().secondsPerGameDay;
        this.timeAccumulated = 0f;
        this.daysPassed = 0;
        this.weeksPassed = 0;
        this.monthsPassed = 0;
        this.yearsPassed = 0;
    }

    @Override
    public void update(float deltaTime) {
        timeAccumulated = timeAccumulated + deltaTime;
        if (timeAccumulated >= secondsPerGameDay) {
            timeAccumulated = 0f;
            daysPassed++;
            eventManager.raiseEvent(EventType.DAY_PASSED)
                    .getParams().putParameter(Parameters.Key.DAYS, daysPassed);
            if (daysPassed % 7 == 0) {
                weeksPassed++;
                eventManager.raiseEvent(EventType.WEEK_PASSED)
                        .getParams().putParameter(Parameters.Key.WEEKS, weeksPassed);
            }
            if (daysPassed % 30 == 0) {
                monthsPassed++;
                eventManager.raiseEvent(EventType.MONTH_PASSED)
                        .getParams().putParameter(Parameters.Key.MONTHS, monthsPassed);
            }
            if (daysPassed % 365 == 0) {
                yearsPassed++;
                eventManager.raiseEvent(EventType.YEAR_PASSED)
                        .getParams().putParameter(Parameters.Key.YEARS,yearsPassed);
            }
        }
    }

    public int getWeeksPassed() {
        return weeksPassed;
    }

    public int getMonthsPassed() {
        return monthsPassed;
    }

    public int getYearsPassed() {
        return yearsPassed;
    }

    public int getDaysPassed() {

        return daysPassed;
    }

    public interface Listener {
        void dayPassed(int daysPassed);

        void weekPassed(int weeksPassed);

        void monthPassed(int monthsPassed);

        void yearPassed(int yearsPassed);
    }

}
