import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

/**guava包下的Service接口实现:
 * 1.AbstractIdleService   AbstractIdleService在我们服务处于running状态时，不会做执行任何动作，我们仅仅只有在startup和shutdown的时候才执行一些动作
 * 2.AbstractExecutionThreadService    AbstractExecutionThreadService在单个线程中执行startup, running, and shutdown，我们必须实现run()方法，同事在方法中要能响应停止服务的请求
 * 3.AbstractScheduledService  AbstractScheduledService类用于在运行时处理一些周期性的任务。子类可以实现 runOneIteration()方法定义一个周期执行的任务，以及相应的startUp()和shutDown()方法
 *              为了能够描述执行周期，你需要实现scheduler()方法
 * 4.如需要自定义的线程管理、可以通过扩展 AbstractService类来实现.
 * 5.除了对Service接口提供基础的实现类，Guava还提供了 ServiceManager类使得涉及到多个Service集合的操作更加容易。通过实例化ServiceManager类来创建一个Service集合
 *       startAsync() ： 将启动所有被管理的服务。如果当前服务的状态都是NEW的话、那么你只能调用该方法一次、这跟 Service#startAsync()是一样的。
         stopAsync() ：将停止所有被管理的服务。
         addListener ：会添加一个ServiceManager.Listener，在服务状态转换中会调用该Listener
         awaitHealthy() ：会等待所有的服务达到Running状态
         awaitStopped()：会等待所有服务达到终止状态
 */
public class TestService {
    public ServiceManager serviceManager;
    private Iterable<Service> serviceIterable;
    {
        Service service = new AbstractIdleService() {
            @Override
            protected void startUp() throws Exception {
                System.out.println("System begin");
            }

            @Override
            protected void shutDown() throws Exception {

            }
        };
        List<Service> list = Lists.newArrayList(service);

        serviceIterable = new Iterable<Service>() {
            @Override
            public Iterator<Service> iterator() {
                return list.iterator();
            }
        };
        serviceManager = new ServiceManager(serviceIterable);
    }

    class TestService1  extends AbstractIdleService {
        @Override
        protected void startUp() throws Exception {
            System.out.println("服务启动!");
        }
        @Override
        protected void shutDown() throws Exception {
            System.out.println("服务停止");
        }
    }

    class TestService2 extends AbstractExecutionThreadService {
        private boolean running = true;

        @Override
        protected void startUp() throws Exception {
            super.startUp();
            System.out.println("服务2启动");
        }

        @Override
        protected void triggerShutdown() {
            super.triggerShutdown();
            running = false;
            System.out.println("停止前处理");
        }

        @Override
        protected void shutDown() throws Exception {
            super.shutDown();
            System.out.println("服务2停止");
        }

        @Override
        protected void run() throws Exception {
            while (running) {
                Thread.sleep(2000);
                System.out.println("running");
            }
        }
    }

    class TestService3 extends AbstractScheduledService {

        @Override
        protected void runOneIteration() throws Exception {
            try {
                System.out.println("do work....");
            } catch (Exception e) {
                //处理异常
            }
        }

        @Override
        protected Scheduler scheduler() {
            return Scheduler.newFixedDelaySchedule(1, 5, TimeUnit.SECONDS);
        }
    }

    class TestService4 extends AbstractService {

        @Override
        protected void doStart() {

        }

        @Override
        protected void doStop() {

        }
    }
}
