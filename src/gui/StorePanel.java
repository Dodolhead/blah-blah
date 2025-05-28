package src.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import src.items.*;
import src.map.Store;
import src.entities.*;

public class StorePanel extends JPanel {
    private Player player;
    private Store store;
    private JPanel itemListPanel;
    GamePanel gp;

    public StorePanel(Store store, Player player, GamePanel gp) {
        this.store = store;
        this.player = player;
        this.gp = gp;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        JLabel title = new JLabel("Store", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        itemListPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(itemListPanel);
        add(scrollPane, BorderLayout.CENTER);

        refreshPanel();
    }

    private JPanel createItemPanel(Item item, int amount) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(new Color(245, 245, 245, 255));

        JLabel iconLabel = new JLabel("", SwingConstants.CENTER);
        if (item.image != null) {
            Image scaledImage = item.image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImage));
        }
        panel.add(iconLabel, BorderLayout.NORTH);

        JLabel nameLabel = new JLabel(item.getItemName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(nameLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
        bottomPanel.setOpaque(false);

        JLabel stockLabel = new JLabel("Stock: " + amount, SwingConstants.CENTER);
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bottomPanel.add(stockLabel);

        Gold buyPrice = null;
        if (item instanceof Seed) {
            buyPrice = ((Seed) item).getBuyPrice();
        } else if (item instanceof Crop) {
            buyPrice = ((Crop) item).getBuyPrice();
        } else if (item instanceof Recipe) {
            buyPrice = ((Recipe) item).getBuyPrice();
        } else if (item instanceof Equipment) {
            buyPrice = ((Equipment) item).getBuyPrice();
        }
        int price = (buyPrice != null) ? buyPrice.getGold() : 0;

        JLabel priceLabel = new JLabel("Price: " + price + "g", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bottomPanel.add(priceLabel);

        JButton buyButton = new JButton("Buy");
        if (buyPrice == null) {
            buyButton.setEnabled(false);
        }
        buyButton.addActionListener(e -> {
            int playerGold = player.getPlayerGold().getGold();
            if (playerGold >= price && store.getStock(item) > 0) {
                player.getPlayerGold().subtractGold(price);
                store.decreaseStock(item, 1);
                player.getPlayerInventory().addItem(item, 1);

                stockLabel.setText("Stock: " + store.getStock(item));
                JOptionPane.showMessageDialog(
                    SwingUtilities.getWindowAncestor(StorePanel.this),
                    "You bought 1 " + item.getItemName() + "!"
                );
                // KEMBALIKAN FOKUS KE GAMEPANEL
                gp.requestFocusInWindow();
            } else {
                JOptionPane.showMessageDialog(
                    SwingUtilities.getWindowAncestor(StorePanel.this),
                    "Not enough gold or item out of stock."
                );
                gp.requestFocusInWindow();
            }
        });
        bottomPanel.add(buyButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    public void refreshPanel() {
        itemListPanel.removeAll();
        Map<Class<?>, Map<Item, Integer>> soldItems = store.getSoldItem();
        int itemCount = 0;
        outerLoop:
        for (Class<?> itemClass : Store.typeToClassMap.values()) {
            Map<Item, Integer> items = soldItems.get(itemClass);
            if (items != null && !items.isEmpty()) {
                for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                    if (itemCount >= 6) break outerLoop; // HANYA 6 ITEM
                    Item item = entry.getKey();
                    int amount = entry.getValue();
                    JPanel itemPanel = createItemPanel(item, amount);
                    itemListPanel.add(itemPanel);
                    itemCount++;
                }
            }
        }
        itemListPanel.revalidate();
        itemListPanel.repaint();
    }
}