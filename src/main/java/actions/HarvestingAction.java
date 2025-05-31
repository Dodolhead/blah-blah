package actions;

import java.util.HashMap;
import java.util.Map;

import entities.*;
import items.*;
import map.*;
import tsw.*;
import java.util.*;

public class HarvestingAction implements Action {
    private static final int ENERGY_COST = 5;
    private static final int TIME_COST = 5;

    private static final Map<Seed, Crop> seedToCropMap = new HashMap<>();

    static {
        // Spring Season
        Seed parsnipSeed = new Seed("Parsnip Seeds", new Gold(20), 1, "SPRING", ItemManager.load("/items/seeds/parsnip-seeds.png"));
        Crop parsnipCrop = new Crop("Parsnip", new Gold(50), new Gold(35), 1, ItemManager.load("/items/crops/parsnip.png"));
        seedToCropMap.put(parsnipSeed, parsnipCrop);

        Seed cauliflowerSeed = new Seed("Cauliflower Seeds", new Gold(80), 5, "SPRING", ItemManager.load("/items/seeds/cauli-seeds.png"));
        Crop cauliflowerCrop = new Crop("Cauliflower", new Gold(200), new Gold(150), 1, ItemManager.load(("/items/crops/cauli.png")));
        seedToCropMap.put(cauliflowerSeed, cauliflowerCrop);

        Seed potatoSeed = new Seed("Potato Seeds", new Gold(50), 3, "SPRING", ItemManager.load(("/items/seeds/potato-seeds.png")));
        Crop potatoCrop = new Crop("Potato", new Gold(0), new Gold(80), 1, ItemManager.load(("/items/crops/potato.png")));
        seedToCropMap.put(potatoSeed, potatoCrop);

        Seed wheatSeed = new Seed("Wheat Seeds", new Gold(60), 1, "SPRING", ItemManager.load(("/items/seeds/wheat-seeds.png")));
        Crop wheatCrop = new Crop("Wheat", new Gold(50), new Gold(30), 3, ItemManager.load(("/items/crops/wheat.png")));
        seedToCropMap.put(wheatSeed, wheatCrop);

        // Summer Season
        Seed blueberrySeed = new Seed("Blueberry Seeds", new Gold(80), 7, "SUMMER", ItemManager.load(("/items/seeds/blueberry-seeds.png")));
        Crop blueberryCrop = new Crop("Blueberry", new Gold(150), new Gold(40), 3, ItemManager.load(("/items/crops/blueberry.png")));
        seedToCropMap.put(blueberrySeed, blueberryCrop);

        Seed tomatoSeed = new Seed("Tomato Seeds", new Gold(50), 3, "SUMMER", ItemManager.load(("/items/seeds/tomato-seeds.png")));
        Crop tomatoCrop = new Crop("Tomato", new Gold(90), new Gold(60), 1, ItemManager.load(("/items/crops/tomato.png")));
        seedToCropMap.put(tomatoSeed, tomatoCrop);

        Seed hotPepperSeed = new Seed("Hot Pepper Seeds", new Gold(40), 1, "SUMMER", ItemManager.load(("/items/seeds/hotpepper-seeds.png")));
        Crop hotPepperCrop = new Crop("Hot Pepper", new Gold(0), new Gold(40), 1, ItemManager.load(("/items/crops/hotpepper.png"))); 
        seedToCropMap.put(hotPepperSeed, hotPepperCrop);

        Seed melonSeed = new Seed("Melon Seeds", new Gold(80), 4, "SUMMER", ItemManager.load(("/items/seeds/melon-seeds.png")));
        Crop melonCrop = new Crop("Melon", new Gold(0), new Gold(250), 1, ItemManager.load(("/items/crops/melon.png"))); 
        seedToCropMap.put(melonSeed, melonCrop);

        // Fall Season
        Seed cranberrySeed = new Seed("Cranberry Seeds", new Gold(100), 2, "FALL", ItemManager.load(("/items/seeds/cranberry-seeds.png")));
        Crop cranberryCrop = new Crop("Cranberry", new Gold(0), new Gold(25), 10, ItemManager.load(("/items/crops/cranberry.png"))); 
        seedToCropMap.put(cranberrySeed, cranberryCrop);

        Seed pumpkinSeed = new Seed("Pumpkin Seeds", new Gold(150), 7, "FALL", ItemManager.load(("/items/seeds/pumpkin-seeds.png")));
        Crop pumpkinCrop = new Crop("Pumpkin", new Gold(300), new Gold(250), 1, ItemManager.load(("/items/crops/pumpkin.png")));
        seedToCropMap.put(pumpkinSeed, pumpkinCrop);

        Seed wheatSeedFall = new Seed("Wheat Seeds", new Gold(60), 1, "FALL", ItemManager.load(("/items/seeds/wheat-seeds.png")));
        Crop wheatCropFall = new Crop("Wheat", new Gold(50), new Gold(30), 3, ItemManager.load(("/items/crops/wheat.png")));
        seedToCropMap.put(wheatSeedFall, wheatCropFall);

        Seed grapeSeed = new Seed("Grape Seeds", new Gold(60), 3, "FALL", ItemManager.load(("/items/seeds/grape-seeds.png")));
        Crop grapeCrop = new Crop("Grape", new Gold(100), new Gold(10), 20, ItemManager.load(("/items/crops/grape.png")));
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
        int px = (playerPos.getX() + 24) / 48;
        int py = (playerPos.getY() + 24) / 48;
        char[][] mapDisplay = farmMap.getFarmMapDisplay();
        Map<Point, Seed> plantedSeeds = farmMap.getPlantedSeeds();
        Map<Point, Integer> plantedDayMap = farmMap.getPlantedDay();

        // Cari tile 'l' (planted) terdekat yang siap panen, di sekitar player (area 3x3)
        List<int[]> candidates = new ArrayList<>();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int x = px + dx;
                int y = py + dy;
                if (y < 0 || y >= mapDisplay.length || x < 0 || x >= mapDisplay[0].length)
                    continue;
                int distance = Math.abs(dx) + Math.abs(dy);
                candidates.add(new int[]{x, y, distance});
            }
        }
        // Urutkan berdasarkan jarak terdekat ke player
        candidates.sort(Comparator.comparingInt(a -> a[2]));

        boolean harvested = false;
        for (int[] tile : candidates) {
            int x = tile[0], y = tile[1];
            Point cropLocation = new Point(x, y);

            // Cek tile harus planted, seed harus ada, plantedDay harus ada
            if (mapDisplay[y][x] != 'l' && mapDisplay[y][x] != 'w') continue;
            if (!plantedSeeds.containsKey(cropLocation)) continue;
            Integer plantedDayObj = plantedDayMap.get(cropLocation);
            if (plantedDayObj == null) continue;

            int plantedDay = plantedDayObj;
            int daysPassed = gameTime.getDay() - plantedDay;

            Seed seed = plantedSeeds.get(cropLocation);
            if (daysPassed < seed.getHarvestDays()) {
                System.out.println("Crop at (" + x + ", " + y + ") is still not ready for harvest.");
                continue;
            }

            if (player.getEnergy() < ENERGY_COST) {
                System.out.println("You don't have enough energy to do this action.");
                return false;
            }

            // Lakukan panen
            mapDisplay[y][x] = 't';
            farmMap.getObjectPosition().get("Planted").remove(cropLocation);
            farmMap.getObjectPosition().get("Tilled").add(cropLocation);

            player.getPlayerInventory().addItem(seedToCropMap.get(seed), seedToCropMap.get(seed).getCropPerPanen());
            plantedSeeds.remove(cropLocation);
            plantedDayMap.remove(cropLocation);

            player.subtractPlayerEnergy(ENERGY_COST);
            gameTime.skipTimeMinute(TIME_COST);

            System.out.println("Harvest successful at (" + x + ", " + y + ")! You got " + seedToCropMap.get(seed).getItemName());
            harvested = true;
            break;
        }
        if (!harvested) {
            System.out.println("No harvestable crops nearby!");
            return false;
        }
        player.cropsHarvested++;
        if (player.cropsHarvested == 1){
            player.getPlayerInventory().addItem(ItemManager.getItem("Veggie Soup Recipe"), 1);
        }
        return true;
    }
}