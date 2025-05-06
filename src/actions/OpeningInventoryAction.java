package src.actions;

import src.entities.Player;

public class OpeningInventoryAction implements Action {
    @Override
    public boolean execute(Player player) {
        player.getPlayerInventory().printInventory();
        return true;
    }
    
}
