package main.java.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuPanel extends JPanel implements ActionListener {
    private JButton play;
    private JButton exit;
    private MainPanel mainPanel;

    public MenuPanel(JFrame frame, MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(800, 600));

        // Icon label
        JLabel iconLabel = null;
        try {
            BufferedImage iconImg = ImageIO.read(getClass().getResourceAsStream("/res/menu/view.jpg"));
            iconLabel = new JLabel(new ImageIcon(iconImg));
        } catch (IOException e) {
            iconLabel = new JLabel("ICON NOT FOUND");
        }
        int iconWidth = 400;
        int iconHeight = 100;
        iconLabel.setBounds((800 - iconWidth) / 2, 100, iconWidth, iconHeight); 

        // Tombol Play 
        try {
            BufferedImage playImg = ImageIO.read(getClass().getResourceAsStream("/res/menu/play.png"));
            play = new JButton(new ImageIcon(playImg));
        } catch (IOException e) {
            play = new JButton("Play");
        }
        play.setBounds(300, 250, 200, 70);
        play.setBorderPainted(false); 
        play.setContentAreaFilled(false); 
        play.setFocusPainted(false); 
        play.setOpaque(false); 
        play.addActionListener(this);

        // Tombol Exit 
        try {
            BufferedImage exitImg = ImageIO.read(getClass().getResourceAsStream("/res/menu/exit.png"));
            exit = new JButton(new ImageIcon(exitImg));
        } catch (IOException e) {
            exit = new JButton("Exit");
        }
        exit.setBounds(300, 350, 200, 70);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setFocusPainted(false);
        exit.setOpaque(false);
        exit.addActionListener(this);

        // Background label
        JLabel bgLabel = null;
        try {
            BufferedImage bgImg = ImageIO.read(getClass().getResourceAsStream("/res/menu/bg.jpg"));
            bgLabel = new JLabel(new ImageIcon(bgImg));
        } catch (IOException e) {
            bgLabel = new JLabel("BG NOT FOUND");
        }
        bgLabel.setBounds(0, 0, 800, 600);

        this.add(iconLabel);
        this.add(play);
        this.add(exit);
        this.add(bgLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            mainPanel.showCreatePlayer();
        }
        if (e.getSource() == exit) {
            System.exit(0);
        }
    }
}