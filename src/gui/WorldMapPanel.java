package src.gui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.map.*;

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

        ImageIcon icon = new ImageIcon("res/worldmap/huahauhau.png");

        // Atas
        forrestRiverButton = createButton(icon, 200, 100);
        mountainLakeButton = createButton(icon, 400, 100);
        // Tengah
        oceanButton = createButton(icon, 200, 250);
        storeButton = createButton(icon, 400, 250); 
        // Bawah
        farmButton = createButton(icon, 200, 400); 
        npcHouseButton = createButton(icon, 400, 400); 

        // Background label
        ImageIcon background = new ImageIcon("res/worldmap/bg-map.png");
        JLabel bgLabel = new JLabel(background);
        bgLabel.setBounds(0, 0, 800, 600);

        // Add buttons first (so they are on top of background)
        this.add(forrestRiverButton);
        this.add(mountainLakeButton);
        this.add(oceanButton);
        this.add(storeButton);
        this.add(farmButton);
        this.add(npcHouseButton);
        this.add(bgLabel);
    }

    private JButton createButton(ImageIcon icon, int x, int y) {
        JButton button = new JButton(icon);
        button.setBounds(x, y, 100, 70);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String location = gp.player.getPlayerLocation().getName();
        if (e.getSource() == forrestRiverButton) {
            if (location.equals("ForestRiver")) {
                gp.mainPanel.showGame();
                gp.resetPlayerMovement();
                gp.player.setPlayerLocation(new Location("ForestRiver", new Point(gp.player.getPlayerLocation().getCurrentPoint().getX()-1, gp.player.getPlayerLocation().getCurrentPoint().getY()-1)));
            }
            else{
                gp.goToForestRiver();
                gp.resetPlayerMovement();
            }
        } 
        else if (e.getSource() == mountainLakeButton) {
            if (location.equals("MountainLake")) {
                gp.mainPanel.showGame();
                gp.resetPlayerMovement();
                gp.player.setPlayerLocation(new Location("MountainLake", new Point(gp.player.getPlayerLocation().getCurrentPoint().getX()-1, gp.player.getPlayerLocation().getCurrentPoint().getY()-1)));
            }
            else{
                gp.goToMountainLake();
                gp.resetPlayerMovement();
            }
        } 
        else if (e.getSource() == oceanButton) {
            if (location.equals("Ocean")) {
                gp.mainPanel.showGame();
                gp.resetPlayerMovement();
                gp.player.setPlayerLocation(new Location("Ocean", new Point(gp.player.getPlayerLocation().getCurrentPoint().getX()-1, gp.player.getPlayerLocation().getCurrentPoint().getY()-1)));
            }
            else{
                gp.goToOcean();
                gp.resetPlayerMovement();
            }
        } 
        else if (e.getSource() == storeButton) {
            if (location.equals("Store")) {
                gp.mainPanel.showGame();
                gp.resetPlayerMovement();
                gp.player.setPlayerLocation(new Location("Store", new Point(gp.player.getPlayerLocation().getCurrentPoint().getX()-1, gp.player.getPlayerLocation().getCurrentPoint().getY()-1)));
            }
            else{
                gp.goToStore();
                gp.resetPlayerMovement();
            }
        } 
        else if (e.getSource() == farmButton) {
            if (location.equals("Farm")) {
                gp.mainPanel.showGame();
                gp.resetPlayerMovement();
                gp.player.setPlayerLocation(new Location("Farm", new Point(gp.player.getPlayerLocation().getCurrentPoint().getX()-1, gp.player.getPlayerLocation().getCurrentPoint().getY()-1)));
            } else {
                gp.returnToFarm();
                gp.resetPlayerMovement();
            }
        } 
        else if (e.getSource() == npcHouseButton) {
            System.out.println("Button 6 clicked");
        }
    }

}
