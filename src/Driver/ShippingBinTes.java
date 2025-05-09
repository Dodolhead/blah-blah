package src.Driver;

import src.items.*;
import src.tsw.*;

public class ShippingBinTes {
    public static void main(String[] args) {
        // Setup awal
        Time time = new Time(); // misalnya hari awal = 0

        Inventory inventory = new Inventory();
        ShippingBin shippingBin = new ShippingBin();

        // Buat item Crop
        Crop wheat = new Crop("Wheat", new Gold(50), new Gold(20), 3);
        Food chickenjockey = new Food("Chicken Jockey", 10, new Gold(20), new Gold(10));

        // Tambahkan ke inventory
        inventory.addItem(wheat, 10);
        inventory.addItem(chickenjockey, 5);

        System.out.println("=== INVENTORY SETELAH ADD ITEM ===");
        inventory.printInventory();

        // Masukkan ke shipping bin
        shippingBin.addToShippingBin(inventory, wheat, 5);
        shippingBin.addToShippingBin(inventory, chickenjockey, 2);

        System.out.println("=== SHIPPING BIN SETELAH ADD ITEM ===");
        shippingBin.printShippingBin();

        System.out.println("=== INVENTORY SETELAH REMOVE UNTUK SHIPPING BIN ===");
        inventory.printInventory();

        // Coba jual
        int earnedGold = shippingBin.sellShippingBin(time);
        System.out.println("Gold didapat: " + earnedGold);

        // Coba jual lagi di hari yang sama (harus gagal)
        shippingBin.sellShippingBin(time);

        // Pindah hari
        time.skipTimeHour(24);
        shippingBin.sellShippingBin(time); // Harus bisa jual lagi, meskipun kosong

        time.stopTime();
    }
}
