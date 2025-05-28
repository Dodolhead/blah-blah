package main.java.items;
import java.awt.image.BufferedImage;

public class Crop extends Item{
    private Gold buyPrice;
    private int cropPerPanen;

    public Crop(String itemName, Gold sellPrice, Gold buyPrice, int cropPerPanen, BufferedImage image) {
        super(itemName, "Crop", true, sellPrice, image);
        this.buyPrice = buyPrice;
        this.cropPerPanen = cropPerPanen;
    }

    public Gold getBuyPrice(){
        return buyPrice;
    }

    public int getCropPerPanen(){
        return cropPerPanen;
    }
}
