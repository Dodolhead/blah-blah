package src.tile;
import java.awt.*;
import javax.imageio.*;
import src.entities.*;
import src.gui.*;


public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public char[][] mapTiles;

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
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tileijo.png"));
            tile[0].collision = false;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/jamoer.jpeg"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/blah.jpg"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tilled.png"));
            tile[3].collision = false;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/images.png"));
            tile[4].collision = false;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/air.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/bruh.jpeg"));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/floor.png"));
            tile[7].collision = false;

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/download.jpg"));
            tile[8].collision = true;

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/upin.jpg"));
            tile[9].collision = false;

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tilekuning.png"));
            tile[10].collision = false;


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
            default -> 0;
        };
    }
}