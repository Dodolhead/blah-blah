package src;

import src.actions.*;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class ShowLocationTest {
    public static void main(String[] args) {
        // Inisialisasi komponen yang diperlukan
        Gold playerGold = new Gold(1000);
        Inventory playerInventory = new Inventory();
        
        // 1. Pengujian di Farm Map
        System.out.println("\n===== PENGUJIAN DI FARM MAP =====");
        // Buat lokasi awal di farm
        Point initialPosition = new Point(10, 10);
        Location farmLocation = new Location("Farm", initialPosition);
        
        // Buat Farm Map dengan posisi pemain
        FarmMap farmMap = new FarmMap(initialPosition);
        
        // Membuat objek Player
        Player player = new Player(
            "Asep",           // playerName
            "Male",           // gender
            "Spakbor Farm",   // farmName
            playerGold,       // gold
            playerInventory,  // inventory
            farmLocation      // lokasi awal
        );
        
        // Tampilkan status awal
        System.out.println("Status Awal:");
        System.out.println("Nama Player: " + player.getPlayerName());
        System.out.println("Lokasi: " + player.getPlayerLocation().getName());
        System.out.println("Posisi: " + player.getPlayerLocation().getCurrentPoint().printPoint());
        
        // Tampilkan peta farm untuk referensi
        System.out.println("\nPeta Farm:");
        farmMap.displayFarmMap();
        
        // Uji aksi Show Location di Farm
        System.out.println("\n--- AKSI SHOW LOCATION DI FARM ---");
        ShowLocation showLocationFarm = new ShowLocation(farmMap);
        boolean success = showLocationFarm.execute(player);
        
        System.out.println("\nAksi Show Location Berhasil: " + success);
        
        // 2. Pengujian di dekat objek (Pond)
        System.out.println("\n===== PENGUJIAN DI DEKAT POND =====");
        // Cari posisi pond dari map
        Point pondPosition = null;
        for (Point p : farmMap.getObjectPosition().get("Pond")) {
            pondPosition = p;
            break;
        }
        
        if (pondPosition != null) {
            // Pindahkan player ke dekat pond (jarak 1 tile)
            Point nearPondPosition = new Point(pondPosition.getX() + 1, pondPosition.getY());
            player.getPlayerLocation().setPoint(nearPondPosition);
            
            // Update farm map dengan posisi baru
            farmMap = new FarmMap(nearPondPosition);
            
            System.out.println("Player dipindahkan ke posisi: " + nearPondPosition.printPoint());
            System.out.println("\nPeta Farm Setelah Pindah:");
            farmMap.displayFarmMap();
            
            // Uji aksi Show Location di dekat Pond
            System.out.println("\n--- AKSI SHOW LOCATION DI DEKAT POND ---");
            showLocationFarm = new ShowLocation(farmMap);
            success = showLocationFarm.execute(player);
            
            System.out.println("\nAksi Show Location Berhasil: " + success);
        }
        
        // 3. Pengujian di lokasi lain (Mountain Lake)
        System.out.println("\n===== PENGUJIAN DI MOUNTAIN LAKE =====");
        // Pindahkan player ke Mountain Lake
        Location mountainLakeLocation = new Location("Mountain Lake", new Point(5, 5));
        player.setPlayerLocation(mountainLakeLocation);
        
        System.out.println("Player dipindahkan ke lokasi: " + player.getPlayerLocation().getName());
        System.out.println("Posisi: " + player.getPlayerLocation().getCurrentPoint().printPoint());
        
        // Uji aksi Show Location di Mountain Lake
        System.out.println("\n--- AKSI SHOW LOCATION DI MOUNTAIN LAKE ---");
        ShowLocation showLocationLake = new ShowLocation(farmMap);
        success = showLocationLake.execute(player);
        
        System.out.println("\nAksi Show Location Berhasil: " + success);
        
        // 4. Pengujian di lokasi lain (Store)
        System.out.println("\n===== PENGUJIAN DI STORE =====");
        // Pindahkan player ke Store
        Location storeLocation = new Location("Store", new Point(2, 2));
        player.setPlayerLocation(storeLocation);
        
        System.out.println("Player dipindahkan ke lokasi: " + player.getPlayerLocation().getName());
        System.out.println("Posisi: " + player.getPlayerLocation().getCurrentPoint().printPoint());
        
        // Uji aksi Show Location di Store
        System.out.println("\n--- AKSI SHOW LOCATION DI STORE ---");
        ShowLocation showLocationStore = new ShowLocation(farmMap);
        success = showLocationStore.execute(player);
        
        System.out.println("\nAksi Show Location Berhasil: " + success);
        System.out.println("\nPengujian selesai!");
    }
}