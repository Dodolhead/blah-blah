package src.actions;

import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Fishing implements Action {
    private Time gameTime;
    private String fishingLocation;
    private static final Map<String, Map<String, FishInfo>> fishDatabase = new HashMap<>();
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);
    
    // Inner class untuk menyimpan informasi ikan
    private static class FishInfo {
        String name;
        String[] seasons;
        String timeRange;
        String[] weather;
        String[] locations;
        String rarity;
        int basePrice;
        
        FishInfo(String name, String[] seasons, String timeRange, String[] weather, String[] locations, String rarity, int basePrice) {
            this.name = name;
            this.seasons = seasons;
            this.timeRange = timeRange;
            this.weather = weather;
            this.locations = locations;
            this.rarity = rarity;
            this.basePrice = basePrice;
        }
    }
    
    static {
        // Inisialisasi database ikan
        
        // Common Fish
        Map<String, FishInfo> commonFish = new HashMap<>();
        commonFish.put("Bullhead", new FishInfo("Bullhead", new String[]{"ANY"}, "ANY", new String[]{"ANY"}, new String[]{"Mountain Lake"}, "common", 10));
        commonFish.put("Carp", new FishInfo("Carp", new String[]{"ANY"}, "ANY", new String[]{"ANY"}, new String[]{"Mountain Lake", "Pond"}, "common", 10));
        commonFish.put("Chub", new FishInfo("Chub", new String[]{"ANY"}, "ANY", new String[]{"ANY"}, new String[]{"Forest River", "Mountain Lake"}, "common", 10));
        fishDatabase.put("common", commonFish);
        
        // Regular Fish
        Map<String, FishInfo> regularFish = new HashMap<>();
        regularFish.put("Largemouth Bass", new FishInfo("Largemouth Bass", new String[]{"ANY"}, "06.00-18.00", new String[]{"ANY"}, new String[]{"Mountain Lake"}, "regular", 5));
        regularFish.put("Rainbow Trout", new FishInfo("Rainbow Trout", new String[]{"SUMMER"}, "06.00-18.00", new String[]{"SUNNY"}, new String[]{"Forest River", "Mountain Lake"}, "regular", 5));
        regularFish.put("Sardine", new FishInfo("Sardine", new String[]{"ANY"}, "06.00-18.00", new String[]{"ANY"}, new String[]{"Ocean"}, "regular", 5));
        regularFish.put("Salmon", new FishInfo("Salmon", new String[]{"FALL"}, "06.00-18.00", new String[]{"ANY"}, new String[]{"Forest River"}, "regular", 5));
        fishDatabase.put("regular", regularFish);
        
        // Legendary Fish
        Map<String, FishInfo> legendaryFish = new HashMap<>();
        legendaryFish.put("Angler", new FishInfo("Angler", new String[]{"FALL"}, "08.00-20.00", new String[]{"ANY"}, new String[]{"Pond"}, "legendary", 25));
        legendaryFish.put("Crimsonfish", new FishInfo("Crimsonfish", new String[]{"SUMMER"}, "08.00-20.00", new String[]{"ANY"}, new String[]{"Ocean"}, "legendary", 25));
        legendaryFish.put("Glacierfish", new FishInfo("Glacierfish", new String[]{"WINTER"}, "08.00-20.00", new String[]{"ANY"}, new String[]{"Forest River"}, "legendary", 25));
        legendaryFish.put("Legend", new FishInfo("Legend", new String[]{"SPRING"}, "08.00-20.00", new String[]{"RAINY"}, new String[]{"Mountain Lake"}, "legendary", 25));
        fishDatabase.put("legendary", legendaryFish);
    }
    
    public Fishing(Time gameTime, String fishingLocation) {
        this.gameTime = gameTime;
        this.fishingLocation = fishingLocation;
    }

    @Override
    public boolean execute(Player player) {
        // Periksa apakah pemain memiliki Fishing Rod
        boolean hasFishingRod = false;
        for (Class<?> kelas : player.getPlayerInventory().getInventoryStorage().keySet()) {
            if (kelas == Equipment.class) {
                for (String itemName : player.getPlayerInventory().getInventoryStorage().get(kelas).keySet()) {
                    if (itemName.contains("Fishing Rod")) {
                        hasFishingRod = true;
                        break;
                    }
                }
            }
        }

        // Jika tidak memiliki Fishing Rod, aksi tidak bisa dilakukan
        if (!hasFishingRod) {
            System.out.println("Anda tidak memiliki Fishing Rod untuk memancing!");
            return false;
        }

        // Periksa apakah pemain memiliki cukup energi
        if (player.getEnergy() < 5) {
            System.out.println("Energi tidak cukup untuk memancing!");
            return false;
        }
        
        // Validasi lokasi memancing
        if (!isValidFishingLocation(fishingLocation)) {
            System.out.println("Anda tidak bisa memancing di lokasi ini!");
            return false;
        }
        
        // Mulai proses memancing
        System.out.println("Memulai memancing di " + fishingLocation + "...");
        
        // Pause waktu game selama aksi berlangsung
        gameTime.pauseTime();
        
        // Kurangi energi pemain
        player.subtractPlayerEnergy(5);
        
        // Tentukan jenis ikan yang akan ditangkap berdasarkan lokasi, waktu, musim, dan cuaca
        String fishType = determineFishType();
        String fishName = selectRandomFish(fishType);
        
        if (fishName == null) {
            System.out.println("Tidak ada ikan yang bisa ditangkap saat ini di lokasi ini.");
            gameTime.resumeTime();
            gameTime.skipTimeMinute(15);
            return false;
        }
        
        System.out.println("Anda mendapat kesempatan untuk menangkap: " + fishName + " (" + fishType + ")");
        
        // Set batas percobaan dan range angka berdasarkan jenis ikan
        int maxAttempts;
        int maxNumber;
        
        if (fishType.equals("common")) {
            maxAttempts = 10;
            maxNumber = 10;
        } else if (fishType.equals("regular")) {
            maxAttempts = 10;
            maxNumber = 100;
        } else { // legendary
            maxAttempts = 7;
            maxNumber = 500;
        }
        
        // Generate angka acak untuk ditebak
        int targetNumber = random.nextInt(maxNumber) + 1;
        
        System.out.println("Untuk menangkap " + fishName + ", tebak angka 1-" + maxNumber);
        System.out.println("Anda memiliki " + maxAttempts + " kesempatan.");
        
        boolean caught = false;
        int attempts = 0;
        
        while (attempts < maxAttempts) {
            attempts++;
            
            System.out.print("Percobaan ke-" + attempts + ", masukkan tebakan Anda: ");
            
            // Dalam implementasi nyata, gunakan input dari user
            // Untuk pengujian otomatis, gunakan angka acak atau logic lain
            // int guess = scanner.nextInt();
            
            // Untuk pengujian, kita buat tebakan acak
            int guess = random.nextInt(maxNumber) + 1;
            System.out.println(guess);
            
            if (guess == targetNumber) {
                caught = true;
                break;
            } else if (guess < targetNumber) {
                System.out.println("Terlalu kecil!");
            } else {
                System.out.println("Terlalu besar!");
            }
        }
        
        // Tambahkan waktu yang dihabiskan untuk memancing (15 menit)
        gameTime.resumeTime();
        gameTime.skipTimeMinute(15);
        
        if (caught) {
            System.out.println("Selamat! Anda berhasil menangkap " + fishName + "!");
            
            // Buat objek Fish dan tambahkan ke inventory
            FishInfo fishInfo = fishDatabase.get(fishType).get(fishName);
            int sellPrice = calculateFishPrice(fishInfo);
            
            Fish caughtFish = new Fish(fishName, fishInfo.timeRange, fishingLocation, new Gold(sellPrice), fishType);
            player.getPlayerInventory().addItem(caughtFish, 1);
            
            return true;
        } else {
            System.out.println("Sayang sekali, ikan lolos!");
            return false;
        }
    }
    
    // Helper method untuk validasi lokasi memancing
    private boolean isValidFishingLocation(String location) {
        return location.equals("Mountain Lake") || 
               location.equals("Forest River") || 
               location.equals("Ocean") || 
               location.equals("Pond");
    }
    
    // Helper method untuk menentukan jenis ikan yang bisa ditangkap
    private String determineFishType() {
        // Logika untuk menentukan jenis ikan (common, regular, legendary)
        // Berdasarkan probabilitas
        int chance = random.nextInt(100);
        
        if (chance < 60) {
            return "common";
        } else if (chance < 95) {
            return "regular";
        } else {
            return "legendary";
        }
    }
    
    // Helper method untuk memilih ikan secara acak berdasarkan jenis
    private String selectRandomFish(String fishType) {
        Map<String, FishInfo> availableFish = fishDatabase.get(fishType);
        
        // Filter ikan berdasarkan lokasi, waktu, musim, dan cuaca
        Map<String, FishInfo> eligibleFish = new HashMap<>();
        
        Season.Seasons currentSeason = new Season().getCurrentSeason();
        String seasonStr = currentSeason.toString();
        
        int currentHour = gameTime.getHour();
        Weather.WeatherCondition currentWeather = new Weather().getCurrentWeather();
        String weatherStr = currentWeather.toString();
        
        for (Map.Entry<String, FishInfo> entry : availableFish.entrySet()) {
            FishInfo fish = entry.getValue();
            
            boolean locationMatch = false;
            for (String loc : fish.locations) {
                if (loc.equals(fishingLocation) || loc.equals("ANY")) {
                    locationMatch = true;
                    break;
                }
            }
            
            boolean seasonMatch = false;
            for (String season : fish.seasons) {
                if (season.equals(seasonStr) || season.equals("ANY")) {
                    seasonMatch = true;
                    break;
                }
            }
            
            boolean weatherMatch = false;
            for (String weather : fish.weather) {
                if (weather.equals(weatherStr) || weather.equals("ANY")) {
                    weatherMatch = true;
                    break;
                }
            }
            
            boolean timeMatch = false;
            if (fish.timeRange.equals("ANY")) {
                timeMatch = true;
            } else {
                String[] timeParts = fish.timeRange.split("-");
                int startHour = Integer.parseInt(timeParts[0].split("\\.")[0]);
                int endHour = Integer.parseInt(timeParts[1].split("\\.")[0]);
                
                if (currentHour >= startHour && currentHour <= endHour) {
                    timeMatch = true;
                }
            }
            
            if (locationMatch && seasonMatch && weatherMatch && timeMatch) {
                eligibleFish.put(entry.getKey(), fish);
            }
        }
        
        if (eligibleFish.isEmpty()) {
            return null;
        }
        
        // Pilih ikan secara acak dari yang eligible
        int randomIndex = random.nextInt(eligibleFish.size());
        int i = 0;
        for (String fishName : eligibleFish.keySet()) {
            if (i == randomIndex) {
                return fishName;
            }
            i++;
        }
        
        return null;
    }
    
    // Helper method untuk menghitung harga jual ikan
    private int calculateFishPrice(FishInfo fish) {
        // Formula sesuai spesifikasi:
        // (4/banyak_season) × (24/jumlah_jam) × (2/jumlah_variasi_weather) × (4/banyak_lokasi) × C
        
        int seasonCount = fish.seasons.length;
        if (seasonCount == 0 || (seasonCount == 1 && fish.seasons[0].equals("ANY"))) {
            seasonCount = 4; // All seasons
        }
        
        int hourCount = 24;
        if (!fish.timeRange.equals("ANY")) {
            String[] timeParts = fish.timeRange.split("-");
            int startHour = Integer.parseInt(timeParts[0].split("\\.")[0]);
            int endHour = Integer.parseInt(timeParts[1].split("\\.")[0]);
            hourCount = endHour - startHour;
        }
        
        int weatherCount = fish.weather.length;
        if (weatherCount == 0 || (weatherCount == 1 && fish.weather[0].equals("ANY"))) {
            weatherCount = 2; // All weather types
        }
        
        int locationCount = fish.locations.length;
        
        int rarityMultiplier = 0;
        if (fish.rarity.equals("common")) {
            rarityMultiplier = 10;
        } else if (fish.rarity.equals("regular")) {
            rarityMultiplier = 5;
        } else if (fish.rarity.equals("legendary")) {
            rarityMultiplier = 25;
        }
        
        int price = (int) ((4.0 / seasonCount) * (24.0 / hourCount) * (2.0 / weatherCount) * (4.0 / locationCount) * rarityMultiplier);
        
        return price;
    }
}