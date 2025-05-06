package src.actions;

import src.entities.*;
import src.map.*;
import src.tsw.Time;

public class Visiting implements Action {
    private WorldMap worldMap;
    private String destination;
    private static final int ENERGY_COST = 10;
    private static final int TIME_COST_MINUTES = 15;
    private Time gameTime;

    public Visiting(WorldMap worldMap, String destination, Time gameTime) {
        this.worldMap = worldMap;
        this.destination = destination;
        this.gameTime = gameTime;
    }

    @Override
    public boolean execute(Player player) {
        // Periksa apakah pemain memiliki cukup energi
        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("Energi tidak cukup untuk mengunjungi " + destination);
            return false;
        }

        // Periksa apakah pemain berada di tepi peta farm
        FarmMap farmMap = new FarmMap(player.getPlayerLocation().getCurrentPoint());
        if (!farmMap.isAtEdge(player.getPlayerLocation().getCurrentPoint())) {
            System.out.println("Anda harus berada di tepi farm untuk mengunjungi lokasi lain.");
            return false;
        }

        // Kurangi biaya energi
        player.subtractPlayerEnergy(ENERGY_COST);
        
        // Tambahkan biaya waktu
        if (gameTime != null) {
            gameTime.skipTimeMinute(TIME_COST_MINUTES);
            System.out.println(TIME_COST_MINUTES + " menit telah berlalu");
        }
        
        // Buat point untuk destinasi
        Point destinationPoint = createPointForDestination(destination);
        
        // Atur lokasi pemain ke destinasi
        Location newLocation = new Location(destination, destinationPoint);
        player.setPlayerLocation(newLocation);
        
        // Tambahkan ke tempat yang sudah dikunjungi
        player.addVisitedPlace(destination);
        
        // Kunjungi lokasi di world map
        worldMap.visit(newLocation);
        
        System.out.println("Anda telah tiba di " + destination);
        return true;
    }
    
    private Point createPointForDestination(String destination) {
        // Buat point yang sesuai berdasarkan destinasi
        switch (destination) {
            case "Forest River":
                return new Point(0, 0);
            case "Mountain Lake":
                return new Point(10, 10);
            case "Ocean":
                return new Point(20, 20);
            case "Store":
                return new Point(2, 2);
            default:
                return new Point(5, 5); // Posisi default
        }
    }
}