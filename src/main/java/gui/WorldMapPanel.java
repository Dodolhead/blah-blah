package main.java.gui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.map.*;
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
                "/res/worldmap/forrestRiverButton.png",
                "/res/worldmap/forrestRiverButton.png",
                220, 100);
        mountainLakeButton = createImageButton("Mountain Lake",
                "/res/worldmap/mountainLakeButton.png",
                "/res/worldmap/mountainLakeButton.png",
                440, 100);
        oceanButton = createImageButton("Ocean",
                "/res/worldmap/oceanButton.png",
                "/res/worldmap/oceanButton.png",
                220, 250);
        storeButton = createImageButton("Store",
                "/res/worldmap/storeButton.png",
                "/res/worldmap/storeButton.png",
                440, 250);
        farmButton = createImageButton("Farm",
                "/res/worldmap/farmButton.png",
                "/res/worldmap/farmButton.png",
                220, 400);
        npcHouseButton = createImageButton("NPC House",
                "/res/worldmap/npcHouseButton.png",
                "/res/worldmap/npcHouseButton.png",
                440, 400);

        add(forrestRiverButton);
        add(mountainLakeButton);
        add(oceanButton);
        add(storeButton);
        add(farmButton);
        add(npcHouseButton);

        // Papan Label
        try {
            BufferedImage papanImg = ImageIO.read(getClass().getResourceAsStream("/res/worldmap/papan.png"));
            JLabel papanLabel = new JLabel(new ImageIcon(papanImg));
            papanLabel.setBounds(160, 60, 500, 500);
            add(papanLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Background
        try {
            BufferedImage bgImg = ImageIO.read(getClass().getResourceAsStream("/res/worldmap/bg-map.png"));
            JLabel bgLabel = new JLabel(new ImageIcon(bgImg));
            bgLabel.setBounds(0, 0, 800, 600);
            add(bgLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JButton createImageButton(String name, String iconPath, String hoverPath, int x, int y) {
        BufferedImage iconImg = null;
        BufferedImage hoverImg = null;
        try {
            iconImg = ImageIO.read(getClass().getResourceAsStream(iconPath));
            hoverImg = ImageIO.read(getClass().getResourceAsStream(hoverPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton button = new JButton(new ImageIcon(iconImg));
        button.setBounds(x, y, 160, 130);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        if (hoverImg != null) {
            button.setRolloverIcon(new ImageIcon(hoverImg));
        }
        button.setToolTipText(name);
        button.addActionListener(this);

        JLabel label = new JLabel(name, JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBackground(new Color(0, 0, 0, 150));
        label.setOpaque(true);
        label.setBounds(0, 100, 160, 30);
        button.setLayout(null);
        button.add(label);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
