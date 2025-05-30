package items;

import java.util.HashMap;
import java.util.Map;

import entities.Player;
import tsw.*;

public class ShippingBin {
    private int MAX_UNIQUE_ITEMS = 16;
    private Map<Item, Integer> shippingBinStorage;
    private int lastSoldDay = 0;

    public ShippingBin() {
        this.shippingBinStorage = new HashMap<>();
    }

    public boolean isFull() {
        return shippingBinStorage.size() >= MAX_UNIQUE_ITEMS;
    }

    public Map<Item, Integer> getShippingBinStorage() {
        return shippingBinStorage;
    }

    public boolean addToShippingBin(Inventory inventory, Item item, int amount) {
        if (!item.getIsSellable()) {
            System.out.println("This item cannot be sold.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return false;
        }
        if (!inventory.hasItemAndAmount(item.getItemName(), amount)) {
            System.out.println("You don't have enough of this item.");
            return false;
        }
        if (!shippingBinStorage.containsKey(item) && isFull()) {
            System.out.println("Shipping bin is full! Max 16 unique items.");
            return false;
        }
        boolean removed = inventory.removeItem(item, amount);
        if (!removed) {
            System.out.println("Failed to remove item from inventory.");
            return false;
        }
        shippingBinStorage.put(item, shippingBinStorage.getOrDefault(item, 0) + amount);
        System.out.println("Added " + amount + "x " + item.getItemName() + " to the shipping bin.");
        return true;
    }

    public int sellShippingBin(Time time, Player player) {
        if (cooldown(time)) {
            System.out.println("You can only sell once per day.");
            return 0;
        }
        int totalValue = 0;
        for (Item item : shippingBinStorage.keySet()) {
            totalValue += item.getSellPrice().getGold() * shippingBinStorage.get(item);
        }
        player.getPlayerGold().addGold(totalValue);
        shippingBinStorage.clear();
        lastSoldDay = time.getDay();
        System.out.println("Items sold! You earned: " + totalValue + " gold.");
        return totalValue;
    }

    public boolean cooldown(Time time){
        return lastSoldDay == time.getDay();
    }

    public void printShippingBin(){
        System.out.println("======= SHIPPING BIN ITEMS =======");
        System.out.println("---------------------------------");
        for (Item items : shippingBinStorage.keySet()) {
            System.out.println(items.getItemName() + ": " + shippingBinStorage.get(items));
        }
        System.out.println("---------------------------------");
        if (shippingBinStorage.isEmpty()) {
            System.out.println("Shipping bin is empty.");
        }
    }
}