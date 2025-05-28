package main.java.tile;
import java.awt.Graphics2D;
import java.io.IOException;
import main.java.map.Point;

import javax.imageio.*;
import java.awt.image.BufferedImage;
import main.java.entities.*;
import main.java.gui.*;


public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public char[][] mapTiles;
    BufferedImage houseImage;
    BufferedImage shipImage;
    BufferedImage pondImage;
    BufferedImage doorImage;
    BufferedImage bedImage;
    BufferedImage tvImage;
    BufferedImage stoveImage;

    public TileManager(GamePanel gp, Player player) {
        this.gp = gp;
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        tile = new Tile[100];
        if (player.getPlayerLocation().getName().equals("Farm")) {
            mapTiles = farm.getFarmMap().getFarmMapDisplay();
        }
        else if (player.getPlayerLocation().getName().equals("House")) {
            mapTiles = gp.houseMap.getHouseMapDisplay();
        }
        else if (player.getPlayerLocation().getName().equals("Ocean")) {
            mapTiles = gp.ocean.getOceanDisplay();
        }
        else if (player.getPlayerLocation().getName().equals("ForestRiver")) {
            mapTiles = gp.forestRiver.getForestRiverDisplay();
        }
        else if (player.getPlayerLocation().getName().equals("MountainLake")) {
            mapTiles = gp.mountainLake.getMountainLakeDisplay();
        }
        else if (player.getPlayerLocation().getName().equals("Store")) {
            mapTiles = gp.store.getStoreDisplay();
        }
        getTileImage();
        loadHouseImage();
        loadShippingImage();
        loadPondImage();
        loadBedImage();
        loadDoorImage();
        loadStoveImage();
        loadTvImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tileijo.png"));
            tile[0].collision = false;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tileijo.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tileijo.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tilled.png"));
            tile[3].collision = false;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/images.png"));
            tile[4].collision = false;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tileijo.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/floor.png"));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/floor.png"));
            tile[7].collision = false;

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));
            tile[8].collision = true;

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/upin.jpg"));
            tile[9].collision = false;

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tilekuning.png"));
            tile[10].collision = false;

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/floor.png"));
            tile[11].collision = true;

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/floor.png"));
            tile[12].collision = true;

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/floor.png"));
            tile[13].collision = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        int playerWorldX = gp.player.getPlayerLocation().getCurrentPoint().getX();
        int playerWorldY = gp.player.getPlayerLocation().getCurrentPoint().getY();
        
        int leftBound = playerWorldX - gp.player.screenX;
        int rightBound = playerWorldX + gp.player.screenX;
        int topBound = playerWorldY - gp.player.screenY;
        int bottomBound = playerWorldY + gp.player.screenY;
        
        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                int worldX = col * gp.tileSize;
                int worldY = row * gp.tileSize;
                
                if (worldX + gp.tileSize > leftBound && 
                    worldX - gp.tileSize < rightBound &&
                    worldY + gp.tileSize > topBound &&
                    worldY - gp.tileSize < bottomBound) {
                    
                    int screenX = worldX - playerWorldX + gp.player.screenX;
                    int screenY = worldY - playerWorldY + gp.player.screenY;

                    char tileChar = mapTiles[row][col];
                    int tileIndex = getTileIndex(tileChar);
                    if (tile[tileIndex] == null) {
                        System.out.println("tile[" + tileIndex + "] is null for char '" + tileChar + "'");
                        continue;
                    }
                    g2.drawImage(tile[tileIndex].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }
        if (gp.player.getPlayerLocation().getName().equals("Farm")) {
            drawHouseImage(g2);
            drawShipping(g2);
            drawPond(g2);
        }
        else if(gp.player.getPlayerLocation().getName().equals("House")) {
            drawBed(g2);
            drawDoor(g2);
            drawStove(g2);
            drawTV(g2);
        }
    }

    public int getTileIndex(char c) {
        return switch (c) {
            case '.' -> 0;
            case 'h' -> 1;
            case 's' -> 2;
            case 't' -> 3;
            case 'l' -> 4;
            case 'o' -> 5;
            case 'D' -> 6;
            case ',' -> 7;
            case 'W' -> 8;
            case '`' -> 9;
            case 'm' -> 10;
            case 'B' -> 11;
            case 'S' -> 12;
            case 'T' -> 13;
            default -> 0;
        };
    }
    private void loadHouseImage() {
        try {
            houseImage = ImageIO.read(getClass().getResourceAsStream("/res/tiles/House3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadShippingImage() {
        try {
            shipImage = ImageIO.read(getClass().getResourceAsStream("/res/tiles/shipping-bin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPondImage() {
        try {
            pondImage = ImageIO.read(getClass().getResourceAsStream("/res/tiles/pond.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBedImage() {
        try {
            bedImage = ImageIO.read(getClass().getResourceAsStream("/res/items/furniture/bed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadStoveImage() {
        try {
            stoveImage = ImageIO.read(getClass().getResourceAsStream("/res/items/furniture/stove.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDoorImage() {
        try {
            doorImage = ImageIO.read(getClass().getResourceAsStream("/res/items/furniture/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadTvImage() {
        try {
            tvImage = ImageIO.read(getClass().getResourceAsStream("/res/items/furniture/tv.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void drawHouseImage(Graphics2D g2) {
        Point houseStart = gp.farm.getFarmMap().houseStartPoint;
        int tileSize = gp.tileSize;
        if (houseStart != null && houseImage != null) {
            int drawX = houseStart.getX() * tileSize;
            int drawY = houseStart.getY() * tileSize;

            int screenX = drawX - gp.player.getPlayerLocation().getCurrentPoint().getX() + gp.player.screenX;
            int screenY = drawY - gp.player.getPlayerLocation().getCurrentPoint().getY() + gp.player.screenY;

            g2.drawImage(houseImage, screenX, screenY, tileSize * 6, tileSize * 6, null);
        }
    }
    private void drawShipping(Graphics2D g2) {
        Point shipStart = gp.farm.getFarmMap().shippingBinPoint;
        int tileSize = gp.tileSize;
        if (shipStart != null && houseImage != null) {
            int drawX = shipStart.getX() * tileSize;
            int drawY = shipStart.getY() * tileSize;

            int screenX = drawX - gp.player.getPlayerLocation().getCurrentPoint().getX() + gp.player.screenX;
            int screenY = drawY - gp.player.getPlayerLocation().getCurrentPoint().getY() + gp.player.screenY;

            g2.drawImage(shipImage, screenX, screenY, tileSize * 3, tileSize * 2, null);
        }
    }

    private void drawPond(Graphics2D g2) {
        Point pondStart = gp.farm.getFarmMap().pondStartPoint;
        int tileSize = gp.tileSize;
        if (pondStart != null && houseImage != null) {
            int drawX = pondStart.getX() * tileSize;
            int drawY = pondStart.getY() * tileSize;

            int screenX = drawX - gp.player.getPlayerLocation().getCurrentPoint().getX() + gp.player.screenX;
            int screenY = drawY - gp.player.getPlayerLocation().getCurrentPoint().getY() + gp.player.screenY;

            g2.drawImage(pondImage, screenX, screenY, tileSize * 4, tileSize * 3, null);
        }
    }
    private void drawBed(Graphics2D g2) {
        Point bedStart = gp.houseMap.getFurnitureStartPoint(Bed.class);
        int tileSize = gp.tileSize;
        if (bedStart != null && bedImage != null) {
            int drawX = bedStart.getX() * tileSize;
            int drawY = bedStart.getY() * tileSize;

            int screenX = drawX - gp.player.getPlayerLocation().getCurrentPoint().getX() + gp.player.screenX;
            int screenY = drawY - gp.player.getPlayerLocation().getCurrentPoint().getY() + gp.player.screenY;

            // Misal bed ukuran 2x4 tile
            g2.drawImage(bedImage, screenX, screenY, tileSize * 2, tileSize * 4, null);
        }
    }

    private void drawStove(Graphics2D g2) {
        Point stoveStart = gp.houseMap.getFurnitureStartPoint(Stove.class);
        int tileSize = gp.tileSize;
        if (stoveStart != null && stoveImage != null) {
            int drawX = stoveStart.getX() * tileSize;
            int drawY = stoveStart.getY() * tileSize;

            int screenX = drawX - gp.player.getPlayerLocation().getCurrentPoint().getX() + gp.player.screenX;
            int screenY = drawY - gp.player.getPlayerLocation().getCurrentPoint().getY() + gp.player.screenY;

            g2.drawImage(stoveImage, screenX, screenY, tileSize, tileSize, null);
        }
    }

    private void drawTV(Graphics2D g2) {
        Point tvStart = gp.houseMap.getFurnitureStartPoint(TV.class);
        int tileSize = gp.tileSize;
        if (tvStart != null && tvImage != null) {
            int drawX = tvStart.getX() * tileSize;
            int drawY = tvStart.getY() * tileSize;

            int screenX = drawX - gp.player.getPlayerLocation().getCurrentPoint().getX() + gp.player.screenX;
            int screenY = drawY - gp.player.getPlayerLocation().getCurrentPoint().getY() + gp.player.screenY;

            g2.drawImage(tvImage, screenX, screenY, tileSize, tileSize, null);
        }
    }

    private void drawDoor(Graphics2D g2) {
        // Ambil Door
        Point doorStart = gp.houseMap.getFurnitureStartPoint(Door.class);
        int tileSize = gp.tileSize;
        if (doorStart != null && doorImage != null) {
            int drawX = (doorStart.getX()-1) * tileSize;
            int drawY = doorStart.getY() * tileSize;

            int screenX = drawX - gp.player.getPlayerLocation().getCurrentPoint().getX() + gp.player.screenX;
            int screenY = drawY - gp.player.getPlayerLocation().getCurrentPoint().getY() + gp.player.screenY;

            g2.drawImage(doorImage, screenX, screenY, tileSize * 2, tileSize, null); // gambar pintu lebar 2 tile
        }
    }
}