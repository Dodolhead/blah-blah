package entities;

import gui.*;
import items.Item;
import items.ItemManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import map.*;

import javax.imageio.ImageIO;

public class Dasco extends NPC {
    public Dasco(GamePanel gp) {
        super("Dasco", "single", gp, null, null, null);
        direction = "down";
        speed = 1;

        // Inisialisasi dan isi list
        List<Item> loved = new ArrayList<>();
        loved.add(ItemManager.getItem("The Legends of Spakbor"));
        loved.add(ItemManager.getItem("Cooked Pig's Head"));
        loved.add(ItemManager.getItem("Wine"));
        loved.add(ItemManager.getItem("Fugu"));
        loved.add(ItemManager.getItem("Spakbor Salad"));

        List<Item> liked = new ArrayList<>();
        liked.add(ItemManager.getItem("Fish Sandwich"));
        liked.add(ItemManager.getItem("Fish Stew"));
        liked.add(ItemManager.getItem("Baguette"));
        liked.add(ItemManager.getItem("Fish and Chips"));

        List<Item> hated = new ArrayList<>();
        hated.add(ItemManager.getItem("Legend"));
        hated.add(ItemManager.getItem("Grape"));
        hated.add(ItemManager.getItem("Cauliflower"));
        hated.add(ItemManager.getItem("Wheat"));
        hated.add(ItemManager.getItem("Pufferfish"));
        hated.add(ItemManager.getItem("Salmon"));

        // Set ke NPC
        setLoved(loved);
        setLiked(liked);
        setHated(hated);

        getNPCImage();
        npcLocation = new Location("DascoHome", new Point(5 * gp.tileSize, 1 * gp.tileSize));
    }

    @Override
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