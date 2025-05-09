package src.Driver;

import src.actions.*;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class HarvestingTest {
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
        
        // Buat seed untuk ditanam
        Seed parsnipSeed = new Seed("Parsnip Seeds", new Gold(20), 1, "SPRING");
        
        // Tambahkan seed ke inventory
        player.getPlayerInventory().addItem(parsnipSeed, 5);
        
        // Pastikan player memiliki energi yang cukup
        player.setEnergy(100);
        
        // Buat FarmMap dengan posisi player
        Point playerPositionFarm = player.getPlayerLocation().getCurrentPoint();
        FarmMap farmMap = new FarmMap(playerPositionFarm);
        
        // Buat objek Time
        Time gameTime = new Time();
        Season season = new Season(); // Buat objek Season terpisah
        
        // Tampilkan status awal
        System.out.println("==== STATUS AWAL ====");
        System.out.println("Posisi Player: " + playerPositionFarm.printPoint());
        System.out.println("Energi Player: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        System.out.println("Hari: " + gameTime.getDay());
        System.out.println("Musim: " + season.getCurrentSeason());
        System.out.println("\nIsi Inventory Awal:");
        player.getPlayerInventory().printInventory();
        
        // Tampilkan peta farm awal
        System.out.println("\nPeta Farm Awal:");
        farmMap.displayFarmMap();
        
        // Buat objek aksi
        TillingAction tilling = new TillingAction(farmMap, gameTime);
        PlantingAction planting = new PlantingAction(farmMap, gameTime, parsnipSeed);
        
        // Buat objek Harvesting dengan daftar tanaman
        HarvestingAction harvesting = new HarvestingAction(farmMap, gameTime);
        
        // 1. Langkah pertama: lakukan tilling
        System.out.println("\n==== MELAKUKAN TILLING ====");
        boolean success = tilling.execute(player);
        
        System.out.println("\n==== HASIL AKSI TILLING ====");
        System.out.println("Aksi Tilling Berhasil: " + success);
        System.out.println("Energi Player Setelah Tilling: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Tilling: " + gameTime.getCurrentTime());
        
        // Tampilkan peta farm setelah tilling
        System.out.println("\nPeta Farm Setelah Tilling:");
        farmMap.displayFarmMap();
        
        // 2. Langkah kedua: lakukan planting
        System.out.println("\n==== MELAKUKAN PLANTING ====");
        success = planting.execute(player);
        
        System.out.println("\n==== HASIL AKSI PLANTING ====");
        System.out.println("Aksi Planting Berhasil: " + success);
        System.out.println("Energi Player Setelah Planting: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Planting: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Setelah Planting:");
        player.getPlayerInventory().printInventory();
        
        // Tampilkan peta farm setelah planting
        System.out.println("\nPeta Farm Setelah Planting:");
        farmMap.displayFarmMap();
        
        // Daftarkan tanaman yang baru ditanam
        Point plantedLocation = new Point(playerPositionFarm.getX(), playerPositionFarm.getY());
        harvesting.registerPlantedCrop(plantedLocation, parsnipSeed.getItemName(), parsnipSeed.getHarvestDays());
        
        // 3. Langkah ketiga: lakukan harvesting 
        // (biasanya perlu menunggu beberapa hari, tetapi dalam pengujian ini kita langsung panen)
        System.out.println("\n==== MELAKUKAN HARVESTING ====");
        success = harvesting.execute(player);
        
        System.out.println("\n==== HASIL AKSI HARVESTING ====");
        System.out.println("Aksi Harvesting Berhasil: " + success);
        System.out.println("Energi Player Setelah Harvesting: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Harvesting: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Setelah Harvesting:");
        player.getPlayerInventory().printInventory();
        
        // Tampilkan peta farm setelah harvesting
        System.out.println("\nPeta Farm Setelah Harvesting:");
        farmMap.displayFarmMap();
        
        // 4. Langkah keempat: coba lakukan harvesting lagi di tempat yang sama (seharusnya gagal)
        System.out.println("\n==== MENCOBA HARVESTING LAGI DI TEMPAT YANG SAMA ====");
        success = harvesting.execute(player);
        System.out.println("Aksi Harvesting Kedua Berhasil: " + success);
        
        // Hentikan waktu game
        gameTime.stopTime();
        System.out.println("\nPengujian selesai!");
    }
}