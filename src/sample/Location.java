package sample;

public class Location {
   private int x;
    private int y;
    Direction direction;
   private int value;

    public Location(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Location(int x, int y, Direction direction,int value) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.value=value;
    }

    public Location(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
