package src.actions;

import src.entities.Player;

public class OpenInventory implements Action {
    
    @Override
    public boolean execute(Player player) {
        if (player == null) {
            System.out.println("Error: Pemain tidak ditemukan");
            return false;
        }
        
        System.out.println("\n===== INVENTARIS =====");
        player.getPlayerInventory().printInventory();
        System.out.println("=====================\n");
        
        return true;
    }
}