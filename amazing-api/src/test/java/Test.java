import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Test {
    public static void main(String args[]) {
        ActorSystem system = ActorSystem.create("system");
        ActorRef ref = system.actorOf(Props.create(MyActorRef.class), "myActorRef");
        EventBusPubSub pubSub = new EventBusPubSub();
        pubSub.subscribe(ref, "topic");
        pubSub.publish(new EventBusMessage("topic", "message"));
    }
}
