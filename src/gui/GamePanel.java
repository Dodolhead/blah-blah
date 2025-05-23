package src.gui;
import javax.swing.*;
import java.awt.*;
import src.entities.*;
import src.tile.*;


public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3; //3x scale
    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxscreenCol = 16; //16 columns
    public final int maxscreenRow = 12; //12 rows
    final int screenWidth = tileSize * maxscreenCol; //768 pixels
    final int screenHeight = tileSize * maxscreenRow; //576 pixels
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    TileManager tileM = new TileManager(this);
    Farm farm;

    int FPS = 60;
    Player player;
    
    public GamePanel(String playerName, String gender, String farmName) {
        this.setPreferredSize(new java.awt.Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        player = new Player(playerName, gender, farmName, this, keyH);
        farm = new Farm(farmName, player);
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval = 1000000000/FPS; //0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        long lastTimer = 0;
        while (gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                drawCount++;
                delta--;
            }
            if (timer - lastTimer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                lastTimer = timer;
            }
        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();

    }
}