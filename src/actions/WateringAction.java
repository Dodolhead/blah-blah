package src.actions;

import src.entities.*;
import src.map.Point;

public class WateringAction implements Action {
    private int ENERGY_COST = 5;
    private int TIME_COST = 5;
    private Point tile;

    public WateringAction(Point tile){
        this.tile = tile;
    }

    /*============= GETTER =============== */

    public Point getTile(){
        return tile;
    }

    /*============= SETTER =============== */

    public void setTile(Point tile){
        this.tile = tile;
    }

    /*========== OTHER METHOD =========== */
    //jujur gw bingung apa lagi yg perlu dilakuin buat nge-"watering"
    @Override
    public boolean execute(Player player){
        if (!player.getPlayerInventory().hasItem("Watering Can")){
            return false;
        }

        if (player.getEnergy() < ENERGY_COST) {
            return false;
        }
    
        // Substract energi dan waktu
        player.setEnergy(player.getEnergy() - ENERGY_COST);
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        farm.getTime().skipTimeMinute(TIME_COST);
    
        return true;
    }
}

