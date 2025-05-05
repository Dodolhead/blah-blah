package src.entities;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import src.items.*;


public class NPC {
    private String npcName;
    private int heartPoints;
    private int INITIAL_HEART_POINTS = 0;
    private List<Item> lovedItem;
    private List<Item> likedItem;
    private List<Item> hatedItem;
    private String relationshipStatus;
    private Map<Item, Integer> npcItemStorage;
    private int proposedDay = -1;
    

    public NPC(String npcName, String relationshipStatus) {
        this.npcName = npcName;
        this.heartPoints = INITIAL_HEART_POINTS;
        this.lovedItem = new ArrayList<Item>();
        this.likedItem = new ArrayList<Item>();
        this.hatedItem = new ArrayList<Item>();
        this.relationshipStatus = relationshipStatus;
        this.npcItemStorage = new HashMap<>();
        NPCManager.addNPC(this);
    }

    public String getNpcName() {
        return npcName;
    }

    public int getHeartPoints() {
        return heartPoints;
    }

    public List<Item> getLovedItem() {
        return lovedItem;
    }

    public List<Item> getLikedItem() {
        return likedItem;
    }

    public List<Item> getHatedItem() {
        return hatedItem;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public void setHeartPoints(int heartPoints) {
        this.heartPoints = heartPoints;
    }

    public void increaseHeartPoints(int heartPoints) {
        this.heartPoints += heartPoints;
    }

    public void decreaseHeartPoints(int heartPoints) {
        this.heartPoints -= heartPoints;
    }

    public void receiveGift(Item item, int amount) {
        npcItemStorage.put(item, amount);
    }

    public void addLovedItem(Item item) {
        lovedItem.add(item);
    }

    public void addLikedItem(Item item) {
        likedItem.add(item);
    }

    public void addHatedItem(Item item) {
        hatedItem.add(item);
    }

    public int getProposedDay() {
        return proposedDay;
    }
    
    public void setProposedDay(int day) {
        this.proposedDay = day;
    }

}
