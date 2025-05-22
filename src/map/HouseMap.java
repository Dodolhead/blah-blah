package src.map;

import java.util.Map;

import src.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class HouseMap {
    private static int houseSizeWidth = 24;
    private static int houseSizeHeight = 24;
    private char[][] houseMapDisplay;
    private Map<Furniture, List<Point>> furnitureLocation;
    private Location playerLocation;
    private Point playerPositionHouse;


    public HouseMap(Location playerLocation) {
        this.playerLocation = playerLocation;
        this.houseMapDisplay = new char[houseSizeHeight][houseSizeWidth];
        this.furnitureLocation = new HashMap<>();
        playerPositionHouse = playerLocation.getCurrentPoint();

        for (int i = 0; i < houseSizeHeight; i++) {
            for (int j = 0; j < houseSizeWidth; j++) {
                houseMapDisplay[i][j] = '.';
            }
        }

        int midBottomX = houseSizeWidth / 2;
        houseMapDisplay[houseSizeHeight - 1][midBottomX] = 'D';
        Door door = new Door();
        List<Point> doorPoints = new ArrayList<>();
        doorPoints.add(new Point(midBottomX, houseSizeHeight - 1));
        furnitureLocation.put(door, doorPoints);
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

    public void setPlayerPosition(Point playerPositionHouse) {
        this.playerPositionHouse = playerPositionHouse;
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
                if (playerLocation.getName().equals("House") && playerPositionHouse.getY() == i && playerPositionHouse.getX() == j) {
                    System.out.print("P ");
                } else {
                    System.out.print(houseMapDisplay[i][j] + " ");
                }
            }
            System.out.println(); 
        }
    }
    

    public void displayObjectPositions() {
        for (Furniture furniture : furnitureLocation.keySet()) {
            System.out.print(furniture.getFurnitureName() + ": ");
            List<Point> points = furnitureLocation.get(furniture);
            for (Point point : points) {
                System.out.print(point.printPoint() + " ");
            }
            System.out.println();
        }
    }


}
