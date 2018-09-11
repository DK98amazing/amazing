package distruptor;

import com.lmax.disruptor.WorkHandler;

public class Consumer implements WorkHandler<PCData> {
    public void onEvent(PCData pcData) throws Exception {
        System.out.println(Thread.currentThread().getId() + ":Event: --" + pcData.getValue() * pcData.getValue() + "--"
    }
}
