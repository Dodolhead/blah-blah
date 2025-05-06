package src;

import src.actions.*;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class SleepingTest {
    public static void main(String[] args) {
        // Inisialisasi komponen yang diperlukan
        Gold playerGold = new Gold(1000);
        Inventory playerInventory = new Inventory();
        
        // Buat FarmMap terlebih dahulu
        Point initialPlayerPosition = new Point(2, 2); // Posisi di dalam area rumah
        FarmMap farmMap = new FarmMap(initialPlayerPosition);
        
        // Buat lokasi player dengan merujuk ke farmMap
        Location playerLocation = new Location("Farm", initialPlayerPosition);
        
        // Membuat objek Player
        Player player = new Player(
            "Asep",           // playerName
            "Male",           // gender
            "Spakbor Farm",   // farmName
            playerGold,       // gold
            playerInventory,  // inventory
            playerLocation    // lokasi awal
        );
        
        // Buat objek Time
        Time gameTime = new Time();
        // Set waktu menjadi malam untuk menguji sleeping
        gameTime.skipTimeHour(15); // Skip ke jam 21:00
        
        // Tampilkan status awal pemain
        System.out.println("==== STATUS AWAL ====");
        System.out.println("Nama Player: " + player.getPlayerName());
        System.out.println("Energi Awal: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        System.out.println("Hari: " + gameTime.getDay());
        System.out.println("Posisi Player: " + player.getPlayerLocation().getCurrentPoint().printPoint());
        
        // Tampilkan peta farm untuk memverifikasi posisi
        System.out.println("\nPeta Farm:");
        farmMap.displayFarmMap();
        
        // 1. Uji skenario tidur dengan energi penuh
        System.out.println("\n==== UJI TIDUR DENGAN ENERGI PENUH ====");
        Sleeping sleeping = new Sleeping(gameTime, farmMap);
        boolean success = sleeping.execute(player);
        
        System.out.println("\n==== HASIL TIDUR (ENERGI PENUH) ====");
        System.out.println("Aksi Tidur Berhasil: " + success);
        System.out.println("Energi Player Setelah Tidur: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Tidur: " + gameTime.getCurrentTime());
        System.out.println("Hari: " + gameTime.getDay());
        
        // 2. Uji skenario tidur dengan energi rendah (kurang dari 10%)
        System.out.println("\n==== UJI TIDUR DENGAN ENERGI RENDAH ====");
        // Set energi player menjadi 5 (5% dari MAX_ENERGY)
        player.setEnergy(5);
        System.out.println("Energi Player diset ke: " + player.getEnergy());
        
        // Maju waktu ke malam lagi
        gameTime.skipTimeHour(15);
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        
        success = sleeping.execute(player);
        
        System.out.println("\n==== HASIL TIDUR (ENERGI RENDAH) ====");
        System.out.println("Aksi Tidur Berhasil: " + success);
        System.out.println("Energi Player Setelah Tidur: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Tidur: " + gameTime.getCurrentTime());
        System.out.println("Hari: " + gameTime.getDay());
        
        // 3. Uji skenario tidur dengan energi habis (0)
        System.out.println("\n==== UJI TIDUR DENGAN ENERGI HABIS ====");
        // Set energi player menjadi 0
        player.setEnergy(0);
        System.out.println("Energi Player diset ke: " + player.getEnergy());
        
        // Maju waktu ke malam lagi
        gameTime.skipTimeHour(15);
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        
        success = sleeping.execute(player);
        
        System.out.println("\n==== HASIL TIDUR (ENERGI HABIS) ====");
        System.out.println("Aksi Tidur Berhasil: " + success);
        System.out.println("Energi Player Setelah Tidur: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Tidur: " + gameTime.getCurrentTime());
        System.out.println("Hari: " + gameTime.getDay());
        
        // 4. Uji skenario tidur di luar rumah (seharusnya gagal)
        System.out.println("\n==== UJI TIDUR DI LUAR RUMAH ====");
        // Pindahkan player ke luar rumah (ke tengah farm)
        Point outsidePosition = new Point(15, 15);
        player.getPlayerLocation().setPoint(outsidePosition);
        System.out.println("Player dipindahkan ke posisi: " + player.getPlayerLocation().getCurrentPoint().printPoint());
        
        // Tampilkan peta farm setelah pemain pindah
        System.out.println("\nPeta Farm Setelah Pemain Pindah:");
        farmMap = new FarmMap(outsidePosition); // Buat farm map baru dengan posisi player baru
        farmMap.displayFarmMap();
        
        // Update objek sleeping dengan farm map baru
        sleeping = new Sleeping(gameTime, farmMap);
        
        success = sleeping.execute(player);
        
        System.out.println("\n==== HASIL TIDUR (DI LUAR RUMAH) ====");
        System.out.println("Aksi Tidur Berhasil: " + success);
        System.out.println("Energi Player: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        
        // Hentikan waktu game
        gameTime.stopTime();
        System.out.println("\nPengujian selesai!");
    }
}