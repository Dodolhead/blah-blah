package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import actions.GiftingAction;
import actions.MarryingAction;
import actions.ProposingAction;
import entities.NPC;
import entities.Player;
import gui.GamePanel.GameState;
import items.Item;

public class ChatPanel {
    private GamePanel gp;
    private boolean isShowing = false;
    private String currentText = "";
    private NPC currentNPC = null;
    private Player player;
    private GameState gameState = GameState.NORMAL;

    private final int panelWidth;
    private final int panelHeight;
    private final int panelX;
    private final int panelY;

    public ChatPanel(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        this.panelWidth = gp.screenWidth - 100;
        this.panelHeight = 100;
        this.panelX = 20;
        this.panelY = gp.screenHeight - panelHeight - 150;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState newState) {
        this.gameState = newState;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public NPC getCurrentNPC() {
        return currentNPC;
    }

    public void showDialogue(NPC npc, String text) {
        showDialogue(npc, text, GameState.NORMAL);
    }

    public void showDialogue(NPC npc, String text, GameState state) {
        if (npc == null || text == null) return;
        this.currentNPC = npc;
        this.currentText = text;
        this.isShowing = true;
        this.gameState = state;
        System.out.println("DEBUG: Menampilkan dialog [" + state + "]: " + text);
    }

    public void hideDialogue() {
        this.isShowing = false;
        this.currentNPC = null;
        this.currentText = "";
        this.gameState = GameState.NORMAL;
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

    public void handleCommand(char commandKey) {
        if (gp.gameState != GamePanel.GameState.NPC_DIALOGUE) return;

        // Hanya boleh pakai command saat mode NPC_DIALOGUE
        if (gp.gameState != GameState.NPC_DIALOGUE) {
            System.out.println("DEBUG: Command hanya dapat digunakan dalam mode NPC_DIALOGUE.");
            return;
        }

        switch (Character.toUpperCase(commandKey)) {
            case 'C':
                showDialogue(currentNPC, currentNPC.getRandomDialogue());
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    gp.chatPanel.hideDialogue();
                }).start();
                break;

            case 'G':
                Item giftItem = gp.inventoryPanel.getSelectedItem();
                int amount = 1;
                if (giftItem == null) {
                    showDialogue(currentNPC, "You have no item to gift.");
                    break;
                }

                boolean gifted = new GiftingAction(giftItem, currentNPC, amount).execute(player);
                if (gifted) {
                    showDialogue(currentNPC, "Thank you for the gift!");
                } else {
                    showDialogue(currentNPC, "I don't really like that...");
                }
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    gp.chatPanel.hideDialogue();
                }).start();
                break;

            case 'Q':
                boolean proposed = new ProposingAction(currentNPC,gp).execute(player);
                if (proposed) {
                    showDialogue(currentNPC, "Will you be mine forever?");
                } else {
                    showDialogue(currentNPC, "I don't think we're ready for that...");
                }
                break;

            case 'M':
                boolean married = new MarryingAction(currentNPC,gp).execute(player);
                if (married) {
                    showDialogue(currentNPC, "We're married now!");
                } else {
                    showDialogue(currentNPC, "We need to be closer before marrying.");
                }
                break;

            default:
                showDialogue(currentNPC, "Unknown command: " + commandKey, GamePanel.GameState.NPC_DIALOGUE);
                break;
        }

        // Kembali ke NORMAL setelah perintah
        gp.gameState = GamePanel.GameState.NORMAL;
    }
}
