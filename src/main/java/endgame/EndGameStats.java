package endgame;

import java.util.List;
import java.util.Map;

import entities.NPC;
import entities.NPCManager;
import entities.Player;
import tsw.Time;

public class EndGameStats {
    public int totalIncome;
    public int totalExpenditure;
    public int avgIncome;
    public int avgExpenditure;
    public int totalDays;
    public int cropsHarvested;
    public Map<String, Integer> fishByRarity;
    public Map<String, Integer> fishByName;
    public List<NPC> npcList;
    public int currentGold;

    public EndGameStats(Player player, Time time) {
        this.totalIncome = player.totalIncome;
        this.totalExpenditure = player.totalExpenditure;
        this.avgIncome = countSeasonalAverage(player.totalIncome, time);
        this.avgExpenditure = countSeasonalAverage(player.totalExpenditure, time);
        this.totalDays = time.getDay();
        this.cropsHarvested = player.cropsHarvested;
        this.fishByRarity = player.fishCaughtByRarity;
        this.fishByName = player.fishCaughtByName;
        this.npcList = NPCManager.getNPCList(); 
        this.currentGold = player.getPlayerGold().getGold();
    }

    private int countSeasonalAverage(int total, Time time) {
        int seasonsPassed = time.getDay() / 10;
        if (seasonsPassed == 0) return 0;
        return total / seasonsPassed;
    }
}
