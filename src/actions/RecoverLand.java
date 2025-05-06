package src.actions;

import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class RecoverLand implements Action {
    private FarmMap farmMap;
    private Time gameTime;

    public RecoverLand(FarmMap farmMap, Time gameTime) {
        this.farmMap = farmMap;
        this.gameTime = gameTime;
    }

    @Override
    public boolean execute(Player player) {
        // Periksa apakah pemain memiliki Pickaxe
        boolean hasPickaxe = false;
        for (Class<?> kelas : player.getPlayerInventory().getInventoryStorage().keySet()) {
            if (kelas == Equipment.class) {
                for (String itemName : player.getPlayerInventory().getInventoryStorage().get(kelas).keySet()) {
                    if (itemName.contains("Pickaxe")) {
                        hasPickaxe = true;
                        break;
                    }
                }
            }
        }

        // Jika tidak memiliki Pickaxe, aksi tidak bisa dilakukan
        if (!hasPickaxe) {
            System.out.println("Anda tidak memiliki Pickaxe untuk mengembalikan tanah!");
            return false;
        }

        // Periksa apakah pemain memiliki cukup energi
        if (player.getEnergy() < 5) {
            System.out.println("Energi tidak cukup untuk melakukan recover land!");
            return false;
        }

        // Mendapatkan posisi pemain saat ini
        Point playerPos = player.getPlayerLocation().getCurrentPoint();
        int playerX = playerPos.getX();
        int playerY = playerPos.getY();

        // Periksa apakah posisi ini adalah tilled soil (t) atau planted land (l)
        char[][] farmDisplay = farmMap.getFarmMapDisplay();
        if (playerY >= 0 && playerY < farmDisplay.length && playerX >= 0 && playerX < farmDisplay[0].length) {
            if (farmDisplay[playerY][playerX] == 't') {
                // Mulai proses recover land
                System.out.println("Memulai proses recover land...");
                
                // Pause waktu game selama aksi berlangsung
                gameTime.pauseTime();
                
                // Simulasi waktu aksi (5 menit dalam waktu game)
                try {
                    // Dalam implementasi nyata, bisa menggunakan Timer atau solusi lain
                    Thread.sleep(1000); // Tunggu 1 detik (simulasi)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Ubah tile menjadi tillable land (.)
                farmDisplay[playerY][playerX] = '.';
                
                // Pindahkan tile dari daftar Tilled ke daftar Tillable
                Point recoveredPoint = new Point(playerX, playerY);
                farmMap.getObjectPosition().get("Tilled").remove(recoveredPoint);
                farmMap.getObjectPosition().get("Tillable").add(recoveredPoint);
                
                // Kurangi energi pemain
                player.subtractPlayerEnergy(5);
                
                // Lanjutkan waktu dan tambahkan 5 menit
                gameTime.resumeTime();
                gameTime.skipTimeMinute(5);
                
                System.out.println("Recover land berhasil! Tanah telah dikembalikan ke keadaan semula.");
                return true;
            } else if (farmDisplay[playerY][playerX] == 'l') {
                System.out.println("Tidak dapat melakukan recover land pada tanah yang sudah ditanami!");
                return false;
            } else {
                System.out.println("Anda hanya bisa melakukan recover land pada tilled soil!");
                return false;
            }
        } else {
            System.out.println("Posisi tidak valid!");
            return false;
        }
    }
}