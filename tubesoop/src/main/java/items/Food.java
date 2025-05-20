package items;

public class Food extends Item {
    private int giveEnergy;
    private Gold buyPrice;

    public Food(String itemName, int giveEnergy, Gold buyPrice, Gold sellPrice){
        super(itemName, "Food", true, sellPrice);
        this.giveEnergy = giveEnergy;
        this.buyPrice = buyPrice;
    }

    public int getGiveEnergy(){
        return giveEnergy;
    }


    public Gold getbuyPrice(){
        return buyPrice;
    }

}
