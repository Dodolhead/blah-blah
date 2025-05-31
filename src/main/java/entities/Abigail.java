package entities;

import gui.*;
import items.ItemManager;

import java.io.IOException;
import map.*;

import javax.imageio.ImageIO;

public class Abigail extends NPC {
    public Abigail(GamePanel gp) {
        super("Abigail", "single", gp);
        direction = "down";
        speed = 1;

        getNPCImage();
        npcLocation = new Location("AbigailHome", new Point(5 * gp.tileSize, 1 * gp.tileSize));
        this.addLovedItem(ItemManager.getItem("Blueberry"));
        this.addLovedItem(ItemManager.getItem("Melon"));
        this.addLovedItem(ItemManager.getItem("Pumpkin"));
        this.addLovedItem(ItemManager.getItem("Grape"));
        this.addLovedItem(ItemManager.getItem("Cranberry"));
        this.addLikedItem(ItemManager.getItem("Baguette"));
        this.addLikedItem(ItemManager.getItem("Pumpkin Pie"));
        this.addLikedItem(ItemManager.getItem("Wine"));
        this.addHatedItem(ItemManager.getItem("Hot Pepper"));
        this.addHatedItem(ItemManager.getItem("Cauliflower"));
        this.addHatedItem(ItemManager.getItem("Parsnip"));
        this.addHatedItem(ItemManager.getItem("Wheat"));
    }

    public void getNPCImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/abigailatas1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/abigailatas2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/abigailbawah1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/abigailbawah2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/abigailkiri1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/abigailkiri2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/abigailkanan1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/abigailkanan2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
