package src.actions;

import src.entities.*;
import src.map.*;

public class MarryingAction implements Action {
    private int ENERGY_COST = 80;
    private int TIME_TARGET_IN_HOUR = 22;
    private NPC targetNpc;

    public MarryingAction(NPC targetNpc){
        this.targetNpc = targetNpc;
    }

    public NPC getTargetNpcToMarry(){
        return targetNpc;
    }

    /*========== OTHER METHOD =========== */
    @Override
    public boolean execute(Player player){
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        int currentDay = farm.getTime().getDay();
        int proposedDay = targetNpc.getProposedDay();

        if (proposedDay == -1 || currentDay <= proposedDay) {
            System.out.println("You must propose or wait at least one day after proposing to marry.");
            return false;
        }

        if (!targetNpc.getRelationshipStatus().equals("Fiance")){
            System.out.println("You can't marry " + targetNpc.getNpcName());
            return false;
        }
        
        if (!player.getPlayerInventory().hasItem("Proposal Ring")){
            System.out.println("You don't have the required item.");
            return false;
        }  

        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action.");
            return false;
        } 

        player.setEnergy(player.getEnergy() - ENERGY_COST);
        farm.getTime().skipTimeMinute(farm.getTime().timeDifference(TIME_TARGET_IN_HOUR, 0));

        player.setPlayerLocation(new Location("House", new Point(8, 8)));
        System.out.println("Congrats! you got married with " + targetNpc.getNpcName() + " 🎉🎉🎉");
        player.setPartner(targetNpc);
        return true;

    }
}
