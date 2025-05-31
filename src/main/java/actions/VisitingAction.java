package actions;

import entities.*;
import tsw.Time;

public class VisitingAction implements Action {
    private String destination;
    private static final int ENERGY_COST = 10;
    private static final int TIME_COST_MINUTES = 15;

    public VisitingAction(String destination) {
        this.destination = destination;
    }

    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        Time gameTime = farm.getTime();

        player.subtractPlayerEnergy(ENERGY_COST);
        gameTime.skipTimeMinute(TIME_COST_MINUTES);

        NPC visitedNPC = NPCManager.getNPCFromHome(destination);
        if (visitedNPC != null) {
            // Tambah counter visiting
            visitedNPC.visitingFrequency++;
            System.out.println("You visited " + visitedNPC.getNpcName());
        } else {
            System.out.println("You visited " + destination);
        }

        return true;

    }


}