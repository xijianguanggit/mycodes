package akka.rumen.ch2;

import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by wangdecheng on 04/04/2018.
 */
public class Main {

    public static void main(String[] args){
        ActorSystem system = ActorSystem.create("akkademy");
        system.actorOf(Props.create(MyDBActor.class), "akkademy-db");
    }

}
