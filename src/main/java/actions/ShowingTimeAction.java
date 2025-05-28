package main.java.actions;

import main.java.entities.*;

public class ShowingTimeAction implements Action {
    public boolean execute(Player player){
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        System.out.println(farm.getTime().getTimeDay());
        return true;
    }
}
