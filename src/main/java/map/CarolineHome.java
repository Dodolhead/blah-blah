package map;

import entities.NPC;
import entities.NPCManager;

public class CarolineHome extends NPCHome {
    public CarolineHome() {
        super("CarolineHome", NPCManager.getNPCByName("Caroline"));
    }
}
