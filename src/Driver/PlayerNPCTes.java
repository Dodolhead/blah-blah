package src.Driver;

import src.actions.OpeningInventoryAction;
import src.actions.ShowingTimeAction;
import src.entities.*;
import src.items.*;
import src.map.*;

public class PlayerNPCTes {
        public static void main(String[] args) {
        // Buat item sesuai permintaan
        Item diamond = new Misc("Diamond", "Gem");
        Item oldKey = new Misc("Old Key", "A rusty key that might open something");
        Item ancientCoin = new Misc("Ancient Coin", "Coin from a forgotten civilization");

        // Relationship status default (bisa disesuaikan nanti)
        String relationshipStatus = "Spouse";

        // Buat NPC
        NPC luna = new NPC("Luna", relationshipStatus);

        luna.addLovedItem(diamond);
        luna.addLikedItem(ancientCoin);
        luna.addHatedItem(oldKey);

        // Buat objek pendukung player
        Gold gold = new Gold(500); // Contoh player punya 500 gold
        Inventory inventory = new Inventory(); // Kosong dulu
        Location homeLocation = new Location("Farm", new Point(16, 16));

        // Buat player dengan NPC tersebut sebagai partner
        Player player = new Player("Alex", "Male", "Sunny Farm", gold, inventory, homeLocation);

        System.out.println("Player Name: " + player.getPlayerName());
        System.out.println("Farm Name: " + player.getFarm());

        Farm anjayFarm = new Farm(player.getFarm(), player);

        player.getPlayerInventory().addItem(ancientCoin, 1);
        player.getPlayerInventory().addItem(diamond, 5);

        
        OpeningInventoryAction openInvent = new OpeningInventoryAction();
        openInvent.execute(player);
        try{
            Thread.sleep(5000);
        }
        catch(Exception e){
            System.out.println(e);
        }

        System.out.println(player.getPlayerInventory().getItemAmount(ancientCoin));
        
        ShowingTimeAction showTime = new ShowingTimeAction();
        showTime.execute(player);


        anjayFarm.getTime().skipDays(10);

        showTime.execute(player);

        anjayFarm.getTime().skipDays(10);

        showTime.execute(player);
        
        anjayFarm.getTime().skipDays(10);

        showTime.execute(player);
        
        anjayFarm.getTime().skipDays(10);

        showTime.execute(player);

        anjayFarm.getTime().stopTime();

    }
}
