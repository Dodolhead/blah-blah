package src.Driver;

import src.actions.GiftingAction;
import src.entities.*;
import src.items.*;
import src.map.*;

public class GiftingTes {
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

        // Cetak informasi dasar
        System.out.println("Player Name: " + player.getPlayerName());
        System.out.println("Farm Name: " + player.getFarm());
        System.out.println("Partner Loved Item: " + player.getPartner().getLovedItem().get(0).getItemName());


        player.getPlayerInventory().addItem(diamond, 5);
        player.getPlayerInventory().addItem(oldKey, 3);
        player.getPlayerInventory().addItem(ancientCoin, 2);

        Farm farm = new Farm(player.getFarm(), player);

        GiftingAction giftDiamond = new GiftingAction(diamond, luna, 1);
        giftDiamond.execute(player);

        // Gifting ancient coin (liked)
        GiftingAction giftCoin = new GiftingAction(ancientCoin, luna, 1);
        giftCoin.execute(player);

        // Gifting old key (hated)
        GiftingAction giftKey = new GiftingAction(oldKey, luna, 1);
        giftKey.execute(player);

        System.out.println("\nHeart point Luna setelah gift: " + luna.getHeartPoints());
        System.out.println("Energi setelah gift: " + player.getEnergy());
        System.out.println("Waktu setelah gift: " + farm.getTime().getCurrentTime());

        System.out.println("\nSisa inventory:");
        player.getPlayerInventory().printInventory();
        farm.getTime().stopTime();
        
    }
}
