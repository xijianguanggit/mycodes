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
public class CompanyActor extends UntypedActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().system(),this);
    private Ref.View<Integer> count = STM.newRef(100);
    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Coordinated){
            Coordinated coordinator = (Coordinated)o;
            int downCount = (int)coordinator.getMessage();
            STMMain.employeeActor.tell(coordinator.coordinate(downCount),getSelf());
            System.out.println("company===="+downCount);

            try{
                coordinator.atomic(()->{
                    if(count.get()<downCount){
                        throw new RuntimeException("余额不足");
                    }
                    STM.increment(count,-downCount);
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if("getCount".equals(o)){
            getSender().tell(count.get(),getSelf());
        }else{
            unhandled(o);
        }
    }
}
