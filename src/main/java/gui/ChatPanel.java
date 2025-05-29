package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import entities.NPC;

public class ChatPanel {
    private GamePanel gp;
    private boolean isShowing = false;
    private String currentText = "";
    private NPC currentNPC = null;

    private final int panelWidth;
    private final int panelHeight;
    private final int panelX;
    private final int panelY;

    public ChatPanel(GamePanel gp) {
        this.gp = gp;
        this.panelWidth = gp.screenWidth - 100;
        this.panelHeight = 100;
        this.panelX = 20;
        this.panelY = gp.screenHeight - panelHeight - 150;
    }

    public void showDialogue(NPC npc, String text) {
        if (npc == null || text == null) return;
        this.currentNPC = npc;
        this.currentText = text;
        this.isShowing = true;
        System.out.println("DEBUG: Menampilkan dialog: " + text);
    }

    public void hideDialogue() {
        this.isShowing = false;
        this.currentNPC = null;
        this.currentText = "";
        System.out.println("DEBUG: Dialog disembunyikan.");
    }

    public void update() {
        if (currentNPC != null && 
            !currentNPC.npcLocation.getName().equals(gp.player.getPlayerLocation().getName())) {
            hideDialogue(); // Sembunyikan jika tidak di map yang sama
        }
    }

    public void draw(Graphics2D g2) {
        if (!isShowing || currentText == null || currentText.isEmpty()) return;

        // Panel background
        g2.setColor(new Color(0, 0, 0, 170));
        g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 20, 20);

        // Panel border
        g2.setColor(Color.white);
        g2.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 20, 20);

        // Teks
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.setColor(Color.white);

        // Nama NPC
        if (currentNPC != null) {
            g2.drawString(currentNPC.getNpcName() + ":", panelX + 20, panelY + 25);
        }

        // Multiline text wrapping
        FontMetrics fm = g2.getFontMetrics();
        int lineHeight = fm.getHeight();
        int maxWidth = panelWidth - 40;

        List<String> lines = wrapText(currentText, fm, maxWidth);
        int y = panelY + 45;

        for (String line : lines) {
            g2.drawString(line, panelX + 20, y);
            y += lineHeight;
        }
    }

    // Bungkus teks agar tidak keluar dari panel
    private List<String> wrapText(String text, FontMetrics fm, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine + (currentLine.length() > 0 ? " " : "") + word;
            if (fm.stringWidth(testLine) > maxWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                currentLine = new StringBuilder(testLine);
            }
        }
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public NPC getCurrentNPC() {
        return currentNPC;
    }
}
