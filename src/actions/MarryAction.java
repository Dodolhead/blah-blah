package src.actions;

import src.entities.*;

public class MarryAction implements Action {
    private int energyCost ;
    private int timeCost;
    private int heartPoints;

    public MarryAction(){
        this.energyCost = 80;
        this.timeCost = 22;
        this.heartPoints = 150;
    }

    /*============= GETTER =============== */
    public int getEnergyCost(){
        return energyCost;
    }

    public int getTimeCost(){
        return timeCost;
    }

    public int getHeartPoints(){
        return heartPoints;
    }

    /*============= SETTER =============== */
    public void setEnergyCost(int energyCost){
        this.energyCost = energyCost;
    }

    public void setTimeCost(int timeCost){
        this.timeCost = timeCost;
    }

    public void setHeartPoints(int heartPoints){
        this.heartPoints = heartPoints;
    }

    /*========== OTHER METHOD =========== */
    @Override
    public boolean execute(Player p){
        if (p.getEnergy() < energyCost) {
            return false;
        } 
        p.setEnergy(p.getEnergy() - energyCost);
        p.setHeartPoints(p.getHeartPoints() + heartPoints);
        Farm farm = FarmManager.getFarmByName(p.getFarm());
        farm.getTime().skipTimeMinute(timeCost);
        return true;

    }
}
