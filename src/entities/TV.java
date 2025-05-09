package src.entities;

public class TV extends Furniture{
    public TV(String furnitureDescription, int furnitureSizeX, int furnitureSizeY) {
        super("tv", "Television (TV)", furnitureDescription, furnitureSizeX, furnitureSizeY, 'T');
    }

    public void useTV(Player p) {
        //nanti tv ini kl dah jadi actionny
    }

}
