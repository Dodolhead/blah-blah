package src.endgame;

import src.entities.*;
import src.tsw.Time;

public class EndGame {

    private Player player;
    private Time time;

    private int totalIncome;
    private int totalExpenditure;
    private int cropsHarvested;
    private int fishCaughtCommon;
    private int fishCaughtRegular;
    private int fishCaughtLegendary;

    public EndGame(Player player, Time time, int totalIncome, int totalExpenditure,
                   int cropsHarvested, int fishCaughtCommon, int fishCaughtRegular, int fishCaughtLegendary) {
        this.player = player;
        this.time = time;
        this.totalIncome = totalIncome;
        this.totalExpenditure = totalExpenditure;
        this.cropsHarvested = cropsHarvested;
        this.fishCaughtCommon = fishCaughtCommon;
        this.fishCaughtRegular = fishCaughtRegular;
        this.fishCaughtLegendary = fishCaughtLegendary;
    }

    public void checkMilestoneAndShowStats() {
        boolean hasEnoughGold = player.getPlayerGold().getGold() >= 17209;
        boolean isMarried = player.getPartner() != null;

        if (hasEnoughGold || isMarried) {
            showEndGameStatistics();
        }
    }

    private void showEndGameStatistics() {
        int totalDays = time.getDay();
        int totalSeasons = (totalDays - 1) / 10 + 1;

        int averageIncomePerSeason = totalSeasons > 0 ? totalIncome / totalSeasons : 0;
        int averageExpenditurePerSeason = totalSeasons > 0 ? totalExpenditure / totalSeasons : 0;

        System.out.println("===== 🎉 END GAME STATISTICS 🎉 =====");
        System.out.println("Player Name: " + player.getPlayerName());
        System.out.println("Farm Name: " + player.getFarm());
        System.out.println("Total Days Played: " + totalDays);
        System.out.println("Total Income: " + totalIncome + "g");
        System.out.println("Total Expenditure: " + totalExpenditure + "g");
        System.out.println("Average Season Income: " + averageIncomePerSeason + "g");
        System.out.println("Average Season Expenditure: " + averageExpenditurePerSeason + "g");

        System.out.println("\n--- 🌱 Crops & Fishing ---");
        System.out.println("Crops Harvested: " + cropsHarvested);
        System.out.println("Fish Caught (Common): " + fishCaughtCommon);
        System.out.println("Fish Caught (Regular): " + fishCaughtRegular);
        System.out.println("Fish Caught (Legendary): " + fishCaughtLegendary);

        System.out.println("\n--- 🤝 NPC Status ---");
        for (NPC npc : NPCManager.getNPCList()) {
            System.out.println("NPC: " + npc.getNpcName());
            System.out.println("  Relationship Status: " + npc.getRelationshipStatus());
            System.out.println("  Heart Points: " + npc.getHeartPoints());

            // Placeholder data untuk frekuensi interaksi
            // Di sini bisa disesuaikan jika ada sistem pencatatan interaksi
            System.out.println("  Chatting Frequency: (data tidak tersedia)");
            System.out.println("  Gifting Frequency: (data tidak tersedia)");
            System.out.println("  Visiting Frequency: " + 
                (player.getVisitedPlace().contains(npc.getNpcName()) ? "Visited" : "Never Visited"));
        }

        System.out.println("=====================================");
    }
}
