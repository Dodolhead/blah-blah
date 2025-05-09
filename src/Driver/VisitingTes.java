package src.Driver;

import src.actions.*;
import src.entities.*;
import src.map.*;
import src.tsw.*;
import src.items.*;

public class VisitingTes {
    public static void main(String[] args) {
        // Setup lokasi awal di edge (misal di titik tepi [0, 5])
        Point startPoint = new Point(0, 5);
        Location startLocation = new Location("Farm", startPoint);

        // Setup objek-objek pemain
        Gold gold = new Gold(200);
        Inventory inventory = new Inventory();
        Player player = new Player("Rizqika", "Male", "Sunny Farm", gold, inventory, startLocation);
        player.setEnergy(100);

        // Setup waktu & farm
        Farm farm = new Farm("Sunny Farm", player);
        Time time = farm.getTime();
        FarmManager.registerFarm(farm);  // pastikan ada method register di FarmManager

        // Setup world map
        WorldMap worldMap = new WorldMap(player);

        // Action: visiting ke "Store"
        VisitingAction visitAction = new VisitingAction(worldMap, "Store");

        System.out.println("=== Before Visiting ===");
        System.out.println("Energy: " + player.getEnergy());
        System.out.println("Time: " + time.getCurrentTime());
        System.out.println("Location: " + player.getPlayerLocation().getName());

        visitAction.execute(player);

        System.out.println("=== After Visiting ===");
        System.out.println("Energy: " + player.getEnergy());
        System.out.println("Time: " + time.getCurrentTime());
        System.out.println("Location: " + player.getPlayerLocation().getName());

        time.stopTime();
    }
}
