package src.map;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import src.entities.*;
public class HouseMap {
    private static int houseSizeWidth = 24;
    private static int houseSizeHeight = 24;
    private char[][] houseMapDisplay;
    private Map<Furniture, List<Point>> furnitureLocation = new HashMap<>();
    private Point playerPositionHouse;


    public HouseMap(Point playerPositionHouse, Map<Furniture, List<Point>> furnitureLocation) {
        this.playerPositionHouse = playerPositionHouse;
        this.houseMapDisplay = new char[houseSizeHeight][houseSizeWidth];
        this.furnitureLocation = furnitureLocation;

        for (int i = 0; i < houseSizeHeight; i++) {
            for (int j = 0; j < houseSizeWidth; j++) {
                houseMapDisplay[i][j] = '.';
            }
        }
    }
    

    public char[][] getHouseMapDisplay() {
        return houseMapDisplay;
    }

    public Point getPlayerPositionHouse() {
        return playerPositionHouse;
    }

    public Map<Furniture, List<Point>> getFurnitureLocation() {
        return furnitureLocation;
    }

    public List<Point> getOccupiedPoints(Furniture furniture, int startX, int startY) {
        int endX = startX + furniture.getFurnitureSizeX();
        int endY = startY + furniture.getFurnitureSizeY();


        if (endX > 24 || endY > 24 || startX < 0 || startY < 0) {
            throw new IllegalArgumentException("Furniture placement out of map bounds.");
        }
    
        List<Point> occupied = new ArrayList<>();
        for (int dx = 0; dx < furniture.getFurnitureSizeX(); dx++) {
            for (int dy = 0; dy < furniture.getFurnitureSizeY(); dy++) {
                occupied.add(new Point(startX + dx, startY + dy));
            }
        }
        return occupied;
    }

    public boolean placeFurniture(Furniture furniture, int startX, int startY) {
        List<Point> occupiedPoints = getOccupiedPoints(furniture, startX, startY);
    
        for (Point p : occupiedPoints) {
            int x = p.getX();
            int y = p.getY();
            if (x < 0 || x >= houseSizeHeight || y < 0 || y >= houseSizeWidth) {
                return false; 
            }
            if (houseMapDisplay[x][y] != '.') {
                return false;
            }
        }

        furnitureLocation.putIfAbsent(furniture, new ArrayList<>());  
        furnitureLocation.get(furniture).add(new Point(startX, startY));  

        for (Point p : occupiedPoints) {
            houseMapDisplay[p.getY()][p.getX()] = furniture.getFurnitureLogo(); 
        }

        return true;
    }

    public void displayHouse() {
        for (int i = 0; i < houseSizeHeight; i++) {
            for (int j = 0; j < houseSizeWidth; j++) {
                if (playerPositionHouse.getX() == i && playerPositionHouse.getY() == j) {
                    System.out.print('P');
                } else {
                    System.out.print(houseMapDisplay[i][j]);
                }
            }
            System.out.println(); 
        }
    }

    

}
