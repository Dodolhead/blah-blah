package items;

import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ItemManager {
    private static final Map<String, Item> itemMap = new HashMap<>();

    public static void setItems(){
        // === Seed & Crop ===
        add(new Seed("Parsnip Seeds", new Gold(20), 1, "SPRING", load("/items/seeds/parsnip-seeds.png")));
        add(new Crop("Parsnip", new Gold(50), new Gold(35), 1, load("/items/crops/parsnip.png")));

        add(new Seed("Cauliflower Seeds", new Gold(80), 5, "SPRING", load("/items/seeds/cauli-seeds.png")));
        add(new Crop("Cauliflower", new Gold(200), new Gold(150), 1, load("/items/crops/cauli.png")));

        add(new Seed("Potato Seeds", new Gold(50), 3, "SPRING", load("/items/seeds/potato-seeds.png")));
        add(new Crop("Potato", new Gold(0), new Gold(80), 1, load("/items/crops/potato.png")));

        add(new Seed("Wheat Seeds", new Gold(60), 1, "SPRING", load("/items/seeds/wheat-seeds.png")));
        add(new Crop("Wheat", new Gold(50), new Gold(30), 3, load("/items/crops/wheat.png")));

        add(new Seed("Blueberry Seeds", new Gold(80), 7, "SUMMER", load("/items/seeds/blueberry-seeds.png")));
        add(new Crop("Blueberry", new Gold(150), new Gold(40), 3, load("/items/crops/blueberry.png")));

        add(new Seed("Tomato Seeds", new Gold(50), 3, "SUMMER", load("/items/seeds/tomato-seeds.png")));
        add(new Crop("Tomato", new Gold(90), new Gold(60), 1, load("/items/crops/tomato.png")));

        add(new Seed("Hot Pepper Seeds", new Gold(40), 1, "SUMMER", load("/items/seeds/hotpepper-seeds.png")));
        add(new Crop("Hot Pepper", new Gold(0), new Gold(40), 1, load("/items/crops/hotpepper.png")));

        add(new Seed("Melon Seeds", new Gold(80), 4, "SUMMER", load("/items/seeds/melon-seeds.png")));
        add(new Crop("Melon", new Gold(0), new Gold(250), 1, load("/items/crops/melon.png")));

        add(new Seed("Cranberry Seeds", new Gold(100), 2, "FALL", load("/items/seeds/cranberry-seeds.png")));
        add(new Crop("Cranberry", new Gold(0), new Gold(25), 10, load("/items/crops/cranberry.png")));

        add(new Seed("Pumpkin Seeds", new Gold(150), 7, "FALL", load("/items/seeds/pumpkin-seeds.png")));
        add(new Crop("Pumpkin", new Gold(300), new Gold(250), 1, load("/items/crops/pumpkin.png")));

        add(new Seed("Wheat Seeds", new Gold(60), 1, "FALL", load("/items/seeds/wheat-seeds.png"))); // Duplicate name
        add(new Crop("Wheat", new Gold(50), new Gold(30), 3, load("/items/crops/wheat.png"))); // Same

        add(new Seed("Grape Seeds", new Gold(60), 3, "FALL", load("/items/seeds/grape-seeds.png")));
        add(new Crop("Grape", new Gold(100), new Gold(10), 20, load("/items/crops/grape.png")));

        // === Fish ===
        add(new Fish("Bullhead", 0, 24, List.of("ANY"), List.of("ANY"), List.of("Mountain Lake"), "common", load("/items/fish/bullhead.png")));
        add(new Fish("Carp", 0, 24, List.of("ANY"), List.of("ANY"), List.of("Mountain Lake", "Pond"), "common", load("/items/fish/carp.png")));
        add(new Fish("Chub", 0, 24, List.of("ANY"), List.of("ANY"), List.of("Forest River", "Mountain Lake"), "common", load("/items/fish/chub.png")));
        add(new Fish("Largemouth Bass", 6, 18, List.of("ANY"), List.of("ANY"), List.of("Mountain Lake"), "regular", load("/items/fish/largemouth-bass.png")));
        add(new Fish("Rainbow Trout", 6, 18, List.of("SUMMER"), List.of("SUNNY"), List.of("Forest River", "Mountain Lake"), "regular", load("/items/fish/rainbow-trout.png")));
        add(new Fish("Sturgeon", 6, 18, List.of("SUMMER", "WINTER"), List.of("ANY"), List.of("Mountain Lake"), "regular", load("/items/fish/sturgeon.png")));
        add(new Fish("Midnight Carp", 20, 2, List.of("WINTER", "FALL"), List.of("ANY"), List.of("Mountain Lake", "Pond"), "regular", load("/items/fish/midnight-carp.png")));
        add(new Fish("Flounder", 6, 22, List.of("SPRING", "SUMMER"), List.of("ANY"), List.of("Ocean"), "regular", load("/items/fish/flounder.png")));
        add(new Fish("Halibut", 6, 11, List.of("ANY"), List.of("ANY"), List.of("Ocean"), "regular", load("/items/fish/halibut.png")));
        add(new Fish("Octopus", 6, 22, List.of("SUMMER"), List.of("ANY"), List.of("Ocean"), "regular", load("/items/fish/octopus.png")));
        add(new Fish("Pufferfish", 0, 16, List.of("SUMMER"), List.of("SUNNY"), List.of("Ocean"), "regular", load("/items/fish/pufferfish.png")));
        add(new Fish("Sardine", 6, 18, List.of("ANY"), List.of("ANY"), List.of("Ocean"), "regular", load("/items/fish/sardine.png")));
        add(new Fish("Super Cucumber", 18, 2, List.of("SUMMER", "FALL", "WINTER"), List.of("ANY"), List.of("Ocean"), "regular", load("/items/fish/super-cucumber.png")));
        add(new Fish("Catfish", 6, 22, List.of("SPRING", "SUMMER", "FALL"), List.of("RAINY"), List.of("Forest River", "Pond"), "regular", load("/items/fish/catfish.png")));
        add(new Fish("Salmon", 6, 18, List.of("FALL"), List.of("ANY"), List.of("Forest River"), "regular", load("/items/fish/salmon.png")));
        add(new Fish("Angler", 8, 20, List.of("FALL"), List.of("ANY"), List.of("Pond"), "legendary", load("/items/fish/angler.png")));
        add(new Fish("Crimsonfish", 8, 20, List.of("SUMMER"), List.of("ANY"), List.of("Ocean"), "legendary", load("/items/fish/crimsonfish.png")));
        add(new Fish("Glacierfish", 8, 20, List.of("WINTER"), List.of("ANY"), List.of("Forest River"), "legendary", load("/items/fish/glacierfish.png")));
        add(new Fish("Legend", 8, 20, List.of("SPRING"), List.of("RAINY"), List.of("Mountain Lake"), "legendary", load("/items/fish/legend.png")));

        // === Tools ===
        add(new Hoe("Hoe", new Gold(0), new Gold(0)));
        add(new Pickaxe("Pickaxe", new Gold(0), new Gold(0)));
        add(new FishingRod("Fishing Rod", new Gold(0), new Gold(0)));
        add(new WateringCan("Watering Can", new Gold(0), new Gold(0)));

        add(new Misc("Wedding Ring", "A wedding ring", load("/items/misc/ring.png")));
    }

    private static void add(Item item) {
        itemMap.put(item.getItemName(), item);
    }

    public static Item getItem(String name) {
        return itemMap.get(name);
    }

    public static Collection<Item> getAllItems() {
        return itemMap.values();
    }

    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(ItemManager.class.getResourceAsStream("/res" + path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
