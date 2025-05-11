package src.items;

import src.actions.RecoveringLandAction;
import src.entities.*;

public class Pickaxe extends Equipment {
    public Pickaxe(String pickName, Gold sellPrice, Gold buyPrice){
        super(pickName, sellPrice, buyPrice, "Pickaxe");
    }

    public void use(Player player){
        RecoveringLandAction recoveringLandAction = new RecoveringLandAction();
        recoveringLandAction.execute(player);
    }  
}
