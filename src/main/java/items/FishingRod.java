package main.java.items;

import main.java.actions.FishingAction;
import main.java.entities.*;
import main.java.map.FarmMap;
import main.java.map.Point;

public class FishingRod extends Equipment {
    public FishingRod(String rodName, Gold sellPrice, Gold buyPrice){
        super(rodName, sellPrice, buyPrice, "FishingRod", ItemManager.load("/items/equipment/fishing-rod.png"));
    }

    public boolean use(Player player){
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        FarmMap farmMap = farm.getFarmMap();
        FishingAction fishing = new FishingAction();
        fishing.isValidFishingLocation(player.getPlayerLocation().getName());
    
        if (player.getPlayerLocation().getName().equals("Farm")) {
            if (!isNearbyPond(player, farmMap)) {
                return false;
            }
        }

        fishing.execute(player);
        return true;
    }


    public boolean isNearbyPond(Player player, FarmMap farmMap) {
        Point playerLoc = player.getPlayerLocation().getCurrentPoint();
        int x = playerLoc.getX();
        int y = playerLoc.getY();
        char[][] map = farmMap.getFarmMapDisplay();

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
                if (map[newY][newX] == 'o') {
                    return true;
                }
            }
        }

        System.out.println("When you are in your farm, you need to be near a pond to fish!");
        return false;
    }


}
