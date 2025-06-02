package entities;
import gui.*;
import items.Item;
import items.ItemManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import map.*;
import javax.imageio.ImageIO;

public class Emily extends NPC {
    public Emily(GamePanel gp) {
        // Konstruktor NPC tetap diberi null dulu, atau bisa new ArrayList kosong
        super("Emily", "single", gp, null, null, null);
        direction = "down";
        speed = 1;

        // Buat list sendiri, lalu set ke NPC dengan setter
        List<Item> loved = new ArrayList<>();
        List<Item> liked = new ArrayList<>();
        List<Item> hated = new ArrayList<>();

        for (Item item : ItemManager.getAllItems()) {
            if (item instanceof items.Seed) {
                loved.add(item);
            }
        }
        liked.add(ItemManager.getItem("Catfish"));
        liked.add(ItemManager.getItem("Salmon"));
        liked.add(ItemManager.getItem("Sardine"));
        hated.add(ItemManager.getItem("Coal"));
        hated.add(ItemManager.getItem("Firewood"));

        // Pakai setter
        setLoved(loved);
        setLiked(liked);
        setHated(hated);

        getNPCImage();
        npcLocation = new Location("Store", new Point(5 * gp.tileSize, 1 * gp.tileSize));
    }

    @Override
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