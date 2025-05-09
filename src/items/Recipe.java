package src.items;

import java.util.*;

public class Recipe extends Misc {
    private String recipeID;
    private Map<String, Integer> ingredients;
    private Food foodProduct;

    public Recipe(String itemName, String itemDescription, String recipeID, Map<String, Integer> ingredients, Food foodProduct) {
        super(itemName, itemDescription);
        this.recipeID = recipeID;
        this.ingredients = ingredients;
        this.foodProduct = foodProduct;
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
            for (Class<?> cls : Inventory.typeToClassMap.values()) {
                if (inventory.hasItemOfType(cls, keyword)) {
                    if (inventory.hasItemTypeWithAmount(cls, requiredAmount)) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) return false;
        }
        return true;
    }


}
