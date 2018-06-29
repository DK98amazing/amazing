package designmodel.flyweightPattern;

import java.util.HashMap;
import java.util.Map;

public class IgoChessmanFactory {
    private static IgoChessmanFactory instance = new IgoChessmanFactory();
    private static Map<String, IgoChessman> ht;

    private IgoChessmanFactory() {
        ht = new HashMap<>();
        IgoChessman black, white;
        black = new BlackIgoChessman();
        ht.put("b", black);
        white = new WhiteIgoChessman();
        ht.put("w", white);
    }

    public static IgoChessmanFactory getInstance() {
        return instance;
    }

    public static IgoChessman getIgoChessman(String color) {
        return ht.get(color);
    }
}
