package src.actions;

import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class Tilling implements Action {
    private FarmMap farmMap;
    private Time gameTime;

    public Tilling(FarmMap farmMap, Time gameTime) {
        this.farmMap = farmMap;
        this.gameTime = gameTime;
    }

    @Override
    public boolean execute(Player player) {
        // Periksa apakah pemain memiliki Hoe
        boolean hasHoe = false;
        for (Class<?> kelas : player.getPlayerInventory().getInventoryStorage().keySet()) {
            if (kelas == Equipment.class) {
                for (String itemName : player.getPlayerInventory().getInventoryStorage().get(kelas).keySet()) {
                    if (itemName.contains("Hoe")) {
                        hasHoe = true;
                        break;
                    }
                }
            }
        }

        // Jika tidak memiliki Hoe, aksi tidak bisa dilakukan
        if (!hasHoe) {
            System.out.println("Anda tidak memiliki Hoe untuk mengolah tanah!");
            return false;
        }

        // Periksa apakah pemain memiliki cukup energi
        if (player.getEnergy() < 5) {
            System.out.println("Energi tidak cukup untuk melakukan tilling!");
            return false;
        }

        // Mendapatkan posisi pemain saat ini
        Point playerPos = player.getPlayerLocation().getCurrentPoint();
        int playerX = playerPos.getX();
        int playerY = playerPos.getY();

        // Periksa apakah posisi ini adalah tillable land (.)
        char[][] farmDisplay = farmMap.getFarmMapDisplay();
        if (playerY >= 0 && playerY < farmDisplay.length && playerX >= 0 && playerX < farmDisplay[0].length) {
            if (farmDisplay[playerY][playerX] == '.') {
                // Mulai proses tilling
                System.out.println("Memulai tilling tanah...");
                
                // Pause waktu game selama aksi berlangsung
                gameTime.pauseTime();
                
                // Simulasi waktu aksi (5 menit dalam waktu game)
                try {
                    // Dalam implementasi nyata, bisa menggunakan Timer atau solusi lain
                    Thread.sleep(1000); // Tunggu 1 detik (simulasi)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Ubah tile menjadi tilled land (t)
                farmDisplay[playerY][playerX] = 't';
                
                // Pindahkan tile dari daftar Tillable ke daftar Tilled
                Point tilledPoint = new Point(playerX, playerY);
                farmMap.getObjectPosition().get("Tillable").remove(tilledPoint);
                farmMap.getObjectPosition().get("Tilled").add(tilledPoint);
                
                // Kurangi energi pemain
                player.subtractPlayerEnergy(5);
                
                // Lanjutkan waktu dan tambahkan 5 menit
                gameTime.resumeTime();
                gameTime.skipTimeMinute(5);
                
                System.out.println("Tilling berhasil! Tanah telah diolah.");
                return true;
            } else {
                System.out.println("Anda hanya bisa melakukan tilling pada tillable land!");
                return false;
            }
        } else {
            System.out.println("Posisi tidak valid!");
            return false;
        }
    }
}