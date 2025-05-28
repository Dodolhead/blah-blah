package main.java.actions;

import main.java.entities.*;
import main.java.items.*;
import main.java.tsw.*;

public class EatingAction implements Action {
    private Item itemToEat;
    private static final int TIME_COST = 5;

    public EatingAction(Item itemToEat) {
        this.itemToEat = itemToEat;
    }

    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        Time gameTime = farm.getTime();

        int itemAmount = player.getPlayerInventory().getItemAmount(itemToEat);
        if (itemAmount <= 0) {
            System.out.println("You don't have a" + itemToEat.getItemName() + " to be eaten!");
            return false;
        }

        int energyToRestore = 0;
        if (itemToEat instanceof Food) {
            energyToRestore = ((Food) itemToEat).getGiveEnergy();
        } else if (itemToEat instanceof Crop) {
            energyToRestore = 3;
        } else if (itemToEat instanceof Fish) {
            energyToRestore = 1;
        }

        if (energyToRestore <= 0) {
            System.out.println(itemToEat.getItemName() + " can't be eaten.");
            return false;
        }

        System.out.println("Eating " + itemToEat.getItemName() + "...");

        player.addPlayerEnergy(energyToRestore);
        player.getPlayerInventory().removeItem(itemToEat, 1);
        gameTime.skipTimeMinute(TIME_COST);

        System.out.println("You have eaten a " + itemToEat.getItemName() + ". Your current energy: " +  player.getEnergy() + ".");

        return true;
    }
}
