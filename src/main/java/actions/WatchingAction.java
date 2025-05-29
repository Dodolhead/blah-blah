package actions;

import entities.*;
import tsw.*;

public class WatchingAction implements Action {
    private static final int ENERGY_COST = 5;
    private static final int TIME_COST = 15;
    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        Time gameTime = farm.getTime();
        if (player.getEnergy() < 5) {
            System.out.println("You don't have enough energy to do this action.");
            return false;
        }
        
        if (!player.getPlayerLocation().getName().equals("House")){
            System.out.println("You need to be in the house to watch TV.");
            return false;
        }
        
        player.subtractPlayerEnergy(ENERGY_COST);
        gameTime.skipTimeMinute(TIME_COST);
        
        System.out.println("You have been watching TV for 15 minutes.");
        
        return true;
    }
}