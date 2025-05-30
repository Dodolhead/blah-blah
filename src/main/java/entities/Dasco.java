package entities;

import gui.*;
import items.ItemManager;

import java.io.IOException;
import map.*;

import javax.imageio.ImageIO;

public class Dasco extends NPC {
    public Dasco(GamePanel gp) {
        super("Dasco", "single", gp);
        direction = "down";
        speed = 1;

        getNPCImage();
        npcLocation = new Location("DascoHome", new Point(5 * gp.tileSize, 1 * gp.tileSize));
        this.addLovedItem(ItemManager.getItem("The Legends of Spakbor"));
        this.addLovedItem(ItemManager.getItem("Cooked Pig's Head"));
        this.addLovedItem(ItemManager.getItem("Wine"));
        this.addLovedItem(ItemManager.getItem("Fugu"));
        this.addLovedItem(ItemManager.getItem("Spakbor Salad"));
        this.addLikedItem(ItemManager.getItem("Fish Sandwich"));
        this.addLikedItem(ItemManager.getItem("Fish Stew"));
        this.addLikedItem(ItemManager.getItem("Baguette"));
        this.addLikedItem(ItemManager.getItem("Fish and Chips"));
        // Hated: Legend, Grape, Cauliflower, Wheat, Pufferfish, Salmon
        this.addHatedItem(ItemManager.getItem("Legend"));
        this.addHatedItem(ItemManager.getItem("Grape"));
        this.addHatedItem(ItemManager.getItem("Cauliflower"));
        this.addHatedItem(ItemManager.getItem("Wheat"));
        this.addHatedItem(ItemManager.getItem("Pufferfish"));
        this.addHatedItem(ItemManager.getItem("Salmon"));
    }

    public void getNPCImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/dascoatas1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/dascoatas2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/dascobawah1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/dascobawah2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/dascokiri1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/dascokiri2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/dascokanan1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/dascokanan2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
