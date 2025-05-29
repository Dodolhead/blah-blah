package gui;

import javax.swing.*;
import java.awt.*;
import entities.Player;

public class PlayerInfoPanel extends JPanel {
    private Player player;
    private JLabel nameLabel, genderLabel, energyLabel, partnerLabel, favoriteItemLabel, goldLabel;

    public PlayerInfoPanel(Player player) {
        this.player = player;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 200));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createTitledBorder("Player Info"));

        // Inisialisasi dan tambah label
        nameLabel = new JLabel();
        genderLabel = new JLabel();
        energyLabel = new JLabel();
        partnerLabel = new JLabel();
        favoriteItemLabel = new JLabel();
        goldLabel = new JLabel();

        add(nameLabel);
        add(genderLabel);
        add(energyLabel);
        add(partnerLabel);
        add(favoriteItemLabel);
        add(goldLabel);

        updateInfo();
    }

    public void updateInfo() {
        nameLabel.setText("Name         : " + player.getPlayerName());
        genderLabel.setText("Gender       : " + player.getGender());
        energyLabel.setText("Energy       : " + player.getEnergy());
        partnerLabel.setText("Partner      : " + (player.getPartner() != null ? player.getPartner().getNpcName() : "-"));
        favoriteItemLabel.setText("Favorite Item: " + getFavoriteItemName());
        goldLabel.setText("Gold         : " + player.getPlayerGold().getGold());
    }

    private String getFavoriteItemName() {
        if (player.getFavoriteItem() != null) {
            return player.getFavoriteItem().getItemName();
        } else {
            return "-";
        }
    }
}
