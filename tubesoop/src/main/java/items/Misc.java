package items;

public class Misc extends Item {
    private String itemDescription;

    public Misc(String itemName, String itemDescription) {
        super(itemName, "Misc", false, new Gold(0));
        this.itemDescription = itemDescription;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}
