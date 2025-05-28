package main.java.actions;

import main.java.entities.*;
import main.java.map.NPCHomeManager;

public class ChattingAction implements Action {
    private NPC npcToChatWith;
    private int ENERGY_COST = 10;
    private int TIME_COST = 10;
    private int HEART_POINTS_GAINED = 10;

    public ChattingAction(NPC npcToChatWith) {
        this.npcToChatWith = npcToChatWith;
    }

    @Override
    public boolean execute(Player player) {
        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action");
            return false;
        }
        
        String npcHome = NPCHomeManager.getHomeNameByNPC(npcToChatWith);
        if (npcHome == null || !player.getPlayerLocation().getName().equals(npcHome)) {
            System.out.println("You need to be in the same place to chat with " + npcToChatWith.getNpcName());
            return false;
        }

        npcToChatWith.increaseHeartPoints(HEART_POINTS_GAINED);
        player.setEnergy(player.getEnergy() - ENERGY_COST);
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        farm.getTime().skipTimeMinute(TIME_COST);
        System.out.println("You chat with " + npcToChatWith.getNpcName());
        System.out.println("Yap yap yap...");
        return true;
    }

    
}
