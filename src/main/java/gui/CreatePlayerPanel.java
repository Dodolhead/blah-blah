package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class CreatePlayerPanel extends JPanel {
    MainPanel mainPanel;

    public CreatePlayerPanel(JFrame frame, MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(null);
        setPreferredSize(new Dimension(800, 600));

        // Background
        JLabel bgLabel;
        try {
            BufferedImage bgImg = ImageIO.read(getClass().getResourceAsStream("/res/menu/backgroundDefault.jpg"));
            bgLabel = new JLabel(new ImageIcon(bgImg));
        } catch (IOException e) {
            bgLabel = new JLabel("BG NOT FOUND");
        }
        bgLabel.setBounds(0, 0, 800, 600);

        // Title
        JLabel titleLabel;
        try {
            BufferedImage titleImg = ImageIO.read(getClass().getResourceAsStream("/res/menu/cyc.png"));
            titleLabel = new JLabel(new ImageIcon(titleImg));
        } catch (IOException e) {
            titleLabel = new JLabel("TITLE NOT FOUND");
        }
        titleLabel.setBounds(150, 50, 500, 63);

        // Name input
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setBounds(200, 120, 100, 30);
        JTextField nameField = new JTextField();
        nameField.setBounds(300, 120, 200, 30);

        // Gender selection
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setForeground(Color.BLACK);
        genderLabel.setBounds(200, 170, 100, 30);
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        maleButton.setOpaque(false);
        femaleButton.setOpaque(false);
        maleButton.setForeground(Color.WHITE);
        femaleButton.setForeground(Color.WHITE);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        maleButton.setBounds(300, 170, 100, 30);
        femaleButton.setBounds(400, 170, 100, 30);

        // Farm Name input
        JLabel farmLabel = new JLabel("Farm Name:");
        farmLabel.setForeground(Color.BLACK);
        farmLabel.setBounds(200, 220, 100, 30);
        JTextField farmField = new JTextField();
        farmField.setBounds(300, 220, 200, 30);

        // Start button
        JButton startButton;
        try {
            BufferedImage startImg = ImageIO.read(getClass().getResourceAsStream("/res/menu/start.png"));
            startButton = new JButton(new ImageIcon(startImg));
        } catch (IOException e) {
            startButton = new JButton("Start");
        }
        startButton.setBounds(300, 300, 200, 70);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);

        startButton.addActionListener(e -> {
            String name = nameField.getText();
            String gender = maleButton.isSelected() ? "male" : (femaleButton.isSelected() ? "female" : "");
            String farmName = farmField.getText();

            if (name.isEmpty() || gender.isEmpty() || farmName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields.");
                return;
            }

            mainPanel.startGame(name, gender, farmName);

        });

        add(titleLabel);
        add(nameLabel);
        add(nameField);
        add(genderLabel);
        add(maleButton);
        add(femaleButton);
        add(farmLabel);
        add(farmField);
        add(startButton);
        add(bgLabel);
    }
}