package map;
import entities.*;

public class NPCHome {
    private String npcHomeName;
    private NPC npc;

    public NPCHome(String npcHomeName, NPC npc) {
        this.npcHomeName = npcHomeName;
        this.npc = npc;
        NPCHomeManager.addNPCHome(npcHomeName, npc);
    }

    public NPC getNpc() {
        return npc;
    }

    public String getNpcHomeName() {
        return npcHomeName;
    }
}
