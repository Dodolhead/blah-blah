package gui;

import items.*;
import entities.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.awt.image.BufferedImage;


public class ShippingBinPanel extends JPanel {
    private static final int GRID_SIZE = 4;
    private JLabel[][] slotLabels = new JLabel[GRID_SIZE][GRID_SIZE];
    private ShippingBin shippingBin;
    private Player player;
    private JButton addButton;
    private Image defaultIcon = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);

    public ShippingBinPanel(ShippingBin shippingBin, Player player, InventoryPanel inventoryPanel) {
        this.shippingBin = shippingBin;
        this.player = player;
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(180, 200));

        // Default icon: abu-abu jika image null
        Graphics2D g2 = (Graphics2D) defaultIcon.getGraphics();
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, 24, 24);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRect(2, 2, 20, 20);
        g2.dispose();

        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 2, 2));
        gridPanel.setOpaque(true);
        gridPanel.setBackground(new Color(255, 255, 240, 220));
        gridPanel.setBorder(BorderFactory.createTitledBorder("Shipping Bin"));

        // Isi grid
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBackground(new Color(250, 250, 230));
                label.setFont(new Font("Arial", Font.PLAIN, 11));
                label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                slotLabels[y][x] = label;
                gridPanel.add(label);
            }
        }

        // Tombol add selected item
        addButton = new JButton("Add Selected Item");
        addButton.setFocusable(false);
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void updateSlots() {
        // Kosongkan seluruh slot
        for (int y = 0; y < GRID_SIZE; y++)
            for (int x = 0; x < GRID_SIZE; x++) {
                slotLabels[y][x].setText("");
                slotLabels[y][x].setIcon(null);
            }

        int idx = 0;
        for (Map.Entry<Item, Integer> entry : shippingBin.getShippingBinStorage().entrySet()) {
            int y = idx / GRID_SIZE;
            int x = idx % GRID_SIZE;
            JLabel label = slotLabels[y][x];
            Item item = entry.getKey();
            int jumlah = entry.getValue();

            // set icon only (centered)
            Image img = (item.image != null)
                ? item.image.getScaledInstance(24, 24, Image.SCALE_SMOOTH)
                : defaultIcon;
            label.setIcon(new ImageIcon(img));
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.BOTTOM);

            // set bottom-center text ONLY amount
            label.setText("<html><div style='text-align:center;'>" +
                          "<span style='font-size:12px; color:#333;'>" + jumlah + "</span></div></html>");
            label.setToolTipText(null); // no name

            idx++;
            if (idx >= GRID_SIZE * GRID_SIZE) break;
        }
    }
}