package items;

public class Gold {
    private int goldAmount;

    public Gold(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public Gold(){
        this.goldAmount = 0;
    }

    public int getGold(){
        return goldAmount;
    }

    public void setGold(int goldAmount){
        this.goldAmount = goldAmount;
    }

    public void addGold(int goldAmount){
        this.goldAmount += goldAmount;
    }

    public void subtractGold(int goldAmount){
        this.goldAmount -= goldAmount;
    }
}
