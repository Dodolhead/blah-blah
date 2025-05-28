package main.java.entities;

import java.util.Objects;

import main.java.map.*;

public class Door extends Furniture {
    private int x, y;
    
    public Door(){
        super("door", "Door", "A door that leads to another place.", 1, 1, 'D');
    }

    public boolean useDoor(Player player, FarmMap farmMap, HouseMap houseMap) {
        if (!isNearbyDoor(player, farmMap, houseMap)) {
            return false;
        }

        String currentLocation = player.getPlayerLocation().getName();

        if (currentLocation.equals("Farm")) {
            player.setLocation("House", 12, 22);


        } else if (currentLocation.equals("House")) {
            Point doorPoint = farmMap.getObjectPosition().get("HouseDoor").get(0);
            player.setLocation("Farm", doorPoint.getX(), doorPoint.getY() + 1);

        }

        return true;
    }




    public boolean isNearbyDoor(Player player, FarmMap farmMap, HouseMap houseMap) {
        Point playerLoc = player.getPlayerLocation().getCurrentPoint();
        String locationName = player.getPlayerLocation().getName();

        char[][] map;
        if (locationName.equals("Farm")) {
            map = farmMap.getFarmMapDisplay();
        } else if (locationName.equals("House")) {
            map = houseMap.getHouseMapDisplay();
        } else {
            System.out.println("Unknown location: " + locationName);
            return false;
        }

        int x = playerLoc.getX();
        int y = playerLoc.getY();

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
                if (map[newY][newX] == 'D') {
                    return true;
                }
            }
        }

        System.out.println("You are not near a door");
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Door door = (Door) obj;
        return x == door.x && y == door.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
