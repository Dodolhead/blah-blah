package entities;

import gui.*;
import items.Item;
import items.ItemManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import map.*;

import javax.imageio.ImageIO;

public class MayorTadi extends NPC {
    public MayorTadi(GamePanel gp) {
        super("MayorTadi", "single", gp, null, null, null);
        direction = "down";
        speed = 1;

        // Inisialisasi dan isi list
        List<Item> loved = new ArrayList<>();
        loved.add(ItemManager.getItem("Legend"));

        List<Item> liked = new ArrayList<>();
        liked.add(ItemManager.getItem("Angler"));
        liked.add(ItemManager.getItem("Crimsonfish"));
        liked.add(ItemManager.getItem("Glacierfish"));

        List<Item> hated = new ArrayList<>();
        for (Item item : ItemManager.getAllItems()) {
            String name = item.getItemName();
            if (
                !name.equals("Legend") &&
                !name.equals("Angler") &&
                !name.equals("Crimsonfish") &&
                !name.equals("Glacierfish")
            ) {
                hated.add(item);
            }
        }

        // Set ke NPC
        setLoved(loved);
        setLiked(liked);
        setHated(hated);

        getNPCImage();
        npcLocation = new Location("MayorTadiHome", new Point(5 * gp.tileSize, 1 * gp.tileSize));
    }

    @Override
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