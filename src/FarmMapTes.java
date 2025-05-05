package src;

import src.map.*;
import src.entities.*;
import src.items.*;

public class FarmMapTes {
    public static void main(String[] args) {
        Gold playerGold = new Gold(1000);
        Inventory playerInventory = new Inventory();
        Location playerLocation = new Location("House", new Point(10, 10));
        Player player = new Player("John", "Male", "Sunny Farm", playerGold, 
                                playerInventory, playerLocation);
        
        Point playerPositionFarm = player.getPlayerLocation().getCurrentPoint();
        // Membuat objek FarmMap
        FarmMap farmMap = new FarmMap(playerPositionFarm);
        
        // Menampilkan peta farm dan posisi objek
        System.out.println("Farm Map:");
        
        // Menambahkan objek lain secara manual
        Point point1 = new Point(5, 5);
        Point point2 = new Point(7, 8);
        Point point3 = new Point(15, 10);
        
        // Menambahkan objek "House" secara manual ke peta
        farmMap.getObjectPosition().get("House").add(point1);
        farmMap.getObjectPosition().get("Pond").add(point2);
        farmMap.getObjectPosition().get("ShippingBin").add(point3);

        
        // Menampilkan peta farm secara keseluruhan
        System.out.println("\nFarm Map Display:");
        farmMap.displayFarmMap();
        farmMap.displayObjectPositions();

        playerPositionFarm.movePlayer("down", farmMap.getFarmMapDisplay());
        playerPositionFarm.movePlayer("down", farmMap.getFarmMapDisplay());
        playerPositionFarm.movePlayer("down", farmMap.getFarmMapDisplay());
        playerPositionFarm.movePlayer("down", farmMap.getFarmMapDisplay());
        farmMap.displayFarmMap();
        farmMap.displayObjectPositions();
    }
}


