package designmodel.flyweightPattern;

public class Client {
    public static void main(String args[]) {
        IgoChessman black1,black2,black3,white1,white2;
        IgoChessmanFactory factory;

        factory = IgoChessmanFactory.getInstance();

        black1 = factory.getIgoChessman("b");
        black2 = factory.getIgoChessman("b");
        black3 = factory.getIgoChessman("b");

        white1 = factory.getIgoChessman("w");
        white2 = factory.getIgoChessman("w");

        black1.display(new Coodinates(1,2));
        black2.display(new Coodinates(2,3));
        black3.display(new Coodinates(3,4));
        white1.display(new Coodinates(4,5));
        white2.display(new Coodinates(5,6));
    }
}
