package src.actions;

import src.entities.*;
import src.map.*;
import src.tsw.*;

public class Sleeping implements Action {
    private Time gameTime;
    private FarmMap farmMap;
    private static final int MAX_ENERGY = 100;
    
    public Sleeping(Time gameTime, FarmMap farmMap) {
        this.gameTime = gameTime;
        this.farmMap = farmMap;
    }

    @Override
    public boolean execute(Player player) {
        // Periksa apakah player berada di rumah
        Point playerPos = player.getPlayerLocation().getCurrentPoint();
        char[][] farmDisplay = farmMap.getFarmMapDisplay();
        
        boolean insideHouse = false;
        if (isInHouse(playerPos, farmMap)) {
            insideHouse = true;
        }
        
        if (!insideHouse) {
            System.out.println("Anda hanya bisa tidur di dalam rumah!");
            return false;
        }
        
        // Mulai proses tidur
        System.out.println("Memulai tidur...");
        
        // Hitung energi yang akan dipulihkan berdasarkan kondisi
        int currentEnergy = player.getEnergy();
        int restoredEnergy;
        
        if (currentEnergy <= 0) {
            // Jika energi habis, hanya pulihkan 10 poin
            restoredEnergy = 10;
            System.out.println("Energi sangat lemah, hanya pulih sebanyak 10 poin");
        } else if (currentEnergy < (0.1 * MAX_ENERGY)) {
            // Jika energi kurang dari 10% MAX_ENERGY, pulihkan setengah
            restoredEnergy = MAX_ENERGY / 2;
            System.out.println("Energi lemah, pulih sebanyak setengah maksimum (" + restoredEnergy + " poin)");
        } else {
            // Pulihkan penuh
            restoredEnergy = MAX_ENERGY;
            System.out.println("Energi pulih sepenuhnya (" + restoredEnergy + " poin)");
        }
        
        // Set energi player
        player.setEnergy(restoredEnergy);
        
        // Time skip ke pagi hari berikutnya
        int currentHour = gameTime.getHour();
        int hoursToSkip = 0;
        
        // Hitung berapa jam yang perlu dilewati untuk sampai ke pukul 06:00 pagi berikutnya
        if (currentHour >= 6) {
            // Jika sekarang pukul 06:00 atau lebih, lewati ke pukul 06:00 hari berikutnya
            hoursToSkip = 24 - currentHour + 6;
        } else {
            // Jika sekarang dini hari (00:00 - 05:59), lewati ke pukul 06:00 pagi ini
            hoursToSkip = 6 - currentHour;
        }
        
        // Time skip
        gameTime.pauseTime();
        gameTime.skipTimeHour(hoursToSkip);
        gameTime.resumeTime();
        
        System.out.println("Tidur selesai. Sekarang pukul " + gameTime.getCurrentTime() + ", hari ke-" + gameTime.getDay());
        System.out.println("Energi sekarang: " + player.getEnergy());
        
        return true;
    }
    
    private boolean isInHouse(Point playerPos, FarmMap farmMap) {
        // Cek apakah posisi player ada di dalam area rumah
        char[][] farmDisplay = farmMap.getFarmMapDisplay();
        
        for (Point housePoint : farmMap.getObjectPosition().get("House")) {
            // Jika posisi player sama dengan posisi rumah, berarti player di dalam rumah
            if (playerPos.getX() == housePoint.getX() && playerPos.getY() == housePoint.getY()) {
                return true;
            }
            
            // Atau jika posisi player bersebelahan dengan rumah
            if (Math.abs(playerPos.getX() - housePoint.getX()) <= 1 && 
                Math.abs(playerPos.getY() - housePoint.getY()) <= 1) {
                return true;
            }
        }
        
        return false;
    }
}