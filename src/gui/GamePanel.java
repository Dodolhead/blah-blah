package src.gui;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import src.entities.*;
import src.items.ItemManager;
import src.tile.*;
import src.map.*;


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
    public Ocean ocean;
    public ForestRiver forestRiver;
    public MountainLake mountainLake;
    public Store store;
    public Farm farm;
    public HouseMap houseMap;

    // Screen
    public int maxScreenCol = 18;
    public int maxScreenRow = 14;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

    // Collision
    public TileChecker cChecker;

    // Current Map
    char[][] currentMap;

    // MainPanel
    MainPanel mainPanel;

    // NPC
    NPCManager npcManager = new NPCManager();

    // Inventory
    public InventoryPanel inventoryPanel = new InventoryPanel();

    // Store
    public StorePanel storePanel;

    // TSW
    public TimeSeasonWeatherPanel timePanel;


    public GamePanel(String playerName, String gender, String farmName, MainPanel mainPanel){ 
        this.mainPanel = mainPanel;
        setupNpc();
        ItemManager.setItems(); 
        cChecker = new TileChecker(this);
        keyH = new KeyHandler();
        player = new Player(playerName, gender, farmName, this, keyH);
        player.getPlayerInventory().addItem(ItemManager.getItem("Hoe"), 1);
        player.getPlayerInventory().addItem(ItemManager.getItem("Potato Seeds"), 5);
        player.getPlayerInventory().addItem(ItemManager.getItem("Pickaxe"), 1);
        farm = new Farm(farmName, player, this);
        houseMap = new HouseMap(player.getPlayerLocation());
        forestRiver = new ForestRiver();
        ocean = new Ocean();
        mountainLake = new MountainLake();
        store = new Store(this);
        store.storeChange();
        storePanel = new StorePanel(store, player, this);
        Point spawn = farm.getFarmMap().getValidRandomSpawnPoint();
        player.getPlayerLocation().setPoint(new Point(spawn.getX() * tileSize, spawn.getY() * tileSize));

        currentMap = farm.getFarmMap().getFarmMapDisplay();
        maxWorldCol = currentMap[0].length;
        maxWorldRow = currentMap.length;

        worldWidth = tileSize * maxWorldCol;
        worldHeight = tileSize * maxWorldRow;
        player.setScreenPosition(screenWidth, screenHeight);
        player.getPlayerGold().addGold(500); // tes
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.tileM = new TileManager(this, player);
        setCurrentMap(currentMap);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocusInWindow();
        inventoryPanel.setBounds(460, 50, 300, 400);
        inventoryPanel.setVisible(false);
        storePanel.setBounds(50, 50, 700, 500);
        storePanel.setVisible(false);
        timePanel = new TimeSeasonWeatherPanel(farm.getTime());
        timePanel.setBounds(200, 0, 400, 30); 
        this.add(timePanel);
        this.setLayout(null);
        this.add(inventoryPanel);
        this.add(storePanel);
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
        long lastTimer = 0;
        while (gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
            }
            if (timer - lastTimer >= 1000000000){
                System.out.println(farm.getTime().getTimeDay());
                // farm.getTime().skipDays(1);
                lastTimer = timer;
            }
        }
    }


    public void update(){
        player.update();
        for (NPC npc : NPCManager.npcList) {
            if (npc != null) {
                npc.update();
            }
        }
        timePanel.updateDisplay();
        inventoryPanel.updateInventoryUI(player.getPlayerInventory());
        if (keyH.inventoryToggle) {
            inventoryPanel.setVisible(!inventoryPanel.isVisible());
            keyH.inventoryToggle = false;
        }
        if (keyH.storeToggle && player.getPlayerLocation().getName().equals("Store")) {
            if (!storePanel.isVisible()) {
                storePanel.refreshPanel();
            }
            storePanel.setVisible(!storePanel.isVisible());
            keyH.storeToggle = false;
        }
        if (!player.getPlayerLocation().getName().equals("Store") && storePanel.isVisible()) {
            storePanel.setVisible(false);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon image = new ImageIcon("res/gamebackground/bg.jpg");
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(image.getImage(), 0, 0, screenWidth, screenHeight, null);
        tileM.draw(g2);
        player.draw(g2);
        for (NPC npc : NPCManager.npcList) {
            if (npc != null) {
                npc.draw(g2);
            }
        }
        super.paintChildren(g);
        g2.dispose();
    }

    public void changeMap(String mapName, int playerX, int playerY) {
        if (mapName.equals("Farm")) {
            currentMap = farm.getFarmMap().getFarmMapDisplay();
        }
        else if (mapName.equals("House")) {
            currentMap = houseMap.getHouseMapDisplay();
        }
        else if (mapName.equals("ForestRiver")) {
            currentMap = forestRiver.getForestRiverDisplay();
        }
        else if (mapName.equals("Ocean")) {
            currentMap = ocean.getOceanDisplay();
        }
        else if (mapName.equals("Store")) {
            currentMap = store.getStoreDisplay();
        }
            else if (mapName.equals("MountainLake")) {
            currentMap = mountainLake.getMountainLakeDisplay();
        }
        else {
            System.out.println("Map " + mapName + " unknown!");
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
        changeMap("House", 13*tileSize + -24, 24*tileSize);
        mainPanel.showGame();
    }

    public void returnToFarm() {
        Point pintuKeluar = farm.getFarmMap().getObjectPosition().get("HouseDoor").get(0);
        changeMap("Farm", pintuKeluar.getX() * tileSize -24, (pintuKeluar.getY() + 1) * tileSize);
        mainPanel.showGame();
    }

    public void goToForestRiver() {
        changeMap("ForestRiver", 12*tileSize, 12*tileSize);
        mainPanel.showGame();
    }

    public void goToOcean() {
        changeMap("Ocean", 12*tileSize, 12*tileSize);
        mainPanel.showGame();
    }
    public void goToMountainLake() {
        changeMap("MountainLake", 12*tileSize, 12*tileSize);
        mainPanel.showGame();
    }
    public void goToStore() {
        changeMap("Store", 5*tileSize, 5*tileSize);
        mainPanel.showGame();
    }

    public void setCurrentMap(char[][] newMap) {
        this.tileM.mapTiles = newMap;
        this.maxWorldRow = newMap.length;
        this.maxWorldCol = newMap[0].length;
    }
    public void showWorldMapPanel() {
        mainPanel.showWorldMapPanel();
    }

    public void resetPlayerMovement() {
        keyH.upPressed = false;
        keyH.downPressed = false;
        keyH.leftPressed = false;
        keyH.rightPressed = false;
        player.direction = "down";
    }

    public void setupNpc() {
        new Emily(this);
        new Caroline(this);
    }


}