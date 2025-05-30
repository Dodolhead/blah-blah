package entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import actions.*;
import gui.*;
import items.*;
import map.*;

public class Player {
    private String playerName;
    private String gender;
    private int energy;
    private int MAX_ENERGY = 100;
    private String farmName;
    private NPC partner = null;
    private Gold playerGold;
    private Inventory playerInventory;
    private Item favoriteItem;
    private Location playerLocation;
    private List<String> visitedPlace;
    GamePanel gp;
    KeyHandler keyH;
    public int speed;
    public int screenX;
    public int screenY;

    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public String direction;
    public Boolean moving;

    public Rectangle playerHitBox;
    public boolean collisionOn = false;
    public boolean collisionWithNPC = false;

    int spriteNum = 1;
    int spriteCount = 0;

    public boolean enteringHouse = false;


    public Player(String playerName, String gender, String farmName, GamePanel gp, KeyHandler keyH) {
        this.playerName = playerName;
        this.gender = gender;
        this.farmName = farmName;
        this.gp = gp;
        this.keyH = keyH;
        
        playerHitBox = new Rectangle(8,16,32,32);
        setDefaultValues();
        getPlayerImage();
        visitedPlace = new ArrayList<>();
        PlayerManager.addPlayer(this);
    }

    
    public void setDefaultValues() {
        this.playerLocation = new Location("Farm", new Point(gp.tileSize*15, gp.tileSize*15));
        this.playerGold = new Gold(0);
        this.playerInventory = new Inventory();
        this.favoriteItem = new Hoe(direction, playerGold, playerGold);
        energy = MAX_ENERGY;
        speed = 4;
        direction = "down";
    }

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

    public Item getFavoriteItem(){
        return this.favoriteItem;
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

    public void setFavoriteItem(Item item){
        this.favoriteItem = item;
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

    public void getPlayerImage(){
        try {
            if (gender.equals("male")){
                up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/atas1.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/atas2.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/bawah1.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/bawah2.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/kiri1.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/kiri2.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/kanan1.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/kanan2.png"));
            }
            else if (gender.equals("female")){
                up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/ceweatas1.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/ceweatas2.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/cewebawah1.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/cewebawah2.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/cewekiri1.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/cewekiri2.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/cewekanan1.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/cewekanan2.png"));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            }
            if (keyH.downPressed == true) {
                direction = "down";
            }
            if (keyH.leftPressed == true) {
                direction = "left";
            }
            if (keyH.rightPressed == true) {
                direction = "right";
            }
            if (keyH.till) {
                if (getPlayerLocation().getName().equals("Farm")) {
                    new TillingAction().execute(this);
                } else {
                    System.out.println("You can only till on the farm.");
                }

                keyH.till = false;
            }
            if (keyH.recoverLand) {
                new RecoveringLandAction().execute(this);
                keyH.recoverLand = false;
            }
            if (keyH.planting) {
                if (gp.inventoryPanel.getSelectedItem() instanceof Seed) {
                    new PlantingAction((Seed) gp.inventoryPanel.getSelectedItem()).execute(this);
                }
                else{
                    System.out.println("Select a seed to plant!");
                }
                keyH.planting = false;
            }
            if (keyH.harvestAction) {
                new HarvestingAction().execute(this);
                keyH.harvestAction = false;
            }
            if (keyH.fishAction){
                if (gp.cChecker.canFish){
                    new FishingAction().execute(this);
                }
                keyH.fishAction = false;
                gp.resetPlayerMovement();
            }
            if (keyH.eatAction){
                if (gp.inventoryPanel.getSelectedItem() instanceof Food) {
                    new EatingAction((Food) gp.inventoryPanel.getSelectedItem()).execute(this);
                }
                else{
                    System.out.println("Select a food to eat!");
                }
                keyH.eatAction = false;
            }
            if (keyH.sleepAction){
                if (gp.cChecker.canSleep){
                    new SleepingAction().execute(this);
                    gp.shippingBin.sellShippingBin(gp.farm.getTime());
                }
                else{
                    System.out.println("You can't sleep here!");
                }
                keyH.sleepAction = false;
            }
            if (keyH.watchAction){
                if (gp.cChecker.canWatch){
                    new WatchingAction().execute(this);
                }
                else{
                    System.out.println("You can't watch here!");
                }
                keyH.watchAction = false;
            }
            if (keyH.waterAction){
                new WateringAction().execute(this);
                keyH.waterAction = false;
            }
            if (keyH.placeFurniture){
                if (gp.inventoryPanel.getSelectedItem() instanceof Furniture && getPlayerLocation().getName().equals("House")) {
                    int playerX = this.getPlayerLocation().getCurrentPoint().getX()/48;
                    int playerY = this.getPlayerLocation().getCurrentPoint().getY()/48;
                    int placeX = playerX + 1;
                    int placeY = playerY;
                    gp.houseMap.placeFurniture((Furniture)gp.inventoryPanel.getSelectedItem(), placeX, placeY);
                }
                keyH.placeFurniture = false;
            }

            if (keyH.addFuel){
                if (gp.inventoryPanel.getSelectedItem() == null){
                    System.out.println("Please select a fuel.");
                }
                if (gp.cChecker.canCook && gp.inventoryPanel.getSelectedItem() != null){
                    gp.houseMap.stove.addFuel(gp.inventoryPanel.getSelectedItem().getItemName(),this);
                    System.out.println("Added Fuel to stove.");
                }
                else{
                    System.out.println("You can't add fuel here");
                }
                keyH.addFuel = false;
            }
            if (keyH.cookingAction){
                if (gp.cChecker.canCook){
                    gp.houseMap.stove.useStove(this, gp.playerInfoPanel.getSelectedRecipe());
                }
                else{
                    System.out.println("You can't cook here");
                }
                keyH.cookingAction = false;
            }
            
            if (gp.cChecker.canSell) {
                if (keyH.shippingBinToggle) {
                    // Toggle panel shipping bin
                    gp.shippingBinPanel.setVisible(!gp.shippingBinPanel.isVisible());
                    // (Opsional) update slot/isi setiap kali panel dibuka
                    if (gp.shippingBinPanel.isVisible()) {
                        gp.shippingBinPanel.updateSlots();
                    }
                } else {
                    System.out.println("You need to be near a shipping bin.");
                }
                keyH.shippingBinToggle = false;
            }

            collisionOn = false;
            collisionWithNPC = false;
            gp.cChecker.checkTilePlayer(this);
            gp.cChecker.checkNPC(this);

            if (!collisionOn && !collisionWithNPC) {
                switch(direction) {
                    case "up":
                        playerLocation.getCurrentPoint().setY(playerLocation.getCurrentPoint().getY()-speed);
                        break;
                    case "down":
                        playerLocation.getCurrentPoint().setY(playerLocation.getCurrentPoint().getY()+speed);
                        break;
                    case "left":
                        playerLocation.getCurrentPoint().setX(playerLocation.getCurrentPoint().getX()-speed);
                        break;
                    case "right":
                        playerLocation.getCurrentPoint().setX(playerLocation.getCurrentPoint().getX()+speed);
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
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":    image = (spriteNum == 1) ? up1 : up2; break;
            case "down":  image = (spriteNum == 1) ? down1 : down2; break;
            case "left":  image = (spriteNum == 1) ? left1 : left2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
        }

        int width = gp.tileSize / 2;
        int height = gp.tileSize;

        // Gambar player di koordinat screenX, screenY
        if (image != null) {
            g2.drawImage(image, screenX, screenY, width, height, null);
        }
    }

    
    public void setScreenPosition(int screenWidth, int screenHeight) {
        this.screenX = screenWidth / 2 - (gp.tileSize / 2);
        this.screenY = screenHeight / 2 - (gp.tileSize / 2);
    }

    public NPC getNearbyNPC() {
        for (NPC npc : NPCManager.getNPCList()) {
            if (!npc.getNPCLocation().getName().equals(this.getPlayerLocation().getName())) continue;

            int npcX = npc.getNPCLocation().getCurrentPoint().getX();
            int npcY = npc.getNPCLocation().getCurrentPoint().getY();
            int playerX = this.getPlayerLocation().getCurrentPoint().getX();
            int playerY = this.getPlayerLocation().getCurrentPoint().getY();

            int tile = gp.tileSize;

            if (Math.abs(npcX - playerX) <= tile && Math.abs(npcY - playerY) <= tile) {
                return npc;
            }
        }
        return null;
    }

    public List<Recipe> getKnownRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Inventory inventory = getPlayerInventory(); // pastikan method ini mengembalikan inventory player

        // Ambil map Misc dari inventory
        Map<Item, Integer> miscMap = inventory.getInventoryStorage().get(Misc.class);
        if (miscMap != null) {
            for (Item item : miscMap.keySet()) {
                if (item instanceof Recipe) {
                    recipes.add((Recipe) item);
                }
            }
        }
        // opsional: urutkan berdasarkan nama, id, atau urutan tertentu
        recipes.sort(Comparator.comparing(Recipe::getItemName));
        return recipes;
    }
}
