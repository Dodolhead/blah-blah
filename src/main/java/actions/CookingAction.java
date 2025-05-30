package actions;


import java.util.HashSet;
import java.util.Map;
import tsw.*;
import entities.*;
import items.*;

public class CookingAction implements Action {
    private Recipe recipe;
    private int ENERGY_COST = 10;
    Stove stove;

    public CookingAction(Recipe recipe, Stove stove) {
        this.recipe = recipe;
        this.stove = stove;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public boolean execute(Player player){
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        Time gameTime = farm.getTime();

        if (recipe == null) {
            System.out.println("Please select a Recipe.");
            return false;
        }
        if (!player.getPlayerInventory().hasItem(recipe.getItemName())){
            System.out.println("You don't have the recipe to cook " + recipe.getItemName());
            return false;
        }

        if (!player.getPlayerLocation().getName().equals("House")){
            System.out.println("You need to be in the house to cook.");
            return false;
        }

        if (player.getEnergy() < ENERGY_COST) {
            System.out.println("You don't have enough energy to do this action.");
            return false;
        }

        if (!recipe.hasSufficientIngredients(player.getPlayerInventory())) {
            System.out.println("You don't have enough ingredients to cook " + recipe.getItemName());
            return false;
        }

        for (Map.Entry<String, Integer> entry : recipe.getIngredients().entrySet()) {
            String keyword = entry.getKey();
            int amount = entry.getValue();

            boolean removed = false;

            for (Class<?> cls : Inventory.typeToClassMap.values()) {
                Map<Item, Integer> map = player.getPlayerInventory().getInventoryStorage().get(cls);
                if (map != null) {
                    for (Item item : new HashSet<>(map.keySet())) {

                        boolean matches = false;

                        if (keyword.equalsIgnoreCase("Any Fish")) {
                            matches = item instanceof Fish;
                        } else {
                            matches = item.getItemName().toLowerCase().contains(keyword.toLowerCase());
                        }

                        if (matches) {
                            if (player.getPlayerInventory().getItemAmount(item) >= amount) {
                                player.getPlayerInventory().removeItem(item, amount);
                                removed = true;
                                break;
                            }
                        }
                    }
                }
                if (removed) break;
            }

            if (!removed) {
                System.out.println("Failed to remove ingredient: " + keyword);
                return false; // stop execution if any ingredient removal failed
            }
        }

        System.out.println("Cooking " + recipe.getItemName() + "...");

        startCooking(player, recipe, gameTime);
        return true;
    }

    public void startCooking(Player player, Recipe recipe, Time time) {
        player.subtractPlayerEnergy(ENERGY_COST);
        System.out.println("Cooking started at " + time.getCurrentTime());

        new Thread(() -> {
            try {
                Thread.sleep(12_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

            System.out.println("Your food is ready at " + time.getCurrentTime());
            
            player.getPlayerInventory().addItem(recipe.getFoodProduct(), 1);
            stove.finishCooking();
        }).start();
    }

}