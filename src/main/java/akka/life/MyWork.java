package akka.life;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by wangdecheng on 19/01/2018.
 */
public class MyWork extends UntypedActor {

    private LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    public static enum Msg {
        WORKING, DONE, CLOSE;
    }

    @Override
    public void preStart() {
        logger.info("myWork starting.");
    }

    @Override
    public void postStop() throws Exception {
        logger.info("myWork stoping..");
    }

    @Override
    public void onReceive(Object msg) {
        try {
            if (msg == Msg.WORKING) {
                logger.info("i am  working");
            } else if (msg == Msg.DONE) {
                logger.info("stop  working");
            } else if (msg == Msg.CLOSE) {
                logger.info("stop  close");
                getSender().tell(Msg.CLOSE, getSelf());
                getContext().stop(getSelf());
            } else {
                unhandled(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
