package src;

import src.actions.*;
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
            "Asep",           // playerName
            "Male",           // gender
            "Spakbor Farm",   // farmName
            playerGold,       // gold
            playerInventory,  // inventory
            playerLocation    // lokasi awal
        );
        
        // Tambahkan Pickaxe ke inventory
        Pickaxe pickaxe = new Pickaxe("Pickaxe Besi", new Gold(50), new Gold(300), 5);
        player.getPlayerInventory().addItem(pickaxe, 1);
        
        // Pastikan player memiliki energi yang cukup
        player.setEnergy(100);
        
        // Buat FarmMap dengan posisi player
        Point playerPositionFarm = player.getPlayerLocation().getCurrentPoint();
        FarmMap farmMap = new FarmMap(playerPositionFarm);
        
        // Buat objek Time
        Time gameTime = new Time();
        
        // Tampilkan status awal
        System.out.println("==== STATUS AWAL ====");
        System.out.println("Posisi Player: " + playerPositionFarm.printPoint());
        System.out.println("Energi Player: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        
        // Tampilkan peta farm awal
        System.out.println("\nPeta Farm Awal:");
        farmMap.displayFarmMap();
        
        // Buat objek Tilling dan RecoverLand
        Tilling tilling = new Tilling(farmMap, gameTime);
        RecoverLand recoverLand = new RecoverLand(farmMap, gameTime);
        
        // 1. Lakukan aksi tilling terlebih dahulu
        System.out.println("\n==== MELAKUKAN TILLING ====");
        boolean success = tilling.execute(player);
        
        System.out.println("\n==== HASIL AKSI TILLING ====");
        System.out.println("Aksi Tilling Berhasil: " + success);
        System.out.println("Energi Player Setelah Tilling: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Tilling: " + gameTime.getCurrentTime());
        
        // Tampilkan peta farm setelah tilling
        System.out.println("\nPeta Farm Setelah Tilling:");
        farmMap.displayFarmMap();
        
        // 2. Lakukan aksi recover land
        System.out.println("\n==== MELAKUKAN RECOVER LAND ====");
        success = recoverLand.execute(player);
        
        System.out.println("\n==== HASIL AKSI RECOVER LAND ====");
        System.out.println("Aksi Recover Land Berhasil: " + success);
        System.out.println("Energi Player Setelah Recover Land: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Recover Land: " + gameTime.getCurrentTime());
        
        // Tampilkan peta farm setelah recover land
        System.out.println("\nPeta Farm Setelah Recover Land:");
        farmMap.displayFarmMap();
        
        // 3. Coba lakukan recover land lagi di tempat yang sama (seharusnya gagal karena sudah menjadi land)
        System.out.println("\n==== MENCOBA RECOVER LAND LAGI DI TEMPAT YANG SAMA ====");
        success = recoverLand.execute(player);
        System.out.println("Aksi Recover Land Kedua Berhasil: " + success);
        
        // Hentikan waktu game
        gameTime.stopTime();
        System.out.println("\nPengujian selesai!");
    }
}