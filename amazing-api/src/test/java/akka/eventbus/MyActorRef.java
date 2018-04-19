package akka.eventbus;

import akka.actor.UntypedActor;

public class MyActorRef extends UntypedActor {
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            System.out.println(message + " is receive");
        }
    }
}
