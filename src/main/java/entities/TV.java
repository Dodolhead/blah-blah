package entities;

import map.HouseMap;
import map.Point;
import actions.*;

public class TV extends Furniture{
    public TV(String furnitureDescription, int furnitureSizeX, int furnitureSizeY) {
        super("tv", "Television (TV)", furnitureDescription, furnitureSizeX, furnitureSizeY, 'T');
    }

    public boolean useTV(Player player, HouseMap houseMap) {
        if (!isNearbyTV(player, houseMap)){
            return false;
        }

        WatchingAction watching = new WatchingAction();

        return watching.execute(player);
    }

    public boolean isNearbyTV(Player player, HouseMap houseMap) {
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
                if (map[newY][newX] == 'T') {
                    return true;
                }
            }
        }

        System.out.println("You are not near a TV!");
        return false;
    }

}
