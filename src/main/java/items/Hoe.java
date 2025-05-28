package main.java.items;

import main.java.actions.TillingAction;
import main.java.entities.*;
public class Hoe extends Equipment {
    public Hoe(String hoeName, Gold sellPrice, Gold buyPrice){
        super(hoeName, sellPrice, buyPrice, "Hoe", ItemManager.load("/items/equipment/hoe.png"));
    }

    public boolean use(Player player){
        TillingAction tillingAction = new TillingAction();
        return tillingAction.execute(player);
    }
}
