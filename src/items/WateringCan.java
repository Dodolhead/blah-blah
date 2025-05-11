package src.items;

import src.actions.WateringAction;
import src.entities.*;;

public class WateringCan extends Equipment {
    public WateringCan(String wCanName, Gold sellPrice, Gold buyPrice){
        super(wCanName, sellPrice, buyPrice, "WateringCan");
    }

    public void use(Player player){
        WateringAction watering = new WateringAction();
        watering.execute(player);
    }
}
