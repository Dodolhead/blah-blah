package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import entities.Player;
import items.Recipe;

public class PlayerInfoPanel extends JPanel {
    private Player player;
    private JLabel nameLabel, genderLabel, energyLabel, partnerLabel, favoriteItemLabel, goldLabel;
    private RecipeSlotLabel[] recipeSlots = new RecipeSlotLabel[11];
    // Mapping recipeID ke slot index
    private static final Map<String, Integer> RECIPE_ID_TO_SLOT = new HashMap<>();
    static {
        RECIPE_ID_TO_SLOT.put("recipe_1", 0);
        RECIPE_ID_TO_SLOT.put("recipe_2", 1);
        RECIPE_ID_TO_SLOT.put("recipe_3", 2);
        RECIPE_ID_TO_SLOT.put("recipe_4", 3);
        RECIPE_ID_TO_SLOT.put("recipe_5", 4);
        RECIPE_ID_TO_SLOT.put("recipe_6", 5);
        RECIPE_ID_TO_SLOT.put("recipe_7", 6);
        RECIPE_ID_TO_SLOT.put("recipe_8", 7);
        RECIPE_ID_TO_SLOT.put("recipe_9", 8);
        RECIPE_ID_TO_SLOT.put("recipe_10", 9);
        RECIPE_ID_TO_SLOT.put("recipe_11", 10);
    }

    public PlayerInfoPanel(Player player) {
        this.player = player;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(320, 260));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createTitledBorder("Player Info"));

        // Info labels
        nameLabel = new JLabel();
        genderLabel = new JLabel();
        energyLabel = new JLabel();
        partnerLabel = new JLabel();
        favoriteItemLabel = new JLabel();
        goldLabel = new JLabel();

        add(nameLabel);
        add(genderLabel);
        add(energyLabel);
        add(partnerLabel);
        add(favoriteItemLabel);
        add(goldLabel);

        // Space before recipe slots
        add(Box.createVerticalStrut(10));
        // Left aligned label
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
        leftPanel.setOpaque(false);
        JLabel recipeTitle = new JLabel("Recipes:");
        recipeTitle.setFont(recipeTitle.getFont().deriveFont(Font.BOLD));
        recipeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(Box.createHorizontalStrut(6));
        leftPanel.add(recipeTitle);
        leftPanel.add(Box.createHorizontalGlue());
        add(leftPanel);

        // Panel for recipe slots
        JPanel recipePanel = new JPanel(new GridLayout(1, 11, 3, 3));
        recipePanel.setOpaque(false);

        for (int i = 0; i < 11; i++) {
            recipeSlots[i] = new RecipeSlotLabel();
            recipePanel.add(recipeSlots[i]);
        }
        add(recipePanel);

        updateInfo();
    }

    public void updateInfo() {
        nameLabel.setText("Name         : " + player.getPlayerName());
        genderLabel.setText("Gender       : " + player.getGender());
        energyLabel.setText("Energy       : " + player.getEnergy());
        partnerLabel.setText("Partner      : " + (player.getPartner() != null ? player.getPartner().getNpcName() : "-"));
        favoriteItemLabel.setText("Favorite Item: " + getFavoriteItemName());
        goldLabel.setText("Gold         : " + player.getPlayerGold().getGold());

        // Kosongkan semua slot dulu
        for (RecipeSlotLabel slot : recipeSlots) slot.setRecipe(null);

        // Update recipe slots by recipeID order
        List<Recipe> recipes = player.getKnownRecipes();
        if (recipes != null) {
            for (Recipe r : recipes) {
                Integer idx = RECIPE_ID_TO_SLOT.get(r.getRecipeID());
                if (idx != null && idx >= 0 && idx < recipeSlots.length) {
                    recipeSlots[idx].setRecipe(r);
                }
            }
        }
    }

    private String getFavoriteItemName() {
        if (player.getFavoriteItem() != null) {
            return player.getFavoriteItem().getItemName();
        } else {
            return "-";
        }
    }

    // === Inner class untuk Recipe slot ===
    private static class RecipeSlotLabel extends JLabel {
        public RecipeSlotLabel() {
            setPreferredSize(new Dimension(28, 28));
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
            setOpaque(true);
            setBackground(new Color(245, 245, 245));
        }

        public void setRecipe(Recipe recipe) {
            if (recipe != null) {
                if (recipe.image != null) {
                    setIcon(new ImageIcon(recipe.image.getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
                    setText("");
                } else {
                    setIcon(null);
                    setText(recipe.getItemName());
                }
                setToolTipText(recipe.getItemName());
            } else {
                setIcon(null);
                setText("");
                setToolTipText(null);
            }
        }
    }
}