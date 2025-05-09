package src.actions;

import src.entities.*;
import src.map.*;

public class ShowLocationAction implements Action {  
    @Override
    public boolean execute(Player player) {
        Location playerLocation = player.getPlayerLocation();
        String locationName = playerLocation.getName();
        Point currentPoint = playerLocation.getCurrentPoint();
        
        System.out.println("===== CURRENT LOCATION =====");
        System.out.println("Location name: " + locationName);
        System.out.println("Position: " + currentPoint.printPoint());
        System.out.println("Additional Information: " + getLocationDescription(locationName));
        return true;
    }

    private String getLocationDescription(String locationName) {
        switch (locationName) {
            case "Mountain Lake":
                MountainLake mountainLake = new MountainLake();
                return mountainLake.getMountainLakeDesc();
            case "Forest River":
                ForestRiver forestRiver = new ForestRiver();
                return forestRiver.getForestRiverDesc();
            case "Ocean":
                Ocean ocean = new Ocean();
                return ocean.getOceanDesc();
            case "Farm":
                return "You are in your own farm. You can plant, harvest, and take care of your crops.";
            case "Store":
                return "Store sells various items. You can buy seeds, tools, and other supplies.";
            case "House":
                return "You are in your own house. You can rest and cook here.";
            case "NPCs Home":
                return "You are in the NPC's home. You can interact with them for quests and trades.";
            default:
                return "No information available for this location.";
        }
    }
}