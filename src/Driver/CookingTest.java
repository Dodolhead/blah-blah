package src.Driver;

import src.actions.*;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class CookingTest {
    public static void main(String[] args) {
        // Inisialisasi komponen yang diperlukan
        Gold playerGold = new Gold(1000);
        Inventory playerInventory = new Inventory();
        Location playerLocation = new Location("House", new Point(5, 5));
        
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
        
        // Tampilkan status awal pemain
        System.out.println("==== STATUS AWAL ====");
        System.out.println("Nama Player: " + player.getPlayerName());
        System.out.println("Energi Awal: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        System.out.println("Posisi Player: " + player.getPlayerLocation().getCurrentPoint().printPoint());
        
        // Tambahkan bahan-bahan dan bahan bakar ke inventory
        // 1. Bahan untuk Fish n' Chips (Recipe 1)
        Fish fish1 = new Fish("Sardine", "06.00-18.00", "Ocean", new Gold(40), "common");
        Fish fish2 = new Fish("Salmon", "06.00-18.00", "Forest River", new Gold(80), "regular");
        Crop wheat = new Crop("Wheat", new Gold(30), new Gold(50), 3);
        Crop potato = new Crop("Potato", new Gold(80), new Gold(0), 1);
        
        // 2. Bahan untuk Baguette (Recipe 2)
        // Wheat sudah ditambahkan di atas
        
        // 3. Bahan Bakar
        Misc firewood = new Misc("Firewood", "Kayu bakar untuk memasak");
        Misc coal = new Misc("Coal", "Batu bara untuk memasak");
        
        // Tambahkan item ke inventory pemain
        player.getPlayerInventory().addItem(fish1, 3);
        player.getPlayerInventory().addItem(fish2, 2);
        player.getPlayerInventory().addItem(wheat, 5);
        player.getPlayerInventory().addItem(potato, 3);
        player.getPlayerInventory().addItem(firewood, 2);
        player.getPlayerInventory().addItem(coal, 1);
        
        // Tampilkan inventory awal
        System.out.println("\nIsi Inventory Awal:");
        player.getPlayerInventory().printInventory();
        
        // 1. Uji memasak Fish n' Chips dengan Firewood
        System.out.println("\n==== UJI MEMASAK FISH N' CHIPS DENGAN FIREWOOD ====");
        CookingAction cookingFishNChips = new CookingAction(gameTime, "recipe_1", firewood);
        boolean success = cookingFishNChips.execute(player);
        
        System.out.println("\n==== HASIL MEMASAK FISH N' CHIPS ====");
        System.out.println("Aksi Memasak Berhasil: " + success);
        System.out.println("Energi Player Setelah Memasak: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Memasak: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Setelah Memasak Fish n' Chips:");
        player.getPlayerInventory().printInventory();
        
        // 2. Uji memasak Baguette dengan Coal
        System.out.println("\n==== UJI MEMASAK BAGUETTE DENGAN COAL ====");
        CookingAction cookingBaguette = new CookingAction(gameTime, "recipe_2", coal);
        success = cookingBaguette.execute(player);
        
        System.out.println("\n==== HASIL MEMASAK BAGUETTE ====");
        System.out.println("Aksi Memasak Berhasil: " + success);
        System.out.println("Energi Player Setelah Memasak: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Memasak: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Setelah Memasak Baguette:");
        player.getPlayerInventory().printInventory();
        
        // 3. Uji memasak tanpa bahan bakar
        System.out.println("\n==== UJI MEMASAK TANPA BAHAN BAKAR ====");
        CookingAction cookingNoFuel = new CookingAction(gameTime, "recipe_1", null);
        success = cookingNoFuel.execute(player);
        
        System.out.println("\n==== HASIL MEMASAK TANPA BAHAN BAKAR ====");
        System.out.println("Aksi Memasak Berhasil: " + success);
        System.out.println("Energi Player: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        
        // 4. Uji memasak tanpa bahan yang cukup
        // Pastikan inventory tidak memiliki bahan yang cukup untuk resep 3 (Sashimi)
        System.out.println("\n==== UJI MEMASAK TANPA BAHAN YANG CUKUP ====");
        CookingAction cookingNoIngredients = new CookingAction(gameTime, "recipe_3", firewood);
        success = cookingNoIngredients.execute(player);
        
        System.out.println("\n==== HASIL MEMASAK TANPA BAHAN YANG CUKUP ====");
        System.out.println("Aksi Memasak Berhasil: " + success);
        System.out.println("Energi Player: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        
        // Hentikan waktu game
        gameTime.stopTime();
        System.out.println("\nPengujian selesai!");
    }
}