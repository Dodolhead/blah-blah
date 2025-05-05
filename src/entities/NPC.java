package src.entities;

import java.util.List;
import java.util.ArrayList;
import src.items.*;

public class NPC {
    private String npcName;
    private int heartPoints;
    private int INITIAL_HEART_POINTS = 0;
    private List<Item> lovedItem = new ArrayList<Item>();
    private List<Item> likedItem = new ArrayList<Item>();
    private List<Item> hatedItem = new ArrayList<Item>();
    private String relationshipStatus;
    private List<Item> npcItemStorage = new ArrayList<Item>();
    

    public NPC(String npcName, List<Item> lovedItem, List<Item> likedItem, List<Item> hatedItem, String relationshipStatus, List<Item> npcItemStorage) {
        this.npcName = npcName;
        this.heartPoints = INITIAL_HEART_POINTS;
        this.lovedItem = lovedItem;
        this.likedItem = likedItem;
        this.hatedItem = hatedItem;
        this.relationshipStatus = relationshipStatus;
        this.npcItemStorage = npcItemStorage;
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

    public void receiveGift(Item item) {
        npcItemStorage.add(item);
    }


}
