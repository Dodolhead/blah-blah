package src.actions;

import src.entities.*;
import src.map.*;
import src.tsw.*;

public class Watching implements Action {
    private Time gameTime;
    
    public Watching(Time gameTime) {
        this.gameTime = gameTime;
    }
    
    @Override
    public boolean execute(Player player) {
        // Cek apakah pemain memiliki cukup energi
        if (player.getEnergy() < 5) {
            System.out.println("Energi kamu tidak cukup untuk menonton TV.");
            return false;
        }
        
        // Cek apakah pemain berada di rumah
        if (!player.getPlayerLocation().getName().equals("House")) {
            System.out.println("Kamu harus berada di rumah untuk menonton TV.");
            return false;
        }
        
        // Pause waktu game, kurangi energi, dan tambahkan waktu
        gameTime.pauseTime();
        player.subtractPlayerEnergy(5);
        gameTime.skipTimeMinute(15);
        
        System.out.println("Kamu menonton TV selama 15 menit.");
        
        // Solusi untuk menangani weather dan season
        try {
            // Gunakan object weather langsung dari gameTime atau buat baru
            Weather weather = new Weather();
            System.out.println("Prakiraan cuaca untuk hari ini: " + weather.getCurrentWeather());
            
            // Untuk season, juga buat object baru
            Season season = new Season();
            System.out.println("Musim saat ini: " + season.getCurrentSeason());
        } catch (Exception e) {
            System.out.println("Tidak dapat menampilkan informasi cuaca dan musim.");
            System.out.println("Error: " + e.getMessage());
        }
        
        // Lanjutkan waktu game
        gameTime.resumeTime();
        
        return true;
    }
}