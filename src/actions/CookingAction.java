package src.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.entities.*;
import src.items.*;
import src.tsw.*;

public class CookingAction implements Action {
    private Time gameTime;
    private String recipeId;
    private Item fuelItem;
    private int fuelAmount;
    
    // Peta untuk menyimpan resep dan bahan-bahannya
    private static final Map<String, Recipe> recipeMap = new HashMap<>();
    
    static {
        // Inisialisasi resep-resep berdasarkan spesifikasi
        
        // Recipe 1: Fish n' Chips
        List<RecipeIngredient> fishNChipsIngredients = new ArrayList<>();
        fishNChipsIngredients.add(new RecipeIngredient("Fish", 2)); // Any Fish x2
        fishNChipsIngredients.add(new RecipeIngredient("Wheat", 1));
        fishNChipsIngredients.add(new RecipeIngredient("Potato", 1));
        recipeMap.put("recipe_1", new Recipe("recipe_1", "Fish n' Chips", fishNChipsIngredients, 50));
        
        // Recipe 2: Baguette
        List<RecipeIngredient> baguetteIngredients = new ArrayList<>();
        baguetteIngredients.add(new RecipeIngredient("Wheat", 3));
        recipeMap.put("recipe_2", new Recipe("recipe_2", "Baguette", baguetteIngredients, 25));
        
        // Recipe 3: Sashimi
        List<RecipeIngredient> sashimiIngredients = new ArrayList<>();
        sashimiIngredients.add(new RecipeIngredient("Salmon", 3));
        recipeMap.put("recipe_3", new Recipe("recipe_3", "Sashimi", sashimiIngredients, 70));
        
        // Recipe 4: Fugu
        List<RecipeIngredient> fuguIngredients = new ArrayList<>();
        fuguIngredients.add(new RecipeIngredient("Pufferfish", 1));
        recipeMap.put("recipe_4", new Recipe("recipe_4", "Fugu", fuguIngredients, 50));
        
        // Recipe 5: Wine
        List<RecipeIngredient> wineIngredients = new ArrayList<>();
        wineIngredients.add(new RecipeIngredient("Grape", 2));
        recipeMap.put("recipe_5", new Recipe("recipe_5", "Wine", wineIngredients, 20));
    }
    
    // Inner class untuk bahan resep
    public static class RecipeIngredient {
        private String itemName;
        private int amount;
        
        public RecipeIngredient(String itemName, int amount) {
            this.itemName = itemName;
            this.amount = amount;
        }
        
        public String getItemName() {
            return itemName;
        }
        
        public int getAmount() {
            return amount;
        }
    }
    
    // Inner class untuk resep
    public static class Recipe {
        private String recipeId;
        private String recipeName;
        private List<RecipeIngredient> ingredients;
        private int energyRestored;
        
        public Recipe(String recipeId, String recipeName, List<RecipeIngredient> ingredients, int energyRestored) {
            this.recipeId = recipeId;
            this.recipeName = recipeName;
            this.ingredients = ingredients;
            this.energyRestored = energyRestored;
        }
        
        public String getRecipeId() {
            return recipeId;
        }
        
        public String getRecipeName() {
            return recipeName;
        }
        
        public List<RecipeIngredient> getIngredients() {
            return ingredients;
        }
        
        public int getEnergyRestored() {
            return energyRestored;
        }
    }
    
    public CookingAction(Time gameTime, String recipeId, Item fuelItem) {
        this.gameTime = gameTime;
        this.recipeId = recipeId;
        this.fuelItem = fuelItem;
        
        // Set default fuel amount berdasarkan jenis bahan bakar
        if (fuelItem != null) {
            if (fuelItem.getItemName().equals("Firewood")) {
                this.fuelAmount = 1; // 1 firewood untuk 1 makanan
            } else if (fuelItem.getItemName().equals("Coal")) {
                this.fuelAmount = 2; // 1 coal untuk 2 makanan
            } else {
                this.fuelAmount = 0;
            }
        }
    }
    
    // Mengambil resep berdasarkan ID
    public static Recipe getRecipe(String recipeId) {
        return recipeMap.get(recipeId);
    }
    
    // Mengambil semua resep yang tersedia
    public static Map<String, Recipe> getAllRecipes() {
        return recipeMap;
    }

    @Override
    public boolean execute(Player player) {
        // Periksa apakah player berada di dalam rumah (House)
        // Dalam implementasi sebenarnya, perlu ditambahkan validasi lokasi
        
        // Periksa apakah resep ada
        Recipe recipe = recipeMap.get(recipeId);
        if (recipe == null) {
            System.out.println("Resep tidak ditemukan!");
            return false;
        }
        
        // Periksa apakah player memiliki bahan bakar
        if (fuelItem == null || fuelAmount <= 0) {
            System.out.println("Bahan bakar tidak cukup!");
            return false;
        }
        
        // Periksa apakah player memiliki semua bahan yang diperlukan
        for (RecipeIngredient ingredient : recipe.getIngredients()) {
            boolean hasIngredient = false;
            
            // Cek untuk setiap jenis item
            for (Class<?> kelas : player.getPlayerInventory().getInventoryStorage().keySet()) {
                for (String itemName : player.getPlayerInventory().getInventoryStorage().get(kelas).keySet()) {
                    if (itemName.contains(ingredient.getItemName())) {
                        int amount = player.getPlayerInventory().getInventoryStorage().get(kelas).get(itemName);
                        if (amount >= ingredient.getAmount()) {
                            hasIngredient = true;
                            break;
                        }
                    }
                }
                if (hasIngredient) break;
            }
            
            if (!hasIngredient) {
                System.out.println("Bahan " + ingredient.getItemName() + " tidak cukup!");
                return false;
            }
        }
        
        // Periksa apakah player memiliki cukup energi
        if (player.getEnergy() < 10) {
            System.out.println("Energi tidak cukup untuk memasak!");
            return false;
        }
        
        // Mulai proses memasak
        System.out.println("Memulai memasak " + recipe.getRecipeName() + "...");
        
        // Kurangi energi pemain
        player.subtractPlayerEnergy(10);
        
        // Kurangi bahan-bahan dari inventory
        for (RecipeIngredient ingredient : recipe.getIngredients()) {
            // Cari item berdasarkan nama
            for (Class<?> kelas : player.getPlayerInventory().getInventoryStorage().keySet()) {
                boolean found = false;
                for (String itemName : player.getPlayerInventory().getInventoryStorage().get(kelas).keySet()) {
                    if (itemName.contains(ingredient.getItemName())) {
                        // Temukan item yang cocok
                        for (Item item : getAllItemsInInventory(player)) {
                            if (item.getItemName().equals(itemName)) {
                                player.getPlayerInventory().removeItem(item, ingredient.getAmount());
                                found = true;
                                break;
                            }
                        }
                        if (found) break;
                    }
                }
                if (found) break;
            }
        }
        
        // Kurangi bahan bakar
        player.getPlayerInventory().removeItem(fuelItem, 1);
        
        // Set waktu memasak (1 jam = 60 menit)
        gameTime.pauseTime();
        
        // Proses memasak bersifat pasif, jadi player bisa melakukan hal lain
        // Dalam implementasi nyata, gunakan Thread atau Timer
        System.out.println("Memasak sedang berlangsung... (1 jam)");
        
        try {
            Thread.sleep(1000); // Simulasi waktu memasak (1 detik)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Tambahkan makanan hasil masakan ke inventory
        Food cookedFood = createFoodFromRecipe(recipe);
        player.getPlayerInventory().addItem(cookedFood, 1);
        
        // Lanjutkan waktu dan tambahkan 1 jam
        gameTime.resumeTime();
        gameTime.skipTimeMinute(60);
        
        System.out.println("Memasak " + recipe.getRecipeName() + " berhasil!");
        System.out.println("Makanan telah ditambahkan ke inventory.");
        
        return true;
    }
    
    // Helper method untuk mendapatkan semua item di inventory
    private List<Item> getAllItemsInInventory(Player player) {
        List<Item> items = new ArrayList<>();
        // Implementasi untuk mendapatkan semua item
        // Dalam implementasi sebenarnya, gunakan getItem dari inventory
        return items;
    }
    
    // Helper method untuk membuat objek Food dari resep
    private Food createFoodFromRecipe(Recipe recipe) {
        // Harga dan parameter lainnya bisa disesuaikan
        Gold sellPrice = new Gold(0);
        Gold buyPrice = new Gold(0);
        
        if (recipe.getRecipeName().equals("Fish n' Chips")) {
            sellPrice = new Gold(135);
            buyPrice = new Gold(150);
        } else if (recipe.getRecipeName().equals("Baguette")) {
            sellPrice = new Gold(80);
            buyPrice = new Gold(100);
        } else if (recipe.getRecipeName().equals("Sashimi")) {
            sellPrice = new Gold(275);
            buyPrice = new Gold(300);
        } else if (recipe.getRecipeName().equals("Fugu")) {
            sellPrice = new Gold(135);
            buyPrice = new Gold(0);
        } else if (recipe.getRecipeName().equals("Wine")) {
            sellPrice = new Gold(90);
            buyPrice = new Gold(100);
        }
        
        return new Food(recipe.getRecipeName(), recipe.getEnergyRestored(), buyPrice, sellPrice);
    }
}