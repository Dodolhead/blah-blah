package actions;

import entities.*;
import items.*;
import map.*;
import tsw.Time;

public class TillingAction implements Action {
    private final int ENERGY_COST = 5;
    private final int TIME_COST = 5;

    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        FarmMap farmMap = farm.getFarmMap();
        Time gameTime = farm.getTime();
        if (!player.getPlayerInventory().hasItemType(Hoe.class)) {
            System.out.println("You need a Hoe to till the land.");
            return false;
        }

        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action.");
            return false;
        }

        Point pos = player.getPlayerLocation().getCurrentPoint();
        int x = (pos.getX() + 24) / 48;
        int y = (pos.getY() + 24) / 48;
        char[][] map = farmMap.getFarmMapDisplay();

        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            System.out.println("You can't till outside the farm area.");
            return false;
        }

        if (map[y][x] != '.' && map[y][x] != 'm') {
            System.out.println("This tile is not tillable.");
            return false;
        }

        map[y][x] = 't';
        Point currentTile = new Point(x, y);
        farmMap.getObjectPosition().get("Tillable").removeIf(p -> p.getX() == x && p.getY() == y);
        farmMap.getObjectPosition().get("Tilled").add(currentTile);

        player.subtractPlayerEnergy(ENERGY_COST);
        gameTime.skipTimeMinute(TIME_COST);

        System.out.println("Tilling successful! You have tilled the land at (" + x + ", " + y + ").");
        return true;
    }
}
