package akka.rumen.ch2;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Status;


/**
 * Created by wangdecheng on 03/04/2018.
 */
public class JavaPongActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("Ping",s -> sender().tell("Pone", ActorRef.noSender()))
                .match(String.class,s-> sender().tell("receive message:"+s, ActorRef.noSender()))
                .matchAny(s->sender().tell(new Status.Failure(new Exception("Unknown")), self()))
                .build();
    }
}
