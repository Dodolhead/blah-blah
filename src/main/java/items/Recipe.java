package items;

import java.util.*;

public class Recipe extends Misc {
    private String recipeID;
    private Map<String, Integer> ingredients;
    private Food foodProduct;
    private Gold buyPrice;

    public Recipe(String itemName, String itemDescription, String recipeID, Map<String, Integer> ingredients, Food foodProduct, Gold buyPrice) {
        super(itemName, itemDescription, ItemManager.load("/items/misc/recipe.png"));
        this.recipeID = recipeID;
        this.ingredients = ingredients;
        this.foodProduct = foodProduct;
        this.buyPrice = buyPrice;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public Food getFoodProduct() {
        return foodProduct;
    }
    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public void addIngredient(String itemKeyword, int quantity) {
        ingredients.put(itemKeyword, quantity);
    }

    public boolean hasSufficientIngredients(Inventory inventory) {
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            String keyword = entry.getKey();
            int requiredAmount = entry.getValue();

            boolean found = false;
            if (keyword.equalsIgnoreCase("Any Fish")) {
                // Hitung semua stok Fish
                int totalFish = 0;
                for (Class<?> cls : Inventory.typeToClassMap.values()) {
                    if (Fish.class.isAssignableFrom(cls)) {
                        Map<Item, Integer> map = inventory.getInventoryStorage().get(cls);
                        if (map != null) {
                            for (Map.Entry<Item, Integer> fishEntry : map.entrySet()) {
                                if (fishEntry.getKey() instanceof Fish) {
                                    totalFish += fishEntry.getValue();
                                }
                            }
                        }
                    }
                }
                if (totalFish >= requiredAmount) {
                    found = true;
                }
            } else {
                // Cara lama: cari item by keyword di class manapun
                for (Class<?> cls : Inventory.typeToClassMap.values()) {
                    if (inventory.hasItemOfType(cls, keyword)) {
                        if (inventory.hasItemTypeWithAmount(cls, requiredAmount)) {
                            found = true;
                            break;
                        }
                    }
                }
            }
            if (!found) return false;
        }
        return true;
    }

    public Gold getBuyPrice() {
        return buyPrice;
    }


}
