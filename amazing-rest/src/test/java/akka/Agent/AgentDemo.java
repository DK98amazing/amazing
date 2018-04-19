package akka.Agent;

import akka.actor.*;
import akka.agent.Agent;
import akka.dispatch.Futures;
import akka.dispatch.OnComplete;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

import java.util.concurrent.ConcurrentLinkedDeque;
import scala.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AgentDemo {
    public static Agent<Integer> countAgent = Agent.create(0, ExecutionContext.global());
    static ConcurrentLinkedDeque<Future<Integer>> futures = new ConcurrentLinkedDeque<>();

    public static void main(String args[]) {
        final ActorSystem system = ActorSystem.create("system");
        ActorRef[] actorRefs = new ActorRef[10];
        for (int i=0; i<actorRefs.length; i++) {
            actorRefs[i] = system.actorOf(Props.create(CountActor.class), "counter_" + i);
        }
        final Inbox inbox = Inbox.create(system);
        for (int i=0; i<actorRefs.length; i++) {
            inbox.send(actorRefs[i], 1);
            inbox.watch(actorRefs[i]);
        }
        int closeCount = 0;
        while (true) {
            try {
                Object msg = inbox.receive(Duration.create(1, TimeUnit.SECONDS));
                if (msg instanceof Terminated) {
                    closeCount ++;
                    if (closeCount == actorRefs.length) {
                        break;
                    }
                }else {
                    System.out.println(msg);
                }
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        Futures.sequence(futures, system.dispatcher()).onComplete(
                new OnComplete<Iterable<Integer>>() {
                    @Override
                    public void onComplete(Throwable failure, Iterable<Integer> success) throws Throwable {
                        System.out.println("countAgent = " + countAgent.get());
                        system.terminate();
                    }
                }, system.dispatcher());
    }
}
