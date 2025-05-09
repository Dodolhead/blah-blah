package src.entities;

public class Bed extends Furniture {
    private int maxPersonSize;

    public Bed(String itemID, String furnitureName, String furnitureDescription, int furnitureSizeX, int furnitureSizeY, int maxPersonSize) {
        super(itemID, furnitureName, furnitureDescription, furnitureSizeX, furnitureSizeY, 'B');
        this.maxPersonSize = maxPersonSize;
    }

    public int getMaxPersonSize() {
        return maxPersonSize;
    }

    public void useBed(Player p){
        //nanti sleep ini kl dh ada actionny
    }
}
