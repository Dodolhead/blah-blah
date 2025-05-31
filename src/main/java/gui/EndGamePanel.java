package gui;

import endgame.EndGameStats;
import entities.NPC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class EndGamePanel extends JPanel {

    public EndGamePanel(EndGameStats stats) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("🎉 End Game Statistics 🎉");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        add(Box.createVerticalStrut(10));

        add(new JLabel("Current Gold: " + stats.currentGold + "g"));
        add(new JLabel("Total Income: " + stats.totalIncome + "g"));
        add(new JLabel("Total Expenditure: " + stats.totalExpenditure + "g"));
        add(new JLabel("Avg. Season Income: " + stats.avgIncome + "g"));
        add(new JLabel("Avg. Season Expenditure: " + stats.avgExpenditure + "g"));
        add(new JLabel("Total Days Played: " + stats.totalDays));
        add(new JLabel("Crops Harvested: " + stats.cropsHarvested));
        add(Box.createVerticalStrut(20));

        // --- NPC Table ---
        String[] npcColumns = {"Name", "Relationship", "Chat", "Gift", "Visit"};
        DefaultTableModel npcModel = new DefaultTableModel(npcColumns, 0);

        for (NPC npc : stats.npcList) {
            npcModel.addRow(new Object[]{
                npc.getNpcName(),
                npc.getRelationshipStatus(),
                npc.chattingFrequency,
                npc.giftingFrequency,
                npc.visitingFrequency
            });
        }

        JTable npcTable = new JTable(npcModel);
        JScrollPane npcScroll = new JScrollPane(npcTable);
        npcScroll.setBorder(BorderFactory.createTitledBorder("NPCs Status"));
        add(npcScroll);
        add(Box.createVerticalStrut(20));

        // --- Fish Table ---
        String[] fishColumns = {"Fish Type", "Amount"};
        DefaultTableModel fishModel = new DefaultTableModel(fishColumns, 0);
        for (Map.Entry<String, Integer> entry : stats.fishByRarity.entrySet()) {
            fishModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
        JTable fishTable = new JTable(fishModel);
        JScrollPane fishScroll = new JScrollPane(fishTable);
        fishScroll.setBorder(BorderFactory.createTitledBorder("Fish Caught by Rarity"));
        add(fishScroll);
    }
}
