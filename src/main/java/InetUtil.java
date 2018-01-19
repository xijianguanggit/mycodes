import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by wangdecheng on 17/01/2018.
 */
public class InetUtil {

    public static void main(String[] args){
        try {
            System.out.println(InetAddress.getLocalHost().getHostName());
            System.out.println(InetAddress.getLocalHost().getCanonicalHostName());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
