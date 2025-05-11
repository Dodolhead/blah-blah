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

    private static final Map<String, String> seedToCropMap = new HashMap<>();
    private static final Map<String, Integer> cropYieldMap = new HashMap<>();

    static {
        seedToCropMap.put("Parsnip Seeds", "Parsnip");
        seedToCropMap.put("Cauliflower Seeds", "Cauliflower");
        seedToCropMap.put("Potato Seeds", "Potato");
        seedToCropMap.put("Wheat Seeds", "Wheat");
        seedToCropMap.put("Blueberry Seeds", "Blueberry");
        seedToCropMap.put("Tomato Seeds", "Tomato");
        seedToCropMap.put("Hot Pepper Seeds", "Hot Pepper");
        seedToCropMap.put("Melon Seeds", "Melon");
        seedToCropMap.put("Cranberry Seeds", "Cranberry");
        seedToCropMap.put("Pumpkin Seeds", "Pumpkin");
        seedToCropMap.put("Grape Seeds", "Grape");

        cropYieldMap.put("Parsnip", 1);
        cropYieldMap.put("Cauliflower", 1);
        cropYieldMap.put("Potato", 1);
        cropYieldMap.put("Wheat", 3);
        cropYieldMap.put("Blueberry", 3);
        cropYieldMap.put("Tomato", 1);
        cropYieldMap.put("Hot Pepper", 1);
        cropYieldMap.put("Melon", 1);
        cropYieldMap.put("Cranberry", 10);
        cropYieldMap.put("Pumpkin", 1);
        cropYieldMap.put("Grape", 20);
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

        if (y < 0 || y >= mapDisplay.length || x < 0 || x >= mapDisplay[0].length) {
            System.out.println("Posisi tidak valid.");
            return false;
        }

        if (mapDisplay[y][x] != 'l') {
            System.out.println("Tidak ada tanaman yang dapat dipanen di sini.");
            return false;
        }

        Point cropLocation = new Point(x, y);
        Map<Point, Seed> plantedSeeds = farmMap.getPlantedSeeds();

        if (!plantedSeeds.containsKey(cropLocation)) {
            System.out.println("Tanaman tidak ditemukan di titik ini.");
            return false;
        }

        // Asumsikan siap panen jika tanaman ditanam
        Seed seed = plantedSeeds.get(cropLocation);
        String seedName = seed.getItemName();
        String cropType = seedToCropMap.getOrDefault(seedName, "Parsnip");
        int yieldAmount = cropYieldMap.getOrDefault(cropType, 1);

        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("Energi tidak cukup untuk memanen.");
            return false;
        }

        // Simulasi harga jual dan beli crop
        Gold cropSellPrice = new Gold(0);
        Gold cropBuyPrice = new Gold(0);

        if ("Parsnip".equals(cropType)) {
            cropSellPrice = new Gold(35);
            cropBuyPrice = new Gold(50);
        } else if ("Wheat".equals(cropType)) {
            cropSellPrice = new Gold(30);
            cropBuyPrice = new Gold(50);
        } else if ("Blueberry".equals(cropType)) {
            cropSellPrice = new Gold(40);
            cropBuyPrice = new Gold(150);
        }

        Crop harvestedCrop = new Crop(cropType, cropSellPrice, cropBuyPrice, yieldAmount);
        player.getPlayerInventory().addItem(harvestedCrop, yieldAmount);

        mapDisplay[y][x] = 't';
        farmMap.getObjectPosition().get("Planted").remove(cropLocation);
        farmMap.getObjectPosition().get("Tilled").add(cropLocation);

        plantedSeeds.remove(cropLocation);

        player.subtractPlayerEnergy(ENERGY_COST);
        gameTime.skipTimeMinute(TIME_COST);

        System.out.println("Berhasil memanen " + cropType + "! Anda mendapat " + yieldAmount + " " + cropType + ".");
        return true;
    }
}