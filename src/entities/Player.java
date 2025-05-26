package src.entities;

import java.util.List;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import src.map.*;
import src.gui.*;
import src.items.*;
import src.actions.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

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
    GamePanel gp;
    KeyHandler keyH;
    public int speed;
    public int screenX;
    public int screenY;

    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public String direction;

    public Rectangle playerHitBox;
    public boolean collisionOn = false;

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
                up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/024.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/atas2.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/bawah1.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/bawah2.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/kiri1.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/kiri2.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/kanan1.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/kanan2.png"));
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
            }
            if (keyH.planting) {
                Seed wheatSeed = new Seed("Wheat Seeds", new Gold(60), 1, "SPRING");
                this.getPlayerInventory().addItem(wheatSeed, 1);
                new PlantingAction(wheatSeed).execute(this);
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            if (collisionOn == false){
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
    
    public void setScreenPosition(int screenWidth, int screenHeight) {
        this.screenX = screenWidth / 2 - (gp.tileSize / 2);
        this.screenY = screenHeight / 2 - (gp.tileSize / 2);
    }

    
}
