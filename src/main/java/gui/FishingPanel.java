package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * FishingPanel adalah dialog popup untuk minigame tebak angka saat fishing.
 * Ketika attempts habis atau jawaban benar, panel akan otomatis tertutup.
 */
public class FishingPanel extends JDialog {
    private int maxAttempts;
    private int maxNumber;
    private int fishNumber;
    private int attempts = 0;
    private boolean caught = false;

    private JTextField inputField;
    private JLabel statusLabel;
    private JLabel attemptsLabel;

    public interface FishingResultListener {
        void onFishingResult(boolean caught, int fishNumber, int attempts);
    }

    public FishingPanel(Frame owner, int maxAttempts, int maxNumber, int fishNumber, FishingResultListener listener) {
        super(owner, "Fishing Minigame", true);
        this.maxAttempts = maxAttempts;
        this.maxNumber = maxNumber;
        this.fishNumber = fishNumber;

        setLayout(new BorderLayout());
        setResizable(false);

        // Center panel
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 8, 8));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        centerPanel.add(new JLabel("Guess the number (0 to " + maxNumber + "):", SwingConstants.CENTER));

        inputField = new JTextField();
        centerPanel.add(inputField);

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        centerPanel.add(statusLabel);

        add(centerPanel, BorderLayout.CENTER);

        // Attempts panel
        attemptsLabel = new JLabel("Attempt: 0 / " + maxAttempts, SwingConstants.CENTER);
        add(attemptsLabel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Submit logic
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess(listener);
            }
        });

        // Enter key support
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess(listener);
            }
        });

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(320, 180);
        setLocationRelativeTo(owner);
    }

    private void handleGuess(FishingResultListener listener) {
        String text = inputField.getText().trim();
        int guess;
        try {
            guess = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            statusLabel.setText("Not a valid number!");
            return;
        }
        if (guess < 0 || guess > maxNumber) {
            statusLabel.setText("Enter between 0 and " + maxNumber);
            return;
        }
        attempts++;
        attemptsLabel.setText("Attempt: " + attempts + " / " + maxAttempts);

        if (guess == fishNumber) {
            caught = true;
            statusLabel.setText("You caught the fish!");
            if (listener != null) listener.onFishingResult(true, fishNumber, attempts);
            // Delay a little before closing
            Timer timer = new Timer(1000, evt -> dispose());
            timer.setRepeats(false);
            timer.start();
        } else if (guess < fishNumber) {
            statusLabel.setText("Too low!");
        } else {
            statusLabel.setText("Too high!");
        }

        if (!caught && attempts >= maxAttempts) {
            statusLabel.setText("The fish got away...");
            if (listener != null) listener.onFishingResult(false, fishNumber, attempts);
            Timer timer = new Timer(1000, evt -> dispose());
            timer.setRepeats(false);
            timer.start();
        }
        inputField.setText("");
    }
}