package src.actions;

import src.entities.*;
import src.tsw.Time;

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
        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action");
            return false;
        }

        player.subtractPlayerEnergy(ENERGY_COST);
        gameTime.skipTimeMinute(TIME_COST_MINUTES);

        System.out.println("You visited " + destination);
        return true;
    }

}