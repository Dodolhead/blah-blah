package actions;

import entities.*;
import map.*;
import tsw.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WateringAction implements Action {
    private int ENERGY_COST = 5;
    private int TIME_COST = 5;

    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        FarmMap farmMap = farm.getFarmMap();
        Time gameTime = farm.getTime();
        if (!player.getPlayerInventory().hasItem("Watering Can")) {
            System.out.println("You need a Watering Can to water the land.");
            return false;
        }

        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action.");
            return false;
        }

        Point pos = player.getPlayerLocation().getCurrentPoint();
        int px = (pos.getX() + 24) / 48;
        int py = (pos.getY() + 24) / 48;
        char[][] map = farmMap.getFarmMapDisplay();

        // Cari tile 'l' (planted) terdekat di sekitar player (area 3x3)
        List<int[]> candidates = new ArrayList<>();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int x = px + dx;
                int y = py + dy;
                if (y < 0 || y >= map.length || x < 0 || x >= map[0].length)
                    continue;
                int distance = Math.abs(dx) + Math.abs(dy);
                candidates.add(new int[]{x, y, distance});
            }
        }
        // Urutkan berdasarkan jarak terdekat ke player
        candidates.sort(Comparator.comparingInt(a -> a[2]));

        boolean watered = false;
        for (int[] tile : candidates) {
            int x = tile[0], y = tile[1];
            // Hanya bisa siram tile 'l' (planted)
            if (map[y][x] != 'l') continue;

            // Tandai sebagai sudah disiram
            map[y][x] = 'w';
            // TAMBAHAN: Update lastWateredDay
            farmMap.getLastWateredDay().put(new Point(x, y), gameTime.getDay());

            System.out.println("You watered the land at (" + x + ", " + y + ").");

            player.setEnergy(player.getEnergy() - ENERGY_COST);
            gameTime.skipTimeMinute(TIME_COST);

            watered = true;
            break; // hanya siram satu tile terdekat
        }

        if (!watered) {
            System.out.println("No plantable land nearby to water!");
            return false;
        }

        return true;
    }
}