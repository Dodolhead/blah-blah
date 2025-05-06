package src.actions;

import src.entities.*;

public class ShowingTimeAction implements Action {
    public boolean execute(Player player){
        Farm farm = FarmManager.getFarmByName(player.getFarm());
        System.out.println(farm.getTime().getTimeDay());
        return true;
    }
}
