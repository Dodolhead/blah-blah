package src.items;

import src.actions.TillingAction;
import src.entities.*;
public class Hoe extends Equipment {
    public Hoe(String hoeName, Gold sellPrice, Gold buyPrice){
        super(hoeName, sellPrice, buyPrice, "Hoe");
    }

    public boolean use(Player player){
        TillingAction tillingAction = new TillingAction();
        return tillingAction.execute(player);
    }
}
