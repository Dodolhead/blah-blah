package src.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.items.*;


public class FarmMap {
    private static int farmSizeWidth = 32;
    private static int farmSizeHeight = 32;
    private Location playerLocation;
    private Map<String, List<Point>> objectPosition;
    private char[][] farmMapDisplay;
    private Point playerPositionFarm;
    private Map<Point, Seed> plantedSeeds;
    private Map<Point, Integer> plantedDay;


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
        int maxWidth = farmSizeWidth - 1;
        int maxHeight = farmSizeHeight - 1;

        int maxPondX = maxWidth - 3; // pond width 4
        int maxPondY = maxHeight - 2; // pond height 3

        int maxHouseX = maxWidth - 5; // house width 6
        int maxHouseY = maxHeight - 5; // house height 6

        int maxShippingBinX = maxWidth - 2; // shipping bin width 3
        int maxShippingBinY = maxHeight - 1; // shipping bin height 2

        // Check farm size minimal
        if (maxPondX < 0 || maxPondY < 0 || maxHouseX < 0 || maxHouseY < 0 || maxShippingBinX < 0 || maxShippingBinY < 0) {
            System.out.println("Farm size terlalu kecil untuk objek");
            return;
        }

        // Place Pond
        boolean pondPlaced = false;
        int pondAttempts = 0;
        while (!pondPlaced && pondAttempts < 1000) {
            int pondStartX = (int)(timeSeed % (maxPondX + 1)); // +1 supaya max inclusive
            int pondStartY = (int)((timeSeed / 1000) % (maxPondY + 1));
            Point pondStartPoint = new Point(pondStartX, pondStartY);

            if (canPlaceObjectAt("Pond", pondStartX, pondStartY, 4, 3) &&
                !isNearPlayer(pondStartPoint, 4, 3, playerPositionFarm)) {
                placeObjectAt("Pond", pondStartPoint, 4, 3);
                pondPlaced = true;
            }
            timeSeed += 1000;
            pondAttempts++;
        }
        if (!pondPlaced) {
            System.out.println("Gagal tempatkan Pond setelah 1000 percobaan");
        }

        // Place House
        boolean housePlaced = false;
        Point houseStartPoint = null;
        int houseAttempts = 0;
        while (!housePlaced && houseAttempts < 1000) {
            int houseStartX = (int)(timeSeed % (maxHouseX + 1));
            int houseStartY = (int)((timeSeed / 2000) % (maxHouseY + 1));
            houseStartPoint = new Point(houseStartX, houseStartY);

            if (canPlaceObjectAt("House", houseStartX, houseStartY, 6, 6) &&
                !isNearPlayer(houseStartPoint, 6, 6, playerPositionFarm)) {
                placeObjectAt("House", houseStartPoint, 6, 6);
                int doorX = houseStartPoint.getX() + 3;
                int doorY = houseStartPoint.getY() + 5;
                farmMapDisplay[doorY][doorX] = 'D';
                objectPosition.get("HouseDoor").add(new Point(doorX, doorY));
                housePlaced = true;
            }
            timeSeed += 1000;
            houseAttempts++;
        }
        if (!housePlaced) {
            System.out.println("Gagal tempatkan House setelah 1000 percobaan");
            // Bisa tambahkan fallback logic jika perlu
            return; // kalau ga ada house, shipping bin ga bisa ditempatkan
        }

        // Place Shipping Bin (di kanan rumah +1 space)
        int shippingBinStartX = houseStartPoint.getX() + 7; // 6 + 1 space
        int shippingBinStartY = houseStartPoint.getY();
        Point shippingBinPoint = new Point(shippingBinStartX, shippingBinStartY);

        if (shippingBinStartX <= maxShippingBinX &&
            canPlaceObjectAt("ShippingBin", shippingBinStartX, shippingBinStartY, 3, 2) &&
            !isNearPlayer(shippingBinPoint, 3, 2, playerPositionFarm)) {
            placeObjectAt("ShippingBin", shippingBinPoint, 3, 2);
        } else {
            // coba kiri rumah
            shippingBinStartX = houseStartPoint.getX() - 4;
            shippingBinPoint = new Point(shippingBinStartX, shippingBinStartY);
            if (shippingBinStartX >= 0 &&
                canPlaceObjectAt("ShippingBin", shippingBinStartX, shippingBinStartY, 3, 2) &&
                !isNearPlayer(shippingBinPoint, 3, 2, playerPositionFarm)) {
                placeObjectAt("ShippingBin", shippingBinPoint, 3, 2);
            } else {
                System.out.println("Gagal tempatkan Shipping Bin");
            }
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
    public char getTileAt(int x, int y) {
        if (x >= 0 && x < farmMapDisplay[0].length && y >= 0 && y < farmMapDisplay.length) {
            return farmMapDisplay[y][x];
        } else {
            return '\0'; // karakter kosong jika posisi tidak valid
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
            case "Pond": return 'o';
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
                    farmMapDisplay[y][x] = '.';
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
