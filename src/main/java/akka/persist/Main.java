package akka.persist;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangdecheng on 20/01/2018.
 */
public class Main {
    public static final Logger log = LoggerFactory.getLogger(System.class);

    public static void main(String... args) throws Exception {

        final ActorSystem actorSystem = ActorSystem.create("actor-server");

        final ActorRef handler = actorSystem.actorOf(Props.create(EventHandler.class));
        // 订阅
        actorSystem.eventStream().subscribe(handler, Evt.class);

        Thread.sleep(5000);

        final ActorRef actorRef = actorSystem.actorOf(Props.create(ExamplePersistentActor.class), "eventsourcing-processor");

        actorRef.tell(new Cmd("CMD 1"), null);
        actorRef.tell(new Cmd("CMD 2"), null);
        actorRef.tell(new Cmd("CMD 3"), null);
        actorRef.tell("snap", null);//发送保存快照命令
        actorRef.tell(new Cmd("CMD 4"), null);
        actorRef.tell(new Cmd("CMD 5"), null);
        actorRef.tell("print", null);

        Thread.sleep(5000);

        log.info("Actor System Shutdown Starting...");

        actorSystem.shutdown();
    }
}
