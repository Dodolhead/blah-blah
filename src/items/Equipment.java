package src.items;

import src.entities.*;

//nanti use abis jadi player aja
public abstract class Equipment extends Item{
    private Gold buyPrice;
    String equipmentType;

    public Equipment(String itemName, Gold sellPrice, Gold buyPrice, String equipmentType){
        super(itemName, "Equipment",true, sellPrice);
        this.buyPrice = buyPrice;
    }


    public int getBuyPrice(){
        return buyPrice.getGold();
    }


    public String getEquipmentType(){
        return equipmentType;
    }
    
    public abstract void use(Player player);
}
