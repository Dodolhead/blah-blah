package map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.NPCManager;
import gui.GamePanel;
import items.Crop;
import items.Gold;
import items.Item;
import items.ItemManager;
import items.Misc;
import items.Seed;

public class Store extends NPCHome {
    GamePanel gp;
    private Map<Class<?>, Map<Item, Integer>> soldItem;
    public static final Map<String, Class<?>> typeToClassMap = Map.of(
        "Seed", Seed.class,
        "Crop", Crop.class,
        "Misc", Misc.class
    );
    final int storeWidth = 10;
    final int storeHeight = 10;
    private static final char[][] DEFAULT_STORE_DISPLAY = {
        {']',']',']',']',']',']',']',']',']',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']',']',']',']','`','`',']',']',']',']'}
    };

    private char[][] storeDisplay;
    
    public Store(GamePanel gp) {
        super("Store", NPCManager.getNPCByName("Emily"));
        soldItem = new HashMap<>();
        for (Class<?> itemClass : typeToClassMap.values()) {
            soldItem.put(itemClass, new HashMap<>());
        }
        storeDisplay = DEFAULT_STORE_DISPLAY;
    }

    public Map<Class<?>, Map<Item, Integer>> getSoldItem() {
        return soldItem;
    }

    public void seeSoldItems() {
        boolean hasAnyItem = soldItem.values().stream().anyMatch(map -> !map.isEmpty());
        if (!hasAnyItem) {
            System.out.println("No items in inventory.");
            return;
        }
        System.out.println("=========== SOLD ITEMS ===========");
        for (Class<?> cls : typeToClassMap.values()) {
            Map<Item, Integer> map = soldItem.get(cls);
            if (map != null && !map.isEmpty()) {
                System.out.println("Items of type: " + cls.getSimpleName());
                for (Map.Entry<Item, Integer> entry : map.entrySet()) {
                    System.out.println("  " + entry.getKey().getItemName() + ": " + entry.getValue());
                }
                System.out.println("---------------------------------");
            }
        }
    }

    public void addItemToStore(Item item, int amount) {
        Class<?> cls = typeToClassMap.get(item.getItemType());
        if (cls == null) return;
        Map<Item, Integer> storage = soldItem.get(cls);
        storage.put(item, amount);
    }


    public void fillStore() {
        // Bersihkan dulu
        soldItem = new HashMap<>();
        for (Class<?> itemClass : typeToClassMap.values()) {
            soldItem.put(itemClass, new HashMap<>());
        }

        // ========== Seed List ==========
        List<Seed> seeds = List.of(
            new Seed("Parsnip Seeds", new Gold(20), 1, "SPRING", ItemManager.load("/items/seeds/parsnip-seeds.png")),
            new Seed("Cauliflower Seeds", new Gold(80), 5, "SPRING", ItemManager.load("/items/seeds/cauli-seeds.png")),
            new Seed("Potato Seeds", new Gold(50), 3, "SPRING", ItemManager.load("/items/seeds/potato-seeds.png")),
            new Seed("Wheat Seeds", new Gold(60), 1, "SPRING", ItemManager.load("/items/seeds/wheat-seeds.png")),
            new Seed("Blueberry Seeds", new Gold(80), 7, "SUMMER", ItemManager.load("/items/seeds/blueberry-seeds.png")),
            new Seed("Tomato Seeds", new Gold(50), 3, "SUMMER", ItemManager.load("/items/seeds/tomato-seeds.png")),
            new Seed("Hot Pepper Seeds", new Gold(40), 1, "SUMMER", ItemManager.load("/items/seeds/hotpepper-seeds.png")),
            new Seed("Melon Seeds", new Gold(80), 4, "SUMMER", ItemManager.load("/items/seeds/melon-seeds.png")),
            new Seed("Cranberry Seeds", new Gold(100), 2, "FALL", ItemManager.load("/items/seeds/cranberry-seeds.png")),
            new Seed("Pumpkin Seeds", new Gold(150), 7, "FALL", ItemManager.load("/items/seeds/pumpkin-seeds.png")),
            new Seed("Grape Seeds", new Gold(60), 3, "FALL", ItemManager.load("/items/seeds/grape-seeds.png"))
        );

        for (Seed seed : seeds) {
            addItemToStore(seed, 10); // stok default 10
        }

        // ========== Crop List ==========
        List<Crop> crops = List.of(
            new Crop("Parsnip", new Gold(50), new Gold(35), 1, ItemManager.load("/items/crops/parsnip.png")),
            new Crop("Cauliflower", new Gold(200), new Gold(150), 1, ItemManager.load("/items/crops/cauli.png")),
            new Crop("Potato", new Gold(0), new Gold(80), 1, ItemManager.load("/items/crops/potato.png")),
            new Crop("Wheat", new Gold(50), new Gold(30), 3, ItemManager.load("/items/crops/wheat.png")),
            new Crop("Blueberry", new Gold(150), new Gold(40), 3, ItemManager.load("/items/crops/blueberry.png")),
            new Crop("Tomato", new Gold(90), new Gold(60), 1, ItemManager.load("/items/crops/tomato.png")),
            new Crop("Hot Pepper", new Gold(0), new Gold(40), 1, ItemManager.load("/items/crops/hotpepper.png")),
            new Crop("Melon", new Gold(0), new Gold(250), 1, ItemManager.load("/items/crops/melon.png")),
            new Crop("Cranberry", new Gold(0), new Gold(25), 10, ItemManager.load("/items/crops/cranberry.png")),
            new Crop("Pumpkin", new Gold(300), new Gold(250), 1, ItemManager.load("/items/crops/pumpkin.png")),
            new Crop("Grape", new Gold(100), new Gold(10), 20, ItemManager.load("/items/crops/grape.png"))
        );

        for (Crop crop : crops) {
            addItemToStore(crop, 10); // stok default 10
        }
    }

    public void refillStock() {
        for (Map<Item, Integer> itemMap : soldItem.values()) {
            for (Item item : itemMap.keySet()) {
                itemMap.put(item, 10); // refill semua item ke 10
            }
        }
    }


    // public void storeChange() {
    //     soldItem = new HashMap<>();
    //     for (Class<?> itemClass : typeToClassMap.values()) {
    //         soldItem.put(itemClass, new HashMap<>());
    //     }

    //     long currentTime = System.currentTimeMillis();

    //     // ========== Seed List ==========
    //     List<Seed> seeds = List.of(
    //         new Seed("Parsnip Seeds", new Gold(20), 1, "SPRING", ItemManager.load("/items/seeds/parsnip-seeds.png")),
    //         new Seed("Cauliflower Seeds", new Gold(80), 5, "SPRING", ItemManager.load("/items/seeds/cauli-seeds.png")),
    //         new Seed("Potato Seeds", new Gold(50), 3, "SPRING", ItemManager.load("/items/seeds/potato-seeds.png")),
    //         new Seed("Wheat Seeds", new Gold(60), 1, "SPRING", ItemManager.load("/items/seeds/wheat-seeds.png")),
    //         new Seed("Blueberry Seeds", new Gold(80), 7, "SUMMER", ItemManager.load("/items/seeds/blueberry-seeds.png")),
    //         new Seed("Tomato Seeds", new Gold(50), 3, "SUMMER", ItemManager.load("/items/seeds/tomato-seeds.png")),
    //         new Seed("Hot Pepper Seeds", new Gold(40), 1, "SUMMER", ItemManager.load("/items/seeds/hotpepper-seeds.png")),
    //         new Seed("Melon Seeds", new Gold(80), 4, "SUMMER", ItemManager.load("/items/seeds/melon-seeds.png")),
    //         new Seed("Cranberry Seeds", new Gold(100), 2, "FALL", ItemManager.load("/items/seeds/cranberry-seeds.png")),
    //         new Seed("Pumpkin Seeds", new Gold(150), 7, "FALL", ItemManager.load("/items/seeds/pumpkin-seeds.png")),
    //         new Seed("Grape Seeds", new Gold(60), 3, "FALL", ItemManager.load("/items/seeds/grape-seeds.png"))
    //     );

    //     // ========== Crop List ==========
    //     List<Crop> crops = List.of(
    //         new Crop("Parsnip", new Gold(50), new Gold(35), 1, ItemManager.load("/items/crops/parsnip.png")),
    //         new Crop("Cauliflower", new Gold(200), new Gold(150), 1, ItemManager.load("/items/crops/cauli.png")),
    //         new Crop("Potato", new Gold(0), new Gold(80), 1, ItemManager.load("/items/crops/potato.png")),
    //         new Crop("Wheat", new Gold(50), new Gold(30), 3, ItemManager.load("/items/crops/wheat.png")),
    //         new Crop("Blueberry", new Gold(150), new Gold(40), 3, ItemManager.load("/items/crops/blueberry.png")),
    //         new Crop("Tomato", new Gold(90), new Gold(60), 1, ItemManager.load("/items/crops/tomato.png")),
    //         new Crop("Hot Pepper", new Gold(0), new Gold(40), 1, ItemManager.load("/items/crops/hotpepper.png")),
    //         new Crop("Melon", new Gold(0), new Gold(250), 1, ItemManager.load("/items/crops/melon.png")),
    //         new Crop("Cranberry", new Gold(0), new Gold(25), 10, ItemManager.load("/items/crops/cranberry.png")),
    //         new Crop("Pumpkin", new Gold(300), new Gold(250), 1, ItemManager.load("/items/crops/pumpkin.png")),
    //         new Crop("Grape", new Gold(100), new Gold(10), 20, ItemManager.load("/items/crops/grape.png"))
    //     );
    //     // ========== Deterministic Selection ==========
    //     for (int i = 0; i < 3; i++) {
    //         int index = (int)((currentTime + i) % seeds.size());
    //         int amount = (int)((currentTime / (i + 1)) % 10) + 1;
    //         addItemToStore(seeds.get(index), amount);
    //     }

    //     for (int i = 0; i < 3; i++) {
    //         int index = (int)((currentTime + i + 1000) % crops.size());
    //         int amount = (int)((currentTime / (i + 2)) % 10) + 1;
    //         addItemToStore(crops.get(index), amount);
    //     }

    //     // ========== Recipe Deterministic Pick ==========
    //     Food fishNChipsFood = new Food("Fish and Chips", 50, new Gold(150), new Gold(135), ItemManager.load("/items/seeds/parsnip-seeds.png"));
    //     Map<String, Integer> ingredients1 = Map.of(
    //         "Any Fish", 2,
    //         "Wheat", 1,
    //         "Potato", 1
    //     );
    //     Recipe recipe1 = new Recipe("Fish and Chips", "A crispy and tasty fish snack", "recipe_1", ingredients1, fishNChipsFood, new Gold(50) ,ItemManager.load("/items/seeds/parsnip-seeds.png"));

    //     Food fishSandwichFood = new Food("Fish Sandwich", 50, new Gold(200), new Gold(180), ItemManager.load("/items/seeds/parsnip-seeds.png"));
    //     Map<String, Integer> ingredients10 = Map.of(
    //         "Any Fish", 1,
    //         "Wheat", 2,
    //         "Tomato", 1,
    //         "Hot Pepper", 1
    //     );
    //     Recipe recipe10 = new Recipe("Fish Sandwich", "A spicy fish sandwich", "recipe_10", ingredients10, fishSandwichFood, new Gold(50),ItemManager.load("/items/seeds/parsnip-seeds.png"));

    //     boolean pickFirst = currentTime % 2 == 0;
    //     Recipe chosenRecipe = pickFirst ? recipe1 : recipe10;
    //     addItemToStore(chosenRecipe, 1);
    // }

    public char[][] getStoreDisplay() {
        return storeDisplay;
    }

    public int getStock(Item item) {
        Class<?> cls = typeToClassMap.get(item.getItemType());
        if (cls == null) return 0;

        Map<Item, Integer> itemMap = soldItem.get(cls);
        if (itemMap != null && itemMap.containsKey(item)) {
            return itemMap.get(item);
        }
        return 0;
    }

    public boolean decreaseStock(Item item, int amount) {
        Class<?> cls = typeToClassMap.get(item.getItemType());
        if (cls == null) return false;

        Map<Item, Integer> itemMap = soldItem.get(cls);
        if (itemMap != null && itemMap.containsKey(item)) {
            int current = itemMap.get(item);
            if (current >= amount) {
                itemMap.put(item, current - amount);
                return true;
            }
        }
        return false;
    }

    
}
