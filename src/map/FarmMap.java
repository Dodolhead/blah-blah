package src.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.items.*;


public class FarmMap {
    private static int farmSizeWidth = 32;
    private static int farmSizeHeight = 32;
    private Point playerPositionFarm;
    private Map<String, List<Point>> objectPosition;
    private char[][] farmMapDisplay;
    private Map<Point, Seed> plantedSeeds;

    public FarmMap(Point playerPositionFarm) {
        this.playerPositionFarm = playerPositionFarm;
        this.objectPosition = new HashMap<>();
        this.farmMapDisplay = new char[farmSizeHeight][farmSizeWidth];
        this.plantedSeeds = new HashMap<>();

        objectPosition.put("House", new ArrayList<>());
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

    private void placeObjectsRandomly() {
        long timeSeed = System.currentTimeMillis(); 
        int maxWidth = farmSizeWidth - 1;
        int maxHeight = farmSizeHeight - 1;
    
        boolean pondPlaced = false;
        while (!pondPlaced) {
            int pondStartX = (int)(timeSeed % (maxWidth - 3));  
            int pondStartY = (int)((timeSeed / 1000) % (maxHeight - 3));  
            Point pondStartPoint = new Point(pondStartX, pondStartY);
    
            if (canPlaceObjectAt("Pond", pondStartX, pondStartY, 4, 3) &&
                !isNearPlayer(pondStartPoint, 4, 3, playerPositionFarm)) {
                placeObjectAt("Pond", pondStartPoint, 4, 3);  
                pondPlaced = true;
            }
            timeSeed += 1000; 
        }
    
        boolean housePlaced = false;
        Point houseStartPoint = null;
        while (!housePlaced) {
            int houseStartX = (int)(timeSeed % (maxWidth - 6)); 
            int houseStartY = (int)((timeSeed / 2000) % (maxHeight - 6));  
            houseStartPoint = new Point(houseStartX, houseStartY);
    
            if (canPlaceObjectAt("House", houseStartX, houseStartY, 6, 6) &&
                !isNearPlayer(houseStartPoint, 6, 6, playerPositionFarm)) {
                placeObjectAt("House", houseStartPoint, 6, 6); 
                housePlaced = true;
            }
            timeSeed += 1000; 
        }
    
        int shippingBinStartX = houseStartPoint.getX() + 6;
        int shippingBinStartY = houseStartPoint.getY();
        Point shippingBinPoint = new Point(shippingBinStartX, shippingBinStartY);
    
        if (canPlaceObjectAt("ShippingBin", shippingBinStartX, shippingBinStartY, 3, 2) &&
            !isNearPlayer(shippingBinPoint, 3, 2, playerPositionFarm)) {
            placeObjectAt("ShippingBin", shippingBinPoint, 3, 2);
        } else {
            shippingBinStartX = houseStartPoint.getX() - 3;
            shippingBinPoint = new Point(shippingBinStartX, shippingBinStartY);
            if (canPlaceObjectAt("ShippingBin", shippingBinStartX, shippingBinStartY, 3, 2) &&
                !isNearPlayer(shippingBinPoint, 3, 2, playerPositionFarm)) {
                placeObjectAt("ShippingBin", shippingBinPoint, 3, 2);
            }
        }
    
        for (int y = 0; y < farmSizeHeight; y++) {
            for (int x = 0; x < farmSizeWidth; x++) {
                if (farmMapDisplay[y][x] == '\0') { 
                    objectPosition.get("Tillable").add(new Point(x, y));
                }
            }
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
    
        // Tambahkan margin 1 tile agar tidak terlalu dekat
        return px >= objStartX - 1 && px < objEndX + 1 && py >= objStartY - 1 && py < objEndY + 1;
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
                if (playerPositionFarm.getY() == i && playerPositionFarm.getX() == j) {
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
