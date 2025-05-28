package main.java.items;
import java.awt.image.BufferedImage;
public class Misc extends Item {
    private String itemDescription;

    public Misc(String itemName, String itemDescription, BufferedImage image) {
        super(itemName, "Misc", false, new Gold(0), image);
        this.itemDescription = itemDescription;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}
