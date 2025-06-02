package entities;

import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import gui.*;
import items.*;
import map.*;
import java.awt.Rectangle;
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
    public Rectangle npcHitBox = new Rectangle(8,16,32,32);
    public GamePanel gp;
    KeyHandler keyH;
    public int screenX;
    public int screenY;
    public Location npcLocation;
    public int actionCount = 0;
    public boolean collisionOn = false;
    public boolean collisionWIthPlayer = false;
    
    public int chattingFrequency = 0;
    public int giftingFrequency = 0;
    public int visitingFrequency = 0;
    
    public NPC(String npcName, String relationshipStatus, GamePanel gp, List<Item> lovedItem, List<Item> likedItem, List<Item> hatedItem) {
        this.gp = gp;
        this.npcName = npcName;
        this.heartPoints = INITIAL_HEART_POINTS;
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

    public Location getNPCLocation(){
        return npcLocation;
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
        if (this.heartPoints < 0) this.heartPoints = 0;
        if (this.heartPoints > 150) this.heartPoints = 150;
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

    public void setLoved(List<Item> loved){
        this.lovedItem = loved;
    }

    public void setLiked(List<Item> liked){
        this.likedItem = liked;
    }

    public void setHated(List<Item> hated){
        this.hatedItem = hated;
    }

    public void draw(Graphics2D g2) {
        if (this != null && npcLocation.getName().equals(gp.player.getPlayerLocation().getName())) {
            BufferedImage image = null;

            // Pilih sprite image berdasarkan arah dan frame animasi
            switch (direction) {
                case "up":
                    image = (spriteNum == 1) ? up1 : up2;
                    break;
                case "down":
                    image = (spriteNum == 1) ? down1 : down2;
                    break;
                case "left":
                    image = (spriteNum == 1) ? left1 : left2;
                    break;
                case "right":
                    image = (spriteNum == 1) ? right1 : right2;
                    break;
            }

            // Posisi dunia NPC
            int npcWorldX = npcLocation.getCurrentPoint().getX();
            int npcWorldY = npcLocation.getCurrentPoint().getY();

            // Posisi dunia Player
            int playerWorldX = gp.player.getPlayerLocation().getCurrentPoint().getX();
            int playerWorldY = gp.player.getPlayerLocation().getCurrentPoint().getY();

            // Posisi layar Player (biasanya tetap di tengah layar)
            int playerScreenX = gp.player.screenX;
            int playerScreenY = gp.player.screenY;

            // Hitung posisi gambar NPC di layar
            int screenX = npcWorldX - playerWorldX + playerScreenX;
            int screenY = npcWorldY - playerWorldY + playerScreenY;

            if (image != null) {
                g2.drawImage(image, screenX, screenY, gp.tileSize/2, gp.tileSize, null);
            }
        }
    }

    public abstract void getNPCImage();

    public void setAction() {
        actionCount++;
        if (actionCount == 120) {
            int randomInt = 1 + (int)(Math.random() * 100);
            if (randomInt <= 25) {
                direction = "up";
            } else if (randomInt <= 50 && randomInt > 25) {
                direction = "down";
            } else if (randomInt <= 75 && randomInt > 50) {
                direction = "left";
            } else {
                direction = "right";
            }
            actionCount = 0;
        }
    }

    public void update(){
        // Jangan update jika player tidak di map yang sama
        if (!npcLocation.getName().equals(gp.player.getPlayerLocation().getName())) {
            return;
        }

        setAction();
        collisionOn = false;
        collisionWIthPlayer = false;
        gp.cChecker.checkTileNPC(this);
        gp.cChecker.checkPlayer(this);

        if (!collisionOn && !collisionWIthPlayer) {
            switch(direction) {
                case "up":
                    npcLocation.getCurrentPoint().setY(npcLocation.getCurrentPoint().getY()-speed);
                    break;
                case "down":
                    npcLocation.getCurrentPoint().setY(npcLocation.getCurrentPoint().getY()+speed);
                    break;
                case "left":
                    npcLocation.getCurrentPoint().setX(npcLocation.getCurrentPoint().getX()-speed);
                    break;
                case "right":
                    npcLocation.getCurrentPoint().setX(npcLocation.getCurrentPoint().getX()+speed);
                    break;
            }
        }
        spriteCount++;
        if (spriteCount > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCount = 0;
        }
    }

    public String getRandomDialogue() {
        List<String> list = Arrays.asList(
        "Nice to see you!",
            "How's the farm?",
            "What a lovely day."
        );
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

}
