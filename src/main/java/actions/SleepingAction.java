package actions;

import entities.*;
import tsw.*;

public class SleepingAction implements Action {
    private static final int MAX_ENERGY = 100;

    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        Time gameTime = farm.getTime();

        if (!gameTime.isNight()){
            System.out.println("You can only sleep at night!");
            return false;
        }

        if (!player.getPlayerLocation().getName().equals("House")){
            System.out.println("You need to be in the house to sleep.");
            return false;
        }

        System.out.println("Sleeping...");

        int currentEnergy = player.getEnergy();
        int restoredEnergy;

        if (currentEnergy <= 0) {
            restoredEnergy = 10;
            System.out.println("Your energy hits 0 or lower. Energy restored: " + restoredEnergy + " points.");
        } else if (currentEnergy < (0.1 * MAX_ENERGY)) {
            restoredEnergy = MAX_ENERGY / 2;
            System.out.println("Your energy was low. Energy restored: " + restoredEnergy + " points.");
        } else {
            restoredEnergy = MAX_ENERGY;
            System.out.println("Energy restored to maximum: " + restoredEnergy + " points.");
        }

        player.setEnergy(currentEnergy + restoredEnergy);

        int currentHour = gameTime.getHour();
        int hoursToSkip = (currentHour >= 6) ? (24 - currentHour + 6) : (6 - currentHour);

        gameTime.skipTimeHour(hoursToSkip);

        System.out.println("You have slept for " + hoursToSkip + " hours. Now is " + gameTime.getCurrentTime() + ", day-" + gameTime.getDay() + ", your energy is " + player.getEnergy() + ".");

        return true;

    }
}
