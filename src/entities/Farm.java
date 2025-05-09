package src.entities;

import src.map.*;
import src.tsw.*;

public class Farm {
    private String farmName;
    private Player farmOwnerPlayer;
    private FarmMap farmMap;
    private Time time;

    public Farm(String farmName, Player farmOwnerPlayer) {
        this.farmName = farmName;
        this.farmOwnerPlayer = farmOwnerPlayer;
        farmMap = new FarmMap(farmOwnerPlayer.getPlayerLocation().getCurrentPoint());
        time = new Time();
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
