package src.entities;

import java.util.List;

import javax.imageio.ImageIO;
import src.engine.*;
import java.io.IOException;
import java.util.ArrayList;
import src.map.*;
import src.engine.GamePanel;
import src.items.*;
import java.awt.Graphics2D;
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

    public BufferedImage up,down,left,right;
    public String direction;

    public Player(String playerName, String gender, String farmName, GamePanel gp, KeyHandler keyH) {
        this.playerName = playerName;
        this.gender = gender;
        this.farmName = farmName;
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
        visitedPlace = new ArrayList<>();
        PlayerManager.addPlayer(this);
    }

    
    public void setDefaultValues() {
        this.playerLocation = new Location("Farm", new Point(100, 100));
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
            up = ImageIO.read(getClass().getResourceAsStream("/res/player/024.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/res/player/bincat.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/res/player/bruh.jpeg"));
            right = ImageIO.read(getClass().getResourceAsStream("/res/player/Mantap.png"));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed == true) {
            direction = "up";
            playerLocation.getCurrentPoint().setY(playerLocation.getCurrentPoint().getY()-speed);
        }
        if (keyH.downPressed == true) {
            direction = "down";
            playerLocation.getCurrentPoint().setY(playerLocation.getCurrentPoint().getY()+speed);
        }
        if (keyH.leftPressed == true) {
            direction = "left";
            playerLocation.getCurrentPoint().setX(playerLocation.getCurrentPoint().getX()-speed);
        }
        if (keyH.rightPressed == true) {
            direction = "right";
            playerLocation.getCurrentPoint().setX(playerLocation.getCurrentPoint().getX()+speed);
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = up;
                break;
            case "down":
                image = down;
                break;
            case "left":
                image = left;
                break;
            case "right":
                image = right;
                break;
        }
        g2.drawImage(image, this.playerLocation.getCurrentPoint().getX(), this.playerLocation.getCurrentPoint().getY(), gp.tileSize, gp.tileSize, null);
    }
    

    
}
