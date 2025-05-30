package map;

import entities.NPC;
import entities.NPCManager;

public class DascoHome extends NPCHome {
    public DascoHome() {
        super("DascoHome", NPCManager.getNPCByName("Dasco"));
    }
}
