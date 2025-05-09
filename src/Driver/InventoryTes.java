package src.Driver;

import src.items.*;

public class InventoryTes {
    public static void main(String[] args) {
        // Buat objek inventory
        Inventory inventory = new Inventory();

        // Buat beberapa item Fish dan Seed
        Fish salmon = new Fish("Salmon", "06:00-20:00", "River", new Gold(120), "Rare");
        Fish tuna = new Fish("Tuna", "10:00-18:00", "Ocean", new Gold(90), "Common");

        Seed carrotSeed = new Seed("Carrot Seed", new Gold(40), 3, "Spring");
        Seed potatoSeed = new Seed("Potato Seed", new Gold(30), 4, "Summer");

        // Tambahkan ke inventory
        inventory.addItem(salmon, 2);
        inventory.addItem(tuna, 1);
        inventory.addItem(carrotSeed, 5);
        inventory.addItem(potatoSeed, 3);

        // Cetak isi inventory
        inventory.printInventory();

        // Coba fitur pengecekan
        System.out.println("\n--- Cek item tertentu ---");
        System.out.println("Ada Salmon? " + inventory.hasItem("Salmon"));
        System.out.println("Ada 2 Carrot Seed? " + inventory.hasItemAndAmount("Carrot Seed", 2));
        System.out.println("Ada 10 Tuna? " + inventory.hasItemAndAmount("Tuna", 10));

        // Coba cek berdasarkan type
        System.out.println("\n--- Cek berdasarkan tipe item ---");
        System.out.println("Ada item tipe Fish? " + inventory.hasItemType(Fish.class));
        System.out.println("Ada item tipe Seed dengan nama mengandung 'Potato'? " +
            inventory.hasItemOfType(Seed.class, "Potato"));
    }
}
