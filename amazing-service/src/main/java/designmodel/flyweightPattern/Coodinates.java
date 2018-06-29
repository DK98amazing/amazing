package designmodel.flyweightPattern;

public class Coodinates {
    private int x;
    private int y;

    public Coodinates(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
