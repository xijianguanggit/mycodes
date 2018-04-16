package akka.rumen.ch2;

import java.io.Serializable;

/**
 * Created by wangdecheng on 04/04/2018.
 */
public class SetRequest implements Serializable {

    public final String key;
    public final String value;

    public SetRequest(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
