package gui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        ImageIcon image = new ImageIcon(Main.class.getResource("/res/gamebackground/bg.jpg"));
        frame.setIconImage(image.getImage());
        MainPanel mainPanel = new MainPanel(frame);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}