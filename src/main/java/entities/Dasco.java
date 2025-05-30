package entities;

import gui.*;
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
