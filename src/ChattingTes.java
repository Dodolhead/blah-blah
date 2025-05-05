package src;

import src.actions.ChattingAction;
import src.entities.*;
import src.items.Gold;
import src.items.Inventory;
import src.items.Item;
import src.items.Misc;
import src.map.*;

public class ChattingTes {
    public static void main(String[] args) {
        // === SETUP ===
        Item diamond = new Misc("Diamond", "Gem");
        Item oldKey = new Misc("Old Key", "A rusty key that might open something");
        Item ancientCoin = new Misc("Ancient Coin", "Coin from a forgotten civilization");

        NPC luna = new NPC("Luna", "Single");
        luna.addLovedItem(diamond);
        luna.addLikedItem(ancientCoin);
        luna.addHatedItem(oldKey);

        Gold gold = new Gold(500);
        Inventory inventory = new Inventory();
        Location homeLocation = new Location("Luna", new Point(16, 16));

        // Simulasikan tempat tinggal Luna
        NPCHomeManager.addNPCHome("Luna", luna); // penting agar ChattingAction bisa jalan
        Player player = new Player("Alex", "Male", "Sunny Farm", gold, inventory, homeLocation);
        Farm farm = new Farm(player.getFarm(), player);
        System.out.println(FarmManager.getFarmByName(player.getFarm()).getFarmName());

        // === TEST CHAT ===
        System.out.println("\n=== CHAT TEST ===");
        ChattingAction chat = new ChattingAction(luna);
        boolean chatResult = chat.execute(player);

        if (chatResult) {
            System.out.println("Heartpoints Luna now: " + luna.getHeartPoints());
            System.out.println("Player Energy now: " + player.getEnergy());
        }

        farm.getTime().stopTime();
    }
}
