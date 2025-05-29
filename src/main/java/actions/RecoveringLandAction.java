package actions;

import entities.*;
import items.*;
import map.*;
import tsw.Time;
import java.util.*;

public class RecoveringLandAction implements Action {
    private int ENERGY_COST = 5;
    private int TIME_COST = 5;

    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        FarmMap farmMap = farm.getFarmMap();
        Time gameTime = farm.getTime();
        if (!player.getPlayerInventory().hasItemType(Pickaxe.class)) {
            System.out.println("You need a Pickaxe to recover the land.");
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

        // Cari tile 't' (tilled) terdekat di sekitar player (area 3x3)
        List<int[]> candidates = new ArrayList<>();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int x = px + dx;
                int y = py + dy;
                if (y < 0 || y >= map.length || x < 0 || x >= map[0].length)
                    continue;
                int distance = Math.abs(dx) + Math.abs(dy); // Manhattan distance
                candidates.add(new int[]{x, y, distance});
            }
        }
        // Urutkan berdasarkan jarak terdekat ke player
        candidates.sort(Comparator.comparingInt(a -> a[2]));

        boolean recovered = false;
        for (int[] tile : candidates) {
            int x = tile[0], y = tile[1];
            if (map[y][x] != 't') continue; // hanya proses tile yang 't'

            map[y][x] = '.';
            Point currentTile = new Point(x, y);
            farmMap.getObjectPosition().get("Tilled").removeIf(p -> p.getX() == x && p.getY() == y);
            farmMap.getObjectPosition().get("Tillable").add(currentTile);

            player.subtractPlayerEnergy(ENERGY_COST);
            gameTime.skipTimeMinute(TIME_COST);

            System.out.println("Recovering land successful! You have recovered the land at (" + x + ", " + y + ").");
            recovered = true;
            break; // recover 1 tile terdekat saja
        }
        if (!recovered) {
            System.out.println("No tilled land nearby to recover!");
            return false;
        }

        return true;
    }
}

