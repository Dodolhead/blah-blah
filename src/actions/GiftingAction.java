package src.actions;

import src.entities.*;
import src.items.*;

public class GiftingAction implements Action {
    private int energyCost ;
    private int timeCost;
    private int heartPoints;
    private Item giftitem;
    private NPC recipient;


    public GiftingAction(Item giftitem, NPC recipient){
        this.energyCost = 5;
        this.timeCost = 10;
        this.heartPoints = 10;
        this.giftitem = giftitem;
        this.recipient = recipient;
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

    public Item getGiftItem(){
        return giftitem;
    }    

    public NPC getRecipient(){
        return recipient;
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

    public void setGiftItem(Item gifItem){
        this.giftitem = gifItem;
    }

    public void setRecipient(NPC recipient){
        this.recipient = recipient;
    }

    /*========== OTHER METHOD =========== */
    @Override
    public boolean execute(Player p){
        if (p.getEnergy() < energyCost) {
            return false;
        }
    
        if (p.getPlayerInventory().getItemAmount(giftitem) <= 0) {
            return false;
        }
    
        if(recipient.getLovedItem().contains(giftitem)){
            this.heartPoints = 25;
        } else if(recipient.getLikedItem().contains(giftitem)){
            this.heartPoints = 20;
        } else if(recipient.getHatedItem().contains(giftitem)){
            this.heartPoints = -25;
        } else{
            this.heartPoints = 0;
        }
    
        // Substract energi dan waktu
        p.setEnergy(p.getEnergy() - energyCost);
        Farm farm = FarmManager.getFarmByName(p.getFarm());
        farm.getTime().skipTimeMinute(timeCost);
    
        // Update heartPoints NPC
        if (heartPoints > 0) {
            recipient.increaseHeartPoints(heartPoints);
        } else if (heartPoints < 0) {
            recipient.decreaseHeartPoints(Math.abs(heartPoints));
        }
    
        // Berikan item ke NPC
        recipient.receiveGift(giftitem);
    
        // Hapus item dari inventory
        p.getPlayerInventory().removeItem(giftitem, 1);

        return true;
    }

}
