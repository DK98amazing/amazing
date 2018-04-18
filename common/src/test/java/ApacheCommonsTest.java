import org.apache.commons.collections4.*;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bag.PredicatedBag;
import org.apache.commons.collections4.bag.TransformedBag;
import org.apache.commons.collections4.bag.TreeBag;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

public class ApacheCommonsTest {
    private final static Logger LOG = LoggerFactory.getLogger(ApacheCommonsTest.class);
    @Test
    public void runA() {
        Bag<String> stringBag = new HashBag<>();
        stringBag.add("a", 10);
        System.out.println(stringBag.getCount("a"));

        PredicatedBag<String> predicatedBag = null;
        try {
            predicatedBag = PredicatedBag.predicatedBag(stringBag, (String s) -> {
                if (s.length() == 1) {
                    return true;
                }
                return false;
            });

            predicatedBag.add("w");
        }catch (Exception e) {
            LOG.error("add failed", e);
        }
        System.out.println(predicatedBag.uniqueSet().size());

        SortedBag<String> sortedBag = new TreeBag<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() > o2.length()) {
                    return -1;
                }else if (o1.length() < o2.length()) {
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        sortedBag.add("q");sortedBag.add("12");
        System.out.println("first: " + sortedBag.first() + "  last: " + sortedBag.last());

        Bag<String> transformedBag = TransformedBag.transformedBag(stringBag, new Transformer<String, String>() {
            @Override
            public String transform(String s) {
                return s + "ssss";
            }
        });
        System.out.println(transformedBag.iterator().next());
    }

    @Test
    public void runB() {
        DualHashBidiMap<String, Integer> dualHashBidiMap = new DualHashBidiMap<>();
        dualHashBidiMap.put("key1", 1);
        BidiMap bidiMap = dualHashBidiMap.inverseBidiMap();
        System.out.println("key:" + bidiMap.getKey("key1") + "    value:" + bidiMap.get(1));
    }

    @Test
    public void runC() {
        CircularFifoQueue<String> queue = new CircularFifoQueue<>();
        queue.add("a");
        queue.add("b");
        queue.peek();
        queue.poll();
        System.out.println(queue.element());
        System.out.println(queue.size());
    }
}