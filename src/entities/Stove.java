package src.entities;

import src.actions.*;
import src.items.*;
import src.map.HouseMap;
import src.map.Point;

public class Stove extends Furniture{
    private int fuelRemaining;
    private String currentFuelType;

    public Stove() {
        super("stove", "Stove", "Tungku untuk memasak makanan", 1, 1, 'S');
        currentFuelType = "Empty";
        fuelRemaining = 0;
    }

    public boolean isNearbyStove(Player player, HouseMap houseMap) {
        Point playerLoc = player.getPlayerLocation().getCurrentPoint();
        int x = playerLoc.getX();
        int y = playerLoc.getY();
        char[][] map = houseMap.getHouseMapDisplay();

        int[][] directions = {
            {0, -1},
            {-1, 0},
            {1, 0}, 
            {0, 1} 
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newY >= 0 && newY < map.length && newX >= 0 && newX < map[0].length) {
                if (map[newY][newX] == 'S') {
                    return true;
                }
            }
        }

        System.out.println("You are not near a stove!");
        return false;
    }

    public void setFuelType(String fuelType) {
        this.currentFuelType = fuelType;
    }

    public boolean addFuel(String fuelName, Player player, HouseMap houseMap) {
        if (!player.getPlayerInventory().hasItem(fuelName)) return false;
        
        if (!isNearbyStove(player, houseMap)){
            return false;
        }

        if (fuelName.equals("Coal")) {
            fuelRemaining += 2;
        } else if (fuelName.equals("Fire Wood")) {
            fuelRemaining += 1;
        } else {
            return false;
        }

        player.getPlayerInventory().removeItemByName(fuelName, 1);
        setFuelType(fuelName);
        return true;
    }

    public boolean consumeFuel() {
        if (fuelRemaining > 0) {
            fuelRemaining--;
            if (fuelRemaining == 0) {
                setFuelType("Empty");
            }
            return true;
        }
        return false;
    }

    public int getFuelRemaining() {
        return fuelRemaining;
    }

    public String getCurrentFuelType() {
        return currentFuelType;
    }

    public boolean useStove(Player player, HouseMap houseMap, Recipe recipe) {
        CookingAction cookingAction = new CookingAction(recipe);
        if (!isNearbyStove(player, houseMap)){
            return false;
        }
        if (currentFuelType.equals("Empty")) {
            System.out.println("You don't have a fuel to cook.");
            return false;
        }
        cookingAction.execute(player);
        consumeFuel();
        return true;
    }
    
}
