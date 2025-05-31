package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import items.*;


public class FarmMap {
    private static int farmSizeWidth = 32;
    private static int farmSizeHeight = 32;
    private Location playerLocation;
    private Map<String, List<Point>> objectPosition;
    private char[][] farmMapDisplay;
    private Point playerPositionFarm;
    private Map<Point, Seed> plantedSeeds;
    private Map<Point, Integer> plantedDay;
    private Map<Point, Integer> lastWateredDay = new HashMap<>();
    public Point houseStartPoint;
    public Point shippingBinPoint;
    public Point pondStartPoint;
    public static final int MIN_DIST_EDGE = 2;
    public static final int MIN_DIST_POND_TO_HOUSE = 5;


    public FarmMap(Location playerLocation) {
        this.playerLocation = playerLocation;
        this.objectPosition = new HashMap<>();
        this.farmMapDisplay = new char[farmSizeHeight][farmSizeWidth];
        this.plantedSeeds = new HashMap<>();
        this.plantedDay = new HashMap<>();
        playerPositionFarm = playerLocation.getCurrentPoint();

        objectPosition.put("House", new ArrayList<>());
        objectPosition.put("HouseDoor", new ArrayList<>());
        objectPosition.put("Pond", new ArrayList<>());
        objectPosition.put("ShippingBin", new ArrayList<>());
        objectPosition.put("Tillable", new ArrayList<>());
        objectPosition.put("Tilled", new ArrayList<>());
        objectPosition.put("Planted", new ArrayList<>());

        placeObjectsRandomly();
        fillTillableLand();
    }
    
    public Map<Point, Integer> getLastWateredDay() {
        return lastWateredDay;
    }
    public Map<Point, Seed> getPlantedSeeds() {
        return plantedSeeds;
    }

    public Point getPlayerPosition(){
        return playerPositionFarm;
    }

    public char[][] getFarmMapDisplay(){
        return farmMapDisplay;
    }

    public Map<String, List<Point>> getObjectPosition(){
        return objectPosition;
    }

    public Map<Point, Integer> getPlantedDay() {
        return plantedDay;
    }

    public void setPlayerPosition(Point playerPositionFarm) {
        this.playerPositionFarm = playerPositionFarm;
    }


    private void placeObjectsRandomly() {
        long timeSeed = System.currentTimeMillis();

        // --- Hitung batas random spawn (min edge & max size)
        int pondWidth = 4, pondHeight = 3;
        int houseWidth = 6, houseHeight = 6;
        int shippingBinWidth = 3, shippingBinHeight = 2;

        int minEdge = MIN_DIST_EDGE;

        int maxPondX = farmSizeWidth - pondWidth - minEdge;
        int maxPondY = farmSizeHeight - pondHeight - minEdge;
        int maxHouseX = farmSizeWidth - houseWidth - minEdge;
        int maxHouseY = farmSizeHeight - houseHeight - minEdge;
        int maxShippingBinX = farmSizeWidth - shippingBinWidth - minEdge;
        int maxShippingBinY = farmSizeHeight - shippingBinHeight - minEdge;

        // Check farm size minimal
        if (maxPondX < minEdge || maxPondY < minEdge || maxHouseX < minEdge || maxHouseY < minEdge 
            || maxShippingBinX < minEdge || maxShippingBinY < minEdge) {
            System.out.println("Farm size terlalu kecil untuk objek dengan jarak minimal dari edge!");
            return;
        }

        // --- Place House dulu (agar pond bisa dicek jarak ke house)
        boolean housePlaced = false;
        int houseAttempts = 0;
        while (!housePlaced && houseAttempts < 1000) {
            int houseStartX = minEdge + (int)(timeSeed % (maxHouseX - minEdge + 1));
            int houseStartY = minEdge + (int)((timeSeed / 2000) % (maxHouseY - minEdge + 1));
            this.houseStartPoint = new Point(houseStartX, houseStartY);

            if (canPlaceObjectAt("House", houseStartX, houseStartY, houseWidth, houseHeight) &&
                !isNearPlayer(houseStartPoint, houseWidth, houseHeight, playerPositionFarm)) {
                placeObjectAt("House", houseStartPoint, houseWidth, houseHeight);
                int doorX = houseStartPoint.getX() + 3;
                int doorY = houseStartPoint.getY() + 5;
                farmMapDisplay[doorY][doorX] = 'D';
                farmMapDisplay[doorY][doorX-1] = 'D';
                objectPosition.get("HouseDoor").add(new Point(doorX, doorY));
                housePlaced = true;
            }
            timeSeed += 1234;
            houseAttempts++;
        }
        if (!housePlaced) {
            System.out.println("Gagal tempatkan House setelah 1000 percobaan");
            return;
        }

        // --- Place Pond, minimal radius ke House
        boolean pondPlaced = false;
        int pondAttempts = 0;
        while (!pondPlaced && pondAttempts < 1000) {
            int pondStartX = minEdge + (int)(timeSeed % (maxPondX - minEdge + 1));
            int pondStartY = minEdge + (int)((timeSeed / 1000) % (maxPondY - minEdge + 1));
            this.pondStartPoint = new Point(pondStartX, pondStartY);

            // Cek bounding box jarak min ke house
            if (canPlaceObjectAt("Pond", pondStartX, pondStartY, pondWidth, pondHeight) &&
                !isNearPlayer(pondStartPoint, pondWidth, pondHeight, playerPositionFarm) &&
                isFarFromHouse(pondStartX, pondStartY, pondWidth, pondHeight,
                               houseStartPoint.getX(), houseStartPoint.getY(), houseWidth, houseHeight,
                               MIN_DIST_POND_TO_HOUSE)) {
                placeObjectAt("Pond", pondStartPoint, pondWidth, pondHeight);
                pondPlaced = true;
            }
            timeSeed += 987;
            pondAttempts++;
        }
        if (!pondPlaced) {
            System.out.println("Gagal tempatkan Pond setelah 1000 percobaan");
        }

        // --- Place Shipping Bin (di kanan rumah +1 space, tapi tetap cek edge & min dist)
        int shippingBinStartX = houseStartPoint.getX() + houseWidth + 1; // kanan rumah +1
        int shippingBinStartY = houseStartPoint.getY();
        boolean shippingBinPlaced = false;

        // Cek kanan rumah
        if (shippingBinStartX <= maxShippingBinX &&
            shippingBinStartY >= minEdge && shippingBinStartY <= maxShippingBinY &&
            canPlaceObjectAt("ShippingBin", shippingBinStartX, shippingBinStartY, shippingBinWidth, shippingBinHeight) &&
            !isNearPlayer(new Point(shippingBinStartX, shippingBinStartY), shippingBinWidth, shippingBinHeight, playerPositionFarm)) {
            this.shippingBinPoint = new Point(shippingBinStartX, shippingBinStartY);
            placeObjectAt("ShippingBin", shippingBinPoint, shippingBinWidth, shippingBinHeight);
            shippingBinPlaced = true;
        }
        // Cek kiri rumah
        if (!shippingBinPlaced) {
            shippingBinStartX = houseStartPoint.getX() - shippingBinWidth - 1;
            if (shippingBinStartX >= minEdge &&
                shippingBinStartY >= minEdge && shippingBinStartY <= maxShippingBinY &&
                canPlaceObjectAt("ShippingBin", shippingBinStartX, shippingBinStartY, shippingBinWidth, shippingBinHeight) &&
                !isNearPlayer(new Point(shippingBinStartX, shippingBinStartY), shippingBinWidth, shippingBinHeight, playerPositionFarm)) {
                this.shippingBinPoint = new Point(shippingBinStartX, shippingBinStartY);
                placeObjectAt("ShippingBin", shippingBinPoint, shippingBinWidth, shippingBinHeight);
                shippingBinPlaced = true;
            }
        }
        if (!shippingBinPlaced) {
            System.out.println("Gagal tempatkan Shipping Bin");
        }

        // Tandai tile yang kosong sebagai Tillable
        for (int y = 0; y < farmSizeHeight; y++) {
            for (int x = 0; x < farmSizeWidth; x++) {
                if (farmMapDisplay[y][x] == '\0') {
                    objectPosition.get("Tillable").add(new Point(x, y));
                }
            }
        }
    }

    // Fungsi helper: bounding box minimal jarak min ke house
    private boolean isFarFromHouse(int pondX, int pondY, int pondW, int pondH,
                                   int houseX, int houseY, int houseW, int houseH,
                                   int minDist) {
        int pondRight = pondX + pondW - 1;
        int pondBottom = pondY + pondH - 1;
        int houseRight = houseX + houseW - 1;
        int houseBottom = houseY + houseH - 1;
        // Hitung jarak terdekat axis-aligned bounding box
        int dx = Math.max(0, Math.max(houseX - pondRight, pondX - houseRight));
        int dy = Math.max(0, Math.max(houseY - pondBottom, pondY - houseBottom));
        int minDistance = Math.max(dx, dy); // pakai radius block (bisa juga Math.sqrt(dx*dx + dy*dy) untuk Euclidean)
        return minDistance >= minDist;
    }
    public char getTileAt(int x, int y) {
        if (x >= 0 && x < farmMapDisplay[0].length && y >= 0 && y < farmMapDisplay.length) {
            return farmMapDisplay[y][x];
        } else {
            return '\0';
        }
    }

    // Helper method
    private boolean isNearPlayer(Point objectPoint, int width, int height, Point playerPoint) {
        int objStartX = objectPoint.getX();
        int objStartY = objectPoint.getY();
        int objEndX = objStartX + width;
        int objEndY = objStartY + height;
    
        int px = playerPoint.getX();
        int py = playerPoint.getY();
    
        return px >= objStartX - 1 && px < objEndX + 1 && py >= objStartY - 1 && py < objEndY + 1;
    }
    public Point getValidRandomSpawnPoint() {
        long seed = System.currentTimeMillis();
        int attempts = 0;
        int maxAttempts = 1000;

        while (attempts < maxAttempts) {
            int x = (int)(seed % farmSizeWidth);
            int y = (int)((seed / 1000) % farmSizeHeight);
            Point candidate = new Point(x, y);
            
            if (farmMapDisplay[y][x] == '.') {
                boolean terlaluDekat = false;
                for (String key : new String[]{"House", "Pond", "ShippingBin"}) {
                    for (Point p : objectPosition.get(key)) {
                        if (Math.abs(p.getX() - x) <= 2 && Math.abs(p.getY() - y) <= 2) {
                            terlaluDekat = true;
                            break;
                        }
                    }
                    if (terlaluDekat) break;
                }

                if (!terlaluDekat) {
                    return candidate;
                }
            }

            seed += 131; 
            attempts++;
        }

        // fallback: return tengah map
        return new Point(farmSizeWidth / 2, farmSizeHeight / 2);
    }

    
    private boolean canPlaceObjectAt(String objectType, int startX, int startY, int width, int height) {
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                if (x < 0 || x >= farmSizeWidth || y < 0 || y >= farmSizeHeight) {
                    return false; 
                }
                if (farmMapDisplay[y][x] != '\0') { 
                    return false;
                }
            }
        }
        return true;  
    }
    

    private void placeObjectAt(String objectType, Point startPoint, int width, int height) {
        int startX = startPoint.getX();
        int startY = startPoint.getY();
    
        if (startX + width > farmSizeWidth || startY + height > farmSizeHeight) {
            throw new IllegalArgumentException("Object does not fit in the map at the given position.");
        }
    
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                farmMapDisplay[y][x] = getObjectChar(objectType);
                objectPosition.get(objectType).add(new Point(x, y));
            }
        }
    }
    

    private char getObjectChar(String objectType) {
        switch (objectType) {
            case "House": return 'h';
            case "Pond": return 'O';
            case "ShippingBin": return 's';
            case "Tillable": return '.';
            case "Tilled": return 't';
            case "Planted": return 'l';
            default: return '.';
        }
    }

    private void fillTillableLand() {
        for (int y = 0; y < farmSizeHeight; y++) {
            for (int x = 0; x < farmSizeWidth; x++) {
                if (farmMapDisplay[y][x] == '\0') {
                    farmMapDisplay[y][x] = Math.random() < 0.1 ? 'm' : '.';
                }
            }
        }
    }



    public void displayObjectPositions() {
        for (String objectType : objectPosition.keySet()) {
            System.out.print(objectType + ": ");
            List<Point> points = objectPosition.get(objectType);
            
            if (!points.isEmpty()) {
                System.out.print(points.get(0).printPoint());
            }
            
            System.out.println();
        }
    }
    
    public void displayFarmMap() {
        for (int i = 0; i < farmSizeHeight; i++) {
            for (int j = 0; j < farmSizeWidth; j++) {
                if (playerLocation.getName().equals("Farm") && playerPositionFarm.getY() == i && playerPositionFarm.getX() == j) {
                    System.out.print("P ");
                } else {
                    System.out.print(farmMapDisplay[i][j] + " ");
                }
            }
            System.out.println(); 
        }
    }
    

    public boolean isAtEdge(Point playerPositionFarm) {
        int x = playerPositionFarm.getX();
        int y = playerPositionFarm.getY();
        return x == 0 || x == 31 || y == 0 || y == 31;
    }

    public boolean canToggleWorldMap(WorldMap worldMap) {
        return isAtEdge(playerPositionFarm) && worldMap.getPlayerLocation().getName().equals("Farm");
    }


    
    
}
