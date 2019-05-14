package com.boomer.imperium.game.events;

import com.boomer.imperium.core.TimedUpdateable;
import com.boomer.imperium.game.configs.GameContextInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class GameCalendarTracker implements TimedUpdateable {

    private static final int[] MONTH_LENGHTS = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private final EventManager eventManager;
    private float timeAccumulated;
    private final float secondsPerGameDay;
    private int daysPassed;
    private int monthDayCounter;
    private int weeksPassed;
    private int monthsPassed;
    private int yearsPassed;
    private final Calendar calendar;

    public GameCalendarTracker(GameContextInterface gameContext) {
        this.eventManager = gameContext.getEventManager();
        this.secondsPerGameDay = gameContext.getGameConfigs().secondsPerGameDay;
        this.timeAccumulated = 0f;
        this.daysPassed = 0;
        this.monthDayCounter = 0;
        this.weeksPassed = 0;
        this.monthsPassed = 0;
        this.yearsPassed = 0;
        this.calendar = Calendar.getInstance();
        try {
            this.calendar.setTime(new SimpleDateFormat().parse(gameContext.getGameConfigs().gameStartingDate));
        } catch (Exception e) {
            this.calendar.set(999,Calendar.DECEMBER,31);
        }
    }

    @Override
    public void update(float deltaTime) {
        timeAccumulated = timeAccumulated + deltaTime;
        if (timeAccumulated >= secondsPerGameDay) {
            timeAccumulated = 0f;
            daysPassed++;
            monthDayCounter++;
            this.calendar.add(Calendar.DATE,1);
            eventManager.raiseEvent(EventType.DAY_PASSED)
                    .getParams().putParameter(Parameters.Key.DAYS, daysPassed);
            if (daysPassed % 7 == 0) {
                weeksPassed++;
                eventManager.raiseEvent(EventType.WEEK_PASSED)
                        .getParams().putParameter(Parameters.Key.WEEKS, weeksPassed);
            }
            if (monthDayCounter % currentMonthLength() == 0) {
                monthDayCounter = 0;
                monthsPassed++;
                eventManager.raiseEvent(EventType.MONTH_PASSED)
                        .getParams().putParameter(Parameters.Key.MONTHS, monthsPassed);
            }
            if (daysPassed % 365 == 0) {
                yearsPassed++;
                eventManager.raiseEvent(EventType.YEAR_PASSED)
                        .getParams().putParameter(Parameters.Key.YEARS, yearsPassed);
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

    private boolean isLeapYear() { return yearsPassed % 4 == 0; }

    private int currentMonthLength(){
        int leapYearTick = isLeapYear() && (monthsPassed % 12)==1  ? 1 : 0;
        return MONTH_LENGHTS[monthsPassed % 12]+leapYearTick;
    }

    public interface Listener {
        void dayPassed(int daysPassed);

        void weekPassed(int weeksPassed);

        void monthPassed(int monthsPassed);

        void yearPassed(int yearsPassed);
    }

}
