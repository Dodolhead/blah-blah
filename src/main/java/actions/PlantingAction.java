package actions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entities.*;
import items.*;
import map.*;
import tsw.*;

public class PlantingAction implements Action {
    private Seed seedToPlant;
    private int ENERGY_COST = 5;
    private int TIME_COST = 5;

    public PlantingAction(Seed seedToPlant) {
        this.seedToPlant = seedToPlant;
    }

    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        FarmMap farmMap = farm.getFarmMap();
        Time gameTime = farm.getTime();
        if (player.getPlayerInventory().getItemAmount(seedToPlant) <= 0) {
            System.out.println("You don't have enough " + seedToPlant.getItemName() + " to be planted!");
            return false;
        }

        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action.");
            return false;
        }

        Point pos = player.getPlayerLocation().getCurrentPoint();
        int px = (pos.getX() + 24) / 48;
        int py = (pos.getY() + 24) / 48;
        char[][] map = farmMap.getFarmMapDisplay();

        // Buat list semua tile sekitar (radius 1)
        List<int[]> candidates = new ArrayList<>();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int x = px + dx;
                int y = py + dy;
                // Skip tile di luar map
                if (y < 0 || y >= map.length || x < 0 || x >= map[0].length)
                    continue;
                // Simpan koordinat dan jaraknya ke player
                candidates.add(new int[]{x, y, Math.abs(dx) + Math.abs(dy)});
            }
        }
        // Urutkan candidates berdasarkan jarak ke player (paling dekat dulu)
        candidates.sort(Comparator.comparingInt(a -> a[2]));

        boolean planted = false;
        for (int[] cand : candidates) {
            int x = cand[0], y = cand[1];
            if (map[y][x] != 't')
                continue;
            if (!isSeasonValid(gameTime.getCurrentSeason(), seedToPlant.getValidSeason()))
                continue;

            // Plant di tile ini
            map[y][x] = 'l';
            Point currentTile = new Point(x, y);
            farmMap.getObjectPosition().get("Tilled").removeIf(p -> p.getX() == x && p.getY() == y);
            farmMap.getObjectPosition().get("Planted").add(currentTile);

            farmMap.getPlantedSeeds().put(currentTile, seedToPlant);
            farmMap.getPlantedDay().put(currentTile, gameTime.getDay());

            player.getPlayerInventory().removeItem(seedToPlant, 1);
            player.subtractPlayerEnergy(ENERGY_COST);
            gameTime.skipTimeMinute(TIME_COST);

            System.out.println("Planting seed successful! You have planted a seed of " + seedToPlant.getItemName() + " on the land at (" + x + ", " + y + ").");
            planted = true;
            break; // Tanam di satu tile terdekat saja
        }
        if (!planted) {
            System.out.println("The seed is not plantable in this current time or land.");
            return false;
        }
        return true;
    }
    private boolean isSeasonValid(Season.Seasons currentSeason, String seedSeason) {
        switch (currentSeason) {
            case SPRING:
                return seedSeason.toUpperCase().contains("SPRING");
            case SUMMER:
                return seedSeason.toUpperCase().contains("SUMMER");
            case FALL:
                return seedSeason.toUpperCase().contains("FALL");
            case WINTER:
                return seedSeason.toUpperCase().contains("WINTER");
            default:
                return false;
        }
    }
}
