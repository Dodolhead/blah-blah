package src.entities;
import java.io.IOException;

import javax.imageio.ImageIO;
import src.map.*;

import src.gui.*;

public class Emily extends NPC{
    public Emily(GamePanel gp) {
        super("Emily", "single", gp);
        direction = "down";
        speed = 1;

        getNPCImage();
        npcLocation = new Location("Store", new Point(5 * gp.tileSize, 1 * gp.tileSize));
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
