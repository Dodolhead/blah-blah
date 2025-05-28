package main.java.map;

public class Location {
    private String name;
    private Point currentPoint;

    public Location(String name, Point currentPoint){
        this.name = name;
        this.currentPoint = currentPoint;
    }

    public String getName() {
        return name;
    }

    public Point getCurrentPoint(){
        return currentPoint;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPoint(Point point){
        this.currentPoint = point;
    }
    
    
    
}
