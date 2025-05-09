package src.actions;

import src.entities.*;
import src.items.*;

public class CookingAction implements Action {
    private Recipe recipe;
    private int ENERGY_COST = 10;

    public CookingAction(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public boolean execute(Player player){
        if (!player.getPlayerInventory().hasItem(recipe.getItemName())){
            System.out.println("You don't have the recipe to cook " + recipe.getItemName());
            return false;
        }

        if (player.getPlayerLocation().getName() != "House"){
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

        player.subtractPlayerEnergy(ENERGY_COST);
        player.getPlayerInventory().addItem(recipe.getFoodProduct(), 1);
        System.out.println("Successfully cooked " + recipe.getItemName() + "!");
        
        return true;
    }


}