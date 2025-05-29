package gui;

import javax.swing.*;
import java.awt.*;
import tsw.Time;

public class TimeSeasonWeatherPanel extends JPanel {
    private final JLabel infoLabel;
    private final Time time;

    public TimeSeasonWeatherPanel(Time time) {
        this.time = time;
        setPreferredSize(new Dimension(400, 30)); // Lebar dan tinggi panel
        setBackground(new Color(50, 50, 50));
        setLayout(new BorderLayout());

        infoLabel = new JLabel();
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        infoLabel.setForeground(Color.WHITE);

        add(infoLabel, BorderLayout.CENTER);

        updateDisplay();
    }

    public void updateDisplay() {
        // Dapatkan teks dari Time
        String text = time.getTimeDay(); // contoh: SEASON: SPRING - WEATHER: SUNNY - Day-1 - 06:00
        infoLabel.setText(text);
    }
}

