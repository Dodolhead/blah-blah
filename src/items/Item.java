package src.items;

public class Item {
    private String itemName;
    private String itemType;
    private boolean isSellable;
    private Gold sellPrice;

    public Item(String itemName, String itemType, boolean isSellable, Gold sellPrice) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.isSellable = isSellable;
        this.sellPrice = sellPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {    
        this.itemType = itemType;
    }

    public boolean getIsSellable() {
        return isSellable;
    }

    public Gold getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Gold sellPrice) {
        this.sellPrice = sellPrice;
    }


}
