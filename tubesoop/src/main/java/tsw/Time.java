package tsw;

import map.*;

public class Time implements Runnable{
    private int hour = 6;
    private int minute = 0;
    private int day = 1;
    private int lastDay = 1;
    private boolean running = true;
    private boolean paused = false;
    private Store store;
    private final Object lock = new Object();
    Season season;
    Weather weather;

    public Time() {
        Thread timeThread = new Thread(this);
        season = new Season();
        weather = new Weather();
        store = new Store();
        timeThread.start();
    }

    @Override
    public void run() {
        while (running) {
            try {
                synchronized (lock) {
                    while (paused) {
                        lock.wait();
                    }
                }
                advanceTime(5);
                Thread.sleep(1000);
                changeDay();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    

    private synchronized void advanceTime(int minutesToAdd) {
        minute += minutesToAdd;
        if (minute >= 60) {
            hour += minute / 60;
            minute = minute % 60;
        }
        if (hour >= 24) {
            hour = hour % 24;
            day++;
        }
    }

    public synchronized void skipTimeHour(int hoursToAdd) {
        hour += hoursToAdd;
        if (hour >= 24) {
            hour = hour % 24;
            day++;
        }
    }
    public synchronized void skipTimeMinute(int minuteToAdd) {
        advanceTime(minuteToAdd);
    }

    public synchronized String getCurrentTime() {
        return String.format("%02d:%02d", hour, minute);
    }

    public synchronized int getDay() {
        return day;
    }

    public synchronized int getHour() {
        return hour;
    }

    public synchronized int getMinute() {
        return minute;
    }

    public synchronized void changeDay(){
        if (day > lastDay) {
            System.out.println("Day-" + day + " has started");
            weather.nextDayWeather();
            season.updateSeasonByDay(day);
            store.storeChange();
            lastDay = day;
        }
    }

    public synchronized boolean isDay() {
        return hour >= 6 && hour <= 17;
    }

    public synchronized boolean isNight() {
        return !isDay();
    }

    public void stopTime() {
        running = false;
    }

    public void pauseTime() {
        paused = true;
    }

    public void resumeTime() {
        synchronized (lock) {
            paused = false;
            lock.notify();
        }
    }

    public synchronized int timeDifference(int targetHour, int targetMinute) {
        int currentTotal = this.hour * 60 + this.minute;
        int targetTotal = targetHour * 60 + targetMinute;
        return targetTotal - currentTotal;
    }

    public synchronized String getTimeDay() {
        return String.format("SEASON: %s - WEATHER: %s - Day-%d - %02d:%02d", season.getCurrentSeason(), weather.getCurrentWeather() ,day, hour, minute);
    }
    public Season.Seasons getCurrentSeason() {
        return season.getCurrentSeason();
    }

    public Weather.WeatherCondition getCurrentWeather() {
        return weather.getCurrentWeather();
    }
    
    public synchronized void skipDays(int daysToAdd) {
        day += daysToAdd;
        for (int i = 0; i < daysToAdd; i++) {
            changeDay();
        }
    }

    
}
