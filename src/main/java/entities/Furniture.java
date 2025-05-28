package main.java.entities;

public class Furniture {
    private String itemID;
    private String furnitureName;
    private String furnitureDescription;
    private int furnitureSizeX;
    private int furnitureSizeY;
    private char furnitureLogo;

    public Furniture(String itemID, String furnitureName, String furnitureDescription, int furnitureSizeX, int furnitureSizeY, char furnitureLogo) {
        if (furnitureSizeX <= 0 || furnitureSizeY <= 0) {
            throw new IllegalArgumentException("Furniture size must be greater than zero.");
        }
    
        this.itemID = itemID;
        this.furnitureName = furnitureName;
        this.furnitureDescription = furnitureDescription;
        this.furnitureSizeX = furnitureSizeX;
        this.furnitureSizeY = furnitureSizeY;
        this.furnitureLogo = furnitureLogo;
    }
    

    public String getItemID(){
        return itemID;
    }

    public String getFurnitureName(){
        return furnitureName;
    }

    public String getfurnitureDescription(){
        return furnitureDescription;
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

    public void rotateFurniture(){
        int temp = furnitureSizeX;
        furnitureSizeX = furnitureSizeY;
        furnitureSizeY = temp;
    }

}
