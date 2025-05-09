package src.actions;

import src.entities.*;
import src.items.*;
import src.tsw.*;

public class EatingAction implements Action {
    private Time gameTime;
    private Item itemToEat;

    public EatingAction(Time gameTime, Item itemToEat) {
        this.gameTime = gameTime;
        this.itemToEat = itemToEat;
    }

    @Override
    public boolean execute(Player player) {
        int itemAmount = player.getPlayerInventory().getItemAmount(itemToEat);
        if (itemAmount <= 0) {
            System.out.println("Anda tidak memiliki " + itemToEat.getItemName() + " untuk dimakan.");
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
            System.out.println(itemToEat.getItemName() + " tidak memberikan energi apapun.");
            return false;
        }

        System.out.println("Makan " + itemToEat.getItemName() + "...");

        player.addPlayerEnergy(energyToRestore);
        player.getPlayerInventory().removeItem(itemToEat, 1);
        gameTime.skipTimeMinute(5);

        System.out.println("Berhasil makan " + itemToEat.getItemName() + ". Energi bertambah " + energyToRestore + ".");
        System.out.println("Energi sekarang: " + player.getEnergy());

        return true;
    }
}
