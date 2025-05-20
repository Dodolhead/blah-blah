package actions;

import entities.*;
import map.*;
import tsw.*;

public class WateringAction implements Action {
    private int ENERGY_COST = 5;
    private int TIME_COST = 5;


    /*========== OTHER METHOD =========== */
    //jujur gw bingung apa lagi yg perlu dilakuin buat nge-"watering"
    @Override
    public boolean execute(Player player){
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        FarmMap farmMap = farm.getFarmMap();
        Time gameTime = farm.getTime();
        if (!player.getPlayerInventory().hasItem("Watering Can")){
            return false;
        }

        if (player.getEnergy() < ENERGY_COST) {
            return false;
        }
    

        Point pos = player.getPlayerLocation().getCurrentPoint();
        int x = pos.getX();
        int y = pos.getY();
        char[][] map = farmMap.getFarmMapDisplay();

        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            System.out.println("You can't water outside the farm area.");
            return false;
        }

        if (map[y][x] != 't') {
            System.out.println("Can't water this tile.");
            return false;
        }

        if (map[y][x] == 't') {
            System.out.println("You watered the land.");
        }
        
        player.setEnergy(player.getEnergy() - ENERGY_COST);
        gameTime.skipTimeMinute(TIME_COST);
    
        return true;
    }
}

