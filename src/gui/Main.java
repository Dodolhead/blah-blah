package src.gui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);       
        frame.setLocationRelativeTo(null);
        MenuPanel menuPanel = new MenuPanel(frame);
        frame.add(menuPanel);
        frame.pack();
        frame.setVisible(true);
    }
}