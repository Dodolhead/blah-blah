package src.actions;

import src.entities.*;
import src.items.*;
import src.map.*;
import src.map.Point;
import src.tsw.Time;

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
        int x = pos.getX();
        int y = pos.getY();
        char[][] map = farmMap.getFarmMapDisplay();

        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            System.out.println("You can't recover land outside the farm area.");
            return false;
        }

        if (map[y][x] != 't') {
            System.out.println("This tile is already recovered.");
            return false;
        }

        map[y][x] = '.';
        Point currentTile = new Point(x, y);
        farmMap.getObjectPosition().get("Tilled").removeIf(p -> p.getX() == x && p.getY() == y);
        farmMap.getObjectPosition().get("Tillable").add(currentTile);

        player.subtractPlayerEnergy(ENERGY_COST);
        gameTime.skipTimeMinute(TIME_COST);

        System.out.println("Recovering land successful! You have recovered the land at (" + x + ", " + y + ").");
        return true;
    }
}

