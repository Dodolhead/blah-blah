package items;

import actions.RecoveringLandAction;
import entities.*;

public class Pickaxe extends Equipment {
    public Pickaxe(String pickName, Gold sellPrice, Gold buyPrice){
        super(pickName, sellPrice, buyPrice, "Pickaxe", ItemManager.load("/items/equipment/pickaxe.png"));
    }

    public boolean use(Player player){
        RecoveringLandAction recoveringLandAction = new RecoveringLandAction();
        return recoveringLandAction.execute(player);
    }  
}
