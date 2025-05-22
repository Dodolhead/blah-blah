package src.tile;
import src.engine.*;
import javax.imageio.*;
import java.awt.*;


public class TileManager {
    GamePanel gp;
    Tile[] tile;
    char[][] mapTiles;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTiles = new char[][]{
            {'t','.','.','.','.','.','.','.','.','.','.','.','.','.','.','t'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','t','.','.','.','.','.','.','.','.'},
            {'.','.','p','p','.','.','.','.','t','.','.','.','.','.','.','.'},
            {'.','.','p','p','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','p','p','.','.'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','t','.','.','.','.','.','.','.','.'},
            {'.','.','p','p','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','t'}
        };
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/ipin.jpg"));
            tile[0].collision = false;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/jamoer.jpeg"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/blah.jpg"));
            tile[2].collision = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int row = 0; row < mapTiles.length; row++) {
            for (int col = 0; col < mapTiles[row].length; col++) {
                int x = col * gp.tileSize;
                int y = row * gp.tileSize;

                char tileChar = mapTiles[row][col];
                int tileIndex = getTileIndex(tileChar);
                g2.drawImage(tile[tileIndex].image, x, y, gp.tileSize, gp.tileSize, null);
            }
        }
    }

    public int getTileIndex(char c) {
        return switch (c) {
            case '0', '.' -> 0;
            case '1', 't' -> 1;
            case '2', 'p' -> 2;
            default -> 0;
        };
    }
}