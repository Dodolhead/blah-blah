package gui;

import items.*;
import entities.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.awt.image.BufferedImage;

public class ShippingBinPanel extends JPanel {
    private static final int GRID_SIZE = 4;
    private static final int ICON_SIZE = 15;
    private SlotPanel[][] slots = new SlotPanel[GRID_SIZE][GRID_SIZE];
    private ShippingBin shippingBin;
    private JButton addButton;
    private Image defaultIcon = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);

    // Slot panel: gambar di atas, jumlah di bawah
    private static class SlotPanel extends JPanel {
        JLabel iconLabel;
        JLabel amountLabel;
        SlotPanel(Image defaultIcon) {
            setLayout(new BorderLayout());
            setOpaque(true);
            setBackground(new Color(250, 250, 230));
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            iconLabel = new JLabel();
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            iconLabel.setVerticalAlignment(SwingConstants.CENTER);
            amountLabel = new JLabel("", SwingConstants.CENTER);
            amountLabel.setFont(new Font("Arial", Font.BOLD, 12));
            amountLabel.setForeground(new Color(40,40,40));
            add(iconLabel, BorderLayout.CENTER);
            add(amountLabel, BorderLayout.SOUTH);
            // Kosong awal
            iconLabel.setIcon(new ImageIcon(defaultIcon));
            amountLabel.setText("");
        }
        void setIconAndAmount(Image img, int amount) {
            iconLabel.setIcon(new ImageIcon(img));
            amountLabel.setText(amount > 0 ? String.valueOf(amount) : "");
        }
        void clear(Image defaultIcon) {
            iconLabel.setIcon(new ImageIcon(defaultIcon));
            amountLabel.setText("");
        }
    }

    public ShippingBinPanel(ShippingBin shippingBin, Player player, InventoryPanel inventoryPanel) {
        this.shippingBin = shippingBin;
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(180, 220));

        // Default icon
        Graphics2D g2 = (Graphics2D) defaultIcon.getGraphics();
        g2.setColor(Color.LIGHT_GRAY);
        g2.setColor(Color.DARK_GRAY);
        g2.dispose();

        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 2, 2));
        gridPanel.setOpaque(true);
        gridPanel.setBackground(new Color(255, 255, 240, 220));
        gridPanel.setBorder(BorderFactory.createTitledBorder("Shipping Bin"));

        // Slot panels
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                SlotPanel slot = new SlotPanel(defaultIcon);
                slots[y][x] = slot;
                gridPanel.add(slot);
            }
        }

        // Tombol add selected item
        addButton = new JButton("Add Selected Item");
        addButton.setFocusable(false);
        addButton.setFont(addButton.getFont().deriveFont(11f));
        addButton.addActionListener(e -> {
            Item selectedItem = inventoryPanel.getSelectedItem();
            if (selectedItem == null) {
                JOptionPane.showMessageDialog(this, "No item selected in inventory.");
                return;
            }
            String amountStr = JOptionPane.showInputDialog(this, "Amount to add:", "1");
            if (amountStr == null) return; // Cancel
            int amount;
            try {
                amount = Integer.parseInt(amountStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
                return;
            }
            boolean ok = shippingBin.addToShippingBin(player.getPlayerInventory(), selectedItem, amount);
            if (ok) {
                updateSlots();
                inventoryPanel.updateInventoryUI(player.getPlayerInventory());
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add item to shipping bin.");
            }
        });

        add(gridPanel, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);

        updateSlots();
    }

    public void updateSlots() {
        // Kosongkan semua slot
        for (int y = 0; y < GRID_SIZE; y++)
            for (int x = 0; x < GRID_SIZE; x++)
                slots[y][x].clear(defaultIcon);

        int idx = 0;
        for (Map.Entry<Item, Integer> entry : shippingBin.getShippingBinStorage().entrySet()) {
            if (idx >= GRID_SIZE * GRID_SIZE) break;
            int y = idx / GRID_SIZE;
            int x = idx % GRID_SIZE;
            Item item = entry.getKey();
            int jumlah = entry.getValue();
            Image img = (item.image != null)
                ? item.image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH)
                : defaultIcon;
            slots[y][x].setIconAndAmount(img, jumlah);
            idx++;
        }
    }
}