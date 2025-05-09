package src.actions;

import src.entities.*;
import src.map.*;
import src.tsw.Time;

public class VisitingAction implements Action {
    private WorldMap worldMap;
    private String destination;
    private static final int ENERGY_COST = 10;
    private static final int TIME_COST_MINUTES = 15;

    public VisitingAction(WorldMap worldMap, String destination) {
        this.worldMap = worldMap;
        this.destination = destination;
    }

    @Override
    public boolean execute(Player player) {
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        Time gameTime = farm.getTime();
        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action");
            return false;
        }

        FarmMap farmMap = new FarmMap(player.getPlayerLocation().getCurrentPoint());
        if (!farmMap.isAtEdge(player.getPlayerLocation().getCurrentPoint())) {
            System.out.println("You need to be at the edge of the farm to visit another place.");
            return false;
        }

        player.subtractPlayerEnergy(ENERGY_COST);
        gameTime.skipTimeMinute(TIME_COST_MINUTES);

        Location newLocation = new Location(destination, new Point(0,0)); 
        worldMap.visit(newLocation);
        
        System.out.println("You visited " + destination);
        return true;
    }

}