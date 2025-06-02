package entities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import map.*;

import javax.imageio.ImageIO;
import gui.*;
import items.Item;
import items.ItemManager;

public class Caroline extends NPC{
    public Caroline(GamePanel gp) {
        // Konstruktor NPC diberi null
        super("Caroline", "single", gp, null, null, null);
        direction = "down";
        speed = 1;

        // Inisialisasi dan isi list
        List<Item> loved = new ArrayList<>();
        loved.add(ItemManager.getItem("Firewood"));
        loved.add(ItemManager.getItem("Coal"));

        List<Item> liked = new ArrayList<>();
        liked.add(ItemManager.getItem("Potato"));
        liked.add(ItemManager.getItem("Wheat"));

        List<Item> hated = new ArrayList<>();
        hated.add(ItemManager.getItem("Hot Pepper"));

        // Set list ke NPC
        setLoved(loved);
        setLiked(liked);
        setHated(hated);

        getNPCImage();
        npcLocation = new Location("CarolineHome", new Point(2 * gp.tileSize, 3 * gp.tileSize));
    }

    @Override
    public void getNPCImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/carolineatas1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/carolineatas2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/carolinebawah1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/carolinebawah2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/carolinekiri1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/carolinekiri2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/carolinekanan1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/carolinekanan2.png"));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}