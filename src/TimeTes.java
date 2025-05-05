package src;

import src.tsw.*;

public class TimeTes {
    public static void main(String[] args) {
        Time gameTime = new Time();

        try {
            System.out.println("Current time: " + gameTime.getCurrentTime());
            // Waktu berjalan 5 detik (waktu game setara 25 menit)
            Thread.sleep(5000);
            System.out.println("Current time before action: " + gameTime.getCurrentTime());
            System.out.println("Day: " + gameTime.getDay());

            // Pause waktu saat action (misalnya interaksi dengan NPC)
            System.out.println("\n[Action started: Interacting with NPC]");
            System.out.println("Current time: " + gameTime.getCurrentTime());
            gameTime.pauseTime();
            // Simulasi durasi aksi (misal 4 detik)
            Thread.sleep(4000);
            System.out.println("[Action done]\n");

            System.out.println("Current time after action: " + gameTime.getCurrentTime());
            // Lanjutkan waktu
            gameTime.resumeTime();

            // Biarkan waktu berjalan 5 detik lagi
            Thread.sleep(5000);
            System.out.println("Current time after action: " + gameTime.getCurrentTime());
            System.out.println("Day: " + gameTime.getDay());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop waktu
        gameTime.stopTime();
        System.out.println("\nGame time stopped.");
    }
}
