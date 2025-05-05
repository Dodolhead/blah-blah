package src;

import src.actions.MarryingAction;
import src.actions.ProposingAction;
import src.entities.*;
import src.items.*;
import src.map.*;

public class ProposeAndMarryTes {

    public static void main(String[] args) {
        // Setup NPC dan player
        NPC luna = new NPC("Luna", "Single");
        luna.setHeartPoints(150); // agar lamaran diterima

        Item proposalRing = new Misc("Proposal Ring", "Special ring for proposing");
        luna.addLovedItem(proposalRing);

        Gold gold = new Gold(500);
        Inventory inventory = new Inventory();
        inventory.addItem(proposalRing, 1); // Tambahkan ring ke inventory

        Location home = new Location("Farm", new Point(16, 16));
        Player player = new Player("Alex", "Male", "Sunny Farm", gold, inventory, home);
        player.setEnergy(100);

        // Setup waktu & farm manager
        Farm farm = new Farm(player.getFarm(),player);

        // === PROPOSING ===
        System.out.println("\n=== PROPOSAL TEST ===");
        ProposingAction propose = new ProposingAction(luna);
        propose.execute(player);

        // Paksa hari bertambah agar bisa menikah
        farm.getTime().skipTimeHour(24); // ganti hari
        luna.setProposedDay(farm.getTime().getDay() - 1); // simulasi hari setelah propose

        // === MARRYING ===
        System.out.println("\n=== MARRY TEST ===");
        MarryingAction marry = new MarryingAction(luna);
        marry.execute(player);

        if (player.getPartner() == null) {
            System.out.println("Player belum punya partner.");
        } else {
            System.out.println("Current Partner: " + player.getPartner().getNpcName());
        }

    }
}

