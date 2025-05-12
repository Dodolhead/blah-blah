package src.items;

import java.util.*;

public class Fish extends Item{
    private int timeStart;
    private int timeEnd;
    private List<String> availableSeasons;
    private List<String> availableWeathers;
    private List<String> fishLocations;
    private Gold sellBasePrice;
    private String rarity;

    public Fish(String itemName, int timeStart, int timeEnd, List<String> availableSeasons, List<String> availableWeathers, List<String> fishLocations ,Gold sellBasePrice, String rarity){
        super(itemName, "Fish", true, sellBasePrice);
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.availableSeasons = availableSeasons;
        this.availableWeathers = availableWeathers;
        this.sellBasePrice = sellBasePrice;
        this.fishLocations = fishLocations;
        this.rarity = rarity;
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

    public Gold getSellBasePrice(){
        return sellBasePrice;
    }
    
    public String getRarity(){
        return rarity;
    }

    public List<String> getAvailableSeasons(){
        return availableSeasons;
    }

    public Gold getSellPrice(Fish fish){
        return new Gold(0);
    }

    // private int calculateFishPrice(Fish fish) {
    //     // Formula sesuai spesifikasi:
    //     // (4/banyak_season) × (24/jumlah_jam) × (2/jumlah_variasi_weather) × (4/banyak_lokasi) × C
        
    //     int seasonCount = fish.seasons.length;
    //     if (seasonCount == 0 || (seasonCount == 1 && fish.seasons[0].equals("ANY"))) {
    //         seasonCount = 4; // All seasons
    //     }
        
    //     int hourCount = 24;
    //     if (!fish.timeRange.equals("ANY")) {
    //         String[] timeParts = fish.timeRange.split("-");
    //         int startHour = Integer.parseInt(timeParts[0].split("\\.")[0]);
    //         int endHour = Integer.parseInt(timeParts[1].split("\\.")[0]);
    //         hourCount = endHour - startHour;
    //     }
        
    //     int weatherCount = fish.weather.length;
    //     if (weatherCount == 0 || (weatherCount == 1 && fish.weather[0].equals("ANY"))) {
    //         weatherCount = 2; // All weather types
    //     }
        
    //     int locationCount = fish.locations.length;
        
    //     int rarityMultiplier = 0;
    //     if (fish.rarity.equals("common")) {
    //         rarityMultiplier = 10;
    //     } else if (fish.rarity.equals("regular")) {
    //         rarityMultiplier = 5;
    //     } else if (fish.rarity.equals("legendary")) {
    //         rarityMultiplier = 25;
    //     }
        
    //     int price = (int) ((4.0 / seasonCount) * (24.0 / hourCount) * (2.0 / weatherCount) * (4.0 / locationCount) * rarityMultiplier);
        
    //     return price;
    // }


}
