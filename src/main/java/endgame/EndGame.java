package endgame;

import java.util.Map;

import entities.*;
import tsw.*;

public class EndGame {

    public boolean checkMilestoneAndShowStats(Player player) {
        if (player.getPlayerGold().getGold() >= 17209){
            return true;
        }
        if (player.getPartner() != null){
            return true;
        }
        return false;
    }
    public void endGameStats(Player player, Time time) {
        // 1. Average income dan expenditure
        int avgIncome = countSeasonalAverage(player.totalIncome, time);
        int avgExpenditure = countSeasonalAverage(player.totalExpenditure, time);

        System.out.println("===== END GAME SUMMARY =====");
        System.out.println("Current Gold: " + player.getPlayerGold().getGold());
        System.out.println("Total Income: " + player.totalIncome);
        System.out.println("Total Expenditure: " + player.totalExpenditure);
        System.out.println("Average Season Income: " + avgIncome);
        System.out.println("Average Season Expenditure: " + avgExpenditure);

        // 2. Status semua NPC
        System.out.println("===== NPC STATUS =====");
        for (NPC npc : NPCManager.getNPCList()) {
            System.out.println("NPC: " + npc.getNpcName());
            System.out.println("  Relationship Status: " + npc.getRelationshipStatus());
            System.out.println("  Chatting Frequency: " + npc.chattingFrequency);
            System.out.println("  Gifting Frequency: " + npc.giftingFrequency);
            System.out.println("  Visiting Frequency: " + npc.visitingFrequency);
        }
        System.out.println("Fish caught by rarity:");
        for (Map.Entry<String, Integer> entry : player.fishCaughtByRarity.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("Fish caught by name:");
        for (Map.Entry<String, Integer> entry : player.fishCaughtByName.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }

    public int countSeasonalAverage(int total, Time time) {
        int seasonsPassed = time.getDay() / 10;
        if (seasonsPassed == 0) return 0; 
        return total / seasonsPassed;
    }

}
