package entities;

import java.util.List;
import java.util.ArrayList;

public class NPCManager {
    public static List<NPC> npcList = new ArrayList<>();

    public static void addNPC(NPC npc) {
        npcList.add(npc);
    }

    public static void removeNPC(NPC npc) {
        npcList.remove(npc);
    }

    public static int getNPCCount() {
        return npcList.size();
    }

    public static List<NPC> getNPCList() {
        return npcList;
    }

    public static NPC getNPCByName(String name) {
        for (NPC npc : npcList) {
            if (npc.getNpcName().equalsIgnoreCase(name)) {
                return npc;
            }
        }
        return null;
    }
    
    public static NPC getNPCFromHome(String homeName) {
        for (NPC npc : NPCManager.getNPCList()) {
            if (npc.npcLocation != null && npc.npcLocation.getName().equalsIgnoreCase(homeName)) {
                return npc;
            }
        }
        return null;
    }
}
