package src.map;

import java.util.Map;
import java.util.HashMap;
import src.entities.*;

public class NPCHomeManager {
    private static Map<String, NPC> npcHomeStorage = new HashMap<>();

    public static Map<String, NPC> getNpcHomeStorage() {
        return npcHomeStorage;
    }

    public static void addNPCHome(String homeName, NPC npc) {
        npcHomeStorage.put(homeName, npc);
    }

    public static void removeNPCHome(String homeName) {
        npcHomeStorage.remove(homeName);
    }

    public static String getHomeNameByNPC(NPC npc) {
        for (Map.Entry<String, NPC> entry : npcHomeStorage.entrySet()) {
            if (entry.getValue().equals(npc)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
}
