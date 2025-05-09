package src.actions;

import src.entities.*;
import src.items.*;
import src.map.*;
import src.tsw.*;

public class PlantingAction implements Action {
    private FarmMap farmMap;
    private Time gameTime;
    private Seed seedToPlant;

    public PlantingAction(FarmMap farmMap, Time gameTime, Seed seedToPlant) {
        this.farmMap = farmMap;
        this.gameTime = gameTime;
        this.seedToPlant = seedToPlant;
    }

    @Override
    public boolean execute(Player player) {
        // Cek apakah pemain memiliki seed yang sesuai
        if (player.getPlayerInventory().getItemAmount(seedToPlant) <= 0) {
            System.out.println("Anda tidak memiliki " + seedToPlant.getItemName() + " untuk ditanam!");
            return false;
        }

        // Cek energi
        if (player.getEnergy() < 5) {
            System.out.println("Energi tidak cukup untuk melakukan planting!");
            return false;
        }

        // Ambil posisi pemain
        Point playerPos = player.getPlayerLocation().getCurrentPoint();
        int playerX = playerPos.getX();
        int playerY = playerPos.getY();

        char[][] farmDisplay = farmMap.getFarmMapDisplay();
        if (playerY >= 0 && playerY < farmDisplay.length && playerX >= 0 && playerX < farmDisplay[0].length) {
            if (farmDisplay[playerY][playerX] == 't') {
                // Ambil musim dari time
                Season.Seasons currentSeason = gameTime.getCurrentSeason();
                String seedSeason = seedToPlant.getSeason();

                if (!isSeasonValid(currentSeason, seedSeason)) {
                    System.out.println(seedToPlant.getItemName() + " tidak dapat ditanam pada musim " + currentSeason);
                    return false;
                }

                // Lakukan planting
                System.out.println("Memulai penanaman " + seedToPlant.getItemName() + "...");

                farmDisplay[playerY][playerX] = 'l';
                Point plantedPoint = new Point(playerX, playerY);
                farmMap.getObjectPosition().get("Tilled").removeIf(p -> p.getX() == playerX && p.getY() == playerY);
                farmMap.getObjectPosition().get("Planted").add(plantedPoint);

                player.getPlayerInventory().removeItem(seedToPlant, 1);
                player.subtractPlayerEnergy(5);
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
        switch (currentSeason) {
            case SPRING:
                return seedSeason.toUpperCase().contains("SPRING");
            case SUMMER:
                return seedSeason.toUpperCase().contains("SUMMER");
            case AUTUMN:
                return seedSeason.toUpperCase().contains("FALL") || seedSeason.toUpperCase().contains("AUTUMN");
            case WINTER:
                return seedSeason.toUpperCase().contains("WINTER");
            default:
                return false;
        }
    }
}
