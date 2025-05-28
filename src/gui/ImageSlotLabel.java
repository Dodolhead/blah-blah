package src.gui;

import javax.swing.*;
import java.awt.*;

public class ImageSlotLabel extends JPanel {
    private final Image background;
    private final JLabel iconLabel;
    private final JLabel textLabel;

    public ImageSlotLabel(Image background) {
        this.background = background;
        setOpaque(false);
        setLayout(new BorderLayout());
        
        iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        iconLabel.setVerticalAlignment(JLabel.CENTER);

        textLabel = new JLabel();
        textLabel.setFont(new Font("Arial", Font.BOLD, 10));
        textLabel.setForeground(Color.decode("#2c1104"));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setVerticalAlignment(JLabel.BOTTOM);

        add(iconLabel, BorderLayout.CENTER);
        add(textLabel, BorderLayout.SOUTH);
    }

    public void setItemIcon(ImageIcon icon) {
        iconLabel.setIcon(icon);
    }

    public void setItemText(String text) {
        textLabel.setText(text);
    }

    public void clearItem() {
        iconLabel.setIcon(null);
        textLabel.setText("");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100); // Pastikan slot square
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
