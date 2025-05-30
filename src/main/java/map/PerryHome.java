package map;

import entities.NPC;
import entities.NPCManager;

public class PerryHome extends NPCHome {
    public PerryHome() {
        super("PerryHome", NPCManager.getNPCByName("Perry"));
    }
}
