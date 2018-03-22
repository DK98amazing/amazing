import com.Distributed.UserServiceImpl;
import com.alipay.jarslink.api.*;
import com.alipay.jarslink.api.impl.ModuleLoaderImpl;
import com.alipay.jarslink.api.impl.ModuleManagerImpl;
import com.alipay.jarslink.api.impl.ModuleServiceImpl;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mapper.api.distributed.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/jarslink.xml"})
public class Jvm {

    @Autowired
    private ModuleService moduleService;

    static {
        i = 0;
//        System.out.println(i);
    }
    public static int i = 0;
    @Test
    public void runA() {
        System.out.println(Jvm.i);
    }

    @Test
    public void runB() {
        ExecutorService service = new ThreadPoolExecutor(5, 5, 0l, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5)){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
                System.out.println("1");
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                System.out.println("2");
            }

            @Override
            protected void terminated() {
                super.terminated();
                System.out.println("3");
            }
        };
        service.execute(() -> System.out.println("ww"));
    }

    class CountTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 10000;
        private long start;
        private long end;

        public CountTask(long start, long end) {
            super();
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long sum = 0;
            boolean canCompute = (end - start) < THRESHOLD;
            if(canCompute)
            {
                for (long i = start; i <= end; i++) {
                    sum = sum + i;
                }
            }else
            {
                //分成100个小任务
                long step = (start + end)/100;
                ArrayList<CountTask> subTasks = new ArrayList<CountTask>();
                long pos = start;
                for (int i = 0; i < 100; i++) {
                    long lastOne = pos + step;
                    if(lastOne > end )
                    {
                        lastOne = end;
                    }
                    CountTask subTask = new CountTask(pos, lastOne);
                    pos += step + 1;
                    subTasks.add(subTask);
                    subTask.fork();//把子任务推向线程池
                }
                for (CountTask t : subTasks) {
                    sum += t.join();//等待所有子任务结束
                }
            }
            return sum;
        }
    }

    @Test
    public void runF() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(0, 200000L);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            long res = result.get();
            System.out.println("sum = " + res);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Test
    public void runG() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("com.amazing.service-1.0.0.0.jar");
        ModuleConfig moduleConfig = new ModuleConfig();
        moduleConfig.setName("demo");
        moduleConfig.setEnabled(true);
        moduleConfig.setOverridePackages(ImmutableList.of("com.Distributed"));
        moduleConfig.setVersion("1.0.0.0");
        Map<String, Object> properties = new HashMap();
        properties.put("url", "127.0.0.1");
        moduleConfig.setProperties(properties);
        moduleConfig.setModuleUrl(ImmutableList.of(url));

//        ModuleLoaderImpl loader = new ModuleLoaderImpl();
//        loader.setApplicationContext(new ClassPathXmlApplicationContext());
//        ModuleManagerImpl manager = new ModuleManagerImpl();
//        ModuleServiceImpl service = new ModuleServiceImpl();
//        service.setModuleLoader(loader);
//        service.setModuleManager(manager);
        Module module = moduleService.loadAndRegister(moduleConfig);
        System.out.println(module.getActions());
    }
}
