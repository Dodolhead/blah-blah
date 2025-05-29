package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import entities.Player;
import items.Recipe;

public class PlayerInfoPanel extends JPanel {
    private Player player;
    private JLabel nameLabel, genderLabel, energyLabel, partnerLabel, favoriteItemLabel, goldLabel;
    private JLabel selectedRecipeLabel;
    private RecipeSlotLabel[] recipeSlots = new RecipeSlotLabel[11];
    private RecipeSlotLabel selectedRecipeSlot = null;
    private Recipe selectedRecipe = null;

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
        setPreferredSize(new Dimension(340, 180));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createTitledBorder("Player Info"));

        // Panel atas untuk info player
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        nameLabel = new JLabel();
        genderLabel = new JLabel();
        energyLabel = new JLabel();
        partnerLabel = new JLabel();
        favoriteItemLabel = new JLabel();
        goldLabel = new JLabel();

        topPanel.add(nameLabel);
        topPanel.add(genderLabel);
        topPanel.add(energyLabel);
        topPanel.add(partnerLabel);
        topPanel.add(favoriteItemLabel);
        topPanel.add(goldLabel);

        add(topPanel);

        add(Box.createVerticalStrut(10));

        // Judul "Recipes:"
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

        // Panel slot resep
        JPanel recipePanel = new JPanel(new GridLayout(1, 11, 3, 3));
        recipePanel.setOpaque(false);

        for (int i = 0; i < 11; i++) {
            RecipeSlotLabel slot = new RecipeSlotLabel();
            recipeSlots[i] = slot;
            recipePanel.add(slot);

            slot.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectRecipeSlot(slot);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (slot != selectedRecipeSlot) {
                        slot.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                    }
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    if (slot != selectedRecipeSlot) {
                        slot.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    }
                }
            });
        }
        add(recipePanel);

        // Label selected recipe di bawah slot, rata kiri
        selectedRecipeLabel = new JLabel("Selected Recipe: None");
        selectedRecipeLabel.setFont(selectedRecipeLabel.getFont().deriveFont(Font.ITALIC, 14f));
        selectedRecipeLabel.setForeground(Color.DARK_GRAY);
        selectedRecipeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel agar rata kiri dan ada margin kiri
        JPanel selectedRecipePanel = new JPanel();
        selectedRecipePanel.setLayout(new BoxLayout(selectedRecipePanel, BoxLayout.X_AXIS));
        selectedRecipePanel.setOpaque(false);
        selectedRecipePanel.add(Box.createHorizontalStrut(6)); // margin kiri seperti judul
        selectedRecipePanel.add(selectedRecipeLabel);
        selectedRecipePanel.add(Box.createHorizontalGlue());

        add(Box.createVerticalStrut(6));
        add(selectedRecipePanel);

        updateInfo();
    }

    private void selectRecipeSlot(RecipeSlotLabel slot) {
        // Hilangkan border slot sebelumnya (kalau ada)
        for (RecipeSlotLabel s : recipeSlots) {
            if (s != slot) {
                s.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            }
        }
        selectedRecipeSlot = slot;
        if (slot.getRecipe() != null) {
            selectedRecipe = slot.getRecipe();
            slot.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            selectedRecipeLabel.setText("Selected Recipe: " + selectedRecipe.getItemName());
        } else {
            selectedRecipe = null;
            slot.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            selectedRecipeLabel.setText("Selected Recipe: None");
        }
    }

    public void updateInfo() {
        nameLabel.setText("Name         : " + player.getPlayerName());
        genderLabel.setText("Gender       : " + player.getGender());
        energyLabel.setText("Energy       : " + player.getEnergy());
        partnerLabel.setText("Partner      : " + (player.getPartner() != null ? player.getPartner().getNpcName() : "-"));
        favoriteItemLabel.setText("Favorite Item: " + getFavoriteItemName());
        goldLabel.setText("Gold         : " + player.getPlayerGold().getGold());

        // Kosongkan semua slot dulu
        for (RecipeSlotLabel slot : recipeSlots) {
            slot.setRecipe(null);
            slot.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));  // Reset border semua slot!
        }

        // Reset selection
        selectedRecipeSlot = null;
        selectedRecipe = null;
        if (selectedRecipeLabel != null) {
            selectedRecipeLabel.setText("Selected Recipe: None");
        }

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

    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }

    private String getFavoriteItemName() {
        if (player.getFavoriteItem() != null) {
            return player.getFavoriteItem().getItemName();
        } else {
            return "-";
        }
    }

    // === Inner class untuk Recipe slot yang selectable ===
    private static class RecipeSlotLabel extends JLabel {
        private Recipe recipe;
        public RecipeSlotLabel() {
            setPreferredSize(new Dimension(28, 28));
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
            setOpaque(true);
            setBackground(new Color(245, 245, 245));
            this.recipe = null;
        }
        public void setRecipe(Recipe recipe) {
            this.recipe = recipe;
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
        public Recipe getRecipe() {
            return recipe;
        }
    }
}