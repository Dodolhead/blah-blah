package src.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuPanel extends JPanel implements ActionListener {
    private JButton play;
    private JButton exit;
    private MainPanel mainPanel;

    public MenuPanel(JFrame frame, MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(800, 600));

        /*
         * 
         * note: kalo mau ganti2 ukuran jgn lupa resize biar ke tengah lagi gpt aj coba dh
         *
         */


        // Icon label
        ImageIcon icon = new ImageIcon("res/menu/view.jpg");
        JLabel iconLabel = new JLabel(icon);
        int iconWidth = 400;
        int iconHeight = 100;
        iconLabel.setBounds((800 - iconWidth) / 2, 100, iconWidth, iconHeight); 

        // Tombol Play dan Exit (di tengah bawah icon)
        //play = new JButton("Play");
        // bro pake dibawah ini kl mau ganti buttonny jd gambar
        play = new JButton(new ImageIcon("res/menu/play.png")); 
        play.setBounds(300, 250, 200, 70);
        play.setBorderPainted(false); 
        play.setContentAreaFilled(false); 
        play.setFocusPainted(false); 
        play.setOpaque(false); 
        play.addActionListener(this);
        

        //exit = new JButton("Exit");
        // bro pake dibawha ini kl mau ganti buttonny jd gambar
        exit = new JButton(new ImageIcon("res/menu/exit.png"));
        exit.setBounds(300, 350, 200, 70);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setFocusPainted(false);
        exit.setOpaque(false);
        exit.addActionListener(this);

        // Background label
        ImageIcon background = new ImageIcon("res/menu/bg.jpg");
        JLabel bgLabel = new JLabel(background);
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
