package src.engine;

import javax.swing.*;
import java.awt.*;

public class CreatePlayerPanel extends JPanel {
    public CreatePlayerPanel(JFrame frame) {
        setLayout(null);
        setPreferredSize(new Dimension(800, 600));

        // Background
        ImageIcon background = new ImageIcon("res/menu/backgroundDefault.jpg");
        JLabel bgLabel = new JLabel(background);
        bgLabel.setBounds(0, 0, 800, 600);

        // Title
        JLabel titleLabel = new JLabel("Create Your Character");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(250, 50, 300, 30);

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
        JButton startButton = new JButton("Start Game");
        startButton.setBounds(300, 280, 200, 40);

        startButton.addActionListener(e -> {
            String name = nameField.getText();
            String gender = maleButton.isSelected() ? "male" : "female";
            String farmName = farmField.getText();

            if (name.isEmpty() || gender.isEmpty() || farmName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields.");
                return;
            }

            GamePanel gamePanel = new GamePanel(name, gender, farmName);
            frame.getContentPane().removeAll();
            frame.add(gamePanel);
            frame.pack();
            gamePanel.startGameThread();
            gamePanel.requestFocusInWindow();
            frame.revalidate();
            frame.repaint();
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
