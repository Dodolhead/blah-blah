package tsw;

public class Season {
    public enum Seasons {
        SPRING, SUMMER, FALL, WINTER;
    }

    public Seasons currentSeason;

    public Season() {
        this.currentSeason = Seasons.SPRING;
    }

    public void updateSeasonByDay(int day) {
        Seasons[] allSeasons = Seasons.values();
        int seasonIndex = (day - 1) / 10 % allSeasons.length;
        currentSeason = allSeasons[seasonIndex];
    }


    public Seasons getCurrentSeason() {
        return currentSeason;
    }
}
