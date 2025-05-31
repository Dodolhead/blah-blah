package entities;

import gui.*;
import items.Item;
import items.ItemManager;

import java.io.IOException;
import map.*;

import javax.imageio.ImageIO;

public class Perry extends NPC {
    public Perry(GamePanel gp) {
        super("Perry", "single", gp);
        direction = "down";
        speed = 1;

        getNPCImage();
        npcLocation = new Location("PerryHome", new Point(5 * gp.tileSize, 1 * gp.tileSize));
        this.addLovedItem(ItemManager.getItem("Cranberry"));
        this.addLovedItem(ItemManager.getItem("Blueberry"));
        this.addLikedItem(ItemManager.getItem("Wine"));
        // Hated: semua item Fish
        for (Item item : ItemManager.getAllItems()) {
            if (item instanceof items.Fish) {
                this.addHatedItem(item);
            }
        }
    }

    public void getNPCImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/perryatas1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/perryatas2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/perrybawah1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/perrybawah2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/perrykiri1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/perrykiri2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/perrykanan1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/perrykanan2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
