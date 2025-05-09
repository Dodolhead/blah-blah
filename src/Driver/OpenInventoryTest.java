package src.Driver;

import src.actions.OpenInventory;
import src.entities.Player;
import src.items.Gold;
import src.items.Inventory;
import src.items.Crop;
import src.items.Food;
import src.map.Location;
import src.map.Point;

public class OpenInventoryTest {
    public static void main(String[] args) {
        // 1. Inisialisasi objek-objek yang diperlukan
        Gold playerGold = new Gold(1000);
        Inventory inventory = new Inventory();
        Location playerLocation = new Location("Farm", new Point(10, 10));
        
        // 2. Membuat objek Player dengan konstruktor yang sudah diperbarui
        Player player = new Player(
            "Asep Spakbor",  // nama
            "Male",         // gender
            "Spakbor Farm", // nama farm
            playerGold,     // gold
            inventory,      // inventory
            playerLocation  // lokasi
        );
        
        // 3. Tambahkan beberapa item ke inventory
        Crop wheat = new Crop("Wheat", new Gold(50), new Gold(20), 3);
        Food chickenJockey = new Food("Chicken Jockey", 10, new Gold(20), new Gold(10));
        
        // Tambahkan item ke inventory
        inventory.addItem(wheat, 10);
        inventory.addItem(chickenJockey, 5);
        
        // 4. Buat objek OpenInventory
        OpenInventory openInventory = new OpenInventory();
        
        // 5. Jalankan aksi dan periksa hasilnya
        System.out.println("=== MENJALANKAN AKSI OPEN INVENTORY ===");
        boolean success = openInventory.execute(player);
        
        // 6. Cetak status keberhasilan
        System.out.println("Aksi berhasil: " + success);
        
        // 7. Uji kasus khusus - player null
        System.out.println("\n=== UJI KASUS KHUSUS - PLAYER NULL ===");
        boolean failureCase = openInventory.execute(null);
        System.out.println("Aksi berhasil dengan player null: " + failureCase);
    }
}