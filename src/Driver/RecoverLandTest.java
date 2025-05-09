package src.Driver;

import src.actions.RecoverLandAction;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class RecoverLandTest {
    public static void main(String[] args) {
        // Inisialisasi komponen yang diperlukan
        Gold playerGold = new Gold(1000);
        Inventory playerInventory = new Inventory();
        Location playerLocation = new Location("Farm", new Point(10, 10));

        // Membuat objek Player
        Player player = new Player(
            "Asep",
            "Male",
            "Spakbor Farm",
            playerGold,
            playerInventory,
            playerLocation
        );

        // Tambahkan Pickaxe ke inventory
        Pickaxe pickaxe = new Pickaxe("Pickaxe Leon", new Gold(50), new Gold(300), 5);
        player.getPlayerInventory().addItem(pickaxe, 1);

        // Pastikan player memiliki energi yang cukup
        player.setEnergy(100);

        // Buat FarmMap dan ubah tile di posisi pemain menjadi 't' (tilled)
        Point playerPosition = player.getPlayerLocation().getCurrentPoint();
        FarmMap farmMap = new FarmMap(playerPosition);
        char[][] map = farmMap.getFarmMapDisplay();
        map[playerPosition.getY()][playerPosition.getX()] = 't';
        farmMap.getObjectPosition().get("Tillable").removeIf(p -> p.getX() == playerPosition.getX() && p.getY() == playerPosition.getY());
        farmMap.getObjectPosition().get("Tilled").add(playerPosition);

        // Buat objek Time
        Time gameTime = new Time();

        // Tampilkan status awal
        System.out.println("==== STATUS AWAL ====");
        System.out.println("Posisi Player: " + playerPosition.printPoint());
        System.out.println("Energi Player: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());

        System.out.println("\nPeta Farm Sebelum Recover Land:");
        farmMap.displayFarmMap();

        // Jalankan RecoverLandAction
        RecoverLandAction recoverLand = new RecoverLandAction(farmMap, gameTime);
        System.out.println("\n==== MELAKUKAN RECOVER LAND ====");
        boolean success = recoverLand.execute(player);

        // Tampilkan hasil
        System.out.println("\n==== HASIL AKSI RECOVER LAND ====");
        System.out.println("Aksi Recover Land Berhasil: " + success);
        System.out.println("Energi Player Setelah Recover Land: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Recover Land: " + gameTime.getCurrentTime());

        System.out.println("\nPeta Farm Setelah Recover Land:");
        farmMap.displayFarmMap();

        // Jalankan aksi recover lagi (seharusnya gagal)
        System.out.println("\n==== MENCOBA RECOVER LAND LAGI DI TEMPAT YANG SAMA ====");
        success = recoverLand.execute(player);
        System.out.println("Aksi Recover Land Kedua Berhasil: " + success);

        gameTime.stopTime();
        System.out.println("\nPengujian selesai!");
    }
}
