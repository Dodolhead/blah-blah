package src.actions;

import java.util.HashMap;
import java.util.Map;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class HarvestingAction implements Action {
    private static final int ENERGY_COST = 5;
    private static final int TIME_COST = 5;

    private static final Map<Seed, Crop> seedToCropMap = new HashMap<>();

    static {
        // Spring Season
        Seed parsnipSeed = new Seed("Parsnip Seeds", new Gold(20), 1, "SPRING");
        Crop parsnipCrop = new Crop("Parsnip", new Gold(50), new Gold(35), 1);
        seedToCropMap.put(parsnipSeed, parsnipCrop);

        Seed cauliflowerSeed = new Seed("Cauliflower Seeds", new Gold(80), 5, "SPRING");
        Crop cauliflowerCrop = new Crop("Cauliflower", new Gold(200), new Gold(150), 1);
        seedToCropMap.put(cauliflowerSeed, cauliflowerCrop);

        Seed potatoSeed = new Seed("Potato Seeds", new Gold(50), 3, "SPRING");
        Crop potatoCrop = new Crop("Potato", new Gold(0), new Gold(80), 1);
        seedToCropMap.put(potatoSeed, potatoCrop);

        Seed wheatSeed = new Seed("Wheat Seeds", new Gold(60), 1, "SPRING");
        Crop wheatCrop = new Crop("Wheat", new Gold(50), new Gold(30), 3);
        seedToCropMap.put(wheatSeed, wheatCrop);

        // Summer Season
        Seed blueberrySeed = new Seed("Blueberry Seeds", new Gold(80), 7, "SUMMER");
        Crop blueberryCrop = new Crop("Blueberry", new Gold(150), new Gold(40), 3);
        seedToCropMap.put(blueberrySeed, blueberryCrop);

        Seed tomatoSeed = new Seed("Tomato Seeds", new Gold(50), 3, "SUMMER");
        Crop tomatoCrop = new Crop("Tomato", new Gold(90), new Gold(60), 1);
        seedToCropMap.put(tomatoSeed, tomatoCrop);

        Seed hotPepperSeed = new Seed("Hot Pepper Seeds", new Gold(40), 1, "SUMMER");
        Crop hotPepperCrop = new Crop("Hot Pepper", new Gold(0), new Gold(40), 1); 
        seedToCropMap.put(hotPepperSeed, hotPepperCrop);

        Seed melonSeed = new Seed("Melon Seeds", new Gold(80), 4, "SUMMER");
        Crop melonCrop = new Crop("Melon", new Gold(0), new Gold(250), 1); 
        seedToCropMap.put(melonSeed, melonCrop);

        // Fall Season
        Seed cranberrySeed = new Seed("Cranberry Seeds", new Gold(100), 2, "FALL");
        Crop cranberryCrop = new Crop("Cranberry", new Gold(0), new Gold(25), 10); 
        seedToCropMap.put(cranberrySeed, cranberryCrop);

        Seed pumpkinSeed = new Seed("Pumpkin Seeds", new Gold(150), 7, "FALL");
        Crop pumpkinCrop = new Crop("Pumpkin", new Gold(300), new Gold(250), 1);
        seedToCropMap.put(pumpkinSeed, pumpkinCrop);

        Seed wheatSeedFall = new Seed("Wheat Seeds", new Gold(60), 1, "FALL");
        Crop wheatCropFall = new Crop("Wheat", new Gold(50), new Gold(30), 3);
        seedToCropMap.put(wheatSeedFall, wheatCropFall);

        Seed grapeSeed = new Seed("Grape Seeds", new Gold(60), 3, "FALL");
        Crop grapeCrop = new Crop("Grape", new Gold(100), new Gold(10), 20);
        seedToCropMap.put(grapeSeed, grapeCrop);

        // Winter Season - No Seeds
        // "Tidak ada seed yang dapat tumbuh saat winter"
    }


    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        FarmMap farmMap = farm.getFarmMap();
        Time gameTime = farm.getTime();
        Point playerPos = player.getPlayerLocation().getCurrentPoint();
        int x = playerPos.getX();
        int y = playerPos.getY();
        char[][] mapDisplay = farmMap.getFarmMapDisplay();
        Point cropLocation = new Point(x, y);
        Map<Point, Seed> plantedSeeds = farmMap.getPlantedSeeds();
        int plantedDay = farmMap.getPlantedDay().get(cropLocation);
        int daysPassed = gameTime.getDay() - plantedDay;


        if (y < 0 || y >= mapDisplay.length || x < 0 || x >= mapDisplay[0].length) {
            System.out.println("You can't Harvest outside the farm area.");
            return false;
        }

        if (mapDisplay[y][x] != 'l') {
            System.out.println("There is no plant in this tile.");
            return false;
        }

        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action.");
            return false;
        }

        if (!plantedSeeds.containsKey(cropLocation)) {
            System.out.println("Plant not found in this tile.");
            return false;
        }

        if (!(daysPassed >= plantedSeeds.get(cropLocation).getHarvestDays())) {
            System.out.println("Crop is still not ready for harvest.");
            return false;
        }

        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action.");
            return false;
        }


        mapDisplay[y][x] = 't';
        farmMap.getObjectPosition().get("Planted").remove(cropLocation);
        farmMap.getObjectPosition().get("Tilled").add(cropLocation);

        Seed seed = plantedSeeds.get(cropLocation);
        player.getPlayerInventory().addItem(seedToCropMap.get(seed), seedToCropMap.get(seed).getCropPerPanen());
        plantedSeeds.remove(cropLocation);
        farmMap.getPlantedDay().remove(cropLocation);

        player.subtractPlayerEnergy(ENERGY_COST);
        gameTime.skipTimeMinute(TIME_COST);

        return true;
    }
}