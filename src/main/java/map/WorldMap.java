package main.java.map;

import main.java.entities.*;

public class WorldMap {
    private Player player;

    public WorldMap(Player player) {
        this.player = player;
    }

    public Location getPlayerLocation() {
        return player.getPlayerLocation();
    }
    
    public void returnToFarm(){
        player.setPlayerLocation(new Location("Farm", new Point(16, 16)));
    }

    public void displayWorldMap(){
        System.out.println("World Map:");
        System.out.println("Where do you want to go?");
        System.out.println("1. Forest River");
        System.out.println("2. Mountain Lake");
        System.out.println("3. Store");
        System.out.println("4. NPCs Home");
        System.out.println("5. Exit");
    }

    public void visit(Location place){
        player.setPlayerLocation(place);
        player.addVisitedPlace(place.getName());
    }


}
