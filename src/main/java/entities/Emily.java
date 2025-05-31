package entities;
import gui.*;
import items.Item;
import items.ItemManager;

import java.io.IOException;
import map.*;

import javax.imageio.ImageIO;

public class Emily extends NPC{
    public Emily(GamePanel gp) {
        super("Emily", "single", gp);
        direction = "down";
        speed = 1;

        getNPCImage();
        npcLocation = new Location("Store", new Point(5 * gp.tileSize, 1 * gp.tileSize));
        for (Item item : ItemManager.getAllItems()) {
            if (item instanceof items.Seed) {
                this.addLovedItem(item);
            }
        }
        this.addLikedItem(ItemManager.getItem("Catfish"));
        this.addLikedItem(ItemManager.getItem("Salmon"));
        this.addLikedItem(ItemManager.getItem("Sardine"));
        this.addHatedItem(ItemManager.getItem("Coal"));
        this.addHatedItem(ItemManager.getItem("Firewood"));
        this.addHatedItem(ItemManager.getItem("Wood")); // jika ada
    }

    public void getNPCImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/emilyatas1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/emilyatas2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/emilybawah1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/emilybawah2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/emilykiri1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/emilykiri2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/emilykanan1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/emilykanan2.png"));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
