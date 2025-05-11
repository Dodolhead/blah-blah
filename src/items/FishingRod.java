package src.items;

import src.actions.FishingAction;
import src.entities.*;

public class FishingRod extends Equipment {
    public FishingRod(String rodName, Gold sellPrice, Gold buyPrice){
        super(rodName, sellPrice, buyPrice, "FishingRod");
    }

    public void use(Player player){
        FishingAction fishing = new FishingAction();
        fishing.execute(player);
    }
}
