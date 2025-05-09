package src.Driver;

import src.actions.PlantingAction;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class PlantingTest {
    public static void main(String[] args) {
        // Inisialisasi data player
        Gold playerGold = new Gold(500);
        Inventory playerInventory = new Inventory();
        Point playerPoint = new Point(10, 10);
        Location playerLocation = new Location("Farm", playerPoint);
        Player player = new Player("Asep", "Male", "Spakbor Farm", playerGold, playerInventory, playerLocation);

        player.setEnergy(100);

        // Siapkan peta dan tandai posisi player sebagai 't' (tilled)
        FarmMap farmMap = new FarmMap(playerPoint);
        char[][] map = farmMap.getFarmMapDisplay();
        map[playerPoint.getY()][playerPoint.getX()] = 't';
        farmMap.getObjectPosition().get("Tillable").removeIf(p -> p.getX() == playerPoint.getX() && p.getY() == playerPoint.getY());
        farmMap.getObjectPosition().get("Tilled").add(playerPoint);

        // Waktu dan seed
        Time time = new Time();
        Seed wheatSeed = new Seed("Wheat", new Gold(20), 3, "SPRING");
        player.getPlayerInventory().addItem(wheatSeed, 1);

        // Status awal
        System.out.println("==== STATUS AWAL ====");
        System.out.println("Posisi Player: " + playerPoint.printPoint());
        System.out.println("Energi Player: " + player.getEnergy());
        System.out.println("Waktu Game: " + time.getCurrentTime());
        System.out.println("\nPeta Farm Sebelum Penanaman:");
        farmMap.displayFarmMap();

        // Jalankan aksi
        PlantingAction planting = new PlantingAction(farmMap, time, wheatSeed);
        System.out.println("\n==== MELAKUKAN PENANAMAN ====");
        boolean success = planting.execute(player);

        // Tampilkan hasil
        System.out.println("\n==== HASIL AKSI PENANAMAN ====");
        System.out.println("Aksi Penanaman Berhasil: " + success);
        System.out.println("Energi Player Setelah Penanaman: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Penanaman: " + time.getCurrentTime());

        System.out.println("\nPeta Farm Setelah Penanaman:");
        farmMap.displayFarmMap();

        // Uji penanaman kedua (harus gagal)
        System.out.println("\n==== MENCOBA MENANAM DI TEMPAT YANG SAMA ====");
        success = planting.execute(player);
        System.out.println("Aksi Penanaman Kedua Berhasil: " + success);

        time.stopTime();
        System.out.println("\nPengujian selesai!");
    }
}
