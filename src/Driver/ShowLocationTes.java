package src.Driver;

import src.entities.*;
import src.actions.*;
import src.map.*;
import src.items.*;

public class ShowLocationTes {
    public static void main(String[] args) {
        // Buat point dan location (misal di "Forest River")
        Point point = new Point(5, 10);
        Location location = new Location("Forest River", point);

        // Buat objek pendukung player
        Gold gold = new Gold(100);
        Inventory inventory = new Inventory();

        // Buat player
        Player player = new Player("Qika", "Male", "Sunny Farm", gold, inventory, location);

        // Jalankan aksi show location
        ShowLocationAction showLoc = new ShowLocationAction();
        showLoc.execute(player);
    }
}
