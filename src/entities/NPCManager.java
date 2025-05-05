package src.entities;

import java.util.List;
import java.util.ArrayList;

public class NPCManager {
    private static List<NPC> npcList = new ArrayList<>();

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
}
