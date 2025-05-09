package src.items;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Class<?>, Map<Item, Integer>> inventoryStorage;
    private static final Map<String, Class<?>> typeToClassMap = Map.of(
        "Fish", Fish.class,
        "Crop", Crop.class,
        "Seed", Seed.class,
        "Food", Food.class,
        "Equipment", Equipment.class,
        "Misc", Misc.class
    );

    public Inventory() {
        this.inventoryStorage = new HashMap<>();
        for (Class<?> itemClass : typeToClassMap.values()) {
            inventoryStorage.put(itemClass, new HashMap<>());
        }
    }

    public Map<Class<?>, Map<Item, Integer>> getInventoryStorage() {
        return inventoryStorage;
    }

    public int getItemAmount(Item item) {
        Class<?> cls = typeToClassMap.get(item.getItemType());
        Map<Item, Integer> storage = inventoryStorage.get(cls);
        if (storage == null) return 0;
        return storage.getOrDefault(item, 0);
    }

    public boolean addItem(Item item, int amount) {
        Class<?> cls = typeToClassMap.get(item.getItemType());
        if (cls == null) return false;
        Map<Item, Integer> storage = inventoryStorage.get(cls);
        storage.put(item, getItemAmount(item) + amount);
        return true;
    }

    public boolean removeItem(Item item, int amount) {
        Class<?> cls = typeToClassMap.get(item.getItemType());
        if (cls == null) return false;
        Map<Item, Integer> storage = inventoryStorage.get(cls);
        int current = getItemAmount(item);
        if (current == 0) return false;
        if (current <= amount) storage.remove(item);
        else storage.put(item, current - amount);
        return true;
    }

    public void printInventory() {
        boolean hasAnyItem = inventoryStorage.values().stream().anyMatch(map -> !map.isEmpty());
        if (!hasAnyItem) {
            System.out.println("No items in inventory.");
            return;
        }
        System.out.println("======= INVENTORY STORAGE =======");
        for (Class<?> cls : typeToClassMap.values()) {
            Map<Item, Integer> map = inventoryStorage.get(cls);
            if (map != null && !map.isEmpty()) {
                System.out.println("Items of type: " + cls.getSimpleName());
                for (Map.Entry<Item, Integer> entry : map.entrySet()) {
                    System.out.println("  " + entry.getKey().getItemName() + ": " + entry.getValue());
                }
                System.out.println("---------------------------------");
            }
        }
    }

    public boolean hasItem(String itemName) {
        for (Map<Item, Integer> map : inventoryStorage.values()) {
            for (Item item : map.keySet()) {
                if (item.getItemName().equalsIgnoreCase(itemName)) return true;
            }
        }
        return false;
    }

    public boolean hasItemAndAmount(String itemName, int requiredAmount) {
        for (Map<Item, Integer> map : inventoryStorage.values()) {
            for (Map.Entry<Item, Integer> entry : map.entrySet()) {
                if (entry.getKey().getItemName().equalsIgnoreCase(itemName) && entry.getValue() >= requiredAmount) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasItemOfType(Class<?> cls, String keyword) {
        Map<Item, Integer> map = inventoryStorage.getOrDefault(cls, Map.of());
        for (Item item : map.keySet()) {
            if (item.getItemName().toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasItemType(Class<?> subclass) {
        for (Map<Item, Integer> map : inventoryStorage.values()) {
            for (Item item : map.keySet()) {
                if (subclass.isInstance(item)) {
                    return true;
                }
            }
        }
        return false;
    }
}
