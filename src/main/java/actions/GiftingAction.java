package actions;

import entities.*;
import items.*;

public class GiftingAction implements Action {
    private int ENERGY_COST = 5;
    private int TIME_COST = 10;
    private Item giftitem;
    private NPC recipient;
    private int heartPoints;
    private int amount;


    public GiftingAction(Item giftitem, NPC recipient, int amount){
        this.giftitem = giftitem;
        this.recipient = recipient;
        this.amount = amount;

        if (recipient.getLovedItem().contains(giftitem)){
            this.heartPoints = 25;
        } else if(recipient.getLikedItem().contains(giftitem)){
            this.heartPoints = 20;
        } else if(recipient.getHatedItem().contains(giftitem)){
            this.heartPoints = -25;
        } else{
            this.heartPoints = 0;
        }
    }

    /*============= GETTER =============== */
    public Item getGiftItem(){
        return giftitem;
    }    

    public NPC getRecipient(){
        return recipient;
    }

    public int getAmount(){
        return amount;
    }

    /*============= SETTER =============== */
    public void setGiftItem(Item gifItem){
        this.giftitem = gifItem;
    }

    public void setRecipient(NPC recipient){
        this.recipient = recipient;
    }

    /*========== OTHER METHOD =========== */
    @Override
    public boolean execute(Player p){
        if (p.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action");
            return false;
        }

        if (!p.getPlayerInventory().hasItemAndAmount(giftitem.getItemName(), amount)){
            System.out.println("You don't have the item you want to gift");
            return false;
        } 
    
        // Substract energi dan waktu
        p.setEnergy(p.getEnergy() - ENERGY_COST);
        Farm farm = FarmManager.getFarmByName(p.getFarm());
        farm.getTime().skipTimeMinute(TIME_COST);
    
        // Update heartPoints NPC
        recipient.increaseHeartPoints(heartPoints);
    
        // Berikan item ke NPC
        recipient.receiveGift(giftitem, amount);
    
        // Hapus item dari inventory
        p.getPlayerInventory().removeItem(giftitem, amount);

        System.out.println("Successfully gifted the item, earned " + heartPoints + " heart points.");
        return true;
    }

}
