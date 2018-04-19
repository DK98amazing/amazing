package distruptor;

import com.lmax.disruptor.EventFactory;

public class PCDataFactory implements EventFactory<PCData> {
    public PCData newInstance() {
        return PCDataFactory.getInstance();
    }
    public PCDataFactory(){}
    //public PCDataFactory(){} 单例模式使用
    private static class SingletonHandler {
        private static PCData instance = new PCData();
    }
    public static PCData getInstance() {
        return SingletonHandler.instance;
    }
}
