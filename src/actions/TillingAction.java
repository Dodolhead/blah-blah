package src.actions;

import src.entities.Player;
import src.items.Hoe;
import src.map.FarmMap;
import src.map.Point;
import src.tsw.Time;

public class TillingAction implements Action {
    private FarmMap farmMap;
    private Time gameTime;

    public TillingAction(FarmMap farmMap, Time gameTime) {
        this.farmMap = farmMap;
        this.gameTime = gameTime;
    }

    @Override
    public boolean execute(Player player) {
        // Cek apakah pemain punya Hoe di inventory
        if (!player.getPlayerInventory().hasItemType(Hoe.class)) {
            System.out.println("Tilling gagal: Anda tidak memiliki Hoe.");
            return false;
        }

        // Cek energi cukup
        if (player.getEnergy() < 5) {
            System.out.println("Tilling gagal: Energi tidak cukup.");
            return false;
        }

        // Ambil posisi pemain
        Point pos = player.getPlayerLocation().getCurrentPoint();
        int x = pos.getX();
        int y = pos.getY();
        char[][] map = farmMap.getFarmMapDisplay();

        // Validasi posisi
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            System.out.println("Tilling gagal: Posisi di luar batas peta.");
            return false;
        }

        // Periksa apakah tile bisa dibajak
        if (map[y][x] != '.') {
            System.out.println("Tilling gagal: Tile ini bukan lahan yang bisa dibajak.");
            return false;
        }

        // Lakukan aksi tilling
        map[y][x] = 't';
        Point currentTile = new Point(x, y);
        farmMap.getObjectPosition().get("Tillable").removeIf(p -> p.getX() == x && p.getY() == y);
        farmMap.getObjectPosition().get("Tilled").add(currentTile);

        // Update energi dan waktu
        player.subtractPlayerEnergy(5);
        gameTime.skipTimeMinute(5);

        System.out.println("Tilling berhasil: Tanah berhasil dibajak.");
        return true;
    }
}
