package akka.unmodifiable;

/**
 * Created by wangdecheng on 17/01/2018.
 */
import akka.actor.UntypedActor;
import com.alibaba.fastjson.JSONObject;

public class Greeter extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws InterruptedException {

        try {
            System.out.println("Greeter receive data:" + JSONObject.toJSONString(msg));
            getSender().tell("Greeter complete.", getSelf());//给发送至发送信息.
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
