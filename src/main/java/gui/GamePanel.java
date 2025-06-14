package gui;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entities.*;
import items.ItemManager;
import items.Seed;
import items.ShippingBin;
import map.*;
import tile.*;
import actions.*;


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
    private String currentLocationName = "Farm"; 

    // MainPanel
    MainPanel mainPanel;

    // NPC
    NPCManager npcManager = new NPCManager();
    public ChatPanel chatPanel; 

    // Inventory
    public InventoryPanel inventoryPanel = new InventoryPanel();

    // Store
    public StorePanel storePanel;

    // TSW
    public TimeSeasonWeatherPanel timePanel;

    // View Player Info
    public PlayerInfoPanel playerInfoPanel;
    // private boolean isPlayerInfoVisible = false;

    // ni buat ngecek doi toggle general atau khusus interact npc
    public enum GameState {
        NORMAL,
        NPC_DIALOGUE
    }
    public GameState gameState = GameState.NORMAL;
    private JLabel selectedItemLabel;
    public ShippingBinPanel shippingBinPanel;
    public ShippingBin shippingBin = new ShippingBin();

    //EndGame
    ShortcutMenuPanel shortcutMenuPanel;
    boolean hasFishStew = false;


    public GamePanel(String playerName, String gender, String farmName, MainPanel mainPanel){ 
        this.mainPanel = mainPanel;
        ItemManager.setItems(); 
        setupNpc();
        cChecker = new TileChecker(this);
        keyH = new KeyHandler(this);
        player = new Player(playerName, gender, farmName, this, keyH);
        player.getPlayerInventory().addItem(ItemManager.getItem("Hoe"), 1);
        player.getPlayerInventory().addItem(ItemManager.getItem("Parsnip Seeds"), 15);
        player.getPlayerInventory().addItem(ItemManager.getItem("Pickaxe"), 1);
        player.getPlayerInventory().addItem(ItemManager.getItem("Fishing Rod"), 1);
        player.getPlayerInventory().addItem(ItemManager.getItem("Baguette Recipe"), 1);
        player.getPlayerInventory().addItem(ItemManager.getItem("Wine Recipe"), 1);
        player.getPlayerInventory().addItem(ItemManager.getItem("Watering Can"), 1);
        player.getPlayerInventory().addItem(ItemManager.getItem("Pumpkin Pie Recipe"), 1);
        player.getPlayerInventory().addItem(ItemManager.getItem("Spakbor Salad Recipe"), 1);
        player.getPlayerInventory().addItem(ItemManager.getItem("Fish and Chips Recipe"), 1);
        player.getPlayerInventory().addItem(ItemManager.getItem("Wheat"), 10);
        player.getPlayerInventory().addItem(ItemManager.getItem("Potato"), 10);
        player.getPlayerInventory().addItem(ItemManager.getItem("Carp"), 20);
        player.getPlayerInventory().addItem(ItemManager.getItem("Coal"), 20);
        player.getPlayerInventory().addItem(ItemManager.getItem("Firewood"), 2);
        player.getPlayerInventory().addItem(ItemManager.getItem("Catfish"), 10);
        player.getPlayerInventory().addItem(ItemManager.getItem("Wedding Ring"), 1);
        farm = new Farm(farmName, player, this);
        houseMap = new HouseMap(player.getPlayerLocation());
        forestRiver = new ForestRiver();
        ocean = new Ocean();
        mountainLake = new MountainLake();
        store = new Store(this);
        store.fillStore();
        storePanel = new StorePanel(store, player, this);
        NPCHomeManager.addNPCHome(new DascoHome());
        NPCHomeManager.addNPCHome(new AbigailHome());
        NPCHomeManager.addNPCHome(new PerryHome());
        NPCHomeManager.addNPCHome(new CarolineHome());
        NPCHomeManager.addNPCHome(new MayorTadiHome());
        Point spawn = farm.getFarmMap().getValidRandomSpawnPoint();
        player.getPlayerLocation().setPoint(new Point(spawn.getX() * tileSize, spawn.getY() * tileSize));

        currentMap = farm.getFarmMap().getFarmMapDisplay();
        maxWorldCol = currentMap[0].length;
        maxWorldRow = currentMap.length;
        shippingBinPanel = new ShippingBinPanel(shippingBin, player, inventoryPanel);
        worldWidth = tileSize * maxWorldCol;
        worldHeight = tileSize * maxWorldRow;
        player.setScreenPosition(screenWidth, screenHeight);
        player.getPlayerGold().addGold(18500); // tes
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
        playerInfoPanel = new PlayerInfoPanel(player);
        playerInfoPanel.setBounds(460, 60, 300, 200); // Atur posisi & ukuran sesuai keinginan
        playerInfoPanel.setVisible(false); // Supaya gak langsung kelihatan
        this.add(playerInfoPanel);
        chatPanel = new ChatPanel(this, player);
        selectedItemLabel = new JLabel("Selected item: None");
        selectedItemLabel.setFont(selectedItemLabel.getFont().deriveFont(Font.ITALIC, 14f));
        selectedItemLabel.setForeground(Color.DARK_GRAY);

        // Hubungkan ke InventoryPanel
        inventoryPanel.setSelectedItemLabel(selectedItemLabel);
        
        // Tambahkan ke layout GamePanel
        selectedItemLabel.setBounds(460, 450, 300, 40); // Atur posisi sesuai keinginan
        selectedItemLabel.setVisible(false);

        this.add(selectedItemLabel);
        shippingBinPanel = new ShippingBinPanel(shippingBin, player, inventoryPanel);
        shippingBinPanel.setBounds(10, 60, 180, 200); // kiri atas
        shippingBinPanel.setVisible(false);
        this.add(shippingBinPanel);

        //EndGame Panel
        this.setLayout(null);
        SwingUtilities.invokeLater(() -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            shortcutMenuPanel = new ShortcutMenuPanel(parentFrame, player, farm.getTime(), this);
            shortcutMenuPanel.setBounds(250, 100, 300, 300);
            shortcutMenuPanel.setVisible(false);
            this.add(shortcutMenuPanel);
        });

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
                // System.out.println(farm.getTime().getTimeDay());
                // farm.getTime().skipDays(1);
                lastTimer = timer;
            }
        }
    }
    
    public void update() {
        player.update();
        for (NPC npc : NPCManager.npcList) {
            if (npc != null) {  
                npc.update();
            }
        }
        if (player.getPlayerInventory().hasItem("Hot Pepper") && !hasFishStew) {
            player.getPlayerInventory().addItem(ItemManager.getItem("Fish Stew Recipe"),1);
            hasFishStew = true;
        }
            

        removeOutOfSeasonCrops(farm.getFarmMap(), farm.getTime().getCurrentSeason().name());
        removeWitheredCrops(farm.getFarmMap(), farm.getTime().getDay());
        timePanel.updateDisplay();
        inventoryPanel.updateInventoryUI(player.getPlayerInventory());

        if (keyH.inventoryToggle) {
            inventoryPanel.setVisible(!inventoryPanel.isVisible());
            selectedItemLabel.setVisible(inventoryPanel.isVisible());
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

        if (keyH.playerInfoToggle) {
            if (!playerInfoPanel.isVisible()) {
                playerInfoPanel.updateInfo();
            }
            playerInfoPanel.setVisible(!playerInfoPanel.isVisible());
            keyH.playerInfoToggle = false;
        }

        if (keyH.interactNPC) {
            NPC npc = player.getNearbyNPC();
            if (npc != null) {
                chatPanel.showDialogue(npc, "Hey, what do you want to do? (C: Chat, G: Gift, Q: Propose, M: Marry)", GameState.NPC_DIALOGUE);
                gameState = GameState.NPC_DIALOGUE;
            }
            keyH.interactNPC = false;
        }
        if (keyH.menuToggle) {
            if (gameState != GameState.NPC_DIALOGUE) {
                shortcutMenuPanel.setVisible(!shortcutMenuPanel.isVisible());
            }
            keyH.menuToggle = false;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon image;
        if (farm.getTime().isNight()) {
            image = new ImageIcon(getClass().getResource("/res/gamebackground/night.jpg"));
        }
        else {
            image = new ImageIcon(getClass().getResource("/res/gamebackground/day.jpg"));
        }
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(image.getImage(), 0, 0, screenWidth, screenHeight, null);
        tileM.draw(g2);
        player.draw(g2);
        for (NPC npc : NPCManager.npcList) {
            if (npc != null) {
                npc.draw(g2);
            }
        }
        chatPanel.draw(g2);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Press Esc for MENU", 10, 50);

        // Draw current location name
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Location: " + currentLocationName, 10, 25);

        super.paintChildren(g);
        g2.dispose();
    }

    public void changeMap(String mapName, int playerX, int playerY) {
        if (mapName.equals("Farm")) {
            currentMap = farm.getFarmMap().getFarmMapDisplay();
            currentLocationName = "Farm";
        }
        else if (mapName.equals("House")) {
            currentMap = houseMap.getHouseMapDisplay();
            currentLocationName = "House";
        }
        else if (mapName.equals("ForestRiver")) {
            currentMap = forestRiver.getForestRiverDisplay();
            currentLocationName = "Forest River";
        }
        else if (mapName.equals("Ocean")) {
            currentMap = ocean.getOceanDisplay();
            currentLocationName = "Ocean";
        }
        else if (mapName.equals("Store")) {
            currentMap = store.getStoreDisplay();
            currentLocationName = "Store";
        }
        else if (mapName.equals("MountainLake")) {
            currentMap = mountainLake.getMountainLakeDisplay();
            currentLocationName = "Mountain Lake";
        }
        else if (NPCHomeManager.getNpcHomeStorage().containsKey(mapName)) {
            NPCHome home = NPCHomeManager.getNPCHomeByName(mapName);
            currentMap = home.getNpcHomeDisplay();
            currentLocationName = home.getNpcHomeName();
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
        if (player.getEnergy() < 10) {
            System.out.println("You don't have enough energy to do this action, go back to your farm to rest");
        }
        else{
            changeMap("ForestRiver", 12*tileSize, 12*tileSize);
            VisitingAction visitingAction = new VisitingAction("Forest River");
            visitingAction.execute(player);
            mainPanel.showGame();
        }
    }

    public void goToOcean() {
        if (player.getEnergy() < 10) {
            System.out.println("You don't have enough energy to do this action, go back to your farm to rest");
        }
        else{
            changeMap("Ocean", 12*tileSize, 12*tileSize);
            VisitingAction visitingAction = new VisitingAction("Ocean");
            visitingAction.execute(player);
            mainPanel.showGame();
        }
    }
    public void goToMountainLake() {
        if (player.getEnergy() < 10) {
            System.out.println("You don't have enough energy to do this action, go back to your farm to rest");
        }
        else{
            changeMap("MountainLake", 12*tileSize, 12*tileSize);
            VisitingAction visitingAction = new VisitingAction("Mountain Lake");
            visitingAction.execute(player);
            mainPanel.showGame();
        }
    }
    public void goToStore() {
        if (player.getEnergy() < 10) {
            System.out.println("You don't have enough energy to do this action, go back to your farm to rest");
        }
        else{
            changeMap("Store", 5*tileSize, 5*tileSize);
            VisitingAction visitingAction = new VisitingAction("Store");
            visitingAction.execute(player);
            mainPanel.showGame();
        }
    }

    public void goToNPCHome(NPCHome home) {
        if (player.getEnergy() < 10) {
            System.out.println("You don't have enough energy to visit " + home.getNpc().getNpcName());
            return;
        }

        int spawnX = (maxWorldCol / 2) * tileSize; 
        int spawnY = (maxWorldRow / 2) * tileSize;

        changeMap(home.getNpcHomeName(), spawnX, spawnY);
        resetPlayerMovement();

        VisitingAction visitingAction = new VisitingAction(home.getNpcHomeName());
        visitingAction.execute(player);
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
        player.moving = false; 
    }

    public void setupNpc() {
        new Emily(this);
        new Caroline(this);
        new Dasco(this);
        new Perry(this);
        new MayorTadi(this);
        new Abigail(this);
    }

    public static void removeOutOfSeasonCrops(FarmMap farmMap, String currentSeason) {
        Map<Point, Seed> plantedSeeds = farmMap.getPlantedSeeds();
        Map<Point, Integer> plantedDay = farmMap.getPlantedDay();
        char[][] mapDisplay = farmMap.getFarmMapDisplay();
        List<Point> toRemove = new ArrayList<>();

        for (Map.Entry<Point, Seed> entry : plantedSeeds.entrySet()) {
            Point point = entry.getKey();
            Seed seed = entry.getValue();
            if (!seed.getValidSeason().equalsIgnoreCase(currentSeason)) {
                mapDisplay[point.getY()][point.getX()] = 't';
                toRemove.add(point);
                farmMap.getObjectPosition().get("Planted").remove(point);
                farmMap.getObjectPosition().get("Tilled").add(point);
                System.out.println("Plant in " + point.getX()+","+point.getY()+" is dead because of the change of seasons.");
            }
        }
        for (Point p : toRemove) {
            plantedSeeds.remove(p);
            plantedDay.remove(p);
        }
    }

    public void resetWateredTiles(FarmMap farmMap) {
        char[][] map = farmMap.getFarmMapDisplay();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 'w') {
                    map[y][x] = 'l';
                }
            }
        }
    }

    public void waterAllTiles(FarmMap farmMap) {
        char[][] map = farmMap.getFarmMapDisplay();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                // Jika tile planted ('l'), baru bisa disiram
                if (map[y][x] == 'l') {
                    map[y][x] = 'w';
                    farmMap.getLastWateredDay().put(new Point(x, y), farm.getTime().getDay());
                }
            }
        }
    }
    public static void removeWitheredCrops(FarmMap farmMap, int currentDay) {
        Map<Point, Seed> plantedSeeds = farmMap.getPlantedSeeds();
        Map<Point, Integer> plantedDay = farmMap.getPlantedDay();
        Map<Point, Integer> lastWateredDay = farmMap.getLastWateredDay();
        char[][] mapDisplay = farmMap.getFarmMapDisplay();
        List<Point> toRemove = new ArrayList<>();

        for (Map.Entry<Point, Seed> entry : plantedSeeds.entrySet()) {
            Point point = entry.getKey();
            int lastWatered = lastWateredDay.getOrDefault(point, plantedDay.getOrDefault(point, currentDay));
            if (currentDay - lastWatered >= 2) {
                mapDisplay[point.getY()][point.getX()] = 't';
                toRemove.add(point);
                farmMap.getObjectPosition().get("Planted").remove(point);
                farmMap.getObjectPosition().get("Tilled").add(point);
                System.out.println("Plant in " + point.getX() + "," + point.getY() + " withered due to lack of watering.");
            }
        }
        for (Point p : toRemove) {
            plantedSeeds.remove(p);
            plantedDay.remove(p);
            lastWateredDay.remove(p);
        }
    }

}