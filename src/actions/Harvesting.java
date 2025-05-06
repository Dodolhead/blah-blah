package src.actions;

import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;
import java.util.HashMap;
import java.util.Map;

public class Harvesting implements Action {
    private FarmMap farmMap;
    private Time gameTime;
    // Map untuk menyimpan seed dan crop-nya
    private static final Map<String, String> seedToCropMap = new HashMap<>();
    // Map untuk menyimpan jumlah crop yang akan dihasilkan
    private static final Map<String, Integer> cropYieldMap = new HashMap<>();
    
    static {
        // Inisialisasi pemetaan seed ke crop
        seedToCropMap.put("Parsnip Seeds", "Parsnip");
        seedToCropMap.put("Cauliflower Seeds", "Cauliflower");
        seedToCropMap.put("Potato Seeds", "Potato");
        seedToCropMap.put("Wheat Seeds", "Wheat");
        seedToCropMap.put("Blueberry Seeds", "Blueberry");
        seedToCropMap.put("Tomato Seeds", "Tomato");
        seedToCropMap.put("Hot Pepper Seeds", "Hot Pepper");
        seedToCropMap.put("Melon Seeds", "Melon");
        seedToCropMap.put("Cranberry Seeds", "Cranberry");
        seedToCropMap.put("Pumpkin Seeds", "Pumpkin");
        seedToCropMap.put("Grape Seeds", "Grape");
        
        // Inisialisasi jumlah crop yang dihasilkan
        cropYieldMap.put("Parsnip", 1);
        cropYieldMap.put("Cauliflower", 1);
        cropYieldMap.put("Potato", 1);
        cropYieldMap.put("Wheat", 3);
        cropYieldMap.put("Blueberry", 3);
        cropYieldMap.put("Tomato", 1);
        cropYieldMap.put("Hot Pepper", 1);
        cropYieldMap.put("Melon", 1);
        cropYieldMap.put("Cranberry", 10);
        cropYieldMap.put("Pumpkin", 1);
        cropYieldMap.put("Grape", 20);
    }

    // Informasi tanaman yang ditanam dan tanggal tanamnya
    private Map<Point, PlantedCrop> plantedCropsInfo;
    
    // Inner class untuk menyimpan informasi tanaman yang ditanam
    private static class PlantedCrop {
        String seedName;
        int plantedDay;
        int daysToHarvest;
        boolean isWatered;
        
        PlantedCrop(String seedName, int plantedDay, int daysToHarvest) {
            this.seedName = seedName;
            this.plantedDay = plantedDay;
            this.daysToHarvest = daysToHarvest;
            this.isWatered = false;
        }
        
        boolean isReadyToHarvest(int currentDay) {
            return currentDay >= (plantedDay + daysToHarvest);
        }
    }
    
    public Harvesting(FarmMap farmMap, Time gameTime) {
        this.farmMap = farmMap;
        this.gameTime = gameTime;
        this.plantedCropsInfo = new HashMap<>();
    }
    
    // Metode untuk mendaftarkan tanaman yang ditanam
    public void registerPlantedCrop(Point location, String seedName, int daysToHarvest) {
        plantedCropsInfo.put(location, new PlantedCrop(seedName, gameTime.getDay(), daysToHarvest));
    }
    
    // Metode untuk mencatat penyiraman tanaman
    public void waterCrop(Point location) {
        if (plantedCropsInfo.containsKey(location)) {
            plantedCropsInfo.get(location).isWatered = true;
        }
    }

    @Override
    public boolean execute(Player player) {
        // Periksa apakah pemain memiliki cukup energi
        if (player.getEnergy() < 5) {
            System.out.println("Energi tidak cukup untuk melakukan harvesting!");
            return false;
        }

        // Mendapatkan posisi pemain saat ini
        Point playerPos = player.getPlayerLocation().getCurrentPoint();
        int playerX = playerPos.getX();
        int playerY = playerPos.getY();

        // Periksa apakah posisi ini adalah planted land (l)
        char[][] farmDisplay = farmMap.getFarmMapDisplay();
        if (playerY >= 0 && playerY < farmDisplay.length && playerX >= 0 && playerX < farmDisplay[0].length) {
            if (farmDisplay[playerY][playerX] == 'l') {
                // Periksa apakah tanaman sudah siap panen
                Point cropLocation = new Point(playerX, playerY);
                
                // Untuk tujuan pengujian, asumsikan tanaman siap panen jika kita tidak memiliki informasi tentangnya
                boolean readyToHarvest = true;
                String cropType = "Parsnip"; // Default crop
                int yieldAmount = 1; // Default yield
                
                if (plantedCropsInfo.containsKey(cropLocation)) {
                    PlantedCrop plantedCrop = plantedCropsInfo.get(cropLocation);
                    readyToHarvest = plantedCrop.isReadyToHarvest(gameTime.getDay());
                    
                    if (readyToHarvest) {
                        String seedName = plantedCrop.seedName;
                        cropType = seedToCropMap.getOrDefault(seedName, "Parsnip");
                        yieldAmount = cropYieldMap.getOrDefault(cropType, 1);
                    } else {
                        System.out.println("Tanaman belum siap untuk dipanen!");
                        return false;
                    }
                }
                
                if (readyToHarvest) {
                    // Mulai proses harvesting
                    System.out.println("Memulai pemanenan " + cropType + "...");
                    
                    // Pause waktu game selama aksi berlangsung
                    gameTime.pauseTime();
                    
                    // Simulasi waktu aksi (5 menit dalam waktu game)
                    try {
                        Thread.sleep(1000); // Tunggu 1 detik (simulasi)
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    
                    // Buat objek crop untuk diberikan ke inventory
                    Gold cropSellPrice = new Gold(0); // Harga jual default 0
                    Gold cropBuyPrice = new Gold(0); // Harga beli default 0
                    
                    // Set harga berdasarkan jenis crop (Dapat dikembangkan lebih lanjut)
                    if ("Parsnip".equals(cropType)) {
                        cropSellPrice = new Gold(35);
                        cropBuyPrice = new Gold(50);
                    } else if ("Wheat".equals(cropType)) {
                        cropSellPrice = new Gold(30);
                        cropBuyPrice = new Gold(50);
                    } else if ("Blueberry".equals(cropType)) {
                        cropSellPrice = new Gold(40);
                        cropBuyPrice = new Gold(150);
                    }
                    
                    Crop harvestedCrop = new Crop(cropType, cropSellPrice, cropBuyPrice, yieldAmount);
                    
                    // Tambahkan crop ke inventory pemain
                    player.getPlayerInventory().addItem(harvestedCrop, yieldAmount);
                    
                    // Ubah tile menjadi tilled land kembali (t)
                    farmDisplay[playerY][playerX] = 't';
                    
                    // Pindahkan tile dari daftar Planted ke daftar Tilled
                    farmMap.getObjectPosition().get("Planted").remove(cropLocation);
                    farmMap.getObjectPosition().get("Tilled").add(cropLocation);
                    
                    // Hapus informasi tanaman yang sudah dipanen
                    plantedCropsInfo.remove(cropLocation);
                    
                    // Kurangi energi pemain
                    player.subtractPlayerEnergy(5);
                    
                    // Lanjutkan waktu dan tambahkan 5 menit
                    gameTime.resumeTime();
                    gameTime.skipTimeMinute(5);
                    
                    System.out.println("Pemanenan " + cropType + " berhasil! Anda mendapatkan " + yieldAmount + " " + cropType + ".");
                    return true;
                }
            } else {
                System.out.println("Tidak ada tanaman yang dapat dipanen di sini!");
                return false;
            }
        } else {
            System.out.println("Posisi tidak valid!");
            return false;
        }
        
        return false;
    }
}