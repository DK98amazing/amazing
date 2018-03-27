import akka.actor.ActorRef;
import akka.event.japi.LookupEventBus;

public class EventBusPubSub extends LookupEventBus<EventBusMessage, ActorRef, String> {
    public int mapSize() {
        return 128;
    }

    public int compareSubscribers(ActorRef a, ActorRef b) {
        return a.compareTo(b);
    }

    public String classify(EventBusMessage event) {
        return event.getTopic();
    }

    public void publish(EventBusMessage event, ActorRef subscriber) {
        subscriber.tell(event.getMessage(), ActorRef.noSender());
    }
}
