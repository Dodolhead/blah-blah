package gui;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean till,recoverLand, planting;
    public boolean inventoryToggle;
    public boolean storeToggle;
    public boolean playerInfoToggle;
    public boolean interactNPC;
    public boolean fishAction;
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
           upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
           leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
           downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
           rightPressed = true;
        }
        if (code == KeyEvent.VK_T){
            till = true;
        }
        if (code == KeyEvent.VK_R){
            recoverLand = true;
        }
        if (code == KeyEvent.VK_P){
            planting = true;
        }
        if (code == KeyEvent.VK_N){
            interactNPC = true;
        }
        if (code == KeyEvent.VK_F){
            fishAction = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_T){
            till = false;
        }
        if (code == KeyEvent.VK_R){
            recoverLand = false;
        }
        if (code == KeyEvent.VK_P){
            planting = false;
        }
        if (code == KeyEvent.VK_I) {
            inventoryToggle = true;
        }
        if (code == KeyEvent.VK_O) {
            storeToggle = true;
        }
        if (code == KeyEvent.VK_V) {
            playerInfoToggle = true;
        }
        if (code == KeyEvent.VK_N){
            interactNPC = false;
        }
        if (code == KeyEvent.VK_F){
            fishAction = false;
        }
    }
}
