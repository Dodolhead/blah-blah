package entities;

import actions.SleepingAction;
import map.HouseMap;
import map.Point;
import items.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Bed extends Furniture {
    private int maxPersonSize;

    public Bed(String itemID, String furnitureName, String furnitureDescription, int furnitureSizeX, int furnitureSizeY, int maxPersonSize) {
        super(
            itemID,
            furnitureName,
            furnitureDescription,
            furnitureSizeX,
            furnitureSizeY,
            'B',
            loadBedImage(),
            new Gold(100) // atau harga lain sesuai keinginanmu
        );
        this.maxPersonSize = maxPersonSize;
    }

    private static BufferedImage loadBedImage() {
        try {
            return ImageIO.read(Bed.class.getResourceAsStream("/res/items/furniture/bed.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Failed to load bed image: " + e.getMessage());
            return null;
        }
    }

    public int getMaxPersonSize() {
        return maxPersonSize;
    }

    public boolean isNearbyBed(Player player, HouseMap houseMap) {
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
                if (map[newY][newX] == 'B') {
                    return true;
                }
            }
        }

        System.out.println("You are not near a Bed!");
        return false;
    }

    public boolean useBed(Player player, HouseMap houseMap) {
        if (!isNearbyBed(player, houseMap)){
            return false;
        }

        SleepingAction sleeping = new SleepingAction();
        sleeping.execute(player);

        return true;
    }
}