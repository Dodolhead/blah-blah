package main.java.gui;

import java.awt.CardLayout;
import javax.swing.*;

public class MainPanel extends JPanel {
    CardLayout cardLayout;
    MenuPanel menuPanel;
    CreatePlayerPanel createPlayerPanel;
    GamePanel gamePanel;
    WorldMapPanel worldMapPanel;
    JFrame frame;

    public MainPanel(JFrame frame) {
        this.frame = frame;
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        menuPanel = new MenuPanel(frame, this);
        createPlayerPanel = new CreatePlayerPanel(frame, this);

        add(menuPanel, "menu");
        add(createPlayerPanel, "create");

        cardLayout.show(this, "menu");
        
    }

    public void showCreatePlayer() {
        cardLayout.show(this, "create");
    }

    public void startGame(String name, String gender, String farm) {
        gamePanel = new GamePanel(name, gender, farm, this);
        worldMapPanel = new WorldMapPanel(gamePanel);
        add(gamePanel, "game");
        add(worldMapPanel, "worldmap");

        cardLayout.show(this, "game");
        gamePanel.startGameThread();
        gamePanel.requestFocusInWindow();
    }

    public void showWorldMap() {
        cardLayout.show(this, "worldmap");
    }

    public void showGame() {
        cardLayout.show(this, "game");
        gamePanel.requestFocusInWindow();
    }

    public void showWorldMapPanel() {
        if (worldMapPanel == null) {
            worldMapPanel = new WorldMapPanel(this.gamePanel); 
            this.add(worldMapPanel, "worldmap");
        }
        cardLayout.show(this, "worldmap");
    }

}