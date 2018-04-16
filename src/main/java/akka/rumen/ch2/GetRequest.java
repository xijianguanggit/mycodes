package akka.rumen.ch2;

import java.io.Serializable;

/**
 * Created by wangdecheng on 04/04/2018.
 */
public class GetRequest implements Serializable {

    public final String key;

    public GetRequest(String key) {
        this.key = key;
    }
}
