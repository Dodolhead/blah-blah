package src.actions;

import src.entities.*;
import src.map.*;

public class ShowLocationAction implements Action {
    private FarmMap farmMap;
    
    public ShowLocationAction(FarmMap farmMap) {
        this.farmMap = farmMap;
    }

    @Override
    public boolean execute(Player player) {
        // Mendapatkan informasi lokasi pemain
        Location playerLocation = player.getPlayerLocation();
        String locationName = playerLocation.getName();
        Point currentPoint = playerLocation.getCurrentPoint();
        
        // Menampilkan informasi lokasi
        System.out.println("===== INFORMASI LOKASI =====");
        System.out.println("Nama Lokasi: " + locationName);
        System.out.println("Posisi: " + currentPoint.printPoint());
        
        // Jika berada di Farm, tampilkan informasi tambahan
        if (locationName.equals("Farm")) {
            char[][] farmDisplay = farmMap.getFarmMapDisplay();
            int x = currentPoint.getX();
            int y = currentPoint.getY();
            
            // Periksa apakah posisi valid
            if (y >= 0 && y < farmDisplay.length && x >= 0 && x < farmDisplay[0].length) {
                char tileType = farmDisplay[y][x];
                String tileDescription = getTileDescription(tileType);
                
                System.out.println("Jenis Tanah: " + tileDescription);
                
                // Periksa objek di sekitar
                checkSurroundingObjects(x, y);
            }
        } else {
            // Informasi tambahan untuk lokasi lain
            System.out.println("Informasi Tambahan: " + getLocationDescription(locationName));
        }
        
        return true;
    }
    
    // Helper method untuk mendapatkan deskripsi tile
    private String getTileDescription(char tileType) {
        switch (tileType) {
            case '.':
                return "Tillable Land (Tanah yang dapat dibajak)";
            case 't':
                return "Tilled Land (Tanah yang sudah dibajak)";
            case 'l':
                return "Planted Land (Tanah yang sudah ditanami)";
            case 'h':
                return "House (Rumah)";
            case 'o':
                return "Pond (Kolam)";
            case 's':
                return "Shipping Bin (Kotak Pengiriman)";
            default:
                return "Tidak Diketahui";
        }
    }
    
    // Helper method untuk memeriksa objek di sekitar pemain
    private void checkSurroundingObjects(int x, int y) {
        // Periksa lokasi objek
        for (String objectType : farmMap.getObjectPosition().keySet()) {
            for (Point objPoint : farmMap.getObjectPosition().get(objectType)) {
                // Jika objek berada di sekitar pemain (jarak 1)
                if (Math.abs(x - objPoint.getX()) <= 1 && Math.abs(y - objPoint.getY()) <= 1) {
                    if (!objectType.equals("Tillable") && !objectType.equals("Tilled") && !objectType.equals("Planted")) {
                        System.out.println("Dekat dengan: " + objectType);
                    }
                }
            }
        }
    }
    
    // Helper method untuk mendapatkan deskripsi lokasi
    private String getLocationDescription(String locationName) {
        switch (locationName) {
            case "Mountain Lake":
                return "Danau besar dengan berbagai jenis ikan. Tempat memancing yang popular.";
            case "Forest River":
                return "Sungai yang mengalir melalui hutan. Terdapat ikan tertentu yang hanya bisa ditangkap di sini.";
            case "Ocean":
                return "Lautan luas dengan variasi ikan laut. Beberapa ikan legendaris dapat ditemukan di sini.";
            case "Pond":
                return "Kolam kecil di dalam farm. Beberapa ikan umum dapat ditangkap di sini.";
            case "Store":
                return "Toko yang menjual berbagai kebutuhan pertanian. Dikelola oleh Emily.";
            case "House":
                return "Rumah Anda di dalam farm. Tempat untuk beristirahat dan memasak.";
            default:
                return "Tidak ada informasi tambahan.";
        }
    }
}