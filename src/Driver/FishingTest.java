package src.Driver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import src.actions.*;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class FishingTest {
    public static void main(String[] args) {
        // Inisialisasi komponen yang diperlukan
        Gold playerGold = new Gold(1000);
        Inventory playerInventory = new Inventory();
        Location playerLocation = new Location("Pond", new Point(10, 10));
        
        // Membuat objek Player
        Player player = new Player(
            "Asep",           // playerName
            "Male",           // gender
            "Spakbor Farm",   // farmName
            playerGold,       // gold
            playerInventory,  // inventory
            playerLocation    // lokasi awal
        );
        
        // Tambahkan Fishing Rod ke inventory
        FishingRod fishingRod = new FishingRod("Fishing Rod", new Gold(50), new Gold(200), 5);
        player.getPlayerInventory().addItem(fishingRod, 1);
        
        // Pastikan player memiliki energi yang cukup
        player.setEnergy(100);
        
        // Buat objek Time dan Weather
        Time gameTime = new Time();
        Weather weather = new Weather();
        
        // Simulasikan input untuk mempermudah pengujian
        // Dalam implementasi nyata, input akan diberikan oleh pengguna
        String input = "5\n";  // simulasi tebakan
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Tampilkan status awal pemain
        System.out.println("==== STATUS AWAL ====");
        System.out.println("Nama Player: " + player.getPlayerName());
        System.out.println("Energi Awal: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        System.out.println("Musim: " + new Season().getCurrentSeason());
        System.out.println("Cuaca: " + weather.getCurrentWeather());
        System.out.println("Lokasi: " + playerLocation.getName());
        
        System.out.println("\nIsi Inventory Awal:");
        player.getPlayerInventory().printInventory();
        
        // 1. Uji memancing di Pond
        System.out.println("\n==== UJI MEMANCING DI POND ====");
        FishingAction fishingPond = new FishingAction(gameTime, "Pond");
        boolean success = fishingPond.execute(player);
        
        System.out.println("\n==== HASIL MEMANCING DI POND ====");
        System.out.println("Aksi Memancing Berhasil: " + success);
        System.out.println("Energi Player Setelah Memancing: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Memancing: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Setelah Memancing di Pond:");
        player.getPlayerInventory().printInventory();
        
        // 2. Pindahkan pemain ke lokasi lain (Mountain Lake)
        System.out.println("\n==== PINDAH KE MOUNTAIN LAKE ====");
        playerLocation.setName("Mountain Lake");
        System.out.println("Lokasi sekarang: " + playerLocation.getName());
        
        // 3. Uji memancing di Mountain Lake
        System.out.println("\n==== UJI MEMANCING DI MOUNTAIN LAKE ====");
        FishingAction fishingLake = new FishingAction(gameTime, "Mountain Lake");
        success = fishingLake.execute(player);
        
        System.out.println("\n==== HASIL MEMANCING DI MOUNTAIN LAKE ====");
        System.out.println("Aksi Memancing Berhasil: " + success);
        System.out.println("Energi Player Setelah Memancing: " + player.getEnergy());
        System.out.println("Waktu Game Setelah Memancing: " + gameTime.getCurrentTime());
        System.out.println("\nIsi Inventory Setelah Memancing di Mountain Lake:");
        player.getPlayerInventory().printInventory();
        
        // 4. Pindahkan pemain ke lokasi yang tidak valid untuk memancing
        System.out.println("\n==== PINDAH KE LOKASI TIDAK VALID ====");
        playerLocation.setName("Village");
        System.out.println("Lokasi sekarang: " + playerLocation.getName());
        
        // 5. Uji memancing di lokasi tidak valid
        System.out.println("\n==== UJI MEMANCING DI LOKASI TIDAK VALID ====");
        FishingAction fishingInvalid = new FishingAction(gameTime, "Village");
        success = fishingInvalid.execute(player);
        
        System.out.println("\n==== HASIL MEMANCING DI LOKASI TIDAK VALID ====");
        System.out.println("Aksi Memancing Berhasil: " + success);
        System.out.println("Energi Player: " + player.getEnergy());
        System.out.println("Waktu Game: " + gameTime.getCurrentTime());
        
        // Kembalikan input stream asli
        System.setIn(originalIn);
        
        // 6. Uji memancing tanpa energi yang cukup
        System.out.println("\n==== UJI MEMANCING TANPA ENERGI CUKUP ====");
        playerLocation.setName("Pond");
        player.setEnergy(3);  // Set energi di bawah minimum (5)
        success = fishingPond.execute(player);
        
        System.out.println("\n==== HASIL MEMANCING TANPA ENERGI CUKUP ====");
        System.out.println("Aksi Memancing Berhasil: " + success);
        System.out.println("Energi Player: " + player.getEnergy());
        
        // 7. Uji memancing tanpa fishing rod
        System.out.println("\n==== UJI MEMANCING TANPA FISHING ROD ====");
        player.setEnergy(100);  // Reset energi
        
        // Hapus fishing rod dari inventory - cara alternatif
        player.getPlayerInventory().removeItem(fishingRod, 1);
        
        System.out.println("\nIsi Inventory Setelah Hapus Fishing Rod:");
        player.getPlayerInventory().printInventory();
        
        success = fishingPond.execute(player);
        
        System.out.println("\n==== HASIL MEMANCING TANPA FISHING ROD ====");
        System.out.println("Aksi Memancing Berhasil: " + success);
        System.out.println("Energi Player: " + player.getEnergy());
        
        // Hentikan waktu game
        gameTime.stopTime();
        System.out.println("\nPengujian selesai!");
    }
}