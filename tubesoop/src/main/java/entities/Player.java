package entities;

import java.util.List;

import items.*;
import map.*;

import java.util.ArrayList;

public class Player {
    private String playerName;
    private String gender;
    private int energy;
    private int MAX_ENERGY = 100;
    private String farmName;
    private NPC partner = null;
    private Gold playerGold;
    private Inventory playerInventory;
    private Location playerLocation;
    private List<String> visitedPlace;

    public Player(String playerName, String gender, String farmName, Gold playerGold, Inventory playerInventory, Location playerLocation) {
        this.playerName = playerName;
        this.gender = gender;
        this.energy = MAX_ENERGY;
        this.farmName = farmName;
        this.playerGold = playerGold;
        this.playerInventory = playerInventory;
        this.playerLocation = playerLocation;
        visitedPlace = new ArrayList<>();
        PlayerManager.addPlayer(this);
    }

    /*============= GETTER =============== */
    public String getPlayerName() {
        return playerName;
    }

    
    public String getGender() {
        return gender;
    }

    public int getEnergy() {
        return energy;
    }

    public String getFarm() {
        return farmName;
    }

    public NPC getPartner() {
        if (partner == null) {return null;}
        return partner;
    }


    public Gold getPlayerGold() {
        return playerGold;
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    public Location getPlayerLocation() {
        return playerLocation;
    }

    /*========== OTHER METHOD =========== */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setEnergy(int energy) {
        if (energy > MAX_ENERGY) {
            this.energy = MAX_ENERGY;
        }
        else if (energy < -20) {
            this.energy = -20;
        }
        else {
            this.energy = energy;
        }
    }
    
    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }
    
    public void setPartner(NPC partner) {
        this.partner = partner;
    }
    
    public void setPlayerGold(Gold playerGold) {
        this.playerGold = playerGold;
    }
    
    public void setPlayerInventory(Inventory playerInventory) {
        this.playerInventory = playerInventory;
    }

    public void setPlayerLocation(Location playerLocation) {
        this.playerLocation = playerLocation;
    }

    /*========== OTHER METHOD =========== */
    public void addPlayerEnergy(int amount) {
        this.energy += amount;
    }

    public void subtractPlayerEnergy(int amount) {
        if (this.energy - amount < 0) {
            this.energy = 0;
        }
        this.energy -= amount;
    }

    public List<String> getVisitedPlace() {
        return visitedPlace;
    }

    public void addVisitedPlace(String place) {
        if (!visitedPlace.contains(place)) {
            visitedPlace.add(place);
        }
    }
    public void setLocation(String locationName, int x, int y) {
        this.playerLocation.setName(locationName);

        if (this.playerLocation.getCurrentPoint() == null) {
            this.playerLocation.setPoint(new Point(x, y));
        } else {
            this.playerLocation.getCurrentPoint().setX(x);
            this.playerLocation.getCurrentPoint().setY(y);
        }
    }

    
}
