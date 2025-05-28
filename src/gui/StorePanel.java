package src.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        // GridLayout(0, 4, ...) = 4 kolom, baris otomatis
        itemListPanel = new JPanel(new GridLayout(0, 4, 14, 12));
        itemListPanel.setBackground(new Color(230, 230, 230));
        // Jangan setPreferredSize pada itemListPanel!

        JScrollPane scrollPane = new JScrollPane(
            itemListPanel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.getVerticalScrollBar().setUnitIncrement(40);
        add(scrollPane, BorderLayout.CENTER);

        refreshPanel();
    }

    private JPanel createItemPanel(Item item, int amount) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(160, 180)); // Ukuran tetap supaya tidak gepeng
        panel.setMaximumSize(new Dimension(160, 180));
        panel.setMinimumSize(new Dimension(160, 180));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(new Color(245, 245, 245, 255));

        // Icon
        JLabel iconLabel = new JLabel("", SwingConstants.CENTER);
        if (item.image != null) {
            Image scaledImage = item.image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImage));
        }
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setBorder(new EmptyBorder(10, 0, 4, 0));

        // Stock label
        JLabel stockLabel = new JLabel("Stock: " + amount, SwingConstants.CENTER);
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Price label
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
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Name label
        JLabel nameLabel = new JLabel(item.getItemName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buy button
        JButton buyButton = new JButton("Buy");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
                gp.requestFocusInWindow();
            } else {
                JOptionPane.showMessageDialog(
                    SwingUtilities.getWindowAncestor(StorePanel.this),
                    "Not enough gold or item out of stock."
                );
                gp.requestFocusInWindow();
            }
        });

        // Spacing
        panel.add(Box.createVerticalStrut(6));
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(2));
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(2));
        panel.add(stockLabel);
        panel.add(priceLabel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(buyButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    public void refreshPanel() {
        itemListPanel.removeAll();
        Map<Class<?>, Map<Item, Integer>> soldItems = store.getSoldItem();

        // Urutkan: Seed, Crop, Misc
        Class<?>[] order = new Class<?>[] { Seed.class, Crop.class, Misc.class };
        for (Class<?> itemClass : order) {
            Map<Item, Integer> items = soldItems.get(itemClass);
            if (items != null && !items.isEmpty()) {
                for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                    Item item = entry.getKey();
                    int amount = entry.getValue();
                    JPanel itemPanel = createItemPanel(item, amount);
                    itemListPanel.add(itemPanel);
                }
            }
        }
        itemListPanel.revalidate();
        itemListPanel.repaint();
    }
}