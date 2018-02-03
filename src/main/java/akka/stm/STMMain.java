package akka.stm;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.transactor.Coordinated;
import akka.util.Timeout;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangdecheng on 19/01/2018.
 */
public class STMMain {
     static ActorRef companyActor;
     static ActorRef employeeActor;
     enum Test{}

    public static void main(String[] args) throws  Exception{
         Test.valueOf(null);
        ActorSystem actorSystem = ActorSystem.create("stm", ConfigFactory.load("akka.conf"));
        companyActor = actorSystem.actorOf(Props.create(CompanyActor.class),"CompanyActor");
        employeeActor = actorSystem.actorOf(Props.create(EmployeeActor.class),"EmployeeActor");
        Timeout timeout = new Timeout(1, TimeUnit.SECONDS);

        for(int i=0;i<23;i++){
            companyActor.tell(new Coordinated(i,timeout),ActorRef.noSender());
            Thread.sleep(200);

            int companyCount = (int) Await.result(Patterns.ask(companyActor, "getCount", timeout), timeout.duration());
            int employeeCount = (int) Await.result(Patterns.ask(employeeActor, "getCount", timeout), timeout.duration());

            System.out.println("companyCount = " + companyCount + ";employeeCount = " + employeeCount);
            System.out.println("-----------------------");
        }
    }
}
