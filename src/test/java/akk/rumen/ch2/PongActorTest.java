package akk.rumen.ch2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.rumen.ch2.JavaPongActor;

/**
 * Created by wangdecheng on 04/04/2018.
 */
public class PongActorTest {

    ActorSystem system = ActorSystem.create();
    ActorRef actorRef = system.actorOf(Props.create(JavaPongActor.class));



}
