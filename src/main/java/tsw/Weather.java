package tsw;

public class Weather {
    public enum WeatherCondition{
        SUNNY, RAINY;
    }

    private WeatherCondition currentWeather;
    private long seed = System.currentTimeMillis(); 

    public Weather() {
        this.currentWeather = WeatherCondition.SUNNY;
    }

    public WeatherCondition getCurrentWeather() {
        return this.currentWeather;
    }

    public void setCurrentWeather(WeatherCondition currentWeather) {
        this.currentWeather = currentWeather;
    }

    private int generateRandom() {
        long a = 1664525; 
        long c = 1013904223;
        long m = (long) Math.pow(2, 32); 

        seed = (a * seed + c) % m;
        return (int) (seed % 3); 
    }

    public void changeWeatherRandomly() {
        if (generateRandom() == 0 || generateRandom() == 1) {
            setCurrentWeather(WeatherCondition.SUNNY);
            System.out.println("You feel your energy replenished!, oh its: " + currentWeather);
        } else {
            setCurrentWeather(WeatherCondition.RAINY);
            System.out.println("You suddenly felt something wet fall on your head!, oh its: " + currentWeather);
        }
    }


    public void nextDayWeather() {
        System.out.println("\nNew day, new weather!");
        changeWeatherRandomly();
    }

}
