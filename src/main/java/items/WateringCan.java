package main.java.items;

import main.java.actions.WateringAction;
import main.java.entities.*;;

public class WateringCan extends Equipment {
    public WateringCan(String wCanName, Gold sellPrice, Gold buyPrice){
        super(wCanName, sellPrice, buyPrice, "WateringCan", ItemManager.load("/items/equipment/watering-can.png"));
    }

    public boolean use(Player player){
        WateringAction watering = new WateringAction();
        return watering.execute(player);
    }
}
