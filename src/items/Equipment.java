package src.items;

import src.entities.*;
import java.awt.image.BufferedImage;
//nanti use abis jadi player aja
public abstract class Equipment extends Item{
    private Gold buyPrice;
    String equipmentType;

    public Equipment(String itemName, Gold sellPrice, Gold buyPrice, String equipmentType, BufferedImage image) {
        super(itemName, "Equipment",true, sellPrice, image);
        this.buyPrice = buyPrice;
    }


    public int getBuyPrice(){
        return buyPrice.getGold();
    }


    public String getEquipmentType(){
        return equipmentType;
    }
    
    public abstract boolean use(Player player);
}
