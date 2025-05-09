package src.Driver;

import src.actions.*;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class WatchingTest {
    public static void main(String[] args) {
        try {
            // Inisialisasi sistem waktu
            Time gameTime = new Time();
            
            // Buat inventory dan gold pemain
            Inventory playerInventory = new Inventory();
            Gold playerGold = new Gold(1000);
            
            // Buat lokasi pemain (mulai di rumah)
            Point housePoint = new Point(12, 12);
            Location houseLocation = new Location("House", housePoint);
            
            // Buat pemain - sesuaikan dengan konstruktor yang benar
            // public Player(String playerName, String gender, String farmName, Gold playerGold, Inventory playerInventory, Location playerLocation) 
            Player player = new Player(
                "TestPlayer",      // playerName
                "Male",            // gender 
                "Test Farm",       // farmName 
                playerGold,        // playerGold
                playerInventory,   // playerInventory
                houseLocation      // playerLocation
            );
            
            // Perhatikan bahwa Player.java Anda tidak memiliki atribut Farm, tetapi hanya farmName (String)
            // Jadi tidak perlu membuat objek Farm dan setFarm()
            
            // Buat HouseMap
            HouseMap houseMap = new HouseMap(housePoint);
            
            // Buat TV untuk fungsi bonus (opsional)
            TV tv = new TV("TV untuk prakiraan cuaca", 1, 1);
            houseMap.placeFurniture(tv, 14, 12);
            
            // Tampilkan status awal
            System.out.println("=== Status Awal ===");
            System.out.println("Pemain: " + player.getPlayerName());
            System.out.println("Lokasi: " + player.getPlayerLocation().getName());
            System.out.println("Energi: " + player.getEnergy());
            System.out.println("Waktu: " + gameTime.getCurrentTime());
            System.out.println();
            
            // Tampilkan peta rumah
            System.out.println("Peta rumah:");
            houseMap.displayHouse();
            System.out.println();
            
            // Buat dan jalankan action Watching
            System.out.println("=== Menjalankan Action Watching ===");
            WatchingAction watchingAction = new WatchingAction(gameTime);
            boolean success = watchingAction.execute(player);
            
            // Tampilkan hasil
            System.out.println("\n=== Setelah Menonton ===");
            System.out.println("Action berhasil: " + success);
            System.out.println("Energi pemain: " + player.getEnergy());
            System.out.println("Waktu saat ini: " + gameTime.getCurrentTime());
            
            // Tes menonton lagi dengan energi tidak cukup
            System.out.println("\n=== Tes dengan energi rendah ===");
            player.setEnergy(3); // Set energi di bawah jumlah yang dibutuhkan
            success = watchingAction.execute(player);
            System.out.println("Action berhasil: " + success);
            System.out.println("Energi pemain: " + player.getEnergy());
            
            // Tes menonton di luar rumah
            System.out.println("\n=== Tes di luar rumah ===");
            player.setEnergy(100); // Reset energi
            player.setPlayerLocation(new Location("Farm", new Point(10, 10)));
            success = watchingAction.execute(player);
            System.out.println("Action berhasil: " + success);
            
            // Hentikan thread waktu
            gameTime.stopTime();
            
        } catch (Exception e) {
            System.out.println("Terjadi error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}