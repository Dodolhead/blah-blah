package src.entities;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import src.items.*;
import src.gui.*;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import src.map.*;
import java.awt.Graphics2D;

public abstract class NPC {
    private String npcName;
    private int heartPoints;
    private final int INITIAL_HEART_POINTS = 0;
    private List<Item> lovedItem;
    private List<Item> likedItem;
    private List<Item> hatedItem;
    private String relationshipStatus;
    private Map<Item, Integer> npcItemStorage;
    private int proposedDay = -1;

    public int speed;
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public String direction;
    int spriteNum = 1;
    int spriteCount = 0;
    public Rectangle npcHitBox = new Rectangle(0, 0, 48, 48);
    public GamePanel gp;
    KeyHandler keyH;
    public int screenX;
    public int screenY;
    public Location npcLocation;

    public boolean collisionOn = false;

    
    public NPC(String npcName, String relationshipStatus, GamePanel gp) {
        this.gp = gp;
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

    public void setNPCLocation(Location npcLocation) {
        this.npcLocation = npcLocation;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }  

    public abstract void getNPCImage();

}
