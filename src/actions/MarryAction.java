package src.actions;

import src.entities.*;
import src.map.*;

public class MarryAction implements Action {
    private int ENERGY_COST = 80;
    private int TIME_TARGET_IN_HOUR = 22;
    private NPC targetNpc;

    public MarryAction(NPC targetNpc){
        this.targetNpc = targetNpc;
    }

    public NPC getTargetNpc(){
        return targetNpc;
    }

    /*========== OTHER METHOD =========== */
    @Override
    public boolean execute(Player p){
        // marry harus minimal 1 hari setelah propose kasih kondisi nanti

        
        if (!targetNpc.getRelationshipStatus().equals("Fiance")){
            return false;
        }
        if (!p.getPlayerInventory().hasItem("Proposal Ring")){
            return false;
        }  

        if (p.getEnergy() < ENERGY_COST) {
            return false;
        } 

        p.setEnergy(p.getEnergy() - ENERGY_COST);
        Farm farm = FarmManager.getFarmByName(p.getFarm());
        farm.getTime().skipTimeMinute(farm.getTime().timeDifference(TIME_TARGET_IN_HOUR, 0));
        p.setPlayerLocation(new Location("House", new Point(8, 8)));
        return true;

    }
}
