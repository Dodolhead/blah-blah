package src.gui;
import javax.swing.*;
import java.awt.*;

import src.entities.*;
import src.tile.*;
import src.map.*;
import src.map.Point;


public class GamePanel extends JPanel implements Runnable{
    // World
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; 
    public int maxWorldCol; 
    public int maxWorldRow;
    public int worldWidth;
    public int worldHeight;

    // Gameplay
    KeyHandler keyH;
    Thread gameThread;
    public TileManager tileM;
    int FPS = 60;
    public Player player;
    
    // Map
    public Farm farm;
    public HouseMap houseMap;

    // Screen
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

    // Collision
    public CollisionChecker cChecker = new CollisionChecker(this);

    // Current Map
    char[][] currentMap;

    public GamePanel(String playerName, String gender, String farmName) {
        keyH = new KeyHandler();
        player = new Player(playerName, gender, farmName, this, keyH);
        farm = new Farm(farmName, player);
        houseMap = new HouseMap(player.getPlayerLocation());
        Point spawn = farm.getFarmMap().getValidRandomSpawnPoint();
        player.getPlayerLocation().setPoint(new Point(spawn.getX() * tileSize, spawn.getY() * tileSize));

        currentMap = farm.getFarmMap().getFarmMapDisplay();
        maxWorldCol = currentMap[0].length;
        maxWorldRow = currentMap.length;

        worldWidth = tileSize * maxWorldCol;
        worldHeight = tileSize * maxWorldRow;
        player.setScreenPosition(screenWidth, screenHeight);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.tileM = new TileManager(this, player);
        setCurrentMap(currentMap);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval = 1000000000/FPS; //0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        long lastTimer = 0;
        while (gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                drawCount++;
                delta--;
            }
            if (timer - lastTimer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                lastTimer = timer;
            }
        }
    }


    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        ImageIcon image = new ImageIcon("res/gamebackground/bg.jpg");
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(image.getImage(), 0, 0, screenWidth, screenHeight, null);
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();
    }

    public void changeMap(String mapName, int playerX, int playerY) {
        if (mapName.equals("Farm")) {
            currentMap = farm.getFarmMap().getFarmMapDisplay();
        }
        else if (mapName.equals("House")) {
            currentMap = houseMap.getHouseMapDisplay();
        }
        else {
            System.out.println("Map " + mapName + " tidak dikenali!");
            return;
        }

        maxWorldCol = currentMap[0].length;
        maxWorldRow = currentMap.length;
        worldWidth = tileSize * maxWorldCol;
        worldHeight = tileSize * maxWorldRow;

        tileM.mapTiles = currentMap;

        player.setLocation(mapName, playerX, playerY);
    }

    public void enterHouse() {
        changeMap("House", 13*tileSize, 24*tileSize);
    }

    public void returnToFarm() {
        Point pintuKeluar = farm.getFarmMap().getObjectPosition().get("HouseDoor").get(0);
        changeMap("Farm", pintuKeluar.getX() * tileSize, (pintuKeluar.getY() + 1) * tileSize);
    }

    public void setCurrentMap(char[][] newMap) {
        this.tileM.mapTiles = newMap;
        this.maxWorldRow = newMap.length;
        this.maxWorldCol = newMap[0].length;
    }
}