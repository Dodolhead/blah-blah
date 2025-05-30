package map;
import java.util.Map;
import entities.*;

import java.util.HashMap;

public class NPCHomeManager {
    private static Map<String, NPCHome> npcHomeStorage = new HashMap<>();

    public static Map<String, NPCHome> getNpcHomeStorage() {
        return npcHomeStorage;
    }

    public static void addNPCHome(NPCHome home) {
        npcHomeStorage.put(home.getNpcHomeName(), home);
    }

    public static void removeNPCHome(String homeName) {
        npcHomeStorage.remove(homeName);
    }

    public static String getHomeNameByNPC(NPC npc) {
        for (Map.Entry<String, NPCHome> entry : npcHomeStorage.entrySet()) {
            if (entry.getValue().getNpc().equals(npc)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static NPCHome getNPCHomeByName(String name) {
        return npcHomeStorage.get(name);
    }
}
