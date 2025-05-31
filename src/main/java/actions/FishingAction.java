package actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import entities.*;
import items.*;
import tsw.*;
import gui.FishingPanel;

public class FishingAction implements Action {
    private static final Map<String, List<Fish>> fishDatabase = new HashMap<>();
    private int ENERGY_COST = 5;
    private int TIME_COST = 15;

    static {
        // COMMON FISH
        List<Fish> commonFish = new ArrayList<>();
        commonFish.add(new Fish("Bullhead", 0, 24,
            Arrays.asList("ANY"),
            Arrays.asList("ANY"),
            Arrays.asList("Mountain Lake"),
            "common",
            ItemManager.load("/items/fish/bullhead.png")
        ));
        commonFish.add(new Fish("Carp", 0, 24,
            Arrays.asList("ANY"),
            Arrays.asList("ANY"),
            Arrays.asList("Mountain Lake", "Pond"),
            "common",
            ItemManager.load("/items/fish/carp.png")
        ));
        commonFish.add(new Fish("Chub", 0, 24,
            Arrays.asList("ANY"),
            Arrays.asList("ANY"),
            Arrays.asList("Forest River", "Mountain Lake"),
            "common",
            ItemManager.load("/items/fish/chub.png")
        ));
        fishDatabase.put("common", commonFish);

        // REGULAR FISH (12 total)
        List<Fish> regularFish = new ArrayList<>();
        regularFish.add(new Fish("Largemouth Bass", 6, 18,
            Arrays.asList("ANY"),
            Arrays.asList("ANY"),
            Arrays.asList("Mountain Lake"),
            "regular",
            ItemManager.load("/items/fish/largemouth-bass.png")
        ));
        regularFish.add(new Fish("Rainbow Trout", 6, 18,
            Arrays.asList("SUMMER"),
            Arrays.asList("SUNNY"),
            Arrays.asList("Forest River", "Mountain Lake"),
            "regular",
            ItemManager.load("/items/fish/rainbow-trout.png")
        ));
        regularFish.add(new Fish("Sturgeon", 6, 18,
            Arrays.asList("SUMMER", "WINTER"),
            Arrays.asList("ANY"),
            Arrays.asList("Mountain Lake"),
            "regular",
            ItemManager.load("/items/fish/sturgeon.png")
        ));
        regularFish.add(new Fish("Midnight Carp", 20, 2,
            Arrays.asList("WINTER", "FALL"),
            Arrays.asList("ANY"),
            Arrays.asList("Mountain Lake", "Pond"),
            "regular",
            ItemManager.load("/items/fish/midnight-carp.png")
        ));
        regularFish.add(new Fish("Flounder", 6, 22,
            Arrays.asList("SPRING", "SUMMER"),
            Arrays.asList("ANY"),
            Arrays.asList("Ocean"),
            "regular",
            ItemManager.load("/items/fish/flounder.png")
        ));
        regularFish.add(new Fish("Halibut", 6, 11,  // Note: only first time range handled
            Arrays.asList("ANY"),
            Arrays.asList("ANY"),
            Arrays.asList("Ocean"),
            "regular",
            ItemManager.load("/items/fish/halibut.png")
        ));
        regularFish.add(new Fish("Octopus", 6, 22,
            Arrays.asList("SUMMER"),
            Arrays.asList("ANY"),
            Arrays.asList("Ocean"),
            "regular",
            ItemManager.load("/items/fish/octopus.png")
        ));
        regularFish.add(new Fish("Pufferfish", 0, 16,
            Arrays.asList("SUMMER"),
            Arrays.asList("SUNNY"),
            Arrays.asList("Ocean"),
            "regular",
            ItemManager.load("/items/fish/pufferfish.png")
        ));
        regularFish.add(new Fish("Sardine", 6, 18,
            Arrays.asList("ANY"),
            Arrays.asList("ANY"),
            Arrays.asList("Ocean"),
            "regular",
            ItemManager.load("/items/fish/sardine.png")
        ));
        regularFish.add(new Fish("Super Cucumber", 18, 2,
            Arrays.asList("SUMMER", "FALL", "WINTER"),
            Arrays.asList("ANY"),
            Arrays.asList("Ocean"),
            "regular",
            ItemManager.load("/items/fish/super-cucumber.png")
        ));
        regularFish.add(new Fish("Catfish", 6, 22,
            Arrays.asList("SPRING", "SUMMER", "FALL"),
            Arrays.asList("RAINY"),
            Arrays.asList("Forest River", "Pond"),
            "regular",
            ItemManager.load("/items/fish/catfish.png")
        ));
        regularFish.add(new Fish("Salmon", 6, 18,
            Arrays.asList("FALL"),
            Arrays.asList("ANY"),
            Arrays.asList("Forest River"),
            "regular",
            ItemManager.load("/items/fish/salmon.png")
        ));
        fishDatabase.put("regular", regularFish);

        // LEGENDARY FISH
        List<Fish> legendaryFish = new ArrayList<>();
        legendaryFish.add(new Fish("Angler", 8, 20,
            Arrays.asList("FALL"),
            Arrays.asList("ANY"),
            Arrays.asList("Pond"),
            "legendary",
            ItemManager.load("/items/fish/angler.png")
        ));
        legendaryFish.add(new Fish("Crimsonfish", 8, 20,
            Arrays.asList("SUMMER"),
            Arrays.asList("ANY"),
            Arrays.asList("Ocean"),
            "legendary",
            ItemManager.load("/items/fish/crimsonfish.png")
        ));
        legendaryFish.add(new Fish("Glacierfish", 8, 20,
            Arrays.asList("WINTER"),
            Arrays.asList("ANY"),
            Arrays.asList("Forest River"),
            "legendary",
            ItemManager.load("/items/fish/glacierfish.png")
        ));
        legendaryFish.add(new Fish("Legend", 8, 20,
            Arrays.asList("SPRING"),
            Arrays.asList("RAINY"),
            Arrays.asList("Mountain Lake"),
            "legendary",
            ItemManager.load("/items/fish/legend.png")
        ));
        fishDatabase.put("legendary", legendaryFish);
    }


    private String determineFishType() {
        int chance = nextInt(100);

        if (chance < 60) {
            return "common";
        } else if (chance < 95) {
            return "regular";
        } else {
            return "legendary";
        }
    }

    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        Time gameTime = farm.getTime();
        String fishType = determineFishType();
        String fishName = selectRandomFish(fishType, player);

        if (!player.getPlayerInventory().hasItem("Fishing Rod")) {
            showMessage("You need a Fishing Rod to fish!");
            return false;
        }
        if (player.getEnergy() < ENERGY_COST) {
            showMessage("You don't have enough energy to do this action.");
            return false;
        }

        if (fishName == null) {
            showMessage("Hmm nothing seems to bite your rod, Try again later");
            return false;
        }

        gameTime.pauseTime();

        // 1. Tampilkan popup, setelah OK baru lanjut ke minigame
        javax.swing.SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Hmm? you felt something bite your rod.");
            // 2. Setelah klik OK, munculkan minigame
            int maxAttempts;
            int maxNumber;
            if (fishType.equals("common")) {
                maxAttempts = 10;
                maxNumber = 10;
            } else if (fishType.equals("regular")) {
                maxAttempts = 10;
                maxNumber = 100;
            } else {
                maxAttempts = 7;
                maxNumber = 500;
            }
            int fishNumber = nextInt(maxNumber);

            FishingPanel.FishingResultListener listener = (caught, guessedNumber, attempts) -> {
                Fish caughtFish = findFishByName(fishType, fishName);
                if (caught) {
                    showMessage("You caught a " + fishName + "!");
                    player.getPlayerInventory().addItem(caughtFish, 1);

                    // Tambah statistik berdasarkan rarity
                    String rarity = caughtFish.getRarity(); // atau getRarity()
                    player.fishCaughtByRarity.put(
                        rarity,
                        player.fishCaughtByRarity.getOrDefault(rarity, 0) + 1
                    );
                    // Tambah statistik berdasarkan nama ikan
                    String name = caughtFish.getItemName();
                    player.fishCaughtByName.put(
                        name,
                        player.fishCaughtByName.getOrDefault(name, 0) + 1
                    );
                } else {
                    showMessage("The fish got away...");
                }
                player.subtractPlayerEnergy(ENERGY_COST);
                gameTime.skipTimeMinute(TIME_COST);
                gameTime.resumeTime();
                // Jika perlu, update GUI/inventory player di sini
            };
            FishingPanel panel = new FishingPanel(
                null,
                maxAttempts,
                maxNumber,
                fishNumber,
                listener
            );
            panel.setVisible(true);
        });

        return true;
    }

    private void showMessage(String msg) {
        // Ganti dengan JOptionPane/showOnMainPanel, atau System.out.println untuk debugging
        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.JOptionPane.showMessageDialog(null, msg);
        });
    }

    private String selectRandomFish(String fishType, Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        Time gameTime = farm.getTime();
        Season.Seasons currentSeason = new Season().getCurrentSeason();
        Weather.WeatherCondition currentWeather = new Weather().getCurrentWeather();
        int currentHour = gameTime.getHour();
        String fishingLocation = player.getPlayerLocation().getName();

        if (fishingLocation.equalsIgnoreCase("Pond") || fishingLocation.equalsIgnoreCase("Farm")) {
            fishingLocation = "Pond";
        }

        // Force uppercase for matching
        String seasonStr = currentSeason.toString().toUpperCase();
        String weatherStr = currentWeather.toString().toUpperCase();

        List<Fish> fishList = fishDatabase.getOrDefault(fishType, Collections.emptyList());
        List<Fish> eligibleFish = new ArrayList<>();

        for (Fish fish : fishList) {
            boolean locationMatch = fish.getFishLocations().contains(fishingLocation) || fish.getFishLocations().contains("ANY");
            boolean seasonMatch = fish.getAvailableSeasons().contains(seasonStr) || fish.getAvailableSeasons().contains("ANY");
            boolean weatherMatch = fish.getWeathers().contains(weatherStr) || fish.getWeathers().contains("ANY");
            boolean timeMatch = isTimeInRange(fish.getTimeStart(), fish.getTimeEnd(), currentHour);

            if (locationMatch && seasonMatch && weatherMatch && timeMatch) {
                eligibleFish.add(fish);
            }
        }

        if (eligibleFish.isEmpty()) {
            return null;
        }

        int randomIndex = nextInt(eligibleFish.size());
        return eligibleFish.get(randomIndex).getItemName();
    }

    private boolean isTimeInRange(int start, int end, int current) {
        if (start <= end) {
            return current >= start && current < end;
        } else {
            return current >= start || current < end;
        }
    }

    private int nextInt(int bound) {
        long seed = System.currentTimeMillis();
        seed = (seed * 6364136223846793005L + 1) & 0x7FFFFFFFFFFFFFFFL;
        return (int) (Math.abs(seed) % bound);
    }

    private Fish findFishByName(String fishType, String name) {
        List<Fish> fishList = fishDatabase.getOrDefault(fishType, Collections.emptyList());
        for (Fish fish : fishList) {
            if (fish.getItemName().equalsIgnoreCase(name)) {
                return fish;
            }
        }
        return null;
    }

}