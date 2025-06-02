package entities;

import gui.*;
import items.Item;
import items.ItemManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import map.*;

import javax.imageio.ImageIO;

public class Abigail extends NPC {
    public Abigail(GamePanel gp) {
        // Konstruktor NPC diberi null/null/null
        super("Abigail", "single", gp, null, null, null);
        direction = "down";
        speed = 1;

        // Buat dan isi list
        List<Item> loved = new ArrayList<>();
        loved.add(ItemManager.getItem("Blueberry"));
        loved.add(ItemManager.getItem("Melon"));
        loved.add(ItemManager.getItem("Pumpkin"));
        loved.add(ItemManager.getItem("Grape"));
        loved.add(ItemManager.getItem("Cranberry"));

        List<Item> liked = new ArrayList<>();
        liked.add(ItemManager.getItem("Baguette"));
        liked.add(ItemManager.getItem("Pumpkin Pie"));
        liked.add(ItemManager.getItem("Wine"));

        List<Item> hated = new ArrayList<>();
        hated.add(ItemManager.getItem("Hot Pepper"));
        hated.add(ItemManager.getItem("Cauliflower"));
        hated.add(ItemManager.getItem("Parsnip"));
        hated.add(ItemManager.getItem("Wheat"));

        // Set ke NPC parent
        setLoved(loved);
        setLiked(liked);
        setHated(hated);

        getNPCImage();
        npcLocation = new Location("AbigailHome", new Point(5 * gp.tileSize, 1 * gp.tileSize));
    }

    @Override
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