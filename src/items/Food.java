package src.items;

import java.awt.image.BufferedImage;

public class Food extends Item {
    private int giveEnergy;
    private Gold buyPrice;

    public Food(String itemName, int giveEnergy, Gold buyPrice, Gold sellPrice, BufferedImage image) {
        super(itemName, "Food", true, sellPrice, image);
        this.giveEnergy = giveEnergy;
        this.buyPrice = buyPrice;
    }

    public int getGiveEnergy(){
        return giveEnergy;
    }


    public Gold getbuyPrice(){
        return buyPrice;
    }

}
