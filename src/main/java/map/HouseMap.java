package map;

import java.util.Map;
import entities.*;
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

        int originalWidth = houseSizeWidth;
        int originalHeight = houseSizeHeight;

        int newWidth = originalWidth + 2;
        int newHeight = originalHeight + 2;

        // Buat array baru dengan border
        this.houseMapDisplay = new char[newHeight][newWidth];

        // Fill dengan ','
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                houseMapDisplay[i][j] = ',';
            }
        }

        // Wall
        for (int i = 0; i < newHeight; i++) {
            houseMapDisplay[i][0] = 'W';
            houseMapDisplay[i][newWidth - 1] = 'W';
        }
        for (int j = 0; j < newWidth; j++) {
            houseMapDisplay[0][j] = 'W';
            houseMapDisplay[newHeight - 1][j] = 'W';
        }

        // Door (dua tile di tengah bawah)
        int midBottomX = newWidth / 2;
        houseMapDisplay[newHeight - 1][midBottomX] = 'D';
        houseMapDisplay[newHeight - 1][midBottomX-1] = 'D';

        furnitureLocation = new HashMap<>();

        // --- FURNITURE ---
        // BED: Single Bed ukuran 2x4 di kiri atas (baris 1 kolom 1)
        // Update the constructor arguments to match the Bed class definition
        Bed singleBed = new Bed("bed_1", "Single Bed", "Kasur ukuran single yang mampu ditempati maks 1 orang", 2, 4, 'B');
        placeFurniture(singleBed, 1, 1); // (x, y) = (1,1)

        // STOVE: ukuran 1x1 di kanan paling atas sebelum wall
        Stove stove = new Stove(); // konstruktor sesuai yang kamu punya
        placeFurniture(stove, newWidth-2, 1); // (x, y) = (25,1) kalau newWidth=26

        // TV: ukuran 1x1 di tengah atas sebelum wall
        TV tv = new TV("Televisi untuk melihat cuaca pada hari tersebut", 1, 1);
        placeFurniture(tv, newWidth/2, 1); // (x, y) di tengah atas

        // Door ke furnitureLocation (opsional, sesuai kode awalmu)
        Door door = new Door();
        List<Point> doorPoints = new ArrayList<>();
        doorPoints.add(new Point(midBottomX, newHeight - 1));
        doorPoints.add(new Point(midBottomX-1, newHeight - 1));
        furnitureLocation.put(door, doorPoints);

        playerPositionHouse = new Point(playerLocation.getCurrentPoint().getX() + 1, playerLocation.getCurrentPoint().getY() + 1);
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


        if (endX > houseMapDisplay[0].length || endY > houseMapDisplay.length || startX < 0 || startY < 0) {
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
        int sizeX = furniture.getFurnitureSizeX();
        int sizeY = furniture.getFurnitureSizeY();

        // Cek boundaries
        if (startX < 1 || startY < 1 ||
            startX + sizeX > houseMapDisplay[0].length - 1 ||
            startY + sizeY > houseMapDisplay.length - 1) {
            System.out.println("Furniture placement out of bounds.");
            return false;
        }

        // Cek seluruh tile kosong
        for (int dx = 0; dx < sizeX; dx++) {
            for (int dy = 0; dy < sizeY; dy++) {
                int x = startX + dx;
                int y = startY + dy;
                if (houseMapDisplay[y][x] != ',') {
                    System.out.println("Furniture placement blocked at " + x + "," + y + " by '" + houseMapDisplay[y][x] + "'");
                    return false;
                }
            }
        }

        // Simpan posisi baru furniture (support multi-instance)
        furnitureLocation.computeIfAbsent(furniture, f -> new ArrayList<>()).add(new Point(startX, startY));

        // Update map char
        for (int dx = 0; dx < sizeX; dx++) {
            for (int dy = 0; dy < sizeY; dy++) {
                int x = startX + dx;
                int y = startY + dy;
                houseMapDisplay[y][x] = furniture.getFurnitureLogo();
            }
        }
        System.out.println("Placed " + furniture.getFurnitureName() + " at (" + startX + "," + startY + ")");
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

    public Point getFurnitureStartPoint(Class<? extends Furniture> furnitureClass) {
        for (Furniture furniture : furnitureLocation.keySet()) {
            if (furnitureClass.isInstance(furniture)) {
                List<Point> points = furnitureLocation.get(furniture);
                if (points != null && !points.isEmpty()) {
                    return points.get(0); // Ambil posisi awal (startX, startY) saat placeFurniture
                }
            }
        }
        return null;
    }

}
