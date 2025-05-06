package src.actions;

import src.entities.*;
import src.items.*;
import src.tsw.*;

public class Eating implements Action {
    private Time gameTime;
    private Item itemToEat;
    
    public Eating(Time gameTime, Item itemToEat) {
        this.gameTime = gameTime;
        this.itemToEat = itemToEat;
    }

    @Override
    public boolean execute(Player player) {
        // Periksa apakah item yang akan dimakan ada di inventory
        boolean hasItem = false;
        int itemAmount = 0;
        
        if (itemToEat instanceof Food) {
            // Jika item adalah makanan
            itemAmount = player.getPlayerInventory().getItemAmount(itemToEat);
            hasItem = itemAmount > 0;
        } else if (itemToEat instanceof Crop) {
            // Jika item adalah hasil panen
            itemAmount = player.getPlayerInventory().getItemAmount(itemToEat);
            hasItem = itemAmount > 0;
        } else if (itemToEat instanceof Fish) {
            // Jika item adalah ikan
            itemAmount = player.getPlayerInventory().getItemAmount(itemToEat);
            hasItem = itemAmount > 0;
        }
        
        if (!hasItem) {
            System.out.println("Anda tidak memiliki " + itemToEat.getItemName() + " untuk dimakan!");
            return false;
        }
        
        // Hitung energi yang akan dipulihkan
        int energyToRestore = 0;
        
        if (itemToEat instanceof Food) {
            Food food = (Food) itemToEat;
            energyToRestore = food.getGiveEnergy();
        } else if (itemToEat instanceof Crop) {
            // Sesuai spesifikasi, setiap crop yang dimakan akan mengembalikan energi sebanyak 3
            energyToRestore = 3;
        } else if (itemToEat instanceof Fish) {
            // Sesuai spesifikasi, setiap fish yang dimakan akan mengembalikan energi sebanyak 1
            energyToRestore = 1;
        }
        
        if (energyToRestore <= 0) {
            System.out.println(itemToEat.getItemName() + " tidak memberikan energi!");
            return false;
        }
        
        // Mulai proses makan
        System.out.println("Memulai makan " + itemToEat.getItemName() + "...");
        
        // Pause waktu game selama aksi berlangsung
        gameTime.pauseTime();
        
        // Simulasi waktu aksi (5 menit dalam waktu game)
        try {
            Thread.sleep(1000); // Tunggu 1 detik (simulasi)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Tambahkan energi ke player
        player.addPlayerEnergy(energyToRestore);
        
        // Kurangi jumlah item dari inventory
        player.getPlayerInventory().removeItem(itemToEat, 1);
        
        // Lanjutkan waktu dan tambahkan 5 menit
        gameTime.resumeTime();
        gameTime.skipTimeMinute(5);
        
        System.out.println("Makan " + itemToEat.getItemName() + " berhasil! Energi bertambah " + energyToRestore + " poin.");
        System.out.println("Energi sekarang: " + player.getEnergy());
        
        return true;
    }
}