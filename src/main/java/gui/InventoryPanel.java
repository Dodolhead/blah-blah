package gui;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import items.*;

public class InventoryPanel extends JPanel {
    private static final int MAX_ITEMS = 20;
    private ImageSlotLabel[] slots = new ImageSlotLabel[MAX_ITEMS];
    private Item[] slotItems = new Item[MAX_ITEMS];
    private ImageSlotLabel selectedSlot = null;
    private Item selectedItem = null;
    private Image slotBackgroundImage;

    // Tambahan: JLabel untuk menampilkan item terpilih
    private JLabel selectedItemLabel;

    public InventoryPanel() {
        setLayout(new GridLayout(4, 5, 6, 6)); // 4 baris x 5 kolom

        // Load gambar frame slot
        try {
            slotBackgroundImage = ImageIO.read(getClass().getResourceAsStream("/res/menu/slot_frame.png"));
        } catch (IOException e) {
            System.err.println("Gagal load gambar slot_frame.png: " + e.getMessage());
            slotBackgroundImage = null;
        }

        for (int i = 0; i < MAX_ITEMS; i++) {
            ImageSlotLabel slot = new ImageSlotLabel(slotBackgroundImage);
            slot.setPreferredSize(new Dimension(100, 100));
            slot.setHorizontalAlignment(SwingConstants.CENTER);
            slot.setVerticalAlignment(SwingConstants.BOTTOM);
            slot.setFont(new Font("Arial", Font.BOLD, 12));
            slot.setForeground(Color.WHITE);

            slots[i] = slot;
            add(slot);

            int finalI = i;
            slot.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (slot != selectedSlot) {
                        slot.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (slot != selectedSlot) {
                        slot.setBorder(null);
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedSlot != null && selectedSlot != slot) {
                        selectedSlot.setBorder(null);
                    }

                    slot.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                    selectedSlot = slot;
                    selectedItem = slotItems[finalI];

                    String itemName = selectedItem != null ? selectedItem.getItemName() : "None";
                    System.out.println("Selected item: " + itemName);

                    if (selectedItemLabel != null) {
                        selectedItemLabel.setText("Selected item: " + itemName);
                    }
                }
            });
        }

        setFocusable(true);
        setBackground(new Color(44, 17, 4, 220));
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

                if (item.image != null) {
                    Image scaledImage = item.image.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                    slots[index].setIcon(new ImageIcon(scaledImage));
                } else {
                    slots[index].setIcon(null);
                }

                slots[index].setText("x" + count);
                slots[index].setHorizontalTextPosition(JLabel.CENTER);
                slots[index].setVerticalTextPosition(JLabel.BOTTOM);
                index++;
            }
        }

        for (int i = index; i < MAX_ITEMS; i++) {
            slotItems[i] = null;
            slots[i].setIcon(null);
            slots[i].setText("");
        }
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    // Tambahan: Setter untuk label eksternal
    public void setSelectedItemLabel(JLabel label) {
        this.selectedItemLabel = label;
    }

    // === INNER CLASS UNTUK SLOT DENGAN BACKGROUND GAMBAR ===
    private static class ImageSlotLabel extends JLabel {
        private final Image background;

        public ImageSlotLabel(Image background) {
            this.background = background;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
            super.paintComponent(g);
        }
    }
}
