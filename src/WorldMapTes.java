package src;

import src.map.*;
import src.entities.*;
import src.items.*;

import java.util.Scanner;
import java.util.List;

public class WorldMapTes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inisialisasi posisi awal
        Point playerStart = new Point(1, 15); // posisi edge agar bisa toggle map
        Location startingLocation = new Location("Farm", playerStart);

        // Dummy objek pendukung konstruktor Player
        System.out.println("\n=== NPC Setup ===");


        NPC alice = new NPC("Alice", "Single");
        System.out.println("Created NPC: " + alice.getNpcName());
        Gold playerGold = new Gold(100);
        Inventory inventory = new Inventory();

        // Membuat objek Player dengan konstruktor lengkap
        Player player = new Player(
            "Bro",           // playerName
            "Male",         // gender
            "BroFarm",       // farmName  // partner
            playerGold,       // gold
            inventory,        // inventory
            startingLocation // lokasi awal    
        );

        // Buat FarmMap dan WorldMap
        FarmMap farmMap = new FarmMap(playerStart);
        WorldMap worldMap = new WorldMap(player);

        // Tampilkan peta awal
        System.out.println("Initial Farm Map:");
        farmMap.displayFarmMap();

        // Cek toggle world map
        if (farmMap.canToggleWorldMap(worldMap)) {
            handleWorldMap(scanner, worldMap);
        } else {
            System.out.println("You are not at the edge of the farm. Move to the edge to switch locations.");
        }

        // Contoh gerak player dan toggle map lagi
        playerStart.movePlayer("left", farmMap.getFarmMapDisplay());

        if (farmMap.canToggleWorldMap(worldMap)) {
            handleWorldMap(scanner, worldMap);
        }

        // Tampilkan tempat yang pernah dikunjungi
        List<String> visited = player.getVisitedPlace();
        System.out.println("Visited Places: " + visited);

        scanner.close();
    }

    private static void handleWorldMap(Scanner scanner, WorldMap worldMap) {
        worldMap.displayWorldMap();
        System.out.print("Enter destination number: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1 -> worldMap.visit(new Location("Forest River", new Point(0, 0)));
            case 2 -> worldMap.visit(new Location("Mountain Lake", new Point(10, 10)));
            case 3 -> worldMap.visit(new Location("Store", new Point(2, 2)));
            case 4 -> worldMap.visit(new Location("NPCs Home", new Point(7, 3)));
            case 5 -> System.out.println("Exiting...");
            default -> System.out.println("Invalid choice");
        }

        System.out.println("You are now at: " + worldMap.getPlayerLocation().getName());
    }
}
