package map;

import entities.NPCManager;

public class PerryHome extends NPCHome {
    public PerryHome() {
        super("PerryHome", NPCManager.getNPCByName("Perry"));
    }
}
