package actions;

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
        int x = (pos.getX() + 24) / 48;
        int y = (pos.getY() + 24) / 48;
        char[][] map = farmMap.getFarmMapDisplay();

        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            System.out.println("You can't plant land outside the farm area.");
            return false;
        }

        if (map[y][x] != 't') {
            System.out.println("This tile is not plantable.");
            return false;
        }

        if (!isSeasonValid(gameTime.getCurrentSeason(), seedToPlant.getValidSeason())) {
            System.out.println("You can't plant " + seedToPlant.getItemName() + " in this season.");
            return false;
        }

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
