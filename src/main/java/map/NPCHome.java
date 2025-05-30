package map;

import entities.NPC;
import gui.GamePanel;

public class NPCHome {
    protected String npcHomeName;
    protected NPC npc;
    protected GamePanel gp;

    protected final int npcHomeWidth = 10;
    protected final int npcHomeHeight = 10;

    protected static final char[][] DEFAULT_NPCHOME_DISPLAY = {
        {']',']',']',']',']',']',']',']',']',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']','`','`','`','`','`','`','`','`',']'},
        {']',']',']',']','`','`',']',']',']',']'}
    };

    protected char[][] npcHomeDisplay;

    public NPCHome(String npcHomeName, NPC npc) {
        this.npcHomeName = npcHomeName;
        this.npc = npc;
        this.npcHomeDisplay = DEFAULT_NPCHOME_DISPLAY;
        NPCHomeManager.addNPCHome(this);
    }

    public NPC getNpc() {
        return npc;
    }

    public String getNpcHomeName() {
        return npcHomeName;
    }

    public char[][] getNpcHomeDisplay() {
        return npcHomeDisplay;
    }
}
