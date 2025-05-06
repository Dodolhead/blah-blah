package src;

import src.actions.*;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class TillingTest {
    public static void main(String[] args) {
        // Inisialisasi komponen yang diperlukan
        PlayerManager playerManager = new PlayerManager();
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
        
        // Tambahkan Hoe ke inventory
        Hoe hoe = new Hoe("Cangkul Kayu", new Gold(50), new Gold(200), 5);
        player.getPlayerInventory().addItem(hoe, 1);
        
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
        
        // Buat objek Tilling
        Tilling tilling = new Tilling(farmMap, gameTime);
        
        // Lakukan aksi tilling
        System.out.println("\n==== MELAKUKAN TILLING ====");
        boolean success = tilling.execute(player);
        
        // Tampilkan hasil
        System.out.println("\n==== HASIL AKSI ====");
        System.out.println("Aksi Tilling Berhasil: " + success);
        System.out.println("Energi Player Setelah Tilling: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Tilling: " + gameTime.getCurrentTime());
        
        // Tampilkan peta farm setelah tilling
        System.out.println("\nPeta Farm Setelah Tilling:");
        farmMap.displayFarmMap();
        
        // Coba lakukan tilling lagi di tempat yang sama (seharusnya gagal)
        System.out.println("\n==== MENCOBA TILLING LAGI DI TEMPAT YANG SAMA ====");
        success = tilling.execute(player);
        System.out.println("Aksi Tilling Kedua Berhasil: " + success);
        
        // Hentikan waktu game
        gameTime.stopTime();
        System.out.println("\nPengujian selesai!");
    }
}