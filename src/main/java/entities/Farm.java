package main.java.entities;

import main.java.gui.GamePanel;
import main.java.map.*;
import main.java.tsw.*;

public class Farm {
    private String farmName;
    private Player farmOwnerPlayer;
    private FarmMap farmMap;
    private Time time;
    GamePanel gp;

    public Farm(String farmName, Player farmOwnerPlayer, GamePanel gp) {
        this.gp = gp;
        this.farmName = farmName;
        this.farmOwnerPlayer = farmOwnerPlayer;
        farmMap = new FarmMap(farmOwnerPlayer.getPlayerLocation());
        time = new Time(gp);
        FarmManager.registerFarm(this);
    }

    public String getFarmName() {
        return farmName;
    }

    public Player getFarmOwnerPlayer() {
        return farmOwnerPlayer;
    }

    public FarmMap getFarmMap() {
        return farmMap;
    }

    public Time getTime() {
        return time;
    }

    public Season.Seasons getSeasonFarm() {
        return time.getCurrentSeason();
    }

    public Weather.WeatherCondition getWeatherFarm() {
        return time.getCurrentWeather();
    }

}
