package distruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class Producer {
    private final RingBuffer<PCData> ringBuffer;
    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void pushData(ByteBuffer byteBuffer) {
        long sequence = ringBuffer.next();
        try {
            PCData data = ringBuffer.get(sequence);
            data.setValue(byteBuffer.getLong(0));
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
