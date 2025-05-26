package src.gui;

import java.awt.Rectangle;
import src.entities.*;
public class TileChecker {
    GamePanel gp;

    public TileChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Player player) {
        boolean isAtEdge = false;
        int playerLeftWorldX = player.getPlayerLocation().getCurrentPoint().getX() + player.playerHitBox.x;
        int playerRightWorldX = player.getPlayerLocation().getCurrentPoint().getX() + player.playerHitBox.x + player.playerHitBox.width;
        int playerTopWorldY = player.getPlayerLocation().getCurrentPoint().getY() + player.playerHitBox.y;
        int playerBottomWorldY = player.getPlayerLocation().getCurrentPoint().getY() + player.playerHitBox.y + player.playerHitBox.height;

        int playerLeftCol = playerLeftWorldX / gp.tileSize;
        int playerRightCol = playerRightWorldX / gp.tileSize;
        int playerTopRow = playerTopWorldY / gp.tileSize;
        int playerBottomRow = playerBottomWorldY / gp.tileSize;

        char tileChar1 = ' ';
        char tileChar2 = ' ';
        int tileNum1 = -1, tileNum2 = -1;

        player.collisionOn = false;

        switch (player.direction) {
                case "up": {
                    double newTopY = playerTopWorldY - player.speed;
                    int checkRow = (int) Math.floor(newTopY / (double) gp.tileSize);
                    if (checkRow < 0) {
                        isAtEdge = true;
                        break;
                    }

                    if (playerLeftCol >= 0 && playerLeftCol < gp.maxWorldCol)
                        tileChar1 = gp.tileM.mapTiles[checkRow][playerLeftCol];
                    if (playerRightCol >= 0 && playerRightCol < gp.maxWorldCol)
                        tileChar2 = gp.tileM.mapTiles[checkRow][playerRightCol];
                    break;
                }
                case "down": {
                int checkRow = (playerBottomWorldY + player.speed) / gp.tileSize;

                // Boundary check
                if (checkRow >= gp.maxWorldRow) {
                    isAtEdge = true;
                    break;
                }

                if (playerLeftCol >= 0 && playerLeftCol < gp.maxWorldCol)
                    tileChar1 = gp.tileM.mapTiles[checkRow][playerLeftCol];
                if (playerRightCol >= 0 && playerRightCol < gp.maxWorldCol)
                    tileChar2 = gp.tileM.mapTiles[checkRow][playerRightCol];

                break;
                }
                case "left": {
                    double newLeftX = playerLeftWorldX - player.speed;
                    int checkCol = (int) Math.floor(newLeftX / (double) gp.tileSize);

                    if (checkCol < 0) {
                        isAtEdge = true;
                        break;
                    }

                    if (playerTopRow >= 0 && playerTopRow < gp.maxWorldRow)
                        tileChar1 = gp.tileM.mapTiles[playerTopRow][checkCol];
                    if (playerBottomRow >= 0 && playerBottomRow < gp.maxWorldRow)
                        tileChar2 = gp.tileM.mapTiles[playerBottomRow][checkCol];
                    break;
                }
            case "right": {
                int checkCol = (playerRightWorldX + player.speed) / gp.tileSize;

                // Boundary check
                if (checkCol >= gp.maxWorldCol) {
                    isAtEdge = true;
                    break;
                }

                if (playerTopRow >= 0 && playerTopRow < gp.maxWorldRow)
                    tileChar1 = gp.tileM.mapTiles[playerTopRow][checkCol];
                if (playerBottomRow >= 0 && playerBottomRow < gp.maxWorldRow)
                    tileChar2 = gp.tileM.mapTiles[playerBottomRow][checkCol];

                break;
            }
            default:
        }

        // Ambil index tile dari char
        tileNum1 = gp.tileM.getTileIndex(tileChar1);
        tileNum2 = gp.tileM.getTileIndex(tileChar2);

        // Cek enter/exit house
        if ((tileChar1 == 'D' || tileChar2 == 'D')) {
            if (gp.player.getPlayerLocation().getName().equals("Farm") && player.direction.equals("up")) {
                gp.enterHouse();
            }
            else if (gp.player.getPlayerLocation().getName().equals("House") && player.direction.equals("down")) {
                gp.returnToFarm();
            }
        }

        // Cek collision tile
        if ((tileNum1 >= 0 && tileNum1 < gp.tileM.tile.length && gp.tileM.tile[tileNum1] != null && gp.tileM.tile[tileNum1].collision) ||
            (tileNum2 >= 0 && tileNum2 < gp.tileM.tile.length && gp.tileM.tile[tileNum2] != null && gp.tileM.tile[tileNum2].collision)) {
            player.collisionOn = true;
        }

        if (isAtEdge) {
            player.collisionOn = true;
            if (!player.getPlayerLocation().getName().equals("House")){
                gp.showWorldMapPanel();
            }
        }
    }

    public void checkNPC(Player player) {
        player.collisionWithNPC = false;

        Rectangle playerArea = new Rectangle(
            player.getPlayerLocation().getCurrentPoint().getX() + player.playerHitBox.x,
            player.getPlayerLocation().getCurrentPoint().getY() + player.playerHitBox.y,
            player.playerHitBox.width,
            player.playerHitBox.height
        );

        // Geser area ke arah gerakan
        switch (player.direction) {
            case "up":
                playerArea.y -= player.speed;
                break;
            case "down":
                playerArea.y += player.speed;
                break;
            case "left":
                playerArea.x -= player.speed;
                break;
            case "right":
                playerArea.x += player.speed;
                break;
        }

        for (NPC npc : NPCManager.getNPCList()) {
            if (npc == null) continue;

            // Cek hanya jika ada di map yang sama
            if (!npc.npcLocation.getName().equals(player.getPlayerLocation().getName())) continue;

            Rectangle npcArea = new Rectangle(
                npc.npcLocation.getCurrentPoint().getX() + npc.npcHitBox.x,
                npc.npcLocation.getCurrentPoint().getY() + npc.npcHitBox.y,
                npc.npcHitBox.width,
                npc.npcHitBox.height
            );

            if (playerArea.intersects(npcArea)) {
                player.collisionWithNPC = true;
                break;
            }
        }
    }
}
