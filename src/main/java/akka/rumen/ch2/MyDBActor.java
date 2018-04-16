package akka.rumen.ch2;

import akka.actor.AbstractActor;
import akka.actor.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdecheng on 04/04/2018.
 */
public class MyDBActor extends AbstractActor {
    Map<String,String> map = new HashMap<>();
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SetRequest.class, message -> {
                    System.out.println("Received Set request: {}"+ message);
                    map.put(message.key, message.value);
                    sender().tell(new Status.Success(message.key), self());
                })
                .match(GetRequest.class, message -> {
                    System.out.println("Received Get request: {}"+ message);
                    String value = map.get(message.key);
                    Object response = (value != null)
                            ? value
                            : new Status.Failure(new KeyNotFoundException(message.key)); sender().tell(response, self());
                })
                .matchAny(o ->
                        sender().tell(new Status.Failure(new ClassNotFoundException()), self()) )
                .build();
    }
}
