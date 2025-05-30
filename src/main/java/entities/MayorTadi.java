package entities;

import gui.*;
import items.Item;
import items.ItemManager;

import java.io.IOException;
import map.*;

import javax.imageio.ImageIO;

public class MayorTadi extends NPC {
    public MayorTadi(GamePanel gp) {
        super("MayorTadi", "single", gp);
        direction = "down";
        speed = 1;

        getNPCImage();
        npcLocation = new Location("MayorTadiHome", new Point(5 * gp.tileSize, 1 * gp.tileSize));
        this.addLovedItem(ItemManager.getItem("Legend"));
        this.addLikedItem(ItemManager.getItem("Angler"));
        this.addLikedItem(ItemManager.getItem("Crimsonfish"));
        this.addLikedItem(ItemManager.getItem("Glacierfish"));
        // Hated: Semua item yang bukan loved/liked
        for (Item item : ItemManager.getAllItems()) {
            if (
                !item.getItemName().equals("Legend") &&
                !item.getItemName().equals("Angler") &&
                !item.getItemName().equals("Crimsonfish") &&
                !item.getItemName().equals("Glacierfish")
            ) {
                this.addHatedItem(item);
            }
        }
    }

    public void getNPCImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/tadiatas1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/tadiatas2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/tadibawah1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/tadibawah2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/tadikiri1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/tadikiri2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/tadikanan1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/tadikanan2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
