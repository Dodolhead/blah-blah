package src.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.gui.GamePanel;
import src.map.Location;
import src.map.Point;

public class WorldMapPanel extends JPanel implements ActionListener {
    private JButton forrestRiverButton;
    private JButton mountainLakeButton;
    private JButton oceanButton;
    private JButton storeButton;
    private JButton farmButton;
    private JButton npcHouseButton;
    GamePanel gp;

    public WorldMapPanel(GamePanel gp) {
        this.gp = gp;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(800, 600)); 

        // Tambahkan tombol
        forrestRiverButton = createImageButton("Forest River", 
            "res/worldmap/forrestRiverButton.png", 
            "res/worldmap/forrestRiverButton_hover.png", 
            220, 100);
        mountainLakeButton = createImageButton("Mountain Lake", 
            "res/worldmap/mountainLakeButton.png", 
            "res/worldmap/mountainLakeButton_hover.png", 
            440, 100);
        oceanButton = createImageButton("Ocean", 
            "res/worldmap/oceanButton.png", 
            "res/worldmap/oceanButton_hover.png", 
            220, 250);
        storeButton = createImageButton("Store", 
            "res/worldmap/storeButton.png", 
            "res/worldmap/storeButton_hover.png", 
            440, 250);
        farmButton = createImageButton("Farm", 
            "res/worldmap/farmButton.png", 
            "res/worldmap/farmButton_hover.png", 
            220, 400);
        npcHouseButton = createImageButton("NPC House", 
            "res/worldmap/npcHouseButton.png", 
            "res/worldmap/npcHouseButton_hover.png", 
            440, 400);

        // Tambah ke panel
        add(forrestRiverButton);
        add(mountainLakeButton);
        add(oceanButton);
        add(storeButton);
        add(farmButton);
        add(npcHouseButton);

        // Background
        ImageIcon papan = new ImageIcon("res/worldmap/papan.png");
        JLabel papanLabel = new JLabel(papan);
        papanLabel.setBounds(160, 60, 500, 500);
        add(papanLabel);

        // Background
        ImageIcon background = new ImageIcon("res/worldmap/bg-map.png");
        JLabel bgLabel = new JLabel(background);
        bgLabel.setBounds(0, 0, 800, 600);
        add(bgLabel);
    }

    private JButton createImageButton(String name, String iconPath, String hoverPath, int x, int y) {
        ImageIcon icon = new ImageIcon(iconPath);
        ImageIcon hoverIcon = new ImageIcon(hoverPath);

        JButton button = new JButton(icon);
        button.setBounds(x, y, 160, 130);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setRolloverIcon(hoverIcon);
        button.setToolTipText(name); // optional tooltip
        button.addActionListener(this);

        // Tambahkan label teks overlay
        JLabel label = new JLabel(name, JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBackground(new Color(0, 0, 0, 150));
        label.setOpaque(true);
        label.setBounds(0, 100, 160, 30); // posisinya di bawah gambar
        button.setLayout(null);
        button.add(label);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String location = gp.player.getPlayerLocation().getName();

        if (e.getSource() == forrestRiverButton) {
            goToLocation("ForestRiver", () -> gp.goToForestRiver());
        } else if (e.getSource() == mountainLakeButton) {
            goToLocation("MountainLake", () -> gp.goToMountainLake());
        } else if (e.getSource() == oceanButton) {
            goToLocation("Ocean", () -> gp.goToOcean());
        } else if (e.getSource() == storeButton) {
            goToLocation("Store", () -> gp.goToStore());
        } else if (e.getSource() == farmButton) {
            goToLocation("Farm", () -> gp.returnToFarm());
        } else if (e.getSource() == npcHouseButton) {
            System.out.println("Button NPC clicked");
        }
    }

    private void goToLocation(String name, Runnable changeLocationAction) {
        String current = gp.player.getPlayerLocation().getName();
        if (current.equals(name)) {
            gp.mainPanel.showGame();
            gp.resetPlayerMovement();
            gp.player.setPlayerLocation(new Location(name,
                new Point(gp.player.getPlayerLocation().getCurrentPoint().getX() - 1,
                          gp.player.getPlayerLocation().getCurrentPoint().getY() - 1)));
        } else {
            changeLocationAction.run();
            gp.resetPlayerMovement();
        }
    }
}
