package items;

import java.util.*;
import java.awt.image.BufferedImage;

public class Fish extends Item{
    private int timeStart;
    private int timeEnd;
    private List<String> availableSeasons;
    private List<String> availableWeathers;
    private List<String> fishLocations;
    private Gold sellPrice;
    private String rarity;

public Fish(String itemName,int timeStart, int timeEnd, List<String> availableSeasons, List<String> availableWeathers, List<String> fishLocations, String rarity, BufferedImage image) {
    super(itemName, "Fish", true, new Gold(0), image);
    this.timeStart = timeStart;
    this.timeEnd = timeEnd;
    this.availableSeasons = availableSeasons;
    this.availableWeathers = availableWeathers;
    this.fishLocations = fishLocations;
    this.rarity = rarity;

    this.sellPrice = this.getSellPriceAfter();
    super.setSellPrice(this.sellPrice);
}

    public int getTimeStart(){
        return timeStart;
    }

    public int getTimeEnd(){
        return timeEnd;
    }

    public List<String> getWeathers(){
        return availableWeathers;
    }

    public List<String> getFishLocations(){
        return fishLocations;
    }

    public Gold getSellPrice(){
        return sellPrice;
    }
    
    public String getRarity(){
        return rarity;
    }

    public List<String> getAvailableSeasons(){
        return availableSeasons;
    }

    public Gold getSellPriceAfter() {
        int totalSeasons = availableSeasons.contains("ANY") ? 4 : availableSeasons.size();
        int totalWeathers = availableWeathers.contains("ANY") ? 2 : availableWeathers.size();
        int totalLocations = fishLocations.contains("ANY") ? 4 : fishLocations.size();

        int totalHours = timeEnd >= timeStart 
            ? (timeEnd - timeStart) 
            : (24 - timeStart + timeEnd);

        double seasonFactor = 4.0 / totalSeasons;
        double hourFactor = 24.0 / totalHours;
        double weatherFactor = 2.0 / totalWeathers;
        double locationFactor = 4.0 / totalLocations;

        int c;
        switch (rarity.toLowerCase()) {
            case "common":
                c = 10;
                break;
            case "regular":
                c = 5;
                break;
            case "legendary":
                c = 25;
                break;
            default:
                c = 1;
        }

        int price = (int) Math.round(seasonFactor * hourFactor * weatherFactor * locationFactor * c);
        return new Gold(price);
    }

    public void setFishSellPrice(Gold sellPrice) {
        this.sellPrice = sellPrice;
    }


}
