package akka.stm;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.transactor.Coordinated;
import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

/**
 * Created by wangdecheng on 19/01/2018.
 */
public class EmployeeActor extends UntypedActor{

    private final LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(),this);

    private Ref.View<Integer> count = STM.newRef(20);
    @Override
    public void onReceive(Object o) throws Exception {

        if(o instanceof Coordinated){
            Coordinated coordinated = (Coordinated) o;
            int upCount = (int)coordinated.getMessage();
            System.out.println("employee====="+upCount);

            coordinated.atomic(()->{
                STM.increment(count,upCount);
            });
        }else if("getCount".equals(o)){
            getSender().tell(count.get(),getSelf());
        }else{
            unhandled(o);
        }
    }
}
