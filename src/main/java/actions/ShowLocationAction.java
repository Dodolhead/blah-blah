package main.java.actions;

import main.java.entities.*;
import main.java.map.*;

public class ShowLocationAction implements Action {  
    @Override
    public boolean execute(Player player) {
        Location playerLocation = player.getPlayerLocation();
        String locationName = playerLocation.getName();
        Point currentPoint = playerLocation.getCurrentPoint();
        
        System.out.println("===== CURRENT LOCATION =====");
        System.out.println("Location name: " + locationName);
        System.out.println("Position: " + currentPoint.printPoint());
        return true;
    }
}