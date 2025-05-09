package src.actions;

import src.entities.Player;
import src.items.Pickaxe;
import src.map.FarmMap;
import src.map.Point;
import src.tsw.Time;

public class RecoverLandAction implements Action {
    private FarmMap farmMap;
    private Time gameTime;

    public RecoverLandAction(FarmMap farmMap, Time gameTime) {
        this.farmMap = farmMap;
        this.gameTime = gameTime;
    }

    @Override
    public boolean execute(Player player) {
        // Cek apakah pemain punya Pickaxe
        if (!player.getPlayerInventory().hasItemType(Pickaxe.class)) {
            System.out.println("Recover Land gagal: Anda tidak memiliki Pickaxe.");
            return false;
        }

        // Cek energi cukup
        if (player.getEnergy() < 5) {
            System.out.println("Recover Land gagal: Energi tidak cukup.");
            return false;
        }

        // Ambil posisi pemain
        Point pos = player.getPlayerLocation().getCurrentPoint();
        int x = pos.getX();
        int y = pos.getY();
        char[][] map = farmMap.getFarmMapDisplay();

        // Validasi posisi dalam batas peta
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            System.out.println("Recover Land gagal: Posisi di luar batas peta.");
            return false;
        }

        char tile = map[y][x];

        // Tile harus tilled (t), tidak bisa planted (l)
        if (tile == 't') {
            map[y][x] = '.';

            Point target = new Point(x, y);
            farmMap.getObjectPosition().get("Tilled").removeIf(p -> p.getX() == x && p.getY() == y);
            farmMap.getObjectPosition().get("Tillable").add(target);

            player.subtractPlayerEnergy(5);
            gameTime.skipTimeMinute(5);

            System.out.println("Recover Land berhasil: Tanah dikembalikan ke kondisi semula.");
            return true;
        } else if (tile == 'l') {
            System.out.println("Recover Land gagal: Tanah yang sudah ditanami tidak bisa dikembalikan.");
            return false;
        } else {
            System.out.println("Recover Land gagal: Hanya bisa dilakukan pada tanah yang telah dibajak.");
            return false;
        }
    }
}
