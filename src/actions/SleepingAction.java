package src.actions;

import java.util.ArrayList;
import src.entities.*;
import src.map.*;
import src.tsw.*;

public class SleepingAction implements Action {
    private Time gameTime;
    private FarmMap farmMap;
    private static final int MAX_ENERGY = 100;
    private boolean hasSleptToday = false; // status tidur hari ini

    public SleepingAction(Time gameTime, FarmMap farmMap) {
        this.gameTime = gameTime;
        this.farmMap = farmMap;
    }

    @Override
    public boolean execute(Player player) {
        Point playerPos = player.getPlayerLocation().getCurrentPoint();

        // Deteksi waktu paksa tidur (pukul 2 pagi) jika belum tidur
        if (gameTime.getHour() == 2 && !hasSleptToday) {
            System.out.println("Sudah pukul 02.00! Anda kelelahan dan tertidur di tempat...");
            return sleep(player, true); // paksa tidur meskipun bukan di dalam rumah
        }

        if (!isInHouseOrBed(playerPos, farmMap)) {
            System.out.println("Anda hanya bisa tidur di dalam rumah atau di tempat tidur!");
            return false;
        }

        return sleep(player, false); // tidur normal
    }

    private boolean sleep(Player player, boolean isForcedSleep) {
        System.out.println("Memulai tidur...");

        int currentEnergy = player.getEnergy();
        int restoredEnergy;

        if (currentEnergy <= 0) {
            restoredEnergy = 10;
            System.out.println("Energi habis, hanya pulih sebanyak 10 poin.");
        } else if (currentEnergy < (0.1 * MAX_ENERGY)) {
            restoredEnergy = MAX_ENERGY / 2;
            System.out.println("Energi lemah, pulih setengah dari maksimum: " + restoredEnergy + " poin.");
        } else {
            restoredEnergy = MAX_ENERGY;
            System.out.println("Energi pulih sepenuhnya: " + restoredEnergy + " poin.");
        }

        player.setEnergy(restoredEnergy);
        hasSleptToday = true;

        // Skip waktu ke pukul 06:00 pagi
        int currentHour = gameTime.getHour();
        int hoursToSkip = (currentHour >= 6) ? (24 - currentHour + 6) : (6 - currentHour);

        gameTime.pauseTime();
        gameTime.skipTimeHour(hoursToSkip);
        gameTime.resumeTime();

        System.out.println("Tidur selesai. Sekarang pukul " + gameTime.getCurrentTime() + ", hari ke-" + gameTime.getDay());
        System.out.println("Energi sekarang: " + player.getEnergy());

        return true;
    }

    private boolean isInHouseOrBed(Point playerPos, FarmMap farmMap) {
        return isNearObject(playerPos, farmMap, "House") || isNearObject(playerPos, farmMap, "Beds");
    }

    private boolean isNearObject(Point playerPos, FarmMap farmMap, String objectName) {
        for (Point point : farmMap.getObjectPosition().getOrDefault(objectName, new ArrayList<>())) {
            if (Math.abs(playerPos.getX() - point.getX()) <= 1 &&
                Math.abs(playerPos.getY() - point.getY()) <= 1) {
                return true;
            }
        }
        return false;
    }

    // Tambahkan jika perlu reset tidur harian di luar class ini:
    public void resetSleepStatus() {
        hasSleptToday = false;
    }
}
