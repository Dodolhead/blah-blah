package src.actions;

import src.entities.Player;

public class OpeningInventoryAction implements Action {
    
    @Override
    public boolean execute(Player player) {
        if (player == null) {
            System.out.println("Error: Pemain tidak ditemukan");
            return false;
        }
        player.getPlayerInventory().printInventory();
        return true;
    }
}