package src.Driver;

import java.util.Scanner;
import src.actions.*;
import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class VisitingTest {
    public static void main(String[] args) {
        // Buat objek Time
        Time gameTime = new Time();
        
        // Buat pemain di lokasi Farm
        Gold playerGold = new Gold(100);
        Inventory inventory = new Inventory();
        
        // Buat pemain di tepi farm (posisi 0, 15) untuk pengujian
        Point playerPosition = new Point(0, 15);
        Location startingLocation = new Location("Farm", playerPosition);
        
        // Menggunakan konstruktor Player yang sesuai dengan kelas Player yang diberikan
        Player player = new Player(
            "Asep",           // playerName
            "Male",           // gender
            "Asep Farm",      // farmName
            playerGold,       // playerGold
            inventory,        // playerInventory
            startingLocation  // playerLocation
        );
        
        // Buat Farm Map
        FarmMap farmMap = new FarmMap(playerPosition);
        
        // Buat World Map
        WorldMap worldMap = new WorldMap(player);
        
        System.out.println("=== PENGUJIAN VISITING ===");
        System.out.println("Lokasi awal pemain: " + player.getPlayerLocation().getName());
        System.out.println("Energi awal pemain: " + player.getEnergy());
        System.out.println("Waktu saat ini: " + gameTime.getCurrentTime());
        
        // Tampilkan peta farm untuk memverifikasi posisi pemain
        System.out.println("\nPeta Farm Awal:");
        farmMap.displayFarmMap();
        
        Scanner scanner = new Scanner(System.in);
        
        // Uji fungsionalitas visiting
        if (farmMap.isAtEdge(playerPosition)) {
            System.out.println("\nPemain berada di tepi farm dan dapat mengunjungi lokasi lain!");
            System.out.println("Pilih destinasi untuk dikunjungi:");
            System.out.println("1. Forest River");
            System.out.println("2. Mountain Lake");
            System.out.println("3. Ocean");
            System.out.println("4. Store");
            
            System.out.print("Masukkan pilihan Anda (1-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // konsumsi baris baru
            
            String destination = "";
            switch (choice) {
                case 1: destination = "Forest River"; break;
                case 2: destination = "Mountain Lake"; break;
                case 3: destination = "Ocean"; break;
                case 4: destination = "Store"; break;
                default: System.out.println("Pilihan tidak valid. Tetap di farm."); break;
            }
            
            if (!destination.isEmpty()) {
                // Jalankan aksi visiting
                VisitingAction visitAction = new VisitingAction(worldMap, destination, gameTime);
                boolean success = visitAction.execute(player);
                
                if (success) {
                    System.out.println("\nBerhasil mengunjungi lokasi!");
                    System.out.println("Lokasi baru: " + player.getPlayerLocation().getName());
                    System.out.println("Energi pemain setelah berkunjung: " + player.getEnergy());
                    System.out.println("Waktu saat ini: " + gameTime.getCurrentTime());
                } else {
                    System.out.println("\nGagal mengunjungi lokasi.");
                }
            }
        } else {
            System.out.println("\nPemain tidak berada di tepi farm. Tidak dapat mengunjungi lokasi lain.");
        }
        
        // Tampilkan tempat yang sudah dikunjungi
        System.out.println("\nTempat yang sudah dikunjungi: " + player.getVisitedPlace());
        
        // Uji kembali ke farm
        System.out.println("\nApakah Anda ingin kembali ke farm? (y/n)");
        String returnChoice = scanner.nextLine();
        
        if (returnChoice.equalsIgnoreCase("y")) {
            worldMap.returnToFarm();
            System.out.println("Kembali ke farm!");
            System.out.println("Lokasi saat ini: " + player.getPlayerLocation().getName());
        }
        
        gameTime.stopTime();
        scanner.close();
    }
}