package designmodel.flyweightPattern;

public abstract class IgoChessman {
    public abstract String getColor();

    public void display(Coodinates coodinates) {
        System.out.println("this color:" + this.getColor() + " coodinate:" + coodinates.toString());
    }
 }
