package items;

public class Crop extends Item{
    private Gold buyPrice;
    private int cropPerPanen;

    public Crop(String itemName, Gold sellPrice, Gold buyPrice, int cropPerPanen){
        super(itemName, "Crop", true, sellPrice);
        this.buyPrice = buyPrice;
        this.cropPerPanen = cropPerPanen;
    }

    public Gold getBuyPrice(){
        return buyPrice;
    }

    public int getCropPerPanen(){
        return cropPerPanen;
    }
}
