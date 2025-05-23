package src.entities;

import java.util.List;
import src.map.*;
import java.util.ArrayList;

public class House {
    private List<Furniture> furnitureList = new ArrayList<Furniture>();
    private HouseMap houseMap;

    public House(List<Furniture> furnitureList, HouseMap houseMap) {
        this.furnitureList = furnitureList;
        this.houseMap = houseMap;
    }

    public List<Furniture> getFurnitureList() {
        return furnitureList;
    }

    public HouseMap getHouseMap() {
        return houseMap;
    }

    public void addFurniture(Furniture furniture) {
        furnitureList.add(furniture);
    }


    public void removeFurniture(Furniture furniture) {
        furnitureList.remove(furniture);
    }

}
