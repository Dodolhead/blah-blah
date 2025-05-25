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
        ImageIcon background = new ImageIcon("res/worldmap/bg.jpg");
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
        if (e.getSource() == forrestRiverButton) {
            System.out.println("Button 1 clicked");
        } 
        else if (e.getSource() == mountainLakeButton) {
            System.out.println("Button 2 clicked");
        } 
        else if (e.getSource() == oceanButton) {
            System.out.println("Button 3 clicked");
        } 
        else if (e.getSource() == storeButton) {
            System.out.println("Button 4 clicked");
        } 
        else if (e.getSource() == farmButton) {
            String location = gp.player.getPlayerLocation().getName();
            if (location.equals("Farm")) {
                gp.mainPanel.showGame();
                gp.resetPlayerMovement();
                gp.player.setPlayerLocation(new Location("Farm", new Point(gp.player.getPlayerLocation().getCurrentPoint().getX()-1, gp.player.getPlayerLocation().getCurrentPoint().getY()-1)));
            } else {
                gp.returnToFarm();
            }
        } 
        else if (e.getSource() == npcHouseButton) {
            System.out.println("Button 6 clicked");
        }
    }

}
