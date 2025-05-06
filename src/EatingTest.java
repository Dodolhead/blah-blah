package src;

import src.actions.*;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class EatingTest {
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
        
        // Set energi awal pemain agak rendah untuk menguji pemulihan energi
        player.setEnergy(40);
        
        // Buat objek Time
        Time gameTime = new Time();
        
        // Buat beberapa item makanan untuk dimakan
        Food breadItem = new Food("Baguette", 25, new Gold(100), new Gold(80));
        Food fishAndChips = new Food("Fish n' Chips", 50, new Gold(150), new Gold(135));
        Crop parsnipItem = new Crop("Parsnip", new Gold(35), new Gold(50), 1);
        Fish fishItem = new Fish("Sardine", "06.00-18.00", "Ocean", new Gold(40), "common");
        
        // Tambahkan item ke inventory pemain
        player.getPlayerInventory().addItem(breadItem, 2);
        player.getPlayerInventory().addItem(fishAndChips, 1);
        player.getPlayerInventory().addItem(parsnipItem, 3);
        player.getPlayerInventory().addItem(fishItem, 1);
        
        // Tampilkan status awal pemain
        System.out.println("==== STATUS AWAL ====");
        System.out.println("Nama Player: " + player.getPlayerName());
        System.out.println("Energi Awal: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Awal:");
        player.getPlayerInventory().printInventory();
        
        // 1. Uji makan Baguette (Food)
        System.out.println("\n==== MAKAN BAGUETTE (FOOD) ====");
        Eating eatBread = new Eating(gameTime, breadItem);
        boolean success = eatBread.execute(player);
        
        System.out.println("\n==== HASIL MAKAN BAGUETTE ====");
        System.out.println("Aksi Makan Berhasil: " + success);
        System.out.println("Energi Player Setelah Makan: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Makan: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Setelah Makan Baguette:");
        player.getPlayerInventory().printInventory();
        
        // 2. Uji makan Parsnip (Crop)
        System.out.println("\n==== MAKAN PARSNIP (CROP) ====");
        Eating eatParsnip = new Eating(gameTime, parsnipItem);
        success = eatParsnip.execute(player);
        
        System.out.println("\n==== HASIL MAKAN PARSNIP ====");
        System.out.println("Aksi Makan Berhasil: " + success);
        System.out.println("Energi Player Setelah Makan: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Makan: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Setelah Makan Parsnip:");
        player.getPlayerInventory().printInventory();
        
        // 3. Uji makan Fish (Fish)
        System.out.println("\n==== MAKAN SARDINE (FISH) ====");
        Eating eatFish = new Eating(gameTime, fishItem);
        success = eatFish.execute(player);
        
        System.out.println("\n==== HASIL MAKAN SARDINE ====");
        System.out.println("Aksi Makan Berhasil: " + success);
        System.out.println("Energi Player Setelah Makan: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Makan: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Setelah Makan Sardine:");
        player.getPlayerInventory().printInventory();
        
        // 4. Coba makan item yang sudah habis
        System.out.println("\n==== MENCOBA MAKAN SARDINE LAGI (SUDAH HABIS) ====");
        success = eatFish.execute(player);
        System.out.println("Aksi Makan Berhasil: " + success);
        
        // Hentikan waktu game
        gameTime.stopTime();
        System.out.println("\nPengujian selesai!");
    }
}