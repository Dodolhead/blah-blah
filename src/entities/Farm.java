package src.entities;

import src.map.*;
import src.tsw.*;

public class Farm {
    private String farmName;
    private Player farmOwnerPlayer;
    private FarmMap farmMap;
    private Time time;
    private Season season;
    private Weather weather;

    public Farm(String farmName, Player farmOwnerPlayer) {
        this.farmName = farmName;
        this.farmOwnerPlayer = farmOwnerPlayer;
        farmMap = new FarmMap(farmOwnerPlayer.getPlayerLocation().getCurrentPoint());
        time = new Time();
        season = new Season();
        weather = new Weather();
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

    

    public Season getSeason() {
        return season;
    }

    public Weather getWeather() {
        return weather;
    }

}
