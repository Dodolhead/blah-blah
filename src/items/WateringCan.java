package src.items;

import src.actions.WateringAction;
import src.entities.*;;

public class WateringCan extends Equipment {
    public WateringCan(String wCanName, Gold sellPrice, Gold buyPrice){
        super(wCanName, sellPrice, buyPrice, "WateringCan");
    }

    public boolean use(Player player){
        WateringAction watering = new WateringAction();
        return watering.execute(player);
    }
}
