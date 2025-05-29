package actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import entities.*;
import items.*;
import tsw.*;

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

    public boolean isValidFishingLocation(String location) {
        return location.equals("Mountain Lake") || 
               location.equals("Forest River") || 
               location.equals("Ocean") || 
               location.equals("Pond");
    }

    private String determineFishType() {
        int chance = nextInt(100);
        
        if (chance < 60) {
            return "common";
        } else if (chance < 95 && chance >= 60) {
            return "regular";
        } else {
            return "legendary";
        }
    }
    @Override
    public boolean execute(Player player) {
        boolean caught = false;

        Farm farm = FarmManager.getFarmByName(player.getFarm());
        Time gameTime = farm.getTime();
        String fishingLocation = player.getPlayerLocation().getName();
        String fishType = determineFishType();
        String fishName = selectRandomFish(fishType, player);
        
        
        if (!player.getPlayerInventory().hasItem("Fishing Rod")) {
            System.out.println("You need a Fishing Rod to fish!");
            return false;
        }

        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action.");
            return false;
        }
        
        if (!isValidFishingLocation(fishingLocation)) {
            System.out.println("You can't Fish in here");
            return false;
        }
        
        if (fishName == null) {
            System.out.println("There is currently no fish that can be caught in this time and place.");
            return false;
        }

        System.out.println("Fishing in " + fishingLocation + "...");
        
        gameTime.pauseTime();
        
        System.out.println("Hmm? you felt something bite your rod.");
        
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
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= maxAttempts; i++) {
            System.out.print("Attempt " + i + "/" + maxAttempts + " - Enter your guess (0 to " + (maxNumber) + "): ");
            
            int guess;
            try {
                guess = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            if (guess < 0 || guess > maxNumber) {
                System.out.println("Please enter a number between 0 and " + maxNumber);
                continue;
            }

            if (guess == fishNumber) {
                caught = true;
                break;
            } else if (guess < fishNumber) {
                System.out.println("Too low!");
            } else {
                System.out.println("Too high!");
            }
        }

        Fish caughtFish = findFishByName(fishType, fishName);
        if (caught) {
            System.out.println("You caught a " + fishName + "!");
            player.getPlayerInventory().addItem(caughtFish, 1);
        } else {
            System.out.println("The fish got away...");
        }

        player.subtractPlayerEnergy(ENERGY_COST);
        gameTime.skipTimeMinute(TIME_COST);
        gameTime.resumeTime();
        scanner.close();
        return true;

    }

    private String selectRandomFish(String fishType, Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        Time gameTime = farm.getTime();
        Season.Seasons currentSeason = new Season().getCurrentSeason();
        Weather.WeatherCondition currentWeather = new Weather().getCurrentWeather();
        int currentHour = gameTime.getHour();
        String fishingLocation = player.getPlayerLocation().getName();

        String seasonStr = currentSeason.toString();
        String weatherStr = currentWeather.toString();

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