package main.java.map;

public class Point {
    private int x;
    private int y;
    private int prevX;
    private int prevY;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public boolean moveTo(int dx, int dy, char[][] mapDisplay) {
        if (dx < 0 || dy < 0 || dx >= mapDisplay.length || dy >= mapDisplay[0].length) {
            System.out.println("Cannot move: Out of bounds!");
            return false;
        }
    
        if (mapDisplay[dx][dy] == '.' || mapDisplay[dx][dy] == 't' || mapDisplay[dx][dy] == 'l') {
            this.x = dx;
            this.y = dy;
            System.out.println("Moved to (" + dx + ", " + dy + ")");
            return true;
        } else {
            System.out.println("Cannot move: Collision detected at (" + dx + ", " + dy + ")");
            return false;
        }
    }
    
    public boolean movePlayer(String direction, char[][] mapDisplay) {
        int newX = this.x;
        int newY = this.y;

        // Simpan posisi lama
        prevX = this.x;
        prevY = this.y;

        switch (direction.toLowerCase()) {
            case "up":
                newY = this.y - 1;
                break;
            case "down":
                newY = this.y + 1;
                break;
            case "left":
                newX = this.x - 1;
                break;
            case "right":
                newX = this.x + 1;
                break;
            default:
                System.out.println("Invalid direction.");
                return false;
        }

        if (newX < 0 || newX >= mapDisplay[0].length || newY < 0 || newY >= mapDisplay.length) {
            System.out.println("You can't move outside the house!");
            return false;
        }

        if (mapDisplay[newY][newX] != '.' && mapDisplay[newY][newX] != 't' && mapDisplay[newY][newX] != 'l') {
            System.out.println("There's something in the way! Can't move there.");
            return false;
        }

        this.x = newX;
        this.y = newY;
        System.out.println("Moved to: (" + this.x + "," + this.y + ")");

        if (mapDisplay[newY][newX] == 'D') {
            System.out.println("Door found! You can now move to a different map.");
        }

        return true;
    }

    public int[] getPosition() {
        return new int[] {this.x, this.y};
    }
    public int[] getPreviousPosition() {
        return new int[] {this.prevX, this.prevY};
    }

    public String printPoint() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

}
