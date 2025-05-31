package items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Inventory {
    private Map<Class<?>, Map<Item, Integer>> inventoryStorage;
    public static final Map<String, Class<?>> typeToClassMap = Map.of(
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

    public boolean removeItemByName(String itemName, int amount) {
        for (Map.Entry<Class<?>, Map<Item, Integer>> entry : inventoryStorage.entrySet()) {
            Map<Item, Integer> map = entry.getValue();
            for (Item item : new HashSet<>(map.keySet())) {
                if (item.getItemName().equalsIgnoreCase(itemName)) {
                    int current = map.get(item);
                    if (current <= amount) {
                        map.remove(item);
                    } else {
                        map.put(item, current - amount);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public int getItemAmountByName(String itemName) {
        for (Map<Item, Integer> map : inventoryStorage.values()) {
            for (Map.Entry<Item, Integer> entry : map.entrySet()) {
                if (entry.getKey().getItemName().equalsIgnoreCase(itemName)) {
                    return entry.getValue();
                }
            }
        }
        return 0;
    }

    public int getItemAmount(Item item) {
        Class<?> cls = typeToClassMap.get(item.getItemType());
        Map<Item, Integer> storage = inventoryStorage.get(cls);
        if (storage == null) return 0;
        return storage.getOrDefault(item, 0);
    }

    public boolean addItem(Item item, int amount) {
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return false;
        }

        boolean isNewItem = getItemAmount(item) == 0;
        if (isNewItem && getTotalItemTypes() >= 20) {
            System.out.println("Inventory is full! You cannot add more item types.");
            return false;
        }

        Class<?> cls = typeToClassMap.get(item.getItemType());
        if (cls == null) return false;

        Map<Item, Integer> storage = inventoryStorage.get(cls);
        storage.put(item, getItemAmount(item) + amount);
        return true;
    }


    public int getTotalItemTypes() {
        int totalTypes = 0;
        for (Map<Item, Integer> storage : inventoryStorage.values()) {
            totalTypes += storage.size();  // setiap item di map ini adalah 1 jenis item
        }
        return totalTypes;
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
            for (Item items : map.keySet()) {
                if (items.getItemName().equalsIgnoreCase(itemName)) return true;
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

    public boolean hasItemTypeWithAmount(Class<?> subclass, int amount) {
        int total = 0;
        for (Map<Item, Integer> map : inventoryStorage.values()) {
            for (Map.Entry<Item, Integer> entry : map.entrySet()) {
                Item item = entry.getKey();
                if (subclass.isInstance(item)) {
                    total += entry.getValue();
                    if (total >= amount) return true;
                }
            }
        }
        return false;
    }

    public Item getFirstItem() {
        for (Map<Item, Integer> map : inventoryStorage.values()) {
            for (Item item : map.keySet()) {
                return item;
            }
        }
        return null;
    }

}
