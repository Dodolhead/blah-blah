package main.java.entities;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private static List<Player> playerList = new ArrayList<>();

    public static void addPlayer(Player player) {
        playerList.add(player);
    }

    public static void removePlayer(Player player) {
        playerList.remove(player);
    }

    public static int getPlayerCount() {
        return playerList.size();
    }

    public static List<Player> getPlayerList() {
        return playerList;
    }

    public static Player getPlayerByName(String name) {
        for (Player player : playerList) {
            if (player.getPlayerName().equals(name)) {
                return player;
            }
        }
        return null;
    }
}
