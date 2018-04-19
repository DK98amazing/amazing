package akka.Agent;

import akka.actor.UntypedActor;
import akka.dispatch.Mapper;

import scala.concurrent.Future;

public class CountActor extends UntypedActor {

    Mapper<Integer, Integer> mapper = new Mapper<Integer, Integer>() {
        @Override
        public Integer apply(Integer parameter) {
            return parameter + 1;
        }
    };

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Integer) {
            for (int i=0; i<10000; i++) {
                Future<Integer> f = AgentDemo.countAgent.alter(mapper);
                AgentDemo.futures.add(f);
            }
            getContext().stop(getSelf());
        }else {
            unhandled(message);
        }
    }
}
