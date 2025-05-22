package src.map;

import java.util.*;

import src.entities.*;
import src.items.*;

public class Store extends NPCHome {
    private Map<Class<?>, Map<Item, Integer>> soldItem;
    public static final Map<String, Class<?>> typeToClassMap = Map.of(
        "Seed", Seed.class,
        "Crop", Crop.class,
        "Misc", Misc.class
    );
    
    public Store(){
        super("Store", new NPC("Emily", "single"));
        soldItem = new HashMap<>();
        for (Class<?> itemClass : typeToClassMap.values()) {
            soldItem.put(itemClass, new HashMap<>());
        }
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

    public void storeChange() {
        soldItem = new HashMap<>();
        for (Class<?> itemClass : typeToClassMap.values()) {
            soldItem.put(itemClass, new HashMap<>());
        }

        long currentTime = System.currentTimeMillis();

        // ========== Seed List ==========
        List<Seed> seeds = List.of(
            new Seed("Parsnip Seeds", new Gold(20), 1, "SPRING"),
            new Seed("Cauliflower Seeds", new Gold(80), 5, "SPRING"),
            new Seed("Potato Seeds", new Gold(50), 3, "SPRING"),
            new Seed("Wheat Seeds", new Gold(60), 1, "SPRING"),
            new Seed("Blueberry Seeds", new Gold(80), 7, "SUMMER"),
            new Seed("Tomato Seeds", new Gold(50), 3, "SUMMER"),
            new Seed("Hot Pepper Seeds", new Gold(40), 1, "SUMMER"),
            new Seed("Melon Seeds", new Gold(80), 4, "SUMMER"),
            new Seed("Cranberry Seeds", new Gold(100), 2, "FALL"),
            new Seed("Pumpkin Seeds", new Gold(150), 7, "FALL"),
            new Seed("Grape Seeds", new Gold(60), 3, "FALL")
        );

        // ========== Crop List ==========
        List<Crop> crops = List.of(
            new Crop("Parsnip", new Gold(50), new Gold(35), 1),
            new Crop("Cauliflower", new Gold(200), new Gold(150), 1),
            new Crop("Potato", new Gold(0), new Gold(80), 1),
            new Crop("Wheat", new Gold(50), new Gold(30), 3),
            new Crop("Blueberry", new Gold(150), new Gold(40), 3),
            new Crop("Tomato", new Gold(90), new Gold(60), 1),
            new Crop("Hot Pepper", new Gold(0), new Gold(40), 1),
            new Crop("Melon", new Gold(0), new Gold(250), 1),
            new Crop("Cranberry", new Gold(0), new Gold(25), 10),
            new Crop("Pumpkin", new Gold(300), new Gold(250), 1),
            new Crop("Grape", new Gold(100), new Gold(10), 20)
        );

        // ========== Deterministic Selection ==========
        for (int i = 0; i < 3; i++) {
            int index = (int)((currentTime + i) % seeds.size());
            int amount = (int)((currentTime / (i + 1)) % 10) + 1;
            addItemToStore(seeds.get(index), amount);
        }

        for (int i = 0; i < 3; i++) {
            int index = (int)((currentTime + i + 1000) % crops.size());
            int amount = (int)((currentTime / (i + 2)) % 10) + 1;
            addItemToStore(crops.get(index), amount);
        }

        // ========== Recipe Deterministic Pick ==========
        Food fishNChipsFood = new Food("Fish n’ Chips", 50, new Gold(150), new Gold(135));
        Map<String, Integer> ingredients1 = Map.of(
            "Any Fish", 2,
            "Wheat", 1,
            "Potato", 1
        );
        Recipe recipe1 = new Recipe("Fish n’ Chips", "A crispy and tasty fish snack", "recipe_1", ingredients1, fishNChipsFood);

        Food fishSandwichFood = new Food("Fish Sandwich", 50, new Gold(200), new Gold(180));
        Map<String, Integer> ingredients10 = Map.of(
            "Any Fish", 1,
            "Wheat", 2,
            "Tomato", 1,
            "Hot Pepper", 1
        );
        Recipe recipe10 = new Recipe("Fish Sandwich", "A spicy fish sandwich", "recipe_10", ingredients10, fishSandwichFood);

        boolean pickFirst = currentTime % 2 == 0;
        Recipe chosenRecipe = pickFirst ? recipe1 : recipe10;
        addItemToStore(chosenRecipe, 1);
    }
}
