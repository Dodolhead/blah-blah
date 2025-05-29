package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean till, recoverLand, planting;
    public boolean inventoryToggle;
    public boolean storeToggle;
    public boolean playerInfoToggle;
    public boolean fishAction;
    public boolean interactNPC;
    public boolean harvestAction;
    public boolean eatAction,waterAction,sleepAction,watchAction,cookingAction;
    public boolean placeFurniture;
    public boolean addFuel;
    

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // ==== gameState jadi NPC Dialogue Mode ====
        if (gp.gameState == GamePanel.GameState.NPC_DIALOGUE) {
            if (gp.chatPanel != null && gp.chatPanel.isShowing()) {
                switch (code) {
                    case KeyEvent.VK_N:
                        gp.chatPanel.hideDialogue();
                        gp.gameState = GamePanel.GameState.NORMAL;
                        break;
                    case KeyEvent.VK_C:
                    case KeyEvent.VK_G:
                    case KeyEvent.VK_Q:
                    case KeyEvent.VK_M:
                        gp.chatPanel.handleCommand((char) code);
                        break;
                }
            }
            return; // stop di sini supaya gak lanjut ke gameplay keys
        }

        // ==== Gameplay Mode ====
        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_T -> till = true;
            case KeyEvent.VK_R -> recoverLand = true;
            case KeyEvent.VK_P -> planting = true;
            case KeyEvent.VK_I -> inventoryToggle = true;
            case KeyEvent.VK_O -> storeToggle = true;
            case KeyEvent.VK_V -> playerInfoToggle = true;
            case KeyEvent.VK_F -> fishAction = true;
            case KeyEvent.VK_N -> interactNPC = true;
            case KeyEvent.VK_H -> harvestAction = true;
        }
        if (code == KeyEvent.VK_E){
            eatAction = true;
        }
        if (code == KeyEvent.VK_Y){
            sleepAction = true;
        }
        if (code == KeyEvent.VK_U){
            watchAction = true;
        }
        if (code == KeyEvent.VK_L){
            waterAction = true;
        }
        if (code == KeyEvent.VK_G){
            placeFurniture = true;
        }
        if (code == KeyEvent.VK_C){
            cookingAction = true;
        }
        if (code == KeyEvent.VK_J){
            addFuel = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_T -> till = false;
            case KeyEvent.VK_R -> recoverLand = false;
            case KeyEvent.VK_P -> planting = false;
            case KeyEvent.VK_F -> fishAction = false;
            case KeyEvent.VK_N -> interactNPC = false;
            case KeyEvent.VK_H -> harvestAction = false;
        }
        if (code == KeyEvent.VK_E){
            eatAction = false;
        }
        if (code == KeyEvent.VK_Y){
            sleepAction = false;
        }
        if (code == KeyEvent.VK_U){
            watchAction = false;
        }
        if (code == KeyEvent.VK_L){
            waterAction = false;
        }
        if (code == KeyEvent.VK_G){
            placeFurniture = false;
        }
        if (code == KeyEvent.VK_C){
            cookingAction = false;
        }
        if (code == KeyEvent.VK_J){
            addFuel = false;
        }
    }
}
