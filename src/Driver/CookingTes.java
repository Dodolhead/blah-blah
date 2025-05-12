package src.Driver;

import java.util.HashMap;
import java.util.Map;

import src.actions.MovingAction;
import src.entities.*;
import src.map.*;
import src.items.*;
public class CookingTes {
    public static void main(String[] args) {

        // 2. Membuat Item dan Misc
        System.out.println("\n=== Item Creation ===");
        
        // Regular Item
        Item diamond = new Misc("Diamond", "Gem");
        
        // Misc Items
        Misc oldKey = new Misc("Old Key", "A rusty key that might open something");
        Misc ancientCoin = new Misc("Ancient Coin", "Coin from a forgotten civilization");
        
        System.out.println("Created items:");
        System.out.println("- " + diamond.getItemName() + " (" + diamond.getItemType() + ")");
        System.out.println("- " + oldKey.getItemName() + " (" + oldKey.getItemType() + "): " + oldKey.getItemDescription());

        // 3. Inisialisasi Inventory
        Inventory playerInventory = new Inventory();

        // 4. Membuat NPC dengan preferensi item
        System.out.println("\n=== NPC Setup ===");

        NPC alice = new NPC("Alice", "Single");

        alice.addLovedItem(diamond);
        alice.addLikedItem(ancientCoin);
        System.out.println("Created NPC: " + alice.getNpcName());
        System.out.println("Loves: " + alice.getLovedItem().get(0).getItemName());

        // 5. Membuat Player dengan Inventory
        System.out.println("\n=== Player Setup ===");
        
        Gold playerGold = new Gold(1000);
        Location playerLocation = new Location("House", new Point(12, 12));
        
        Player player = new Player("John", "Male", "Sunny Farm", playerGold, 
                                playerInventory, playerLocation);
        Farm anjayFarm = new Farm(player.getFarm(), player);
        
        // Menambahkan item ke inventory
        player.getPlayerInventory().addItem(diamond, 1);
        player.getPlayerInventory().addItem(oldKey, 1);
        player.getPlayerInventory().addItem(ancientCoin, 3);
        
        System.out.println("Created player: " + player.getPlayerName());
        System.out.println("Inventory contents:");
        player.getPlayerInventory().printInventory();

        // 6. Interaksi dengan NPC
        System.out.println("\n=== NPC Interaction ===");
        
        // Memberi hadiah ke NPC
        if (player.getPlayerInventory().getItemAmount(diamond) > 0) {
            alice.receiveGift(diamond, 1);
            alice.increaseHeartPoints(50);
            player.getPlayerInventory().removeItem(diamond, 1);
            System.out.println("Gave " + diamond.getItemName() + " to Alice!");
            System.out.println("Alice's heart points now: " + alice.getHeartPoints());
            System.out.println("Remaining diamonds: " + player.getPlayerInventory().getItemAmount(diamond));
        }

        // 7. House Map dan Furniture
        System.out.println("\n=== House Setup ===");
        
        HouseMap houseMap = new HouseMap(playerLocation);

        // Membuat dan menempatkan furniture
        Bed bed = new Bed("bed1", "King Bed", "A comfortable king size bed", 2, 3, 2);
        Stove stove = new Stove();

        // bed.rotateFurniture();
        
        houseMap.placeFurniture(bed, 5, 5);
        houseMap.placeFurniture(stove, 10, 10);
        
        System.out.println("Placed furniture in house:");
        System.out.println("- " + bed.getFurnitureName() + " (size: " + bed.getFurnitureSizeX() + "x" + bed.getFurnitureSizeY() + ")");
        System.out.println("- " + stove.getFurnitureName() + " (logo: '" + stove.getFurnitureLogo() + "')");

        houseMap.displayHouse();
        

        // 8. Player Movement
        System.out.println("\n=== Player Movement ===");
        Point playerPos = player.getPlayerLocation().getCurrentPoint();
        System.out.println("Current position: (" + playerPos.getX() + "," + playerPos.getY() + ")");
        

        playerPos.movePlayer("up", houseMap.getHouseMapDisplay());
        System.out.println("Moved forward to: (" + playerPos.getX() + "," + playerPos.getY() + ")");

        playerPos.movePlayer("up", houseMap.getHouseMapDisplay());
        System.out.println("Moved forward to: (" + playerPos.getX() + "," + playerPos.getY() + ")");

        playerPos.movePlayer("up", houseMap.getHouseMapDisplay());
        System.out.println("Moved forward to: (" + playerPos.getX() + "," + playerPos.getY() + ")");

        playerPos.movePlayer("left", houseMap.getHouseMapDisplay());
        System.out.println("Moved left to: (" + playerPos.getX() + "," + playerPos.getY() + ")");

        playerPos.movePlayer("left", houseMap.getHouseMapDisplay());
        
        
        // playerPos.movePlayer("up", houseMap.getHouseMapDisplay());
        // System.out.println("Moved forward to: (" + playerPos.getX() + "," + playerPos.getY() + ")");

        // playerPos.movePlayer("left", houseMap.getHouseMapDisplay());
        // System.out.println("Moved left to: (" + playerPos.getX() + "," + playerPos.getY() + ")");

        // playerPos.movePlayer("down", houseMap.getHouseMapDisplay());

        // playerPos.movePlayer("left", houseMap.getHouseMapDisplay());
        // System.out.println("Moved left to: (" + playerPos.getX() + "," + playerPos.getY() + ")");

        houseMap.displayHouse();

        houseMap.displayObjectPositions();

        MovingAction move = new MovingAction(24, 0, houseMap.getHouseMapDisplay());
        move.execute(player);

        houseMap.displayHouse();

        System.out.println("\n=== Cooking Test ===");

        // 1. Tambahkan bahan makanan ke inventory
        Food egg = new Food("Egg", 5, new Gold(50), new Gold(30));
        Food tomato = new Food("Tomato", 4, new Gold(40), new Gold(25));
        Misc firewood = new Misc("Fire Wood", "Kayu bakar");

        player.getPlayerInventory().addItem(egg, 1);
        player.getPlayerInventory().addItem(tomato, 1);
        player.getPlayerInventory().addItem(firewood, 1);

        // 2. Tambahkan recipe item ke inventory
        Misc omeletteRecipeItem = new Misc("Omelette Recipe", "Resep membuat omelette");
        player.getPlayerInventory().addItem(omeletteRecipeItem, 1);

        // 3. Buat objek recipe
        Map<String, Integer> ingredients = new HashMap<>();
        ingredients.put("egg", 1);
        ingredients.put("tomato", 1);

        Food omelette = new Food("Omelette", 15, new Gold(120), new Gold(80));
        Recipe omeletteRecipe = new Recipe("Omelette Recipe", "ini resep omelet", "recipe_1", ingredients, omelette);

        // 4. Set lokasi player ke House
        // player.getPlayerLocation().setCurrentPoint(new Point(10, 9));

        // 5. Tambahkan bahan bakar ke kompor
        System.out.println("Menambahkan bahan bakar...");
        player.getPlayerInventory().printInventory();
        stove.addFuel("Fire Wood", player, houseMap);
        System.out.println("Sisa fuel: " + stove.getFuelRemaining());

        // 6. Memasak makanan
        System.out.println("Memasak omelette...");
        boolean success = stove.useStove(player, houseMap, omeletteRecipe);

        if (success) {
            System.out.println("Berhasil memasak omelette!");
            player.getPlayerInventory().printInventory();
            System.out.println("Sisa bahan bakar: " + stove.getFuelRemaining());
        } else {
            System.out.println("Gagal memasak!");
        }
        System.out.println("Energy: " + player.getEnergy());

        // anjayFarm.getTime().stopTime();

    }
}