package main.java.actions;

import main.java.entities.*;

public class MovingAction implements Action {
    private char[][] mapDisplay;
    private int x;
    private int y;

    public MovingAction(int x, int y, char [][]mapDisplay) {
        this.x = x;
        this.y = y;
        this.mapDisplay = mapDisplay;
    }

    public char[][] getMapDisplay() {
        return mapDisplay;
    }

    @Override
    public boolean execute(Player player) {
        return player.getPlayerLocation().getCurrentPoint().moveTo(x, y, mapDisplay);
    }
    
}
