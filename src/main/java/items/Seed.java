package items;
import java.awt.image.BufferedImage;
//ganti season nanti

public class Seed extends Item {
    private Gold buyPrice;
    private int harvestDays;
    private String validSeason;

    public Seed(String itemName, Gold buyPrice, int harvestDays, String validSeason, BufferedImage image) {
        super(itemName, "Seed", false, new Gold(0), image);
        this.buyPrice = buyPrice;
        this.harvestDays = harvestDays;
        this.validSeason = validSeason;
    }

    public Gold getBuyPrice() {
        return buyPrice;
    }
    
    public int getHarvestDays(){
        return harvestDays;
    }

    public String getValidSeason(){
        return validSeason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seed seed = (Seed) o;

        return this.getItemName().equalsIgnoreCase(seed.getItemName());
    }

    @Override
    public int hashCode() {
        return this.getItemName().toLowerCase().hashCode();
    }

}
