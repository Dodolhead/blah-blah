package src.actions;

import src.entities.*;
import src.map.Point;

public class WateringAction implements Action {
    private int energyCost;
    private int timeCost;
    private Point tile;

    public WateringAction(Point tile){
        this.energyCost = 5;
        this.timeCost = 5;
        this.tile = tile;
    }

        /*============= GETTER =============== */
    public int getEnergyCost(){
        return energyCost;
    }

    public int getTimeCost(){
        return timeCost;
    }

    public Point getTile(){
        return tile;
    }

    /*============= SETTER =============== */
    public void setEnergyCost(int energyCost){
        this.energyCost = energyCost;
    }

    public void setTimeCost(int timeCost){
        this.timeCost = timeCost;
    }

    public void setTile(Point tile){
        this.tile = tile;
    }

    /*========== OTHER METHOD =========== */
    //jujur gw bingung apa lagi yg perlu dilakuin buat nge-"watering"
    @Override
    public boolean execute(Player p){
        if (p.getEnergy() < energyCost) {
            return false;
        }
    
        // Substract energi dan waktu
        p.setEnergy(p.getEnergy() - energyCost);
        Farm farm = FarmManager.getFarmByName(p.getFarm());
        farm.getTime().skipTimeMinute(timeCost);
    
        return true;
    }
}

