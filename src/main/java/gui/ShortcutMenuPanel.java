package gui;

import endgame.EndGame;
import endgame.EndGameStats;
import entities.Player;
import tsw.Time;

import javax.swing.*;
import java.awt.*;

public class ShortcutMenuPanel extends JPanel {

    private static final Dimension BUTTON_SIZE = new Dimension(220, 40);

    public ShortcutMenuPanel(JFrame frame, Player player, Time time, GamePanel gamePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(300, 1000));
        setMaximumSize(new Dimension(300, 1000));
        setFocusable(true);


        // Judul Panel
        JLabel label = new JLabel("MENU");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        add(label);
        add(Box.createVerticalStrut(20));

        // TUJUAN
        JButton tujuanButton = createButton("Tujuan Permainan");
        tujuanButton.addActionListener(e -> {
            String tujuan = """
                    Tujuan Permainan:

                    - Mengelola lahan pertanian dan melakukan beberapa aksi (lihat menu HELP).
                    - Mencapai milestones seperti memiliki 17.209g dan menikah.
                    - Menyelesaikan tugas besar OOP mungkin?
                    """;
            JOptionPane.showMessageDialog(this, tujuan, "Tujuan Permainan", JOptionPane.INFORMATION_MESSAGE);
            gamePanel.requestFocusInWindow(); 
        });
        add(tujuanButton);
        add(Box.createVerticalStrut(20));
        

        // HELP
        JButton helpButton = createButton("HELP");
        helpButton.addActionListener(e -> {
            String helpText = """
                    KEYBIND

                    - W A S D : Move
                    - T       : Tilling
                    - R       : Recover Land
                    - P       : Planting
                    - L       : Watering
                    - H       : Harvesting
                    - F       : Fishing
                    - E       : Eating
                    - C       : Cooking
                    - U       : Watching
                    - Y       : Sleep
                    - J       : Add fuel
                    - G       : Place furniture
                    - I       : Toggle Inventory
                    - O       : Toggle Store
                    - V       : View Player Info
                    - B       : Toggle Shipping Bin
                    - N       : Interact with NPC
                    - ESC     : Menu Shortcut
                    """;
            JOptionPane.showMessageDialog(this, helpText, "Bantuan", JOptionPane.INFORMATION_MESSAGE);
            gamePanel.requestFocusInWindow(); 
        });
        add(helpButton);
        add(Box.createVerticalStrut(20));

        // END GAME
        JButton endGameButton = createButton("End Game");
        endGameButton.addActionListener(e -> {
            EndGame endGame = new EndGame();
            if (endGame.checkMilestoneAndShowStats(player)) {
                EndGameStats stats = new EndGameStats(player, time);
                EndGamePanel panel = new EndGamePanel(stats);
                frame.setContentPane(panel);
                frame.revalidate();
            } else {
                String message = "Milestone belum tercapai!\nBelum bisa melihat statistik akhir.";
                JOptionPane.showMessageDialog(this, message, "End Game Gagal", JOptionPane.WARNING_MESSAGE);
                gamePanel.requestFocusInWindow(); 
            }
        });
        add(endGameButton);
        add(Box.createVerticalStrut(20));

        // EXIT
        JButton exitButton = createButton("Exit");
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Apakah Anda yakin ingin keluar dari permainan?",
                    "Konfirmasi Exit",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
            gamePanel.requestFocusInWindow(); 
        });
        add(exitButton);
        add(Box.createVerticalStrut(20));

    }

    // Helper method untuk membuat tombol seragam
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(BUTTON_SIZE);
        button.setPreferredSize(BUTTON_SIZE);
        return button;
    }

    // @Override
    // public void setVisible(boolean aFlag) {
    //     super.setVisible(aFlag);
    //     System.out.println("shortcutMenuPanel.setVisible(" + aFlag + ")");
    //     Thread.dumpStack();
    // }
}
