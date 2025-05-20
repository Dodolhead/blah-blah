package entities;

import java.util.HashMap;
import java.util.Map;

public class FarmManager {
    private static Map<String, Farm> farms = new HashMap<>();

    public static void registerFarm(Farm farm) {
        farms.put(farm.getFarmName(), farm);
    }

    public static Farm getFarmByName(String name) {
        return farms.get(name);
    }
}
