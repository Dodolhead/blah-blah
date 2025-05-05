package src.entities;

import java.util.List;
import java.util.ArrayList;
import src.map.*;
import src.items.*;

public class Player {
    private String playerName;
    private String gender;
    private int energy;
    private int heartPoints;
    private int MAX_ENERGY = 100;
    private Farm farm;
    private NPC partner;
    private Gold playerGold;
    private Inventory playerInventory;
    private Location playerLocation;
    private List<String> visitedPlace;

    public Player(String playerName, String gender, Farm farm, NPC partner, Gold playerGold, Inventory playerInventory, Location playerLocation, PlayerManager playerManagerList) {
        this.playerName = playerName;
        this.gender = gender;
        this.energy = MAX_ENERGY;
        this.heartPoints = 0;
        this.farm = farm;
        this.partner = partner;
        this.playerGold = playerGold;
        this.playerInventory = playerInventory;
        this.playerLocation = playerLocation;
        visitedPlace = new ArrayList<>();
        playerManagerList.addPlayer(this);
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

    public int getHeartPoints() {
        return heartPoints;
    }

    public Farm getFarm() {
        return farm;
    }

    public NPC getPartner() {
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
        this.energy = energy;
    }
    
    public void setHeartPoints(int heartPoints) {
        this.heartPoints = heartPoints;
    }
    
    public void setFarm(Farm farmName) {
        this.farm = farmName;
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

    
}
