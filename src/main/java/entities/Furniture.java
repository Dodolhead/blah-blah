package entities;
import items.*;
import java.awt.image.BufferedImage;

public class Furniture extends Misc {
    private String itemID;
    private int furnitureSizeX;
    private int furnitureSizeY;
    private char furnitureLogo;
    private Gold buyPrice;

    public Furniture(
        String itemID, 
        String furnitureName, 
        String furnitureDescription, 
        int furnitureSizeX, 
        int furnitureSizeY, 
        char furnitureLogo, 
        BufferedImage image,
        Gold buyPrice
    ) {
        super(furnitureName, furnitureDescription, image);

        if (furnitureSizeX <= 0 || furnitureSizeY <= 0) {
            throw new IllegalArgumentException("Furniture size must be greater than zero.");
        }
        this.itemID = itemID;
        this.furnitureSizeX = furnitureSizeX;
        this.furnitureSizeY = furnitureSizeY;
        this.furnitureLogo = furnitureLogo;
        this.buyPrice = buyPrice;
    }

    public String getItemID() {
        return itemID;
    }
    public int getFurnitureSizeX(){
        return furnitureSizeX;
    }
    public int getFurnitureSizeY(){
        return furnitureSizeY;
    }
    public char getFurnitureLogo(){
        return furnitureLogo;
    }
    public Gold getBuyPrice() {
        return buyPrice;
    }
    public void rotateFurniture(){
        int temp = furnitureSizeX;
        furnitureSizeX = furnitureSizeY;
        furnitureSizeY = temp;
    }
    // Nama dan deskripsi furniture diakses lewat Misc:
    public String getFurnitureName(){
        return getItemName();
    }
    public String getFurnitureDescription(){
        return getItemDescription();
    }
}