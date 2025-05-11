package src.actions;

import src.map.*;
import src.tsw.Time;

import java.util.Map;

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
        Farm farm = new Farm(player.getFarm(), player);
        FarmMap farmMap = farm.getFarmMap();
        Time gameTime = farm.getTime();
        Equipment hoe = new Hoe("Hoe", new Gold(5), new Gold(10));
        Equipment pickaxe = new Pickaxe("Pickaxe", new Gold(5), new Gold(10));
        Seed wheatSeed = new Seed("Wheat Seeds", new Gold(60), 1, "SPRING");

        player.getPlayerInventory().addItem(pickaxe, 1);
        player.getPlayerInventory().addItem(hoe, 1);
        player.getPlayerInventory().addItem(wheatSeed, 1);

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

        MovingAction move = new MovingAction(24, 0, farmMap.getFarmMapDisplay());
        move.execute(player);
        farmMap.displayFarmMap();

        hoe.use(player);
        playerPositionFarm.movePlayer("right", farmMap.getFarmMapDisplay());
        farmMap.displayFarmMap();

        playerPositionFarm.movePlayer("left", farmMap.getFarmMapDisplay());
        pickaxe.use(player);
        playerPositionFarm.movePlayer("right", farmMap.getFarmMapDisplay());
        farmMap.displayFarmMap();
        System.out.println(gameTime.getTimeDay());

        playerPositionFarm.movePlayer("left", farmMap.getFarmMapDisplay());
        hoe.use(player);
        PlantingAction plant = new PlantingAction(wheatSeed);
        plant.execute(player);
        playerPositionFarm.movePlayer("right", farmMap.getFarmMapDisplay());
        farmMap.displayFarmMap();

        

        System.out.println("Data plantedSeeds:");
        for (Map.Entry<Point, Seed> entry : farmMap.getPlantedSeeds().entrySet()) {
            Point key = entry.getKey();
            Seed value = entry.getValue();
            System.out.println("Lokasi: " + key.printPoint() + ", Seed: " + value.getItemName()); // Menampilkan lokasi dan seed yang ditanam
        }

        // Loop untuk memeriksa plantedDay
        System.out.println("\nData plantedDay:");
        for (Map.Entry<Point, Integer> entry : farmMap.getPlantedDay().entrySet()) {
            Point key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Lokasi: " + key.printPoint() + ", Hari Ditanam: " + value); // Menampilkan lokasi dan hari tanam
        }


        playerPositionFarm.movePlayer("left", farmMap.getFarmMapDisplay());
        HarvestingAction harvest = new HarvestingAction();
        gameTime.skipDays(1);
        harvest.execute(player);
        player.getPlayerInventory().printInventory();
        playerPositionFarm.movePlayer("left", farmMap.getFarmMapDisplay());
        farmMap.displayFarmMap();


        gameTime.stopTime();
    }
}


