package src.actions;

import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class Planting implements Action {
    private FarmMap farmMap;
    private Time gameTime;
    private Seed seedToPlant;
    
    public Planting(FarmMap farmMap, Time gameTime, Seed seedToPlant) {
        this.farmMap = farmMap;
        this.gameTime = gameTime;
        this.seedToPlant = seedToPlant;
    }

    @Override
    public boolean execute(Player player) {
        // Periksa apakah pemain memiliki seed yang akan ditanam
        boolean hasSeed = false;
        for (Class<?> kelas : player.getPlayerInventory().getInventoryStorage().keySet()) {
            if (kelas == Seed.class) {
                for (String itemName : player.getPlayerInventory().getInventoryStorage().get(kelas).keySet()) {
                    if (itemName.equals(seedToPlant.getItemName())) {
                        hasSeed = true;
                        break;
                    }
                }
            }
        }

        // Jika tidak memiliki seed, aksi tidak bisa dilakukan
        if (!hasSeed) {
            System.out.println("Anda tidak memiliki " + seedToPlant.getItemName() + " untuk ditanam!");
            return false;
        }

        // Periksa apakah pemain memiliki cukup energi
        if (player.getEnergy() < 5) {
            System.out.println("Energi tidak cukup untuk melakukan planting!");
            return false;
        }

        // Mendapatkan posisi pemain saat ini
        Point playerPos = player.getPlayerLocation().getCurrentPoint();
        int playerX = playerPos.getX();
        int playerY = playerPos.getY();

        // Periksa apakah posisi ini adalah tilled soil (t)
        char[][] farmDisplay = farmMap.getFarmMapDisplay();
        if (playerY >= 0 && playerY < farmDisplay.length && playerX >= 0 && playerX < farmDisplay[0].length) {
            if (farmDisplay[playerY][playerX] == 't') {
                // Buat objek Season baru untuk memeriksa musim
                Season season = new Season();
                Season.Seasons currentSeason = season.getCurrentSeason(); 
                String seedSeason = seedToPlant.getSeason();
                
                if (!isSeasonValid(currentSeason, seedSeason)) {
                    System.out.println(seedToPlant.getItemName() + " tidak dapat ditanam pada musim " + currentSeason);
                    return false;
                }
                
                // Mulai proses planting
                System.out.println("Memulai penanaman " + seedToPlant.getItemName() + "...");
                
                // Pause waktu game selama aksi berlangsung
                gameTime.pauseTime();
                
                // Simulasi waktu aksi (5 menit dalam waktu game)
                try {
                    // Dalam implementasi nyata, bisa menggunakan Timer atau solusi lain
                    Thread.sleep(1000); // Tunggu 1 detik (simulasi)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Ubah tile menjadi planted land (l)
                farmDisplay[playerY][playerX] = 'l';
                
                // Pindahkan tile dari daftar Tilled ke daftar Planted
                Point plantedPoint = new Point(playerX, playerY);
                farmMap.getObjectPosition().get("Tilled").remove(plantedPoint);
                farmMap.getObjectPosition().get("Planted").add(plantedPoint);
                
                // Kurangi jumlah seed di inventory
                player.getPlayerInventory().removeItem(seedToPlant, 1);
                
                // Kurangi energi pemain
                player.subtractPlayerEnergy(5);
                
                // Lanjutkan waktu dan tambahkan 5 menit
                gameTime.resumeTime();
                gameTime.skipTimeMinute(5);
                
                System.out.println("Penanaman " + seedToPlant.getItemName() + " berhasil!");
                return true;
            } else {
                System.out.println("Anda hanya bisa menanam pada tilled soil!");
                return false;
            }
        } else {
            System.out.println("Posisi tidak valid!");
            return false;
        }
    }
    
    private boolean isSeasonValid(Season.Seasons currentSeason, String seedSeason) {
        // Validasi musim sesuai dengan seed
        switch (currentSeason) {
            case SPRING:
                return seedSeason.toUpperCase().contains("SPRING");
            case SUMMER:
                return seedSeason.toUpperCase().contains("SUMMER");
            case AUTUMN: // AUTUMN dan FALL adalah sama
                return seedSeason.toUpperCase().contains("FALL") || seedSeason.toUpperCase().contains("AUTUMN");
            case WINTER:
                return seedSeason.toUpperCase().contains("WINTER");
            default:
                return false;
        }
    }
}