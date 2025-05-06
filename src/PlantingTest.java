package src;

import src.actions.*;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class PlantingTest {
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
        
        // Tambahkan Hoe dan Seeds ke inventory
        Hoe hoe = new Hoe("Cangkul Kayu", new Gold(50), new Gold(200), 5);
        player.getPlayerInventory().addItem(hoe, 1);
        
        // Buat beberapa seed untuk ditanam
        Seed parsnipSeed = new Seed("Parsnip Seeds", new Gold(20), 1, "SPRING");
        Seed blueberrySeed = new Seed("Blueberry Seeds", new Gold(80), 7, "SUMMER");
        
        // Tambahkan seeds ke inventory
        player.getPlayerInventory().addItem(parsnipSeed, 5);
        player.getPlayerInventory().addItem(blueberrySeed, 3);
        
        // Pastikan player memiliki energi yang cukup
        player.setEnergy(100);
        
        // Buat FarmMap dengan posisi player
        Point playerPositionFarm = player.getPlayerLocation().getCurrentPoint();
        FarmMap farmMap = new FarmMap(playerPositionFarm);
        
        // Buat objek Time dan Season
        Time gameTime = new Time();
        Season season = new Season(); // Buat objek Season terpisah
        // Pastikan musim saat ini adalah SPRING untuk menguji validasi musim
        
        // Tampilkan status awal
        System.out.println("==== STATUS AWAL ====");
        System.out.println("Posisi Player: " + playerPositionFarm.printPoint());
        System.out.println("Energi Player: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        System.out.println("Musim: " + season.getCurrentSeason());
        System.out.println("\nIsi Inventory:");
        player.getPlayerInventory().printInventory();
        
        // Tampilkan peta farm awal
        System.out.println("\nPeta Farm Awal:");
        farmMap.displayFarmMap();
        
        // Buat objek Tilling dan Planting
        Tilling tilling = new Tilling(farmMap, gameTime);
        Planting plantingParsnip = new Planting(farmMap, gameTime, parsnipSeed);
        Planting plantingBlueberry = new Planting(farmMap, gameTime, blueberrySeed);
        
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
        
        // 2. Lakukan aksi planting dengan Parsnip Seed (musim sesuai - SPRING)
        System.out.println("\n==== MELAKUKAN PLANTING PARSNIP ====");
        success = plantingParsnip.execute(player);
        
        System.out.println("\n==== HASIL AKSI PLANTING PARSNIP ====");
        System.out.println("Aksi Planting Parsnip Berhasil: " + success);
        System.out.println("Energi Player Setelah Planting: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Planting: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Setelah Planting Parsnip:");
        player.getPlayerInventory().printInventory();
        
        // Tampilkan peta farm setelah planting
        System.out.println("\nPeta Farm Setelah Planting Parsnip:");
        farmMap.displayFarmMap();
        
        // 3. Pindahkan player ke lokasi lain untuk menanam seed lain
        // Gunakan koordinat baru dan movePlayer untuk memindahkan player
        System.out.println("\n==== MEMINDAHKAN PLAYER KE LOKASI BARU ====");
        playerPositionFarm.movePlayer("right", farmMap.getFarmMapDisplay());
        playerPositionFarm.movePlayer("right", farmMap.getFarmMapDisplay());
        playerPositionFarm.movePlayer("down", farmMap.getFarmMapDisplay());
        playerPositionFarm.movePlayer("down", farmMap.getFarmMapDisplay());
        System.out.println("Player dipindahkan ke posisi: (" + 
                           playerPositionFarm.getX() + "," + 
                           playerPositionFarm.getY() + ")");
        
        // 4. Lakukan tilling lagi untuk lokasi baru
        System.out.println("\n==== MELAKUKAN TILLING DI LOKASI BARU ====");
        success = tilling.execute(player);
        
        // 5. Coba lakukan planting dengan Blueberry Seed (musim tidak sesuai - SUMMER di SPRING)
        System.out.println("\n==== MENCOBA PLANTING BLUEBERRY (TIDAK SESUAI MUSIM) ====");
        success = plantingBlueberry.execute(player);
        
        System.out.println("\n==== HASIL AKSI PLANTING BLUEBERRY ====");
        System.out.println("Aksi Planting Blueberry Berhasil: " + success);
        System.out.println("Energi Player: " + player.getEnergy());
        System.out.println("\nIsi Inventory Setelah Mencoba Planting Blueberry:");
        player.getPlayerInventory().printInventory();
        
        // Tampilkan peta farm setelah percobaan planting
        System.out.println("\nPeta Farm Setelah Mencoba Planting Blueberry:");
        farmMap.displayFarmMap();
        
        // Hentikan waktu game
        gameTime.stopTime();
        System.out.println("\nPengujian selesai!");
    }
}