package akka.persist;

/**
 * Created by wangdecheng on 20/01/2018.
 */
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.persistence.SnapshotOffer;
import akka.persistence.UntypedPersistentActor;
import com.alibaba.fastjson.JSON;
import scala.Option;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import static java.util.Arrays.asList;

class Cmd implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String data;

    public Cmd(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}

class Evt implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String data;
    private final String uuid;

    public Evt(String data, String uuid) {
        this.data = data;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getData() {
        return data;
    }
}

class ExampleState implements Serializable {
    private static final long serialVersionUID = 1L;
    private final ArrayList<String> events;

    public ExampleState() {
        this(new ArrayList<String>());
    }

    public ExampleState(ArrayList<String> events) {
        this.events = events;
    }

    public ExampleState copy() {
        return new ExampleState(new ArrayList<String>(events));
    }

    public void update(Evt evt) {
        events.add(evt.getData());
    }

    public int size() {
        return events.size();
    }

    @Override
    public String toString() {
        return events.toString();
    }
}

public class ExamplePersistentActor extends UntypedPersistentActor {

    LoggingAdapter log = Logging.getLogger(getContext().system (), this );

    @Override
    public String persistenceId() { return "sample-id-1"; }

    private ExampleState state = new ExampleState();

    public int getNumEvents() {
        return state.size();
    }

    /**
     * Called on restart. Loads from Snapshot first, and then replays Journal Events to update state.
     * @param msg
     */
    @Override
    public void onReceiveRecover(Object msg) {
        log.info("onReceiveRecover: " + JSON.toJSONString(msg));
        if (msg instanceof Evt) {
            log.info("onReceiveRecover -- msg instanceof Event");
            log.info("event --- " + ((Evt) msg).getData());
            state.update((Evt) msg);
        } else if (msg instanceof SnapshotOffer) {
            log.info("onReceiveRecover -- msg instanceof SnapshotOffer");
            state = (ExampleState)((SnapshotOffer)msg).snapshot();
        } else {
            unhandled(msg);
        }
    }

    /**
     * Called on Command dispatch
     * @param msg
     */
    @Override
    public void onReceiveCommand(Object msg) {
        log.info("onReceiveCommand: " + JSON.toJSONString(msg));
        if (msg instanceof Cmd) {
            final String data = ((Cmd)msg).getData();

            // generate an event we will persist after being enriched with a uuid
            final Evt evt1 = new Evt(data + "-" + getNumEvents(), UUID.randomUUID().toString());
            final Evt evt2 = new Evt(data + "-" + (getNumEvents() + 1), UUID.randomUUID().toString());

            // persist event and THEN update the state of the processor
            persistAll(asList(evt1, evt2), evt -> {
                state.update(evt);
                if (evt.equals(evt2)) {
                    // broadcast event on eventstream 发布该事件
                    getContext().system().eventStream().publish(evt);
                }
            });
        } else if (msg.equals("snap")) {
            // IMPORTANT: create a copy of snapshot
            // because ExampleState is mutable !!!
            saveSnapshot(state.copy());
        } else if (msg.equals("print")) {
            System.out.println("state:  " + state);
        } else {
            unhandled(msg);
        }
    }

    @Override
    public void akka$actor$UnrestrictedStash$$super$preRestart(Throwable throwable, Option<Object> option) {
        System.out.println("akka$actor$UnrestrictedStash$$super$preRestart");
    }

    @Override
    public void akka$actor$UnrestrictedStash$$super$postStop() {
        System.out.println("akka$actor$UnrestrictedStash$$super$postStop");
    }
}

class EventHandler extends UntypedActor {


    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object msg ) throws Exception {
        log.info( "Handled Event: " + JSON.toJSONString(msg));
    }
}
