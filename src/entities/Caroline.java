package src.entities;
import java.io.IOException;

import javax.imageio.ImageIO;
import src.map.*;

import src.gui.*;

public class Caroline extends NPC{
    public Caroline(GamePanel gp) {
        super("Caroline", "single", gp);
        direction = "down";
        speed = 1;

        getNPCImage();
        npcLocation = new Location("Farm", new Point(2 * gp.tileSize, 3 * gp.tileSize));
    }

    public void getNPCImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/atas1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/atas2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/bawah1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/bawah2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/kiri1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/kiri2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/kanan1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/kanan2.png"));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
