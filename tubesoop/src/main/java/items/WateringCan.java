package items;

import actions.WateringAction;
import entities.*;;

public class WateringCan extends Equipment {
    public WateringCan(String wCanName, Gold sellPrice, Gold buyPrice){
        super(wCanName, sellPrice, buyPrice, "WateringCan");
    }

    public boolean use(Player player){
        WateringAction watering = new WateringAction();
        return watering.execute(player);
    }
}
