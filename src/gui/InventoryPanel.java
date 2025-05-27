package src.gui;

import javax.swing.*;

import src.items.Inventory;
import src.items.Item;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.awt.*;

public class InventoryPanel extends JPanel {
    private static final int MAX_ITEMS = 20;
    private JLabel[] slots = new JLabel[MAX_ITEMS];
    private Item[] slotItems = new Item[MAX_ITEMS]; 
    private Item selectedItem = null; 

    public InventoryPanel() {
        setLayout(new GridLayout(4, 5, 10, 10)); 

        // Contoh placeholder untuk slot item
       for (int i = 0; i < MAX_ITEMS; i++) {
            JLabel slot = new JLabel("", SwingConstants.CENTER);
            slot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            slot.setPreferredSize(new Dimension(32, 16));

            slots[i] = slot;
            add(slot);

            int finalI = i; 
            slot.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    slot.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    slot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedItem = slotItems[finalI];
                    System.out.println("Selected item: " + (selectedItem != null ? selectedItem.getItemName() : "None"));
                }
            });
        }

        // setOpaque(true);
        setFocusable(true);
        setBackground(new Color(200, 200, 200, 220));
        setVisible(false);
        
    }

    public void updateInventoryUI(Inventory inventory) {
        int index = 0;
        for (Map<Item, Integer> map : inventory.getInventoryStorage().values()) {
            for (Map.Entry<Item, Integer> entry : map.entrySet()) {
                if (index >= MAX_ITEMS) break;
                Item item = entry.getKey();
                int count = entry.getValue();

                slotItems[index] = item;

                // Set icon jika gambar tersedia
                if (item.image != null) {
                    Image scaledImage = item.image.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                    slots[index].setIcon(new ImageIcon(scaledImage));
                } else {
                    slots[index].setIcon(null);
                }

                // Set jumlah (text di bawah icon)
                slots[index].setText("x" + count);
                slots[index].setHorizontalTextPosition(JLabel.CENTER);
                slots[index].setVerticalTextPosition(JLabel.BOTTOM);
                slots[index].setFont(new Font("Arial", Font.PLAIN, 10));
                index++;
            }
        }

        // Kosongkan slot sisanya
        for (int i = index; i < MAX_ITEMS; i++) {
            slotItems[i] = null;
            slots[i].setIcon(null);
            slots[i].setText("");
        }
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

}
